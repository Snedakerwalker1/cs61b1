import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    private HashMap<Long, Node> nodeMap;
    private List<Long> nodeList;
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    class Node {
        long id;
        double lon;
        double lat;
        String name = "";
        List<Long> adjacent;
        List<Edge> edges;

        Node(long id1, double lon1, double lat1) {
            this.id = id1;
            this.lon = lon1;
            this.lat = lat1;
            this.adjacent = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
        void setName(String name1) {
            this.name = name1;
        }
    }
    class Edge {
        long edgeId;
        String maxSpeed = "";
        String name = "";

        Edge(long id) {
            this.edgeId = id;
        }
        void setMaxSpeed(String speed) {
            this.maxSpeed = speed;
        }
        void setName(String name1) {
            this.name = name1;
        }
    }
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        this.nodeList = new ArrayList<>();
        this.nodeMap = new HashMap<>();
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    public void addNode(long id, double lon, double lat) {
        Node node = new Node(id, lon, lat);
        nodeMap.put(id, node);
        nodeList.add(id);
    }
    public void addEdge(long edge1, ArrayList<Long> ids, String name, String speed) {
        for (int i = 0; i < ids.size(); i += 1) {
            Node node1 = nodeMap.get(ids.get(i));
            Edge edge = new Edge(edge1);
            edge.setName(name);
            edge.setMaxSpeed(speed);
            node1.edges.add(edge);
            if (i + 1 != ids.size()) {
                nodeMap.get(ids.get(i + 1)).adjacent.add(ids.get(i));
                node1.adjacent.add(ids.get(i + 1));
            }
        }
    }
    public void addNodeName(long id, String name1) {
        if (nodeMap.containsKey(id)) {
            nodeMap.get(id).setName(name1);
        }
    }

    private void clean() {
        ArrayList<Long> dontRemoveUs = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i += 1) {
            long v = nodeList.get(i);
            Node node = getNode(v);
            if (!node.adjacent.isEmpty()) {
                dontRemoveUs.add(v);
            } else {
                nodeMap.remove(v);
            }
        }
        nodeList = dontRemoveUs;

    }

    /** Returns an iterable of all vertex IDs in the graph. */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return this.nodeList;
    }
    Node getNode(long v) {
        if (nodeMap.containsKey(v)) {
            return nodeMap.get(v);
        }
        return null;
    }

    /** Returns ids of all vertices adjacent to v. */
    Iterable<Long> adjacent(long v) {
        Node node = nodeMap.get(v);
        return node.adjacent;
    }

    /** Returns the Euclidean distance between vertices v and w, where Euclidean distance
     *  is defined as sqrt( (lonV - lonW)^2 + (latV - latW)^2 ). */
    double distance(long v, long w) {
        Node node1 = this.nodeMap.get(v);
        Node node2 = this.nodeMap.get(w);
        return Math.sqrt((node1.lon - node2.lon) * (node1.lon - node2.lon)
                + (node1.lat - node2.lat) * (node1.lat - node2.lat));
    }
    double distance2(long v, double lon, double lat) {
        Node node1 = this.nodeMap.get(v);
        return Math.sqrt((node1.lon - lon) * (node1.lon - lon)
                + (node1.lat - lat) * (node1.lat - lat));
    }

    /** Returns the vertex id closest to the given longitude and latitude. */
    long closest(double lon, double lat) {
        long posMin = nodeList.get(1);
        double dist1 = distance2(posMin, lon, lat);
        for (int i = 1; i < nodeList.size(); i += 1) {
            double dist2 = distance2(nodeList.get(i), lon, lat);
            if (dist1 == 0) {
                return posMin;
            }
            if (dist2 == 0) {
                return nodeList.get(i);
            }
            if (dist2 < dist1) {
                dist1 = dist2;
                posMin = nodeList.get(i);
            }
        }
        return posMin;
    }

    /** Longitude of vertex v. */
    double lon(long v) {
        Node node1 = this.nodeMap.get(v);
        return node1.lon;
    }

    /** Latitude of vertex v. */
    double lat(long v) {
        Node node1 = this.nodeMap.get(v);
        return node1.lat;
    }
    public LinkedList<Long> shortDist(double stlon, double stlat,
                                      double destlon, double destlat) {
        long start = closest(stlon, stlat);
        long end = closest(destlon, destlat);
        Node startNode = getNode(start);
        HashMap<Node, SearchNode> nodeMap = new HashMap<>();
        ArrayHeap<SearchNode> que = new ArrayHeap<>();
        SearchNode firstNode = new SearchNode(startNode, 0, null, end);
        LinkedList<Long> solution = new LinkedList<>();
        que.insert(firstNode, firstNode.dist);
        while (que.peek().current.id !=  end) {
            SearchNode node = que.removeMin();
            nodeMap.put(node.current, node);
            if(node.current.id == 53055000)  {
                int i = 0;
            }
            for (Long nebor: node.current.adjacent) {
                Node nebhor = getNode(nebor);
                SearchNode newSearch = new SearchNode(nebhor,
                        distance(node.current.id, nebhor.id) + node.distFromStart, node, end);
                if (node.last != null) {
                    if (!(nodeMap.containsKey(nebhor))) {
                        que.insert(newSearch, newSearch.dist);
                    } else {
                        SearchNode oldNode = nodeMap.get(nebhor);
                        if (newSearch.dist < oldNode.dist) {
                            nodeMap.remove(nebhor);
                            que.insert(newSearch, newSearch.dist);
                        }
                    }
                } else {
                    que.insert(newSearch, newSearch.dist);
                }
            }
        }
        SearchNode nodeSolution = que.removeMin();
        while (nodeSolution.last != null) {
            solution.add(0, nodeSolution.current.id);
            nodeSolution = nodeSolution.last;
        }
        solution.add(0, nodeSolution.current.id);
        return solution;
    }




    private class SearchNode implements Comparable<SearchNode> {
        private double distToEnd;
        private Node current;
        private SearchNode last;
        private double distFromStart;
        private double dist;

        SearchNode(Node node, double dist, SearchNode l, long end) {
            this.distFromStart = dist;
            this.last = l;
            this.current = node;
            this.distToEnd = distance(node.id, end);
            this.dist = distFromStart + distToEnd;
        }

        @Override
        public int compareTo(SearchNode sn) {
            Double thing = (this.distFromStart + this.distToEnd)
                    - (sn.distFromStart + sn.distToEnd);
            return thing.intValue();
        }

    }
}
