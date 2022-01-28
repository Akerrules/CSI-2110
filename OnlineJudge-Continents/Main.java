import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	// class to keep track of cordrinates in the matrix (easier to use and keeps
	// code organized)
	static class cords { 
		int row;
		int col;
		int distance = 0;

		public cords(int row, int col) {
			this.col = col;
			this.row = row;
		}

		public cords(int row, int col, int distance) {
			this.col = col;
			this.row = row;
			this.distance = distance;
		}
	}

	static String[][] Adjacent;
	static boolean[][] visitedEdges;
	static int[][] distanceMatrix;
	static cords farthestNode;
	static boolean partA;

	public static int[] bfs(String[][] Graph, boolean[][] visited, cords s, String word) {
		int m = Graph.length;
		int n = Graph[0].length;
		Queue<cords> q = new LinkedList<>();
		q.add(s);
		visitedEdges[s.row][s.col] = true;
		distanceMatrix = new int[m][n];
		int count = 0;
		while (!q.isEmpty()) {
			int i = q.peek().row;
			int j = q.peek().col;
			int distance = q.peek().distance;
			q.remove();
			System.err.println("("+i+","+j+")");
			farthestNode = new cords(i, j); // get the farthest node from the northwestpoint.
			if (Graph[i][(j + 1) % n].equals(word) && !isNoValid(i, (j + 1) % n, visitedEdges)) { // move right
				distanceMatrix[i][(j + 1) % n] = distance + 1; // store distance for land region
				visitedEdges[i][(j + 1) % n] = true; // set this land region to visited
				q.add(new cords(i, (j + 1) % n, distance + 1));// add this to queue 
			}
			if (!isNoValid(i - 1, j, visitedEdges) && Graph[i - 1][j].equals(word)) { // move up
				visitedEdges[i - 1][j] = true;
				distanceMatrix[i - 1][j] = distance + 1;
				q.add(new cords(i - 1, j, distance + 1)); // add one distance than before
			}
			if (Graph[i][Math.floorMod((j - 1), n)].equals(word)
					&& !isNoValid(i, Math.floorMod((j - 1), n), visitedEdges)) { // move left
				visitedEdges[i][Math.floorMod((j - 1), n)] = true;
				distanceMatrix[i][Math.floorMod((j - 1), n)] = distance + 1;
				q.add(new cords(i, Math.floorMod((j - 1), n), distance + 1));
			}
			if (!isNoValid(i + 1, j, visitedEdges) && Graph[i + 1][j].equals(word)) { // move down
				visitedEdges[i + 1][j] = true;
				distanceMatrix[i + 1][j] = distance + 1;
				q.add(new cords(i + 1, j, distance + 1));
			}
			count++;
		}
		// for (int[] cords : distanceMatrix) {
		// 	System.out.println(Arrays.toString(cords));
		// }
	System.err.println("new continetn");
		return new int[] { count, distanceMatrix[farthestNode.row][farthestNode.col] };
	}

	static void bfsBasePartA(String word, cords origin) {
		bfs(Adjacent, visitedEdges, new cords(origin.row, origin.col, 0), word);
		int m = Adjacent.length;
		int n = Adjacent[0].length;
		int maxContinent = 0;
		int continentDistance = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) { // visit every land region 
				if (Adjacent[i][j].equals(word) && !visitedEdges[i][j]) { //check if its visited
					int[] tmp = bfs(Adjacent, visitedEdges, new cords(i, j, 0), word);
					if (tmp[0] > maxContinent) {
						maxContinent = tmp[0];
						if (!partA) {
							continentDistance = tmp[1];
						}
					} else if (tmp[0] == maxContinent && !partA) {
						if (continentDistance < tmp[1]) {
							continentDistance = tmp[1];
						}
					}
				}
			}
		}
		if (partA) {
			System.out.println(maxContinent);
		} else {
			continentDistance = maxContinent==0?-1:continentDistance;
			System.out.println(maxContinent + " " + continentDistance);
		}
	}

	public static void beginPartA(String s) {
		if (s.equals("PartA")) {
			partA = true;
		}
		try {
			ReadInput();
		} catch (Exception e) {
			System.out.println("check the input data");
			e.printStackTrace();
		}
	}

	public static boolean isNoValid(int row, int col, boolean[][] visited) {
		return (row < 0 || row >= Adjacent.length || visited[row][col]);
	}

	static void ReadInput() throws IOException { // read input
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String input = " ";
		int row = 0;
		String[] matrixSize = bf.readLine().trim().split(" ");
		Adjacent = new String[Integer.parseInt(matrixSize[0])][Integer.parseInt(matrixSize[1])];
		visitedEdges = new boolean[Integer.parseInt(matrixSize[0])][Integer.parseInt(matrixSize[1])];
		while (!(input = bf.readLine()).equals("")) {
			if ((row) < Adjacent.length) {
				Adjacent[row] = input.split("");
				row++;
			} else {
				String[] origin = input.trim().split(" ");
				bfsBasePartA(Adjacent[Integer.parseInt(origin[0])][Integer.parseInt(origin[1])],
						new cords(Integer.parseInt(origin[0]), Integer.parseInt(origin[1])));
				bf.readLine();
				row = 0;
				String s;
				if ((s = bf.readLine()) != null && !s.equals("")) {
					matrixSize = s.split(" ");
					Adjacent = new String[Integer.parseInt(matrixSize[0])][Integer.parseInt(matrixSize[1])];
					visitedEdges = new boolean[Integer.parseInt(matrixSize[0])][Integer.parseInt(matrixSize[1])];
				} else {
					break;
				}
			}
		}

	}

	public static void main(String[] args) {
		Main main = new Main();
		if (args.length == 1 && args[0].equals("PartB")) {
			Main.beginPartA(args[0]);
		} else {
			Main.beginPartA("PartA");
		}
	}

}