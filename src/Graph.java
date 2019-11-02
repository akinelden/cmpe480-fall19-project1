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

	private void calculateHeuristicCost(Node t) {
		t.setHeuristicCost(Math.abs(puzzle.getGoalState()[0] - t.r) + Math.abs(puzzle.getGoalState()[1] - t.c));
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
		Comparator<Node> ucsComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost));
		SortedList<Node> queue = new SortedList<>(ucsComparator);
		uninformedSearch(queue, true);
	}

	public void AStar() {
		Comparator<Node> aStarComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost + t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(aStarComparator);
		informedSearch(queue);
	}

	public void Greedy() {
		Comparator<Node> greedyComparator = Comparator.comparingInt(t -> (t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(greedyComparator);
		informedSearch(queue);
	}
}
