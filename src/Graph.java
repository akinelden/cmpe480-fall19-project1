import java.util.*;

public class Graph {

	private Puzzle puzzle;
	private State[][][] stateGraph;
	private int maxDepth, totalExpanded;

	public Graph(Puzzle p) {
		puzzle = p;
		stateGraph = new State[p.getRow()][p.getCol()][3];
		maxDepth = 0;
		totalExpanded = 0;
	}

	private void outputSearchResult(State st) {
		System.out.println(st.getPathCost() + " " + totalExpanded + " " + maxDepth + " " + st.getDepth());
		String solution = "";
		while (st.getPred() != null) {
			solution = st.getMove() + solution;
			st = st.getPred();
		}
		System.out.println(solution);
	}

	private Node initializeStateGraph() {
		int[] coords = puzzle.getInitialState();
		State init = new State(coords[0], coords[1], Node.Orientation.S.ordinal());
		stateGraph[coords[0]][coords[1]][Node.Orientation.S.ordinal()] = init;
		Node initTp = new Node(init.getR_coord(), init.getC_coord(), Node.Orientation.S, 0, ' ', null);
		return initTp;
	}

	private void uninformedSearch(LinkedList<Node> queue, boolean addToLast) {
		Node initNode = initializeStateGraph();
		queue.add(initNode);
		while (queue.size() > 0) {
			Node nd = queue.poll();
			State st = stateGraph[nd.r][nd.c][nd.orientation];
			if (st.isVisited()) {
				continue;
			}
			st.exploreNode(nd.prev, nd.direction, nd.moveCost);
			System.out.println(nd.direction+" " +nd.r+" "+nd.c + " "+nd.orientation); //TODO: remove that line(test)
			maxDepth = Math.max(maxDepth, st.getDepth());
			if (puzzle.checkGoalState(st)) {
				outputSearchResult(st);
				return;
			}
			ArrayList<Node> succs = puzzle.getSuccessors(st);
			for (int i = 0; i < succs.size(); i++) {
				Node _n = succs.get(i);
				if (stateGraph[_n.r][_n.c][_n.orientation] == null) { // It means node is not explored and not in the queue
					stateGraph[_n.r][_n.c][_n.orientation] = new State(_n.r, _n.c, _n.orientation);
					if(!addToLast){
						queue.addFirst(_n);
					}
				}
				if (addToLast) {
					queue.add(_n);
				}
			}
			totalExpanded++;
		}
	}

	private void informedSearch(PriorityQueue<Node> queue){
		Node initNd = initializeStateGraph();
		calculateHeuristicCost(initNd);
		queue.add(initNd);
		while (queue.size() > 0) {
			Node nd = queue.poll();
			State st = stateGraph[nd.r][nd.c][nd.orientation];
			if (st.isVisited()) {
				continue;
			}
			st.exploreNode(nd.prev, nd.direction, nd.moveCost);
			//System.out.println(nd.direction+" " +nd.r+" "+nd.c + " "+nd.orientation); //TODO: remove that line(test)
			maxDepth = Math.max(maxDepth, st.getDepth());
			if (puzzle.checkGoalState(st)) {
				outputSearchResult(st);
				return;
			}
			ArrayList<Node> succs = puzzle.getSuccessors(st);
			for (Node _n : succs) {
				if (stateGraph[_n.r][_n.c][_n.orientation] == null) { // It means node is not explored and not in the queue
					stateGraph[_n.r][_n.c][_n.orientation] = new State(_n.r, _n.c, _n.orientation);
				}
				calculateHeuristicCost(_n);
				queue.add(_n);
			}
			totalExpanded++;
		}
	}

	public void DFS() {
		LinkedList<Node> queue = new LinkedList<>();
		uninformedSearch(queue,false);
	}

	public void BFS() {
		LinkedList<Node> queue = new LinkedList<>();
		uninformedSearch(queue,true);
	}

	public void UCS() {
		// TODO: decide on comparators
		/*Comparator<Tuple> ucsComparator = new Comparator<Tuple>() {
			@Override
			public int compare(Tuple t1, Tuple t2) {
				int diff = t1.prev.getPathCost() + t1.moveCost - (t2.prev.getPathCost() + t2.moveCost);
				if (diff == 0) {
					return getDirectionInt(t1) - getDirectionInt(t2);
				}
				return diff;
			}
		};*/
		Comparator<Node> ucsComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost));
		SortedList<Node> queue = new SortedList<>(ucsComparator);
		uninformedSearch(queue, true);
	}

	public void AStar() {
		Comparator<Node> aStarComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost + t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(aStarComparator);
		informedSearch(queue);
		/*Tuple initTp = initializeStateGraph();
		calculateHeuristicCost(initTp);
		queue.add(initTp);
		while (queue.size() > 0) {
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
				calculateHeuristicCost(_t);
				queue.add(_t);
			}
			totalExpanded++;
		}*/
		// TODO: implement
	}

	public void Greedy() {
		Comparator<Node> greedyComparator = Comparator.comparingInt(t -> (t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(greedyComparator);
		informedSearch(queue);
		/*Tuple initTp = initializeStateGraph();
		calculateHeuristicCost(initTp);
		queue.add(initTp);
		while (queue.size() > 0) {
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
				calculateHeuristicCost(_t);
				queue.add(_t);
			}
			totalExpanded++;
		}*/
		// TODO: implement
	}

	private int getDirectionInt(Node t) {
		char[] directions = {'L', 'U', 'R', 'D'};
		for (int i = 0; i < 4; i++) {
			if (t.direction == directions[i]) {
				return i;
			}
		}
		return 0;
	}

	private void calculateHeuristicCost(Node t) {
		t.setHeuristicCost(Math.abs(puzzle.getGoalState()[0] - t.r) + Math.abs(puzzle.getGoalState()[1] - t.c));
	}
}
