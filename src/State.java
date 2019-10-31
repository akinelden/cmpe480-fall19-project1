public class State {
	// Location of the agent is determined by [row,column] pair
	public int r_coord, c_coord, orientation;
	private State pred = null;
	private State[] Successors;
	private int depth, pathCost;
	public boolean isVisited = false;

	public State(){
		// TODO: state intialization
	}
}
