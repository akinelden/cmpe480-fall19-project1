import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String level = args[0]; // TODO: remove them
		//String level = "levels/level1.txt"; // TODO: remove them
		String method = args[1]; // TODO: remove them
		//String method = "bfs"; // TODO: remove them

		Puzzle puzzle = createPuzzle(level);

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
