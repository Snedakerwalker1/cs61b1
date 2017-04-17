import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.
    QuadTree imagelist;

    private class QuadTree {
        int node;
        int deapth;
        double d_lon;
        double d_lat;
        double ul_lat;
        double ul_lon;
        double lr_lat;
        double lr_lon;
        String root;
        QuadTree child1;
        QuadTree child2;
        QuadTree child3;
        QuadTree child4;
        double[] dis_lon = new double[8];
        double[] dis_lat = new double[8];

        QuadTree(String root, int height, int node, int deapth,
                 double ul_lon, double ul_lat, double lr_lon, double lr_lat) {
            if (height > 7) {
                return;
            }
            this.deapth = deapth;
            this.lr_lat = lr_lat;
            this.lr_lon = lr_lon;
            this.ul_lat = ul_lat;
            this.ul_lon = ul_lon;
            this.node = node;
            this.root = root;
            this.d_lon = (ul_lon - lr_lon) / 2;
            this.d_lat = (ul_lat - lr_lat) / 2;
            this.dis_lon[deapth] = d_lon;
            this.dis_lat[deapth] = d_lat;
            child1 = new QuadTree(root, height + 1, node * 10 + 1, deapth + 1,
                    ul_lon, ul_lat, lr_lon - d_lon, lr_lat + d_lat);
            child2 = new QuadTree(root, height + 1, node * 10 + 2, deapth + 1,
                    ul_lon + d_lon, ul_lat, lr_lon, lr_lat + d_lat);
            child3 = new QuadTree(root, height + 1, node * 10 + 3, deapth + 1,
                    ul_lon, ul_lat - d_lat, lr_lon - d_lon, lr_lat);
            child4 = new QuadTree(root, height + 1, node * 10 + 4, deapth + 1,
                    ul_lon + d_lon, ul_lat - d_lat, lr_lon , lr_lat);
        }
         QuadTree QuadNode(QuadTree qt, double ul_lon, double ul_lat, double lr_lon, double lr_lat) {
            if (qt.ul_lon == ul_lon && qt.ul_lat == ul_lat
                    && qt.lr_lat == lr_lat && qt.lr_lon == lr_lon) {
                return qt;
            }
            if (qt.child1 != null) {
                if (qt.child1.ul_lon >= ul_lon && qt.child1.ul_lat >= ul_lat &&
                        qt.child1.lr_lat <= lr_lat && qt.child1.lr_lon <= lr_lon) {
                    return QuadNode(qt.child1, ul_lon, ul_lat, lr_lon, lr_lat);
                }
                if (qt.child2.ul_lon >= ul_lon && qt.child2.ul_lat >= ul_lat &&
                        qt.child2.lr_lat <= lr_lat && qt.child2.lr_lon <= lr_lon) {
                    return QuadNode(qt.child2, ul_lon, ul_lat, lr_lon, lr_lat);
                }
                if (qt.child3.ul_lon >= ul_lon && qt.child3.ul_lat >= ul_lat &&
                        qt.child3.lr_lat <= lr_lat && qt.child3.lr_lon <= lr_lon) {
                    return QuadNode(qt.child3, ul_lon, ul_lat, lr_lon, lr_lat);
                }
                if (qt.child4.ul_lon >= ul_lon && qt.child4.ul_lat >= ul_lat &&
                        qt.child4.lr_lat <= lr_lat && qt.child4.lr_lon <= lr_lon) {
                    return QuadNode(qt.child4, ul_lon, ul_lat, lr_lon, lr_lat);
                }
            }
            return this;
        }
        QuadTree QuadNodeLeftAprrox(QuadTree qt, double ul_lon, double ul_lat,
                          double lr_lon, double lr_lat, int depth) {
            if (qt.ul_lon >= ul_lon && qt.ul_lat >= ul_lat &&
                qt.lr_lon <= ul_lon && qt.lr_lat <= ul_lat && depth == qt.deapth) {
                return qt;
            }
            if (qt.child1 != null) {
                if (qt.child1.ul_lon >= ul_lon && qt.child1.ul_lat >= ul_lat &&
                        qt.child1.lr_lat <= lr_lat && qt.child1.lr_lon <= lr_lon) {
                    return QuadNodeLeftAprrox(qt.child1, ul_lon, ul_lat, lr_lon, lr_lat, depth);
                }
                if (qt.child2.ul_lon >= ul_lon && qt.child2.ul_lat >= ul_lat &&
                        qt.child2.lr_lat <= lr_lat && qt.child2.lr_lon <= lr_lon) {
                    return QuadNodeLeftAprrox(qt.child2, ul_lon, ul_lat, lr_lon, lr_lat, depth);
                }
                if (qt.child3.ul_lon >= ul_lon && qt.child3.ul_lat >= ul_lat &&
                        qt.child3.lr_lat <= lr_lat && qt.child3.lr_lon <= lr_lon) {
                    return QuadNodeLeftAprrox(qt.child3, ul_lon, ul_lat, lr_lon, lr_lat, depth);
                }
                if (qt.child4.ul_lon >= ul_lon && qt.child4.ul_lat >= ul_lat &&
                        qt.child4.lr_lat <= lr_lat && qt.child4.lr_lon <= lr_lon) {
                    return QuadNodeLeftAprrox(qt.child4, ul_lon, ul_lat, lr_lon, lr_lat, depth);
                }
            }
            return this;
        }

        int get_size(double dpp) {
            for (int i = 0; i < this.dis_lat.length; i += 1) {
                if (i + 1 == dis_lat.length) {
                    return i;
                }
                if (dis_lat[i] > dpp && dis_lat[i + 1] < dpp) {
                    return i + 1;
                }
                if (dis_lat[i] == dpp) {
                    return i;
                }
                if (dis_lat[i] < dpp && i == 0) {
                    return  0;
                }
            }
            return 0;
        }

        Map<String, Object> Neighbors(double ul_lon, double ul_lat,
                             double lr_lon, double lr_lat, double w, double h) {
            Map<String, Object> results = new HashMap<>();
            String[][] array;
            double LonDDP = 256 * (lr_lon - ul_lon) / w;
            double LatDPP = 256 * (lr_lat - ul_lat) / h;
            Double height = (h / 256);
            int ht = height.intValue();
            Double width = (w / 256);
            int wd = width.intValue();
            array = new String[wd][ht];
            QuadTree leftApprox = QuadNodeLeftAprrox(this, ul_lon, ul_lat, lr_lon,
                    lr_lat, get_size(LatDPP));
            QuadTree arrtree = leftApprox;
            QuadTree temptree = arrtree;
            for (int i = 0; i < wd; i += 1) {
                temptree = arrtree;
                for (int j = 0; j < ht; j += 1) {
                    array[i][j] = temptree.root + temptree.node;
                    temptree = QuadNode(this, temptree.ul_lon, temptree.ul_lat - temptree.d_lat,
                            temptree.lr_lon, temptree.lr_lat - temptree.d_lat);
                }
                arrtree = QuadNode(this, arrtree.ul_lon - arrtree.d_lon, arrtree.ul_lat,
                        arrtree.lr_lon - arrtree.d_lon, arrtree.lr_lat);
            }
            results.put("render_grid", array);
            results.put("raster_ul_lon", leftApprox.ul_lon);
            results.put("raster_ul_lat", leftApprox.ul_lat);
            results.put("raster_lr_lon", temptree.lr_lon);
            results.put("raster_lr_lat", temptree.lr_lat);
            results.put("depth", get_size(LatDPP));
            return results;
        }
    }

    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        double Y_ROOT_ULLAT = 37.892195547244356, X_ROOT_ULLON = -122.2998046875,
                Y_ROOT_LRLAT = 37.82280243352756, X_ROOT_LRLON = -122.2119140625;
        this.imagelist = new QuadTree(imgRoot, 0, 0, 0,
                X_ROOT_ULLON, Y_ROOT_ULLAT, X_ROOT_LRLON, Y_ROOT_LRLAT);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>Has dimensions of at least w by h, where w and h are the user viewport width
     *         and height.</li>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = imagelist.Neighbors(params.get("ullon"), params.get("ullat"),
                params.get("lrlon"), params.get("lrlat"), params.get("w"), params.get("h"));
        results.put("query_success", true);
        return results;
    }

    public static void main(String[] args) {
        Rasterer rast = new Rasterer("img/");
        System.out.print(rast.imagelist.child1.node);
        System.out.println(rast.imagelist.child1.child1.child1.child1.child1.child1.node);
        System.out.println(rast.imagelist.child1.child2.node);
        System.out.println(rast.imagelist.child3.child2.node);
        System.out.println(rast.imagelist.node);
        double d_lat = (37.892195547244356 - 37.82280243352756) / 2;
        double d_lon = (-122.2998046875 + 122.2119140625) / 2;
        double d_lon2 = (-122.2998046875 - (-122.2119140625 - d_lon)) / 2;
        double d_lat2 = (37.892195547244356 - (37.82280243352756 + d_lat)) / 2;
        System.out.println(d_lat);
        System.out.println(d_lon);
        System.out.println(rast.imagelist.QuadNode(rast.imagelist,
                -122.2998046875 + d_lon2, 37.892195547244356 - d_lat2,
                -122.2119140625 - d_lon, 37.82280243352756 + d_lat));

    }

}
