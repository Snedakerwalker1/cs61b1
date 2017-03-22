package hw3.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] board;
    private int size;
    private int BLANK = 0;

    public Board(int[][] tiles) {
        size = tiles[0].length;
        board = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                board[i][j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i > size - 1 || i < 0 || j < 0 || j > size - 1) {
            throw new IndexOutOfBoundsException("Invaled index");
        }
        return board[i][j];
    }
    public int size() {
        return this.size;
    }

    /**
     * Graciously given by the amazing CS61B teaching staff
     * Thanks Hug.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int returnVal = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if ((i == this.size - 1) && (j == this.size - 1)) {
                    returnVal += 0;
                } else if (board[i][j] != this.size * i + j + 1) {
                    returnVal += 1;
                }
            }
        }
        return returnVal;
    }

    public int manhattan() {
        int returnVal = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                returnVal += manhattanCalc(i, j);
            }
        }
        return returnVal;
    }

    private int manhattanCalc(int i, int j) {
        int val = tileAt(i, j);
        if (val == 0) {
            return 0;
        }
        if (val == this.size * i + j + 1) {
            return 0;
        }
        return Math.abs(val / this.size - i) + Math.abs(val % this.size - j - 1);
    }

    @Override
    public int estimatedDistanceToGoal() {
        return this.manhattan();
    }

    @Override
    public boolean isGoal() {
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (i != this.size - 1 && j != this.size - 1) {
                    if (!(this.board[i][j] == i * this.size + j + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object y) {
        if (y.getClass().equals(this.getClass())) {
            Board newY = (Board) y;
            for (int i = 0; i < size; i += 1) {
                for (int j = 0; j < size; j += 1) {
                    if (this.tileAt(i, j) != newY.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }



    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
