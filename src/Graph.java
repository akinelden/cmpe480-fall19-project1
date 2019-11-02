import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

	private Puzzle puzzle;
	private State[][][] stateGraph;
	private int maxDepth, totalExpanded;

	public Graph(Puzzle p){
		puzzle = p;
		stateGraph = new State[p.getRow()][p.getCol()][3];
		maxDepth = 0;
		totalExpanded = 0;
	}


	private State createInitialState(){
		int[] coords = puzzle.getInitialState();
		State initState = new State(coords[0], coords[1], Tuple.Orientation.S.ordinal());
		stateGraph[coords[0]][coords[1]][Tuple.Orientation.S.ordinal()] = initState;
		return  initState;
	}


	public void DFS(){
		State init = createInitialState();
		LinkedList<Tuple> queue = new LinkedList<>();
		Tuple initTp = new Tuple(init.getR_coord(),init.getC_coord(),Tuple.Orientation.S,0,' ',null);
		queue.add(initTp);
		while(queue.size()>0){
			Tuple tp = queue.poll();
			State st = stateGraph[tp.r][tp.c][tp.orientation];
			if(st.isVisited()){
				continue;
			}
			st.exploreNode(tp.prev, tp.direction, tp.moveCost);
			maxDepth = Math.max(maxDepth, st.getDepth());
			if(puzzle.checkGoalState(st)){
				//TODO: implement goal reached method
				State current = st;
				while(current.getPred()!=null){
					System.out.print(current.getMove());
					current = current.getPred();
				}
				return;
			}
			ArrayList<Tuple> succs = puzzle.getSuccessors(st);
			for(int i=succs.size()-1; i>=0; i--){
				Tuple _t = succs.get(i);
				if(stateGraph[_t.r][_t.c][_t.orientation]==null){ // It means node is not explored and not in the queue
					stateGraph[_t.r][_t.c][_t.orientation] = new State(_t.r,_t.c,_t.orientation);
					//queue.addFirst(_t); // TODO: delete this line
				}
				queue.addFirst(_t);
			}
			totalExpanded++;
		}
	}

	public void BFS(){
		State init = createInitialState();
		LinkedList<Tuple> queue = new LinkedList<>();
		Tuple initTp = new Tuple(init.getR_coord(),init.getC_coord(),Tuple.Orientation.S,0,' ',null);
		queue.add(initTp);
		while(queue.size()>0) {
			Tuple tp = queue.poll();
			State st = stateGraph[tp.r][tp.c][tp.orientation];
			if (st.isVisited()) {
				continue;
			}
			st.exploreNode(tp.prev, tp.direction, tp.moveCost);
			maxDepth = Math.max(maxDepth, st.getDepth());
			if (puzzle.checkGoalState(st)) {
				//TODO: implement goal reached method
				State current = st;
				while (current.getPred() != null) {
					System.out.print(current.getMove());
					current = current.getPred();
				}
				return;
			}
			ArrayList<Tuple> succs = puzzle.getSuccessors(st);
			for (int i = 0; i < succs.size(); i++) {
				Tuple _t = succs.get(i);
				if (stateGraph[_t.r][_t.c][_t.orientation] == null) { // It means node is not explored and not in the queue
					stateGraph[_t.r][_t.c][_t.orientation] = new State(_t.r, _t.c, _t.orientation);
					queue.addLast(_t);
				}
				//queue.addLast(_t);
			}
			totalExpanded++;
		}
		// TODO: implement
	}

	public void UCS(){
		// TODO: implement
	}

	public void AStar(){
		// TODO: implement
	}

	public void Greedy(){
		// TODO: implement
	}
}
