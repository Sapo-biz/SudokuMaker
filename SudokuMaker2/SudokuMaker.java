/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Jason He
 *	@version 1/21/26
 *
 */
public class SudokuMaker {

	private int[][] puzzle;
	int row, col;
	boolean allFailed;
	int[] randoms = new int[9];
	
	private final int MAX_ROWS = 9; 
	private final int MAX_COLS = 9;
	
	public SudokuMaker()
	{
		row = 0;
		col = 0;
		allFailed = false;
		
		puzzle = new int[MAX_ROWS][MAX_COLS];
		
		for(int i = 0; i < MAX_ROWS ; i++)
		{
			for(int j = 0; j < MAX_COLS; j++)
			{
				puzzle[i][j] = 0;
			}
		}
	}
	
	public static void main(String[] args)
	{
		SudokuMaker sm = new SudokuMaker();
		sm.runIt();
	}
	
	public void runIt()
	{
		printPuzzle();
		
		genRan();
		
		recursive(0, 0);
	}
	
	
	
	// logic looks good for now
	public void recursive(int row, int col)
	{
		int failCount = 0;
		genRan();
		
		// base case
		if (row == MAX_ROWS-1 && col == MAX_COLS) // 8 and 9 (indices, 1 lower)
		{
			printPuzzle();
		}
		
		// lets check each random num!
		for(int i = 0; i < randoms.length; i++)
		{
			if ( rowOk(randoms[i], row) && colOk(randoms[i], col) && 
				gridOk(randoms[i], row, col) )
			{
				puzzle[row][col] = randoms[i];
				// System.out.print("1");
				printPuzzle();
				
				if(col == MAX_COLS-1)
					recursive(row + 1, 0);
				else
					recursive(row, col+1);
			}
			else
				failCount++;
				
		}
		
		// if EACH number 1-9 fails the test- we need to backtrack
		if(failCount == randoms.length)
		{
			allFailed = true;
			
		}
	}
	
	/// ************************** helpers ****************************
	
	// we want to generate ALL NUMBERS 1-9 in RANDOM ORDER
	public void genRan()
	{
		int ranDex1 = -1;
		int ranDex2 = -1;
		int temp = -1;
		// generate 1-9
		for(int i = 0; i < randoms.length; i++) // 9
		{
			randoms[i] = i;
		}
		
		// 100 = generic, huge number. we want max randomization
		for(int k = 0; k < 100; k++) 
		{
			ranDex1 = (int)(Math.random()*(MAX_ROWS) + 0);
			ranDex2 = (int)(Math.random()*(MAX_ROWS) + 0);
			
			temp = randoms[ranDex1];
			randoms[ranDex1] = randoms[ranDex2];
			randoms[ranDex2] = temp;
		}
	}
	
	public boolean rowOk(int num, int row)
	{
		for(int i = 0; i < MAX_ROWS; i++)
		{
			if(puzzle[row][i] == num)
				return false;
		}
		
		System.out.println("row failed");
		return true;
	}
	
	public boolean colOk(int num, int col)
	{
		for(int i = 0; i < MAX_COLS; i++)
		{
			if(puzzle[i][col] == num)
				return false;
		}
		
		System.out.println("col failed");
		return true;
	}
	
	// calculate "box" numbers - 
	// [0] [1] [2]
	// [3] [4] [5]
	// [6] [7] [8]
	// each one of these boxes is a 3x3 grid
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
		
		System.out.println("grid failed");
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
