public class State {
	// Location of the agent is determined by [row,column] pair
	// and the state is determined with location, orientation pair
	private int r_coord, c_coord, orientation;

	// The move taken after the predecessor state to reach that state.
	// Possible values are : L, U, R, D
	private char move;
	private State pred = null;
	private int depth, pathCost;
	private boolean visited = false;

	/**
	 * Initialization of state object
	 * @param _r
	 * @param _c
	 * @param _orient
	 */
	public State(int _r, int _c, int _orient) {
		r_coord = _r;
		c_coord = _c;
		orientation = _orient;
	}

	/**
	 * When the state is explored, it means the first node pointing to that state is explored
	 * and its information is transferred to that state.
	 * When state is explored, it's still not expanded!
	 * @param _pred Predecessor state
	 * @param _move The direction of the move made to reach the state
	 * @param moveCost The cost of the move
	 */
	public void exploreState(State _pred, char _move, int moveCost) {
		pred = _pred;
		move = _move;
		pathCost = _pred == null ? 0 : _pred.pathCost + moveCost;
		// Depth of the initial state is assumed to be zero.
		depth = _pred == null ? 0 : _pred.depth + 1;
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
