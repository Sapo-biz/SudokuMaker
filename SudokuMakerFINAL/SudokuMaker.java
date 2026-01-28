/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Jason He
 *	@version 1/21/26
 *
 */
public class SudokuMaker 
{
	private int[][] puzzle; // the main puzzle for the program, a 2d array
	
	private final int MAX_ROWS = 9; // max rows = 9
	private final int MAX_COLS = 9; // max cols = 9
	
	/**
	 * constructor
	 */
	public SudokuMaker()
	{
		puzzle = new int[MAX_ROWS][MAX_COLS];
		
		// init
		for(int i = 0; i < MAX_ROWS ; i++)
		{
			for(int j = 0; j < MAX_COLS; j++)
			{
				puzzle[i][j] = 0;
			}
		}
	}
	
	/**
	 * main
	 */
	public static void main(String[] args)
	{
		SudokuMaker sm = new SudokuMaker();
		sm.runIt();
	}
	
	/**
	 * prints and calls some helpers
	 */
	public void runIt()
	{
		System.out.println("Sudoku puzzle");
		if (createPuzzle(0, 0))
			printPuzzle();
	}
	
	/**
	 * creates the puzzle using recursion and backtracking
	 * 
	 * @param row (the current row)
	 * @param col (the current col)
	 * @return true if solution found, false otherwise
	 */
	public boolean createPuzzle(int row, int col)
	{
		// wrap to next row
		if (col == MAX_COLS) 
		{
			row++;
			col = 0;
		}
		
		// base case 
		if (row == MAX_ROWS) 
		{
			return true;
		}
		
		int[] myRandoms = getRandomNumbers();
		
		// check each random num!
		for(int i = 0; i < myRandoms.length; i++)
		{
			if ( rowOk(myRandoms[i], row) && colOk(myRandoms[i], col) && 
				gridOk(myRandoms[i], row, col) )
			{
				puzzle[row][col] = myRandoms[i];
				
				// recursive step
				if (createPuzzle(row, col + 1))
					return true;
				
				// backtrack if fail
				puzzle[row][col] = 0;
			}
		}
		
		return false;
	}
	
	/// ************************** helpers ****************************
	
	/**
	 * we want to generate ALL NUMBERS 1-9 in RANDOM ORDER
	 * 
	 * @return an array of random integers including 1-9 in random order
	 */
	public int[] getRandomNumbers()
	{
		int[] nums = new int[9];
		int ranDex1 = -1;
		int ranDex2 = -1;
		int temp = -1;
		
		// generate 1-9
		for(int i = 0; i < nums.length; i++) // 9
		{
			nums[i] = i + 1;
		}
		
		// 100 = generic, huge number. we want max randomization
		for(int k = 0; k < 100; k++) // NOT magic number
		{
			ranDex1 = (int)(Math.random()*(MAX_ROWS) + 0);
			ranDex2 = (int)(Math.random()*(MAX_ROWS) + 0);
			
			temp = nums[ranDex1];
			nums[ranDex1] = nums[ranDex2];
			nums[ranDex2] = temp;
		}
		
		return nums;
	}
	
	/**
	 * checks if the row is okay by comparing the value to other numbers
	 * in the row. 
	 * 
	 * @return true if no other are the same, false otherwise
	 */
	public boolean rowOk(int num, int row)
	{
		for(int i = 0; i < MAX_ROWS; i++)
		{
			if(puzzle[row][i] == num)
				return false;
		}
		
		return true;
	}
	
	/**
	 * checks if the col is okay by comparing the value to other numbers
	 * in the col. 
	 * 
	 * @return true if no other are the same, false otherwise
	 */
	public boolean colOk(int num, int col)
	{
		for(int i = 0; i < MAX_COLS; i++)
		{
			if(puzzle[i][col] == num)
				return false;
		}
		
		return true;
	}
	
	// calculate "box" numbers - 
	// [0] [1] [2]
	// [3] [4] [5]
	// [6] [7] [8]
	// each one of these "boxes" is a 3x3 grid
	
	/**
	 * calculate "box" numbers - 
	 * [0] [1] [2]
	 * [3] [4] [5]
	 * [6] [7] [8]
	 * each one of these "boxes" is a 3x3 grid 
	 * 
	 * @return true if no other are the same, false otherwise
	 */
	public boolean gridOk(int num, int row, int col)
	{
		int topLeftRowIndex = 0;
		int topLeftColIndex = 0;
		
		topLeftRowIndex = row - row%3;
		topLeftColIndex = col - col%3;
		
		for(int i = topLeftRowIndex; i < topLeftRowIndex + 3; i++)
		{
			for(int j = topLeftColIndex; j < topLeftColIndex + 3; j++)
			{
				if(puzzle[i][j] == num)
					return false;
			}
		}
		
		return true;
	}
		
	/// ******************** printers *********************************	
		
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
}
