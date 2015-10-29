package cc2.g6;

import cc2.sim.Point;
import cc2.sim.Dough;
import cc2.sim.Move;
import cc2.sim.Shape;

import java.util.*;

/**
 * Created by rbtying on 10/26/15.
 */
public class Player implements cc2.sim.Player {

    private static final int UNDECOMINO = 0;
    private static final int OCTOMINO = 1;
    private static final int PENTOMINO = 2;


    private int cutter_attempts[] = new int[3];
    private long tick = -1;
    private Set<Integer> attempted_octominoes = new HashSet<>();
    private Set<Integer> attempted_pentominoes = new HashSet<>();

    private Random rng = new Random(0);

    private Shape undecomino(Shape cutters[], Shape oppo_cutters[]) {
        // try to make an L
        // make most of the L using the first 9 points
        // *****
        // *
        // *
        // *
        // *
        // *
        Point p[] = new Point[11];
        p[0] = new Point(0, 0);

        for (int i = 1; i < 5; ++i) {
            p[2 * i] = new Point(0, i);
            p[2 * i - 1] = new Point(i, 0);
        }

        switch (cutter_attempts[UNDECOMINO]) {
            case 0:
                // symmetric L
                p[9] = new Point(0, 5);
                p[10] = new Point(5, 0);
                break;
            case 1:
                // long i
                p[9] = new Point(0, 5);
                p[10] = new Point(0, 6);
                break;
            case 2:
                // long j
                p[9] = new Point(5, 0);
                p[10] = new Point(6, 0);
                break;
            case 3:
                // center point and j
                p[9] = new Point(1, 1);
                p[10] = new Point(5, 0);
                break;
            case 4:
                // center point and i
                p[9] = new Point(1, 1);
                p[10] = new Point(0, 5);
                break;
            default:
                System.err.println("Should have only had five tries");
        }

        return new Shape(p);
    }

    private Shape octomino(Shape cutters[], Shape oppo_cutters[]) {
        // try to fit our undecomino
        System.out.println("Selecting octomino");
        Shape undec = cutters[0];
        assert (undec != null);

        int oct_id = Util.findArgMin(0, Util.ALL_OCTOMINOES.length, (idx) -> {
            // minimize total manhattan distance between shapes
            if (attempted_octominoes.contains(idx)) {
                return Integer.MAX_VALUE;
            }

            return -Util.evaluateTilingShape(undec, Util.getOctomino(idx), 11);
        });

        attempted_octominoes.add(oct_id);

        return Util.getOctomino(oct_id);
    }

    private Shape pentomino(Shape cutters[], Shape oppo_cutters[]) {
        // try to fit their undecomino
        System.out.println("Selecting pentomino");
        Shape undec = oppo_cutters[0];
        assert (undec != null);

        int pent_id = Util.findArgMin(0, Util.ALL_PENTOMINOES.length, (idx) -> {
            // minimize total manhattan distance between shapes
            if (attempted_pentominoes.contains(idx)) {
                return Integer.MAX_VALUE;
            }
            return -Util.evaluateTilingShape(undec, Util.getPentomino(idx), 11);
        });

        attempted_pentominoes.add(pent_id);

        return Util.getPentomino(pent_id);
    }

    @Override
    public Shape cutter(int length, Shape[] your_cutters, Shape[] oppo_cutters) {
        Shape s = null;
        switch (length) {
            case 11:
                s = undecomino(your_cutters, oppo_cutters);
                ++cutter_attempts[UNDECOMINO];
                break;
            case 8:
                s = octomino(your_cutters, oppo_cutters);
                ++cutter_attempts[OCTOMINO];
                break;
            case 5:
                s = pentomino(your_cutters, oppo_cutters);
                ++cutter_attempts[PENTOMINO];
                break;
            default:
                System.err.println("Unexpected length!");
        }
        return s;
    }

    @Override
    public Move cut(Dough dough, Shape[] your_cutters, Shape[] oppo_cutters) {
        ++tick;
        if (dough.uncut()) {
            // can only use min
            int si = Util.findArgMin(0, your_cutters.length, (i) -> your_cutters[i].size());

            for (Move m : Util.getValidMoves(dough, your_cutters[si], si)) {
                if (m.point.i > 0 && m.point.j > 0) {
                    return m;
                }
            }
        } else {
            Integer si_idx[] = new Integer[your_cutters.length];
            for (int i = 0; i < si_idx.length; ++i) {
                si_idx[i] = i;
            }
            // sorted descending
            Arrays.sort(si_idx, (a, b) -> Double.compare(your_cutters[b].size(), your_cutters[a].size()));

            for (int si : si_idx) {
                List<Move> moves = Util.getValidMoves(dough, your_cutters[si], si);

                // just return the first one
                if (!moves.isEmpty()) {
                    return moves.get(0);
                }
            }
        }
        return null;
    }
}
