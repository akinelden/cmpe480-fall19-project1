import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

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

	private void outputSearchResult(State st){
		System.out.println(st.getPathCost()+" "+totalExpanded+" "+maxDepth+" "+st.getDepth());
		String solution = "";
		while(st.getPred()!=null){
			solution = st.getMove() + solution;
			st = st.getPred();
		}
		System.out.println(solution);
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
				outputSearchResult(st);
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
				outputSearchResult(st);
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
		Comparator<Tuple> ucsComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost));
		PriorityQueue<Tuple> queue = new PriorityQueue<>(ucsComparator);
		State init = createInitialState();
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
				outputSearchResult(st);
				return;
			}
			ArrayList<Tuple> succs = puzzle.getSuccessors(st);
			for (Tuple _t : succs) {
				if (stateGraph[_t.r][_t.c][_t.orientation] == null) { // It means node is not explored and not in the queue
					stateGraph[_t.r][_t.c][_t.orientation] = new State(_t.r, _t.c, _t.orientation);
					//queue.addLast(_t);
				}
				queue.add(_t);
			}
			totalExpanded++;
		}
		// TODO: implement
	}

	public void AStar(){
		// TODO: implement
	}

	public void Greedy(){
		// TODO: implement
	}

	private void calculateHeuristicCost(Tuple t){

	}
}
