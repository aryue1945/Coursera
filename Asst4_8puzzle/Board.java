import java.util.Vector;

public class Board {
	final private int [] data;
	final private int n;
	private int hamValue;
	private int manValue;
	private int zeroPos;
	private boolean goal;
	

	
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks
    	if (blocks == null)
    		throw new IllegalArgumentException();
    	n = blocks[0].length;
    	data = new int[n*n];
    	for (int i = 0; i < n; i++)
    		for (int j = 0; j < n; j++) {
    			data[i*n+j] = blocks[i][j];
    			if (blocks[i][j] == 0)
    				zeroPos = i*n+j;
    		}
    	calValue();
    	if (manValue == 0)
    	    goal = true;
    }
    
    
    private Board(Board old) {
    	this.n = old.n;
    	this.data = new int[n*n];
    	for (int i = 0; i < n*n; i++)
    		this.data[i] = old.data[i];
    	this.hamValue = old.hamValue;
    	this.manValue = old.manValue;
    	this.zeroPos = old.zeroPos;
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                 // board dimension n
    	return n;
    }
    private Board swap(int i) {
    	if (i == 0)
    		return swapUp();
    	else if (i == 1)
    		return swapRight();
    	else if (i == 2)
    		return swapDown();
    	else
    		return swapLeft();
    }
    
    
    private void swapHelper(Board res, int oldZero, int newZero) {
    	res.data[oldZero] = res.data[newZero];
    	if (res.data[newZero] == newZero+1)
    	    res.hamValue++;
    	else if (res.data[oldZero] == oldZero+1)
    		res.hamValue--;
    	
    	int tile = res.data[newZero];
    	int oldDis = Math.abs((tile-1)%n - newZero%n) + Math.abs((tile-1)/n - newZero/n);
    	int newDis = Math.abs((tile-1)%n - oldZero%n) + Math.abs((tile-1)/n - oldZero/n);
    	res.manValue += (newDis - oldDis);
    	
    	res.data[newZero] = 0;
    	res.zeroPos = newZero;
    	
    	if (res.manhattan() == 0)
    	    res.goal = true;
    }
    
    private Board swapUp() {
    	if (zeroPos/n == 0)
    		return null;
    	Board res = new Board(this);
    	swapHelper(res, zeroPos, zeroPos-n);
    	return res;	
    }
    private Board swapDown() {
    	if (zeroPos/n == n-1)
    		return null;
    	Board res = new Board(this);
    	swapHelper(res, zeroPos, zeroPos+n);
    	return res;	
    }
    private Board swapLeft() {
    	if (zeroPos%n == 0)
    		return null;
    	Board res = new Board(this);
    	swapHelper(res, zeroPos, zeroPos-1);
    	return res;	
    }
    private Board swapRight() {
    	if (zeroPos%n == n-1)
    		return null;
    	Board res = new Board(this);
    	swapHelper(res, zeroPos, zeroPos+1);
    	return res;	
    }
    
    
    private void calValue() {
    	int ham = 0;
    	int man = 0;
    	for (int i = 0; i < n*n; i++)
    		if (data[i] != 0 && data[i] != i+1) {
    		    ham++;
    		    man += Math.abs((data[i]-1)%n - i%n);
    		    man += Math.abs((data[i]-1)/n - i/n);
    		}
    	hamValue = ham;
    	manValue = man;
        
    }
    
    public int hamming() {                  // number of blocks out of place
    	return hamValue;
    }
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
    	return manValue;
    }
    public boolean isGoal() {                // is this board the goal board?
    	return goal;
    }
    public Board twin() {                    // a board that is obtained by exchanging any pair of blocks
    	Board res = new Board(this);
    	if (zeroPos/n == 0) {
    		int temp = res.data[n];
    		res.data[n] = res.data[n+1];
    		res.data[n+1] = temp;
    	} else {
    		int temp = res.data[0];
    		res.data[0] = res.data[1];
    		res.data[1] = temp;
    	}
    	res.calValue();
    	return res;
    		
    }
    public boolean equals(Object y) {        // does this board equal y?
    	if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
    	Board yy = (Board)y;
    	if (this.n != yy.n || this.data.length != yy.data.length)
    	    return false;
    	for (int i = 0; i < n*n; i++)
    		if (data[i] != yy.data[i])
    			return false;
    	return true;
    }
    public Iterable<Board> neighbors() {     // all neighboring boards
    	Vector<Board> collection = new Vector<Board>();
    	for (int i = 0; i <  4; i++) {
    		Board x = this.swap(i);
    		if (x != null)
    			collection.add(x);
    	}
    	return collection;
  	
    }
    public String toString() {              // string representation of this board (in the output format specified below)
    	StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", data[i*n+j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static void main(String[] args) { // unit tests (not graded)
    	int[][] a = {{5,8,7},{1,4,6},{3,0,2}}; 
    	Board t = new Board(a);
    	System.out.println(t);
    	System.out.println(t.isGoal());
    	System.out.println(t.manhattan());
    	System.out.println();
    	
    	
    	Board t2 = t.swapRight();
    	
    	System.out.println(t2);
    	System.out.println(t2.isGoal());
    	System.out.println(t2.manhattan());
    	System.out.println();
    	
    	System.out.println(t.swapRight().swapUp());
    	System.out.println(t.swapRight().swapUp().isGoal());
    	System.out.println(t.swapRight().swapUp().manhattan());
    	System.out.println();
    	
    	System.out.println(t.swapRight().swapUp().swapLeft());
    	System.out.println(t.swapRight().swapUp().swapLeft().isGoal());
    	System.out.println(t.swapRight().swapUp().swapLeft().manhattan());
    	System.out.println();
    	
    	System.out.println(t.swapRight().swapUp().swapLeft().swapDown());
    	System.out.println(t.swapRight().swapUp().swapLeft().swapDown().isGoal());
    	System.out.println(t.swapRight().swapUp().swapLeft().swapDown().manhattan ());
    	System.out.println();
    	
    }
}
