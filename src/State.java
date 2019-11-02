public class State {
	// Location of the agent is determined by [row,column] pair
	private int r_coord, c_coord, orientation;
	private char move;
	private State pred = null;
	//private State[] successors;
	private int depth, pathCost;
	private boolean visited = false;

	public State(int _r, int _c, int _orient) {
		r_coord = _r;
		c_coord = _c;
		orientation = _orient;
	}

	public void exploreNode(State _pred, char _move, int moveCost /*, State[] _suc*/) {
		pred = _pred;
		move = _move;
		pathCost = _pred == null ? 0 : _pred.pathCost + moveCost;
		depth = _pred == null ? 0 : _pred.depth + 1;
		//successors = _suc;
		visited = true;
	}

	public boolean isVisited(){
		return visited;
	}

	public int getR_coord(){
		return r_coord;
	}
	public int getC_coord(){
		return c_coord;
	}
	public int getOrientation(){
		return orientation;
	}
	public int getDepth(){
		return depth;
	}
	public int getPathCost(){
		return pathCost;
	}

	public State getPred(){
		return pred;
	}

	public char getMove(){
		return move;
	}
}
