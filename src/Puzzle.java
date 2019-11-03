import java.util.ArrayList;

public class Puzzle {
	private int[] initialState, goalState;
	private int row, col;
	private int[][] map;

	/**
	 * Initialization of the puzzle map
	 * @param _row
	 * @param _col
	 * @param _map
	 */
	public Puzzle(int _row, int _col, String[] _map) {
		row = _row;
		col = _col;
		map = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				char ch = _map[i].charAt(j);
				if (ch != ' ') {
					map[i][j] = 1;
					if (ch == 's') {
						initialState = new int[]{i, j};
					} else if (ch == 'g') {
						goalState = new int[]{i, j};
					}
				}
			}
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int[] getInitialState() {
		return initialState;
	}

	public int[] getGoalState(){
		return goalState;
	}

	/**
	 * Gets the valid successor state nodes of a given state looking at its row, column and orientation
	 * @param st The current state given as input
	 * @return ArrayList of successor valid state nodes
	 */
	public ArrayList<Node> getSuccessors(State st) {
		// I assumed that when agent is in horizontal orientation, its location is represented by left occupied cell
		// and when agent is in vertical orientation, its location is represented by upper occupied cell
		int r = st.getR_coord();
		int c = st.getC_coord();
		int orient = st.getOrientation();

		// I generated successors according to the orientation of the current state
		ArrayList<Node> successors = new ArrayList<>();
		if (orient == Node.Orientation.S.ordinal()) {
			if (c > 1 && map[r][c - 2] == 1 && map[r][c - 1] == 1) {
				successors.add(new Node(r, c - 2, Node.Orientation.H, 1, 'L', st));
			}
			if (r > 1 && map[r - 2][c] == 1 && map[r - 1][c] == 1) {
				successors.add(new Node(r - 2, c, Node.Orientation.V, 1, 'U',st));
			}
			if (c < col - 2 && map[r][c + 2] == 1 && map[r][c + 1] == 1) {
				successors.add(new Node(r, c + 1, Node.Orientation.H, 1, 'R',st));
			}
			if (r < row - 2 && map[r + 2][c] == 1 && map[r + 1][c] == 1) {
				successors.add(new Node(r + 1, c, Node.Orientation.V, 1, 'D',st));
			}
		} else if (orient == Node.Orientation.H.ordinal()) {
			if (c > 0 && map[r][c - 1] == 1) {
				successors.add(new Node(r, c - 1, Node.Orientation.S, 3, 'L',st));
			}
			if (r > 0 && map[r - 1][c] == 1 && map[r - 1][c + 1] == 1) {
				successors.add(new Node(r - 1, c, Node.Orientation.H, 1, 'U',st));
			}
			if (c < col - 2 && map[r][c + 2] == 1) {
				successors.add(new Node(r, c + 2, Node.Orientation.S, 3, 'R',st));
			}
			if (r < row - 1 && map[r + 1][c] == 1 && map[r + 1][c + 1] == 1) {
				successors.add(new Node(r + 1, c, Node.Orientation.H, 1, 'D',st));
			}
		} else if (orient == Node.Orientation.V.ordinal()) {
			if (c > 0 && map[r][c - 1] == 1 && map[r + 1][c - 1] == 1) {
				successors.add(new Node(r, c - 1, Node.Orientation.V, 1, 'L',st));
			}
			if (r > 0 && map[r - 1][c] == 1) {
				successors.add(new Node(r - 1, c, Node.Orientation.S, 3, 'U',st));
			}
			if (c < col - 1 && map[r][c + 1] == 1 && map[r + 1][c + 1] == 1) {
				successors.add(new Node(r, c + 1, Node.Orientation.V, 1, 'R',st));
			}
			if (r < row - 2 && map[r + 2][c] == 1) {
				successors.add(new Node(r + 2, c, Node.Orientation.S, 3, 'D',st));
			}
		}
		return successors;
	}

	public boolean checkGoalState(State st) {
		return st.getR_coord() == goalState[0] && st.getC_coord() == goalState[1] && st.getOrientation() == Node.Orientation.S.ordinal();
	}

}
