import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Arrays;


/**
 * Created by wsnedaker on 4/23/2017.
 */
public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private boolean[][] visited;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
        visited = new boolean[height][width];
    }

    public Picture picture() {
        // current picture
        return new Picture(this.picture);
    }
    public int width() {
        // width of current picture
        return this.width;
    }
    public int height() {
        // height of current picture
        return this.height;
    }
    public double energy(int x, int y) {
        // energy of pixel at column x and row y
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new java.lang.IndexOutOfBoundsException("Not within length");
        }
        double rx, gx, bx, ry, gy, by;
        if (x == 0) {
            if (this.width - 1 != 0) {
                rx = this.picture.get(x + 1, y).getRed()
                        - this.picture.get(width - 1, y).getRed();
                gx = this.picture.get(x + 1, y).getGreen()
                        - this.picture.get(width - 1, y).getGreen();
                bx = this.picture.get(x + 1, y).getBlue()
                        - this.picture.get(width - 1, y).getBlue();
            } else {
                rx = 0;
                gx = 0;
                bx = 0;
            }
        } else if (x == width - 1) {
            if (this.width - 1 != 0) {
                rx = this.picture.get(0, y).getRed()
                        - this.picture.get(x - 1, y).getRed();
                gx = this.picture.get(0, y).getGreen()
                        - this.picture.get(x - 1, y).getGreen();
                bx = this.picture.get(0, y).getBlue()
                        - this.picture.get(x - 1, y).getBlue();
            } else {
                rx = 0;
                gx = 0;
                bx = 0;
            }
        } else {
            rx = this.picture.get(x + 1, y).getRed()
                    - this.picture.get(x - 1, y).getRed();
            gx = this.picture.get(x + 1, y).getGreen()
                    - this.picture.get(x - 1, y).getGreen();
            bx = this.picture.get(x + 1, y).getBlue()
                    - this.picture.get(x - 1, y).getBlue();
        }
        if (y == 0) {
            if (this.height - 1 != 0) {
                ry = this.picture.get(x, y + 1).getRed()
                        - this.picture.get(x, height - 1).getRed();
                gy = this.picture.get(x, y + 1).getGreen()
                        - this.picture.get(x, height - 1).getGreen();
                by = this.picture.get(x, y + 1).getBlue()
                        - this.picture.get(x, height - 1).getBlue();
            } else {
                ry = 0;
                gy = 0;
                by = 0;
            }
        } else if (y == height - 1) {
            if (this.height - 1 != 0) {
                ry = this.picture.get(x, 0).getRed()
                        - this.picture.get(x, y - 1).getRed();
                gy = this.picture.get(x, 0).getGreen()
                        - this.picture.get(x, y - 1).getGreen();
                by = this.picture.get(x, 0).getBlue()
                        - this.picture.get(x, y - 1).getBlue();
            } else {
                ry = 0;
                gy = 0;
                by = 0;
            }
        } else {
            ry = this.picture.get(x, y + 1).getRed()
                    - this.picture.get(x, y - 1).getRed();
            gy = this.picture.get(x, y + 1).getGreen()
                    - this.picture.get(x, y - 1).getGreen();
            by = this.picture.get(x, y + 1).getBlue()
                    - this.picture.get(x, y - 1).getBlue();
        }
        return (rx * rx) + (gx * gx) + (bx * bx) + (ry * ry) + (gy * gy) + (by * by);
    }

    public int[] findHorizontalSeam() {
        // sequence of indices for horizontal seam
        int[] retWidth = new int[this.width];
        Picture pic = new Picture(this.height, this.width);
        for (int i = 0; i < this.width; i += 1) {
            for (int j = 0; j < this.height; j += 1) {
                pic.set(j, i, this.picture.get(i, j));
            }
        }
        SeamCarver carver = new SeamCarver(pic);
        return carver.findVerticalSeam();
    }
    public int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        for (int i = 0; i < this.width; i += 1) {
            int[] verticleSeam = new int[this.height];
            SearchNode sn = new SearchNode(i, 0, energy(i, 0), null, verticleSeam);
            this.visited[0][i] = true;
            minPQ.insert(sn);
        }
        while (true) {
            if (minPQ.min().currentY + 1 == this.height) {
                break;
            }
            SearchNode node = minPQ.delMin();
            for (int i : neighbors(node.currentX, node.currentY)) {
                if (!this.visited[node.currentY + 1][i]) {
                    SearchNode sn = new SearchNode(i, node.currentY + 1,
                            energy(i, node.currentY + 1)
                            + node.distance, node, node.array);
                    minPQ.insert(sn);
                    this.visited[node.currentY + 1][i] = true;
                }
            }
        }
        SearchNode node = minPQ.min();
        //while (node.last != null) {
          //  verticleSeam[node.currentY] = node.currentX;
            //node = node.last;
        //}
        //verticleSeam[0] = node.currentX;
        this.visited = new boolean[height][width];
        return node.array;
    }
    private class SearchNode implements Comparable<SearchNode> {
        int currentX;
        int currentY;
        SearchNode last;
        double distance;
        int[] array;
        SearchNode(int x, int y, double dist, SearchNode last, int[] arr) {
            this.currentX = x;
            this.currentY = y;
            this.distance = dist;
            this.last = last;
            this.array = Arrays.copyOf(arr, height);
            this.array[y] = x;
        }
        @Override
        public int compareTo(SearchNode sn) {
            Double comp = this.distance - sn.distance;
            return comp.intValue();
        }
    }

    private int[] neighbors(int x, int y) {
        if (y == this.height - 1) {
            return null;
        }
        int[] rets;
        if (0 == this.width - 1) {
            rets = new int[1];
            rets[0] = 0;
        } else if (x == 0) {
            rets = new int[2];
            rets[0] = 0;
            rets[1] = 1;
        } else if (x == this.width - 1) {
            rets = new int[2];
            rets[0] = x;
            rets[1] = x - 1;
        } else {
            rets = new int[3];
            rets[0] = x;
            rets[1] = x - 1;
            rets[2] = x + 1;
        }
        return rets;
    }
    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from picture
        if (seam.length != this.width) {
            throw new java.lang.IllegalArgumentException("not correct size");
        }
        for (int i = 0; i + 1 < seam.length; i += 1) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new java.lang.IllegalArgumentException("not correct size");
            }
        }
        this.picture = SeamRemover.removeHorizontalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }
    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from picture
        if (seam.length != this.height) {
            throw new java.lang.IllegalArgumentException("not correct size");
        }
        for (int i = 0; i + 1 < seam.length; i += 1) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new java.lang.IllegalArgumentException("not correct size");
            }
        }
        this.picture = SeamRemover.removeVerticalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }
}
