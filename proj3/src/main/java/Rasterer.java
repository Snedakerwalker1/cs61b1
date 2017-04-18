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
    double[] depth_DPP;

    private class QuadTree {
        int node;
        int deapth;
        double dlon;
        double dlat;
        double ullat;
        double ullon;
        double lrlat;
        double lrlon;
        double LonDPP;
        String root;
        QuadTree child1;
        QuadTree child2;
        QuadTree child3;
        QuadTree child4;
        double[] dislon = new double[9];
        double[] dislat = new double[9];
        double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

        QuadTree(String root, int height, int node, int deapth,
                 double ullon, double ullat, double lrlon, double lrlat) {
            if (height > 7) {
                return;
            }
            this.deapth = deapth;
            this.lrlat = lrlat;
            this.lrlon = lrlon;
            this.ullat = ullat;
            this.ullon = ullon;
            this.LonDPP = (lrlon - ullon) / 256;
            this.node = node;
            this.root = root;
            this.dlon = (lrlon - ullon) / 2;
            this.dlat = (ullat - lrlat) / 2;
            this.dislon[deapth] = dlon;
            this.dislat[deapth] = dlat;
            child1 = new QuadTree(root, height + 1, node * 10 + 1, deapth + 1,
                    ullon, ullat, lrlon - dlon, lrlat + dlat);
            child2 = new QuadTree(root, height + 1, node * 10 + 2, deapth + 1,
                    ullon + dlon, ullat, lrlon, lrlat + dlat);
            child3 = new QuadTree(root, height + 1, node * 10 + 3, deapth + 1,
                    ullon, ullat - dlat, lrlon - dlon, lrlat);
            child4 = new QuadTree(root, height + 1, node * 10 + 4, deapth + 1,
                    ullon + dlon, ullat - dlat, lrlon, lrlat);
        }
        QuadTree quadNode(QuadTree qt, double ullon1, double ullat1,
                          double lrlon1, double lrlat1, int depth) {
            if (qt.lrlat == lrlat1 && ullat1 == qt.ullat
                    && qt.ullon == ullon1 && lrlon1 == qt.lrlon && qt.deapth == depth) {
                return qt;
            }
            if (qt.child1 != null) {
                if (qt.child1.lrlat < ullat1 && ullat1 <= qt.child1.ullat
                        && qt.child1.ullon <= ullon1 && ullon1 < qt.child1.lrlon) {
                    return quadNode(qt.child1, ullon1, ullat1, lrlon1, lrlat1, depth);
                }
                if (qt.child2.lrlat < ullat1 && ullat1 <= qt.child2.ullat
                        && qt.child2.ullon <= ullon1 && ullon1 < qt.child2.lrlon) {
                    return quadNode(qt.child2, ullon1, ullat1, lrlon1, lrlat1, depth);
                }
                if (qt.child3.lrlat < ullat1 && ullat1 <= qt.child3.ullat
                        && qt.child3.ullon <= ullon1 && ullon1 < qt.child3.lrlon) {
                    return quadNode(qt.child3, ullon1, ullat1, lrlon1, lrlat1, depth);
                }
                if (qt.child4.lrlat < ullat1 && ullat1 <= qt.child4.ullat
                        && qt.child4.ullon <= ullon1 && ullon1 < qt.child4.lrlon) {
                    return quadNode(qt.child4, ullon1, ullat1, lrlon1, lrlat1, depth);
                }
            }
            return qt;
        }
        QuadTree quadNodeLeftAprrox(QuadTree qt, double ullon1, double ullat1, int depth) {
            if (qt.lrlat < ullat1 && ullat1 <= qt.ullat && qt.ullon <= ullon1
                    && ullon1 < qt.lrlon && qt.deapth == depth) {
                return qt;
            }
            if (qt.child1 != null) {
                if (qt.child1.lrlat < ullat1 && ullat1 <= qt.child1.ullat
                        && qt.child1.ullon <= ullon1 && ullon1 < qt.child1.lrlon) {
                    return quadNodeLeftAprrox(qt.child1, ullon1, ullat1, depth);
                }
                if (qt.child2.lrlat < ullat1 && ullat1 <= qt.child2.ullat
                        && qt.child2.ullon <= ullon1 && ullon1 < qt.child2.lrlon) {
                    return quadNodeLeftAprrox(qt.child2, ullon1, ullat1, depth);
                }
                if (qt.child3.lrlat < ullat1 && ullat1 <= qt.child3.ullat
                        && qt.child3.ullon <= ullon1 && ullon1 < qt.child3.lrlon) {
                    return quadNodeLeftAprrox(qt.child3, ullon1, ullat1, depth);
                }
                if (qt.child4.lrlat < ullat1 && ullat1 <= qt.child4.ullat
                        && qt.child4.ullon <= ullon1 && ullon1 < qt.child4.lrlon) {
                    return quadNodeLeftAprrox(qt.child4, ullon1, ullat1, depth);
                }
            }
            return qt;
        }
        QuadTree quadNodeRightAprrox(QuadTree qt, double lrlon1, double lrlat1, int depth) {
            if (qt.lrlat <= lrlat1 && lrlat1 < qt.ullat && qt.ullon < lrlon1
                    && lrlon1 <= qt.lrlon && qt.deapth == depth) {
                return qt;
            }
            if (qt.child1 != null) {
                if (qt.child1.lrlat <= lrlat1 && lrlat1 < qt.child1.ullat
                        && qt.child1.ullon < lrlon1 && lrlon1 <= qt.child1.lrlon) {
                    return quadNodeLeftAprrox(qt.child1, lrlon1, lrlat1, depth);
                }
                if (qt.child2.lrlat <= lrlat1 && lrlat1 < qt.child2.ullat
                        && qt.child2.ullon < lrlon1 && lrlon1 <= qt.child2.lrlon) {
                    return quadNodeLeftAprrox(qt.child2, lrlon1, lrlat1, depth);
                }
                if (qt.child3.lrlat <= lrlat1 && lrlat1 < qt.child3.ullat
                        && qt.child3.ullon < lrlon1 && lrlon1 <= qt.child3.lrlon) {
                    return quadNodeLeftAprrox(qt.child3, lrlon1, lrlat1, depth);
                }
                if (qt.child4.lrlat <= lrlat1 && lrlat1 < qt.child4.ullat
                        && qt.child4.ullon < lrlon1 && lrlon1 <= qt.child4.lrlon) {
                    return quadNodeLeftAprrox(qt.child4, lrlon1, lrlat1, depth);
                }
            }
            return qt;
        }
        int getDepth(double lonDPP) {
            for (int i = 0; i < depth_DPP.length; i += 1) {
                if (i + 1 == depth_DPP.length) {
                    return i + 1;
                }
                if (depth_DPP[i] > lonDPP && depth_DPP[i + 1] <= lonDPP) {
                    return i + 1;
                }
                if (depth_DPP[i] <= lonDPP) {
                    return i;
                }
            }
            return 0;
        }
        int getlength(double start, double dist, double end) {
            int i = 1;
            while (start < end) {
                start += dist;
                i += 1;
            }
            return i;
        }
        Map<String, Object> neighbors(double ullon1, double ullat1,
                             double lrlon1, double lrlat1, double w, double h) {
            Map<String, Object> results = new HashMap<>();
            String[][] array;
            double lonDDP = (lrlon1 - ullon1) / w;
            int depth = getDepth(lonDDP);
            if (ullon1 <= ROOT_ULLON) {
                ullon1 = ROOT_ULLON;
            }
            if (ullat1 >= ROOT_ULLAT) {
                ullat1 = ROOT_ULLAT;
            }
            if (lrlat1 <= ROOT_LRLAT) {
                lrlat1 = ROOT_LRLAT;
            }
            if (lrlon1 >= ROOT_LRLON) {
                lrlon1 = ROOT_LRLON;
            }
            QuadTree leftApprox = quadNodeLeftAprrox(this, ullon1, ullat1, depth);
            QuadTree arrtree = leftApprox;
            QuadTree temptree = leftApprox;
            if (leftApprox.node == 0) {
                array = new String[1][1];
                array[0][0] = leftApprox.root + leftApprox.node + ".png";
            } else {
                int width = getlength(leftApprox.lrlon,  2 * leftApprox.dlon, lrlon1);
                int height = getlength(-leftApprox.lrlat, 2 * leftApprox.dlat, -lrlat1);
                array = new String[height][width];
                for (int i = 0; i < height; i += 1) {
                    temptree = arrtree;
                    for (int j = 0; j < width; j += 1) {
                        array[i][j] = temptree.root + temptree.node + ".png";
                        if (j < width - 1) {
                            temptree = quadNodeLeftAprrox(this,
                                    temptree.ullon + 2 * temptree.dlon, temptree.ullat, depth);
                        }
                    }
                    if (i < height - 1) {
                        arrtree = quadNodeLeftAprrox(this, arrtree.ullon,
                                arrtree.ullat - 2 * arrtree.dlat, depth);
                    }
                }
            }
            results.put("render_grid", array);
            results.put("raster_ul_lon", leftApprox.ullon);
            results.put("raster_ul_lat", leftApprox.ullat);
            results.put("raster_lr_lon", temptree.lrlon);
            results.put("raster_lr_lat", temptree.lrlat);
            results.put("depth", depth);
            results.put("query_success", true);
            return results;
        }
    }

    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        double yROOTULLAT = 37.892195547244356, xROOTULLON = -122.2998046875,
                yROOTLRLAT = 37.82280243352756, xROOTLRLON = -122.2119140625;
        this.imagelist = new QuadTree(imgRoot, 0, 0, 0,
                xROOTULLON, yROOTULLAT, xROOTLRLON, yROOTLRLAT);
        this.depth_DPP = new double[7];
        QuadTree temp = imagelist;
        for (int i = 0; i < 7; i += 1) {
            depth_DPP[i] = temp.LonDPP;
            temp = temp.child1;
        }
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
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
     * "raster_ullon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ullat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lrlon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lrlat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
     * @see #
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        return imagelist.neighbors(params.get("ullon"), params.get("ullat"),
                params.get("lrlon"), params.get("lrlat"), params.get("w"), params.get("h"));
    }

    public static void main(String[] args) {
        Rasterer rast = new Rasterer("img/");
        double dlat = (37.892195547244356 - 37.82280243352756) / 2;
        double dlon = (-122.2998046875 + 122.2119140625) / 2;
        double dlon2 = (-122.2998046875 - (-122.2119140625 - dlon)) / 2;
        double dlat2 = (37.892195547244356 - (37.82280243352756 + dlat)) / 2;
        System.out.println(dlat);
        System.out.println(dlon);
        System.out.println(rast.imagelist.quadNodeLeftAprrox(rast.imagelist,
                -122.2998046875, 37.892195547244356, 2).node);
        System.out.println((122.2998046875 - 122.2119140625) / 1200);
        System.out.println(rast.imagelist.neighbors(-122.24163047377972, 37.87655856892288,
                -122.24053369025242, 37.87548268822065, 892.0, 875.0).get("raster_ullon"));
    }

}
