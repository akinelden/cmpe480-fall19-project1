import java.util.ArrayList;

public class Puzzle {
	private int[] initial_state, goal_state;
	public int row, col;
	public int[][] map;

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
						initial_state = new int[]{i, j};
					} else if (ch == 'g') {
						goal_state = new int[]{i, j};
					}
				}
			}
		}
	}

	public ArrayList<Tuple> GetSuccessors(int r, int c, int orient) {
		// I assumed that when agent is in horizontal orientation, its location is represented by the left cell
		// and when agent is in vertical orientation, its location is represented by the upper cell

		ArrayList<Tuple> successors = new ArrayList<>();
		if(orient == Tuple.Orientation.S.ordinal()){
			if(c>1 && map[r][c-2] == 1 && map[r][c-1] == 1){
				successors.add(new Tuple(r,c-2,Tuple.Orientation.H,1,'L'));
			}
			if(r>1 && map[r-2][c] == 1 && map[r-1][c] == 1){
				successors.add(new Tuple(r-2,c,Tuple.Orientation.V,1,'U'));
			}
			if(c<col-2 && map[r][c+2] == 1 && map[r][c+1] == 1){
				successors.add(new Tuple(r,c+1,Tuple.Orientation.H,1,'R'));
			}
			if(r<row-2 && map[r+2][c] == 1 && map[r+1][c] == 1){
				successors.add(new Tuple(r+1,c,Tuple.Orientation.V,1,'D'));
			}
		}else if(orient == Tuple.Orientation.H.ordinal()){
			if(c>0 && map[r][c-1] == 1){
				successors.add(new Tuple(r,c-1,Tuple.Orientation.S,3,'L'));
			}
			if(r>0 && map[r-1][c] == 1 && map[r-1][c+1] == 1){
				successors.add(new Tuple(r-1,c,Tuple.Orientation.H,1,'U'));
			}
			if(c<col-2 && map[r][c+2] == 1){
				successors.add(new Tuple(r,c+2,Tuple.Orientation.S,3,'R'));
			}
			if(r<row-1 && map[r+1][c] == 1 && map[r+1][c+1] == 1){
				successors.add(new Tuple(r+1,c,Tuple.Orientation.H,1,'D'));
			}
		}else if(orient == Tuple.Orientation.V.ordinal()){
			if(c>0 && map[r][c-1] == 1 && map[r+1][c-1] == 1){
				successors.add(new Tuple(r,c-1,Tuple.Orientation.V,1,'L'));
			}
			if(r>0 && map[r-1][c] == 1){
				successors.add(new Tuple(r-1,c,Tuple.Orientation.S,3,'U'));
			}
			if(c<col-1 && map[r][c+1] == 1 && map[r+1][c+1] == 1){
				successors.add(new Tuple(r,c+1,Tuple.Orientation.V,1,'R'));
			}
			if(r<row-2 && map[r+2][c] == 1){
				successors.add(new Tuple(r+2,c,Tuple.Orientation.S,3,'D'));
			}
		}

		return successors;
	}

}
