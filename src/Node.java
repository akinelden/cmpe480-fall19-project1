/**
 * Since the state's predecessor etc. is not known until its expanded,
 * node class is added to the queue instead of state.
 * There may be more than one node pointing to same state in queue.
 */
public class Node {
	enum Orientation {S, H, V}

	// Move cost is whether 1 or 3, determined by previous state and move direction
	public int r, c, orientation, moveCost;
	public char direction;
	public State prev;
	public int heuristicCost = 0;

	public Node(int _r, int _c, Orientation _orient, int _cost, char _dir, State _st) {
		r = _r;
		c = _c;
		orientation = _orient.ordinal();
		moveCost = _cost;
		direction = _dir;
		prev = _st;
	}

	// That method is used only in informed search algorithms
	public void setHeuristicCost(int hC){
		heuristicCost = hC;
	}
}
