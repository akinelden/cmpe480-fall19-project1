import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String level = "levels/level1.txt"; // TODO: remove them
		String method = "bfs"; // TODO: remove them

		Puzzle puzzle = createPuzzle(level);

		// TODO: remove below
		//testPuzzle(puzzle);

		Graph graph = new Graph(puzzle);

		switch (method) {
			case "dfs":
				graph.DFS();
				break;
			case "bfs":
				graph.BFS();
				break;
			case "ucs":
				graph.UCS();
				break;
			case "gs":
				graph.Greedy();
				break;
			case "as":
				graph.AStar();
				break;
		}

	}

	// TODO: remove that test function
	private static void testPuzzle(Puzzle puzzle) {
		while (true) {
			try {
				System.out.println("Enter coordinates and orientation:");
				Scanner scanner = new Scanner(System.in);
				String s = scanner.nextLine();
				String[] coords = s.split(" ");
				State st = new State(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
				ArrayList<Node> successors = puzzle.getSuccessors(st);
				for (Node suc : successors) {
					suc.printNode();
				}
			} catch (Exception ex1) {
				System.out.println("Wrong input given!");
			}
		}
	}

	private static Puzzle createPuzzle(String level) {
		String[] map;
		int row, col;
		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			String line = reader.readLine();
			String[] parts = line.split(" ");
			col = Integer.parseInt(parts[0]);
			row = Integer.parseInt(parts[1]);
			map = new String[row];
			for (int i = 0; i < row; i++) {
				line = reader.readLine();
				map[i] = line;
			}
		} catch (FileNotFoundException ex1) {
			System.out.println("File couldn't be found!");
			throw new RuntimeException(ex1);
		} catch (IOException ex2) {
			System.out.println("An error occurred while reading the file!");
			throw new RuntimeException(ex2);
		}
		return new Puzzle(row, col, map);
	}
}
