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
	public void recursive(row, col)
	{
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
				puzzle[row][col] = arr[i];
				
				if(col == MAX_COLS)
					recursive(row + 1, 0);
				else
					recursive(row, col+1);
					
				if(allFailed)
					i = 0; // reset and regenerate
			}
		}
		
		// if EACH number 1-9 fails the test- we need to backtrack
		allFailed = true;
		genRan();
	}
	
	/// ************************** helpers ****************************
	
	public void genRan()
	{
		// put random numbers in arr
		for(int i = 0; i < randoms.length; i++)
			randoms[i] = (int)(Math.random()*ROWS + 1);
	}
	
	public boolean rowOk(int num, int row)
	{
		for(int i = 0; i < MAX_ROWS; i++)
		{
			if(puzzle[row][i] == num)
				return false;
		}
		
		return true;
	}
	
	public boolean colOk(int num, int col)
	{
		for(int i = 0; i < MAX_COLS; i++)
		{
			if(puzzle[i][col] == num)
				return false;
		}
	}
	
	public boolean gridOk(int num, int row, int col)
	{
		row 
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
