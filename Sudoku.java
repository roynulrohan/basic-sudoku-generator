class sudoku {

	static int grid[][] = new int[9][9];

	public static void main(String[] args) {
		generate();
		disp();
	}

	// this method resets the board to 0s
	public static void reset() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = 0;
			}
		}
	}

	// this method generates the puzzle
	public static void generate() {
		int isFinished;
		int count = 0; // keeps count of how many tries it took to generate a valid puzzle
		
        /* 
         * first it lays the foundation which is the three subgrids from top left to bottom right.
		 * Then, it tries to fill in the rest of the board. If there is no way, fill() returns -1 and that's when it 
		 * resets, lays a different foundation and tries again until done.
		 * The only difference between foundation() and fill() is that foundation is based on all random numbers and
		 * gives the fill something to work with. The fill method however is counted(for loops) and checked until a num is placed
		*/
		
		do {
			count += 1;
			reset();
			foundation();
			isFinished = fill();
		} while (isFinished != 0);
		
		System.out.println("Tries: "+count+"\n");
	}

	// this method fills in the rest of the board
	public static int fill() {
		int count = 0; // counter of how many matches of a number is found

		// i and j for loops are for each subgrid
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				
				// 2nd subgrid - top middle
				for (int num = 1; num < 10; num++) { // tests every number from 1-9
					count = 0; // resets count

					// checks just the subgrid to see if num is present, if so then it adds to counter
					for (int x = 0; x < 3; x++) {
						for (int y = 3; y < 6; y++) { // 3-6 offset since we're working with the top middle subgrid
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					// checks horizontally and vertically through the entire board
					if (horizontalCheck(num, i) == true || verticalCheck(num, j + 3) == true) {
						count += 1;
					}
					// if number is valid, then it sets that index to num and the breaks the num for loop
					if (count == 0) {
						grid[i][j + 3] = num;
						break;
					}
					// if all numbers from 1-9 are checked and none of them are valid then the method returns -1
					if (num == 9 && count != 0) {
						return -1;
					}
				}
				
				// 3rd subgrid - top right
				// every other subgrid works just the same, the only difference between these are the offset to match the subgrid
				for (int num = 1; num < 10; num++) {
					count = 0;

					for (int x = 0; x < 3; x++) {
						for (int y = 6; y < 9; y++) {
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					if (horizontalCheck(num, i) == true || verticalCheck(num, j + 6) == true) {
						count += 1;
					}

					if (count == 0) {
						grid[i][j + 6] = num;
						break;
					}

					if (num == 9 && count != 0) {
						return -1;
					}
				}

				// 4th subgrid - middle left
				for (int num = 1; num < 10; num++) {
					count = 0;

					for (int x = 3; x < 6; x++) {
						for (int y = 0; y < 3; y++) {
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					if (horizontalCheck(num, i + 3) == true || verticalCheck(num, j) == true) {
						count += 1;
					}

					if (count == 0) {
						grid[i + 3][j] = num;
						break;
					}

					if (num == 9 && count != 0) {
						return -1;
					}
				}
				
				// 6th subgrid - middle right
				for (int num = 1; num < 10; num++) {
					count = 0;

					for (int x = 3; x < 6; x++) {
						for (int y = 6; y < 9; y++) {
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					if (horizontalCheck(num, i + 3) == true || verticalCheck(num, j + 6) == true) {
						count += 1;
					}

					if (count == 0) {
						grid[i + 3][j + 6] = num;
						break;
					}

					if (num == 9 && count != 0) {
						return -1;
					}
				}

				// 7th subgrid - bottom left
				for (int num = 1; num < 10; num++) {
					count = 0;

					for (int x = 6; x < 9; x++) {
						for (int y = 0; y < 3; y++) {
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					if (horizontalCheck(num, i + 6) == true || verticalCheck(num, j) == true) {
						count += 1;
					}

					if (count == 0) {
						grid[i + 6][j] = num;
						break;
					}

					if (num == 9 && count != 0) {
						return -1;
					}
				}

				// 9th subgrid - bottom right
				for (int num = 1; num < 10; num++) {
					count = 0;

					for (int x = 6; x < 9; x++) {
						for (int y = 3; y < 6; y++) {
							if (grid[x][y] == num) {
								count += 1;
							}
						}
					}
					if (horizontalCheck(num, i + 6) == true || verticalCheck(num, j + 3) == true) {
						count += 1;
					}

					if (count == 0) {
						grid[i + 6][j + 3] = num;
						break;
					}

					if (num == 9 && count != 0) {
						return -1;
					}
				}
			}
		}
		return 0; // returns 0 when the entire method is successful
	}

	// this method randomly fills in the top left, middle and bottom right subgrids
	public static void foundation() {
		int randNum; // random number
		boolean exit = false; // exit condition for each subgrid
		int count = 0; // count of how many matches of a certain number is found

		// i and j go from 0-3 since we're working per subgrid
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				exit = false;
				// top left subgrid
				// do loops since we're working with random nums
				do {
					count = 0;
					randNum = (int) (Math.random() * 9) + 1;

					for (int x = 0; x < 3; x++) {
						for (int y = 0; y < 3; y++) {
							if (grid[x][y] == randNum) {
								count += 1;
							}
						}
					}

					if (count == 0) {
						grid[i][j] = randNum;
						exit = true;
					}
				} while (exit == false);

				exit = false;
				// middle subgrid
				do {
					count = 0;
					randNum = (int) (Math.random() * 9) + 1;

					for (int x = 3; x < 6; x++) {
						for (int y = 3; y < 6; y++) {
							if (grid[x][y] == randNum) {
								count += 1;
							}
						}
					}

					if (count == 0) {
						grid[i + 3][j + 3] = randNum;
						exit = true;
					}
				} while (exit == false);

				exit = false;
				// bottom right subgrid
				do {
					count = 0;
					randNum = (int) (Math.random() * 9) + 1;

					for (int x = 6; x < 9; x++) {
						for (int y = 6; y < 9; y++) {
							if (grid[x][y] == randNum) {
								count += 1;
							}
						}
					}

					if (count == 0) {
						grid[i + 6][j + 6] = randNum;
						exit = true;
					}
				} while (exit == false);

				exit = false;
			}
		}
	}

	// this method checks for a given num in the given row (i)
	public static boolean horizontalCheck(int num, int i) {
		for (int j = 0; j < grid.length; j++) {
			if (grid[i][j] == num) {
				return true;
			}
		}
		return false;
	}
	
	// this method checks for a given num in the given column (j)
	public static boolean verticalCheck(int num, int j) {
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][j] == num) {
				return true;
			}
		}
		return false;
	}
	
	// this method displays the board
	public static void disp() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");

				if (j == 2 || j == 5) {
					System.out.print("| ");
				}
			}
			System.out.println();

			if (i == 2 || i == 5) {
				System.out.println("------+-------+------");
			}
		}
	}
}