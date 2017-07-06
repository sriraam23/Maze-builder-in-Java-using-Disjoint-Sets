import java.util.Random;
import java.util.Scanner;

public class MazeCreator {

	public static class Maze {
		int rows, columns;
		boolean horWall[][], verWall[][];

		/*
		 * Constructor - creates horizontal walls and vertical walls and assigns
		 * them all to be true.
		 */
		public Maze(int r, int c) {
			rows = r;
			columns = c;

			if (rows > 1) {
				horWall = new boolean[columns][rows];
				for (int j = 0; j < rows; j++) {
					for (int i = 0; i < columns; i++) {
						horWall[i][j] = true;
					}
				}
			}

			if (columns > 1) {
				verWall = new boolean[columns][rows];
				for (int i = 0; i < columns; i++) {
					for (int j = 0; j < rows; j++) {
						verWall[i][j] = true;
					}
				}
			}
		}

		/* toString() function - prints out the maze. */
		public String toString() {
			int i, j;
			String s = "  ";

			// Creating the bottom right exit point
			horWall[columns - 1][rows - 1] = false;

			// Top wall.
			for (i = 0; i < columns - 1; i++) {
				s = s + " _";
			}
			s = s + " \n";

			// Maze interior + Bottom wall.
			for (j = 0; j < rows; j++) {
				s = s + "|";
				for (i = 0; i < columns; i++) {
					if (horWall[i][j]) {
						s = s + "_";
					} else {
						s = s + " ";
					}
					if (i < columns - 1) {
						if (verWall[i][j]) {
							s = s + "|";
						} else {
							s = s + " ";
						}
					}
				}
				s = s + "|\n";
			}
			return s + "\n";
		}

		/*
		 * Function removewall - sets an internal wall which is true to false
		 * and returns success. Else returns failure.
		 */
		public boolean removewall(int r, int c, int dir) {
			if (dir == 0) {
				if (horWall[r][c] == true) {
					horWall[r][c] = false;
					return true;
				} else
					return false;
			} else {
				if (verWall[r][c] == true) {
					verWall[r][c] = false;
					return true;
				} else
					return false;
			}
		}
	}

	public static void main(String[] args) {
		int rows, columns;
		Scanner s = new Scanner(System.in);

		System.out
				.println("Enter the number of rows in the maze (at least 2):");
		rows = s.nextInt();
		while (rows < 2) {
			System.out
					.println("Number of rows entered is less than 2. Please enter again:");
			rows = s.nextInt();
		}

		System.out
				.println("Enter the number of columns in the maze (at least 2):");
		columns = s.nextInt();
		while (columns < 2) {
			System.out
					.println("Number of columns entered is less than 2. Please enter again:");
			columns = s.nextInt();
		}

		Maze maze = new Maze(rows, columns);

		 System.out.println("The grid generated is:");
		 System.out.print(maze);

		Random r1 = new Random();
		Random r2 = new Random();

		int size = rows * columns;
		int internal_wall_row, internal_wall_column, cell1_row, cell1_column, cell2_row, cell2_column;
		int cell1, cell2, set1, set2;

		DisjointSets ds = new DisjointSets(rows * columns);

		// Creating the maze
		while (size > 1) {
			int dir = r1.nextInt(2);
			if (dir == 0) {
				// Dir = 0 means an internal horizontal wall
				internal_wall_row = r2.nextInt(columns);
				internal_wall_column = r2.nextInt(rows - 1);

				// Cell1 row and column values
				cell1_row = internal_wall_column + 1;
				cell1_column = internal_wall_row + 1;

				// Cell2 row and column values
				cell2_row = internal_wall_column + 2;
				cell2_column = internal_wall_row + 1;

				// Finding the actual cell1 and cell2
				cell1 = (cell1_row - 1) * columns + cell1_column - 1;
				cell2 = (cell2_row - 1) * columns + cell2_column - 1;
			} else {
				// Dir = 1 means an internal vertical wall
				internal_wall_row = r2.nextInt(columns - 1);
				internal_wall_column = r2.nextInt(rows);

				// Cell1 row and column values
				cell1_row = internal_wall_column + 1;
				cell1_column = internal_wall_row + 1;

				// Cell2 row and column values
				cell2_row = internal_wall_column + 1;
				cell2_column = internal_wall_row + 2;

				// Finding the actual cell1 and cell2
				cell1 = (cell1_row - 1) * columns + cell1_column - 1;
				cell2 = (cell2_row - 1) * columns + cell2_column - 1;
			}

			set1 = ds.find(cell1);
			set2 = ds.find(cell2);
			if (set1 != set2) {
				// We can remove the wall
				if (maze.removewall(internal_wall_row, internal_wall_column,
						dir) == true) {
					size--;
					ds.union(set1, set2);
				}
			}
		}
		System.out.println("The maze generated is:");
		System.out.print(maze);
	}
}
