import java.util.*;

public class Graph {

	private Puzzle puzzle;
	private State[][][] stateGraph;
	private int maxDepth, totalExpanded;

	public Graph(Puzzle p) {
		puzzle = p;

		// Since there are two orientation possibilities for each location,
		// total state number is three times of cells.
		// State graph is formed as a 3 dimensional matrix, first dimension for row,
		// second for column and third for orientation
		stateGraph = new State[p.getRow()][p.getCol()][3];
		maxDepth = 0;
		totalExpanded = 0;
	}

	/**
	 * Prints the search result by going back to initial state from the goal state
	 * @param st Goal state
	 */
	private void outputSearchResult(State st) {
		System.out.println(st.getPathCost() + " " + totalExpanded + " " + maxDepth + " " + st.getDepth());
		String solution = "";
		while (st.getPred() != null) {
			solution = st.getMove() + solution;
			st = st.getPred();
		}
		System.out.println(solution);
	}

	/**
	 * Creates the initial state and corresponding node object.
	 * Then returns that node object so that it can be added to
	 * queue as the first element to be explored.
	 * @return Node object of initial state
	 */
	private Node initializeStateGraph() {
		int[] coords = puzzle.getInitialState();
		State init = new State(coords[0], coords[1], Node.Orientation.S.ordinal());
		stateGraph[coords[0]][coords[1]][Node.Orientation.S.ordinal()] = init;
		Node initTp = new Node(init.getR_coord(), init.getC_coord(), Node.Orientation.S, 0, ' ', null);
		return initTp;
	}

	/**
	 * Used for DFS, BFS and UCS algorithms
	 * @param queue A LinkedList queue
	 * @param addToLast Whether the new nodes are added to last of the queue or not.
	 *                   It's false for DFS, true for BFS and UCS
	 */
	private void uninformedSearch(LinkedList<Node> queue, boolean addToLast) {
		Node initNode = initializeStateGraph();
		queue.add(initNode);
		while (queue.size() > 0) {
			Node nd = queue.poll();
			// Since the state is created when the first node pointing to that node is found,
			// there is no need to check whether its null or not.
			State st = stateGraph[nd.r][nd.c][nd.orientation];
			if (st.isVisited()) {
				continue;
			}
			// If a node pointing to that state is pulled and the state is not visited before,
			// then explore the state and assign the predecessor state, move direction and move cost
			// The function also assigns the depth and path cost of the state looking at the predecessor and
			// current move
			st.exploreState(nd.prev, nd.direction, nd.moveCost);
			maxDepth = Math.max(maxDepth, st.getDepth());

			// If the state is goal state, print the output and return
			if (puzzle.checkGoalState(st)) {
				outputSearchResult(st);
				return;
			}

			// Gets the valid successors of the current state
			// and adds to the queue.
			ArrayList<Node> succs = puzzle.getSuccessors(st);
			for (int i = 0; i < succs.size(); i++) {
				Node _n = succs.get(i);
				// First check whether the the state pointed by the node is created or not.
				// If it's not created yet, it means the current node is the first node pointing to that state.
				if (stateGraph[_n.r][_n.c][_n.orientation] == null) {
					stateGraph[_n.r][_n.c][_n.orientation] = new State(_n.r, _n.c, _n.orientation);
					// For DFS search, add the node only if there is no other node pointing to that state.
					if(!addToLast){
						queue.addFirst(_n);
					}
				}
				/* For BFS, adding another node pointing to same state with another node is not important since
				* firstly added one will be pulled first.
				*/
				/* For UCS, lately explored node's path cost may be less than the previous ones, therefore
				 add the node to the queue and let the queue sort it and place at the appropriate index
				 */
				if (addToLast) {
					queue.add(_n);
				}
			}
			// A state is counted as expanded when its successors are explored
			totalExpanded++;
		}
	}

	/**
	 * Calculates the heuristic cost (Manhattan distance to goal state) of the given node and assigns it
	 * @param t Input node
	 */
	private void calculateHeuristicCost(Node t) {
		t.setHeuristicCost(Math.abs(puzzle.getGoalState()[0] - t.r) + Math.abs(puzzle.getGoalState()[1] - t.c));
	}

	/**
	 * Used for Greedy and A* search algorithms.
	 * The main difference is that, when a node is created,
	 * its heuristic cost is calculated and assigned to node.
	 * @param queue A Priority queue to make the queue operations
	 */
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
			st.exploreState(nd.prev, nd.direction, nd.moveCost);
			maxDepth = Math.max(maxDepth, st.getDepth());
			if (puzzle.checkGoalState(st)) {
				outputSearchResult(st);
				return;
			}
			ArrayList<Node> succs = puzzle.getSuccessors(st);
			for (Node _n : succs) {
				if (stateGraph[_n.r][_n.c][_n.orientation] == null) {
					stateGraph[_n.r][_n.c][_n.orientation] = new State(_n.r, _n.c, _n.orientation);
				}
				// When a node is created, its heuristic cost is calculated and assigned,
				// so priority queue can sort the node
				calculateHeuristicCost(_n);
				queue.add(_n);
			}
			totalExpanded++;
		}
	}

	public void DFS() {
		LinkedList<Node> queue = new LinkedList<>();
		// Regular LinkedList is used as queue container and successor nodes are added to front of the queue
		uninformedSearch(queue,false);
	}

	public void BFS() {
		LinkedList<Node> queue = new LinkedList<>();
		// Regular LinkedList is used as queue container and successor nodes are added to back of the queue
		uninformedSearch(queue,true);
	}

	public void UCS() {
		// A comparator is created to compare the nodes according to their path cost. which is equal to
		// predecessor state's path cost + move cost.
		Comparator<Node> ucsComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost));
		// A SortedList is created, which uses ucsComparator to compare the added nodes.
		SortedList<Node> queue = new SortedList<>(ucsComparator);
		// SortedList is used as queue container.
		uninformedSearch(queue, true);
	}

	public void AStar() {
		// A comparator is created to compare the nodes according to their path cost + heuristic cost.
		Comparator<Node> aStarComparator = Comparator.comparingInt(t -> (t.prev.getPathCost() + t.moveCost + t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(aStarComparator);
		// PriorityQueue is used as queue container
		informedSearch(queue);
	}

	public void Greedy() {
		// A comparator is created to compare the nodes according to their heuristic cost.
		Comparator<Node> greedyComparator = Comparator.comparingInt(t -> (t.heuristicCost));
		PriorityQueue<Node> queue = new PriorityQueue<>(greedyComparator);
		// PriorityQueue is used as queue container
		informedSearch(queue);
	}
}
