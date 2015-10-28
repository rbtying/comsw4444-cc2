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

    // it turns out there's only 363 free octominoes...
    private static final int[][] all_octominoes = {
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 1, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 1, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 1, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 1, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 1, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 0, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 1, 1, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 1, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 1, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 1, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 2, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 2, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 1, 5, 1, 6},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 1, 1, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 1, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 1, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 1, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 1, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 2, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 2, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 2, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 2, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 3, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 3, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 1, 4, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 0, 2, 0, 3, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 1, 2, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 1, 2, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 1, 2, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 1, 3, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 1, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 2, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 2, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 2, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 3, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 4, 1, 5, 1, 6},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 4, 1, 5, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 4, 1, 5, 2, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 1, 4, 2, 4, 2, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 5, 1, 3, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 2, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 2, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 2, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 3, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 3, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 3, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 2, 0, 3, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 0, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 0, 3, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 2, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 1, 4, 2, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 2, 0, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 2, 0, 3, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 2, 0, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 2, 0, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 2, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 2, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 2, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 2, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 1, 4, 2, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 0, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 0, 2, 1, 3, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 0, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 3, 0, 3, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1, 2, 1, 3, 1, 4, 1},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 1, 4, 1, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 2, 2, 2, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 1, 5, 1, 6},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 1, 5, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 1, 5, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 1, 5, 2, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 4, 2, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 4, 3, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 2, 2, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 2, 3, 2, 4, 2, 5},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 3, 2, 3, 3, 3, 3, 4},
            {0, 0, 0, 1, 0, 2, 0, 4, 0, 5, 1, 2, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 4, 1, 0, 1, 2, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 2, 0, 4, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 2, 0, 4, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 0, 4, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 0},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 0, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 3, 0, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 3, 1, 4, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 1, 4, 2, 0},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 0, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 0, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 1, 3, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 0, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 0, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 1, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 2, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 1, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 3, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 2, 1, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 1, 2, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 2, 2, 3, 0},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 3, 0, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 3, 1, 4, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 0, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 0, 3, 1, 4, 0},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 0, 3, 1, 4, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 1, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 1, 4, 0, 4, 1},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 1, 5, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 1, 5, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 1, 5, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 1, 5, 2, 5},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 2, 2, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 2, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 4, 2, 5},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 1, 4, 2, 4, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 0, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 1, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 2, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 2, 3, 2, 4, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 3, 2, 4, 2, 5},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3, 2, 3, 3, 3, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 1, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 1, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 2, 4, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 3, 1, 3, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 3, 2, 3, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 3, 2, 4, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 3, 2, 3, 3, 3, 4},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 3, 2, 3, 3, 4, 2},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 3, 2, 3, 3, 4, 3},
            {0, 0, 0, 1, 0, 2, 1, 2, 2, 2, 3, 2, 4, 2, 4, 3},
            {0, 0, 0, 1, 0, 3, 0, 4, 1, 1, 1, 2, 1, 3, 2, 1},
            {0, 0, 0, 1, 0, 3, 0, 4, 1, 1, 1, 2, 1, 3, 2, 2},
            {0, 0, 0, 1, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1},
            {0, 0, 0, 1, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 2, 0, 2, 1},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2},
            {0, 0, 0, 1, 0, 3, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 0, 4, 0, 5, 1, 1, 1, 2, 1, 3, 1, 4},
            {0, 0, 0, 1, 0, 4, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 0, 0, 1, 0, 4, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 0, 4, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 4},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 2, 3},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2, 2, 3},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 5},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 0, 2, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1, 2, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1, 2, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1, 2, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2, 2, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2, 2, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 1, 4, 2, 4, 2, 5},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 0, 2, 1, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 2, 3, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 3, 0, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 2, 3, 1, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2, 4, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 1, 3, 2, 3, 3, 3, 3, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 2, 1, 2, 2, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 2, 1, 3, 1, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 2, 1, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 3, 0, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 2, 2, 3, 2, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 3, 0, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 3, 1, 3, 2, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 3, 1, 4, 0, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 3, 1, 4, 1, 4, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1, 3, 1, 4, 1, 5, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 1, 3, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 2, 4, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 1, 3, 2, 4, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 2, 3, 3, 4, 2},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 2, 4, 2, 4, 3},
            {0, 0, 0, 1, 1, 1, 1, 3, 1, 4, 2, 1, 2, 2, 2, 3},
            {0, 0, 0, 1, 1, 1, 1, 3, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 0, 0, 1, 1, 1, 1, 3, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 3},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 3, 1, 3, 2, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 0, 2, 1, 3, 1, 4, 1, 4, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 2, 4, 3, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 2, 4, 3, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 2, 4, 3, 3},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 2, 4, 3, 4},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 3, 1, 3, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 2, 3, 3, 2, 4, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 0, 3, 1, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 1, 3, 2, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 1, 4, 0, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 1, 4, 1, 5, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 2, 3, 3, 4, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2, 3, 2, 4, 2, 4, 3},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 0, 3, 1, 3, 2, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 1, 3, 2, 3, 3, 4, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 1, 3, 2, 3, 3, 4, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 1, 3, 2, 4, 1, 5, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 4, 2, 5, 1},
            {0, 0, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 5, 2},
            {0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 3, 1, 4, 2, 1},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 3, 1},
            {0, 1, 0, 2, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 2},
            {0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 1, 0, 2, 1, 0, 1, 1, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 1},
            {0, 1, 0, 2, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 1, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1},
            {0, 1, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 1},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 3},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 4},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 1, 3, 1},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 2, 3, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 2, 3, 3, 3},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2, 3, 1},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 1, 2, 2, 3, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2, 3, 2, 4, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 1, 3, 2, 3, 2, 4, 3, 3},
            {0, 1, 1, 0, 1, 1, 1, 2, 2, 1, 2, 2, 2, 3, 3, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4, 3, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 2, 4, 3, 3},
            {0, 1, 1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 2, 4, 2},
            {0, 1, 1, 1, 1, 2, 1, 3, 2, 0, 2, 1, 2, 2, 3, 2},
            {0, 1, 1, 1, 1, 2, 1, 3, 2, 0, 2, 1, 3, 1, 4, 1},
            {0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 1, 4, 1},
            {0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 2, 3, 3, 2, 4, 2},
            {0, 1, 1, 1, 2, 0, 2, 1, 2, 2, 3, 1, 4, 1, 5, 1},
            {0, 1, 1, 1, 2, 0, 2, 1, 3, 1, 3, 2, 4, 1, 5, 1},
    };

    // and only 12 pentominoes
    private static final int all_pentominoes[][] = {
            {0, 0, 0, 1, 0, 2, 0, 3, 0, 4},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 0},
            {0, 0, 0, 1, 0, 2, 0, 3, 1, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 1},
            {0, 0, 0, 1, 0, 2, 1, 0, 1, 2},
            {0, 0, 0, 1, 0, 2, 1, 0, 2, 0},
            {0, 0, 0, 1, 0, 2, 1, 1, 2, 1},
            {0, 0, 0, 1, 0, 2, 1, 2, 1, 3},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 1},
            {0, 0, 0, 1, 1, 1, 1, 2, 2, 2},
            {0, 0, 0, 1, 1, 1, 2, 1, 2, 2},
            {0, 1, 1, 0, 1, 1, 1, 2, 2, 1},
    };


    private int cutter_attempts[] = new int[3];
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

    private Shape getOctomino(int idx) {
        int coord_list[] = all_octominoes[idx];

        Point p[] = new Point[8];

        for (int i = 0; i < 8; ++i) {
            p[i] = new Point(coord_list[2 * i], coord_list[2 * i + 1]);
        }

        return new Shape(p);
    }

    private Shape octomino(Shape cutters[], Shape oppo_cutters[]) {
        // try to fit our undecomino
        System.out.println("Selecting octomino");
        Shape undec = cutters[0];
        assert (undec != null);

        boolean space[][] = new boolean[22][22];
        for (int i = 0; i < 22; ++i) {
            for (int j = 0; j < 22; ++j) {
                space[i][j] = false;
            }
        }

        Point dim[] = Util.dimensions(undec);
        int w = dim[0].i;
        int h = dim[0].j;

        int woffset = (22 - w) / 2;
        int hoffset = (22 - h) / 2;

        // place undecomino
        for (Point p : undec) {
            space[p.i + woffset][p.j + hoffset] = true;
        }

        int oct_id = Util.findArgMin(0, all_octominoes.length, (idx) -> {
            // minimize total manhattan distance between shapes
            // no, this doesn't make that much sense.
            // I got lazy OK.

            // making a copy of the grid
            // boolean grid[][] = Arrays.stream(space).map(boolean[]::clone).toArray(boolean[][]::new);

            if (attempted_octominoes.contains(idx)) {
                return Integer.MAX_VALUE;
            }

            Shape oct[] = getOctomino(idx).rotations();

            return Util.findMin(0, oct.length, (o) -> {
                int c = 0;
                for (Point p : oct[o]) {
                    for (Point p2 : undec) {
                        c += Math.abs(p.i - p2.i);
                        c += Math.abs(p.j - p2.j);
                    }
                }
                return c;
            });
        });

        attempted_octominoes.add(oct_id);

        return getOctomino(oct_id);
    }

    private Shape getPentomino(int idx) {
        int coord_list[] = all_pentominoes[idx];

        Point p[] = new Point[5];

        for (int i = 0; i < 5; ++i) {
            p[i] = new Point(coord_list[2 * i], coord_list[2 * i + 1]);
        }

        return new Shape(p);
    }

    private Shape pentomino(Shape cutters[], Shape oppo_cutters[]) {
        // try to fit their undecomino
        System.out.println("Selecting pentomino");
        Shape undec = oppo_cutters[0];
        assert (undec != null);

        boolean space[][] = new boolean[22][22];
        for (int i = 0; i < 22; ++i) {
            for (int j = 0; j < 22; ++j) {
                space[i][j] = false;
            }
        }

        Point dim[] = Util.dimensions(undec);
        int w = dim[0].i;
        int h = dim[0].j;

        int woffset = (22 - w) / 2;
        int hoffset = (22 - h) / 2;

        // place undecomino
        for (Point p : undec) {
            space[p.i + woffset][p.j + hoffset] = true;
        }

        int pent_id = Util.findArgMin(0, all_pentominoes.length, (idx) -> {
            // minimize total manhattan distance between shapes
            // no, this doesn't make that much sense.
            // I got lazy OK.

            // making a copy of the grid
            // boolean grid[][] = Arrays.stream(space).map(boolean[]::clone).toArray(boolean[][]::new);

            if (attempted_pentominoes.contains(idx)) {
                return Integer.MAX_VALUE;
            }

            Shape pent[] = getPentomino(idx).rotations();

            return Util.findMin(0, pent.length, (o) -> {
                int c = 0;
                for (Point p : pent[o]) {
                    for (Point p2 : undec) {
                        c += Math.abs(p.i - p2.i);
                        c += Math.abs(p.j - p2.j);
                    }
                }
                return c;
            });
        });

        attempted_pentominoes.add(pent_id);

        return getPentomino(pent_id);
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

    public List<Move> valid_moves(Dough d, Shape c, int si) {
        // find all valid cuts

        List<Move> moves = new ArrayList<>();
        if (c == null) {
            return moves;
        }

        Shape[] rotations = c.rotations();
        for (int i = 0; i < d.side(); ++i) {
            for (int j = 0; j < d.side(); ++j) {
                Point p = new Point(i, j);
                for (int ri = 0; ri < rotations.length; ++ri) {
                    Shape s = rotations[ri];
                    if (d.cuts(s, p)) {
                        moves.add(new Move(si, ri, p));
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public Move cut(Dough dough, Shape[] your_cutters, Shape[] oppo_cutters) {
        if (dough.uncut()) {
            // can only use min
            int si = Util.findArgMin(0, your_cutters.length, (i) -> your_cutters[i].size());

            System.out.println("Placing first cut :: Dough is uncut");

            for (Move m : valid_moves(dough, your_cutters[si], si)) {
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
                List<Move> moves = valid_moves(dough, your_cutters[si], si);

                // just return the first one
                if (!moves.isEmpty()) {
                    return moves.get(0);
                }
            }
        }
        return null;
    }
}
