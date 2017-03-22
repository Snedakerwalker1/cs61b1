package hw3.puzzle;

import javafx.concurrent.WorkerStateEvent;

import java.util.*;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by wsnedaker on 3/20/2017.
 */
public class Solver {
    private int moves = 0;
    private List<WorldState> solution;
    private MinPQ<SearchNode> que = new MinPQ<>();

    public Solver(WorldState initial) {
        SearchNode firstNode = new SearchNode(initial, 0, null);
        HashSet<WorldState> worldSet = new HashSet<>();
        que.insert(firstNode);
        solution = new ArrayList<>();
        while (!que.min().world.isGoal()) {
            SearchNode node = que.delMin();
            worldSet.add(node.world);
            for (WorldState world : node.world.neighbors()) {
                if (node.last != null) {
                    if (!(node.last.world.equals(world)) && !(world.equals(initial)) && !(worldSet.contains(world))) {
                        worldSet.add(world);
                        SearchNode newSearch = new SearchNode(world, node.movesTaken + 1, node);
                        que.insert(newSearch);
                    }
                } else {
                    worldSet.add(world);
                    SearchNode newSearch = new SearchNode(world, node.movesTaken + 1, node);
                    que.insert(newSearch);
                }
            }
        }
        SearchNode nodesolution = que.min();
        solution.add(0, nodesolution.world);
        nodesolution = nodesolution.last;
        while (nodesolution.last != null) {
            solution.add(0, nodesolution.world);
            nodesolution = nodesolution.last;
        }
        solution.add(0, nodesolution.world);
        moves = que.min().movesTaken;
    }
    public int moves() {
        return this.moves;
    }
    public Iterable<WorldState> solution() {
        return this.solution;
    }
    private class SearchNode implements Comparable<SearchNode> {
        public WorldState world;
        public int movesTaken;
        public SearchNode last;
        public int estimate;

        public SearchNode(WorldState w, int m, SearchNode l) {
            this.world = w;
            this.movesTaken = m;
            this.last = l;
            this.estimate = w.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode sn) {
            return (this.movesTaken + this.estimate) - (sn.movesTaken + sn.estimate);
        }
    }
}
