public class Tuple {
	enum Orientation {S, H, V}

	public int r, c, orientation, moveCost;
	public char direction;
	public State prev;
	public int heuristicCost = 0;

	public Tuple(int _r, int _c, Orientation _orient, int _cost, char _dir, State _st) {
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

	//TODO: remove that one
	public void printTuple(){
		System.out.println("Direction:" + direction + " Coords:" + Integer.toString(r) + "," +
				Integer.toString(c) + " Orientation:" + Integer.toString(orientation) + " Cost:" +
				Integer.toString(moveCost));
	}
}
