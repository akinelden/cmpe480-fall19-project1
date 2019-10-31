public class Tuple {
	enum Orientation {S, H, V}

	public int r, c, orientation, cost;
	public char direction;

	public Tuple(int _r, int _c, Orientation _orient, int _cost, char _dir) {
		r = _r;
		c = _c;
		orientation = _orient.ordinal();
		cost = _cost;
		direction = _dir;
	}
}
