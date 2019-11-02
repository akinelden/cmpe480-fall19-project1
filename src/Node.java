public class Node {
	enum Orientation {S, H, V}

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

	public void setHeuristicCost(int hC){
		heuristicCost = hC;
	}
}
