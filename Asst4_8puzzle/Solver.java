import java.util.LinkedList;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private Node target;
    private boolean solvable;
    
    private class Node implements Comparable<Node>{
        private final Board board;
        private final Node prev;
        private final int move;
        private final int cachedPriority;
        private final boolean mainB;

        Node(Board board, int move, Node prev, boolean mainB) {
            this.board = board;
            this.move = move;
            this.prev = prev;
            cachedPriority = move + board.manhattan();
            this.mainB = mainB;
        }
        
        @Override
        public int compareTo(Node o) {
            if (this.cachedPriority != o.cachedPriority)
                return this.cachedPriority - o.cachedPriority;
            else
                return o.move - this.move;
        }
        
    }
    
    public Solver(Board initial) {        // find a solution to the initial board (using the A* algorithm)
        if (initial == null)
            throw new IllegalArgumentException();
      
        MinPQ<Node> myPQ = new MinPQ<Node>();
        Node node = new Node(initial, 0, null, true);
        myPQ.insert(new Node(initial.twin(), 0, null, false));
        
        
        while (!node.board.isGoal()) {
            for (Board neighbour : node.board.neighbors()) {
                if (node.prev == null) {
                    if (node.mainB)
                        myPQ.insert(new Node(neighbour, node.move+1, node, true));
                    else
                        myPQ.insert(new Node(neighbour, node.move+1, node, false));
                } else if (!neighbour.equals(node.prev.board)) {
                    if (node.mainB)
                        myPQ.insert(new Node(neighbour, node.move+1, node, true));
                    else
                        myPQ.insert(new Node(neighbour, node.move+1, node, false));
                }
            }
            node = myPQ.delMin();
        }
        if (node.mainB) {
            solvable = true;
            target = node;
        } else
            solvable = false;
            
        
    }
    public boolean isSolvable() {            // is the initial board solvable?
        return solvable;
    }
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) {
            return target.move;
        }
        else return -1;
    }
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if unsolvable 
        if (isSolvable()) {
            LinkedList<Board> res = new LinkedList<Board>();
            Node x = target;
            while (x != null) {
                res.push(x.board);
                x = x.prev;
            }
            return res;
        } else
            return null;
        
            
    }
    public static void main(String[] args) {// solve a slider puzzle (given below)
        int[][] a = {{0,1,3},{4,2,5},{7,8,6}}; 
        Board t = new Board(a);
        Solver s = new Solver(t);
        if (s.solvable) {
            System.out.println(s.moves());
            for (Board x : s.solution())
                System.out.println(x);
        }
        else
            System.out.println(-1);
        
    }
}