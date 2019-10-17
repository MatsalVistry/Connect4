import java.io.Serializable;

public class Game_Data implements Serializable
{
	private int status 							= 0;
	private int turn 							= 10;
	public static final int WAITING_FOR_BLACK 	= 0;
	public static final int WAITING_RESTART_BLACK = 6;
	public static final int WAITING_RESTART_RED = 7;
	public static final int PLAYER_LEFT			= 8;
	public static final int PLAYING				= 1;
	public static final int RED_WINS 			= 5;
	public static final int BLACK_WINS 			= 2;
	public static final int CAT 				= 4;
	private int[][] board						= null;
	
	public Game_Data(int status)
	{
		this.status = status;
		turn = 10;
		board = new int[6][7];
		for(int r=0;r<board.length; r++)
			for(int c=0; c<board[0].length; c++)
				board[r][c]= 12;
	}
	
	public void update(Game_Data other)
	{
		this.status = other.getStatus();
		turn = other.getTurn();

		for(int r=0;r<board.length; r++)
			for(int c=0; c<board[0].length; c++)
				board[r][c]=other.getSpot(r,c);
	}




	
	public int getStatus()
	{	return status;	}
	
	public int getTurn()
	{	return turn;	}
	
	public void changeTurns()
	{
		if(getTurn()==10)
			setTurn(11);
		else
			setTurn(10);
	}
	
	public void setStatus(int status)
	{
		this.status = status;	}
	
	public void setTurn(int turn)
	{	this.turn = turn;	}
	
	public int getSpot(int r, int c)
	{	return board[r][c];	}
	
	public void setSpot(int r, int c, char letter)
	{	board[r][c] = letter;	}
	
	public void reset()
	{
		turn = 10;
		status = PLAYING;	
		board = new int[6][7];
		for(int r=0;r<board.length; r++)
			for(int c=0; c<board[0].length; c++)
				board[r][c]=12;
	}
	
	public int getNumberOfRows()
	{
		return board.length;
	}
	
	public int getNumberOfCols()
	{
		return board[0].length;
	}
	public int[][] getBoard() {
		return board;
	}
	
	public boolean isCat()
	{
		// horizontal
		for(int r=0; r<6;r++)
		{
			for(int c=0; c<=3; c++)
			{

				if(board[r][c] == 10 &&board[r][c+1] == 10
						&&board[r][c+2] == 10 &&board[r][c+3] == 10)
					return false;
				else if(board[r][c] == 11 &&board[r][c+1] == 11
						&&board[r][c+2] == 11 &&board[r][c+3] == 11)
					return false;
			}
		}

		// veritical
		for(int r=0; r<=2;r++)
		{
			for(int c=0; c<7; c++)
			{
				if(board[r][c] == 10 &&board[r+1][c] == 10
						&&board[r+2][c] == 10 &&board[r+3][c] == 10)
					return false;
				else if(board[r][c] == 11 &&board[r+1][c] == 11
						&&board[r+2][c] == 11 &&board[r+3][c] == 11)
					return false;
			}
		}
		//
		//
		//
		//
		for(int r=0; r<=2;r++)
		{
			for(int c=3; c<7; c++)
			{

				if(board[r][c] == 10 &&board[r+1][c-1] == 10
						&&board[r+2][c-2] == 10 &&board[r+3][c-3] == 10)
					return false;
				else if(board[r][c] == 11 &&board[r+1][c-1] == 11
						&&board[r+2][c-2] == 11 &&board[r+3][c-3] == 11)
					return false;
			}
		}

		//
		//
		//
		//
		for(int r=0; r<=2;r++)
		{
			for(int c=0; c<=3; c++)
			{

				if(board[r][c] == 10 &&board[r+1][c+1] == 10
						&&board[r+2][c+2] == 10 &&board[r+3][c+3] == 10)
					return false;
				else if(board[r][c] == 11 &&board[r+1][c+1] == 11
						&&board[r+2][c+2] == 11 &&board[r+3][c+3] == 11)
					return false;
			}
		}

		// playing
		for(int r=0; r<6;r++)
		{
			for(int c=0; c<7; c++)
			{

				if(board[r][c] == 12)
					return false;
			}
		}

		return true;
	}
	public boolean dropPiece(int c, int piece)
	{
		for(int r=5; r>=0; r--)
		{
			if(board[r][c] == ' ')
			{
				board[r][c] = piece;
				return true;
			}
		}

		return false;
	}
	public boolean changeSpot(int c)
	{
		int success = 0;

		for(int r=5; r>=0; r--)
		{
			if(board[r][c] == 12)
			{
				System.out.println("B");
				board[r][c] = turn;
				success = 1;
				break;
			}
		}
		if(success==1)
		{
			System.out.println("A");
			changeTurns();
			updateStatus();
			return true;
		}
		return false;

	}
	
	public void updateStatus()
	{
		if(Winner()==10)
		{
			setStatus(Game_Data.RED_WINS);
			System.out.println("RED wins");
		}
		else if(Winner()==11)
		{
			setStatus(Game_Data.BLACK_WINS);
			System.out.println("BLACK wins");
		}
		else if(Winner()==20)
			setStatus(Game_Data.CAT);
		else
			setStatus(Game_Data.PLAYING);
	}
	
	public int Winner()
	{
		// horizontal
		for(int r=0; r<6;r++)
		{
			for(int c=0; c<=3; c++)
			{

				if(board[r][c] == 10 &&board[r][c+1] == 10
						&&board[r][c+2] == 10 &&board[r][c+3] == 10)
					return 10;
				else if(board[r][c] == 11 &&board[r][c+1] == 11
						&&board[r][c+2] == 11 &&board[r][c+3] == 11)
					return 11;
			}
		}

		// veritical
		for(int r=0; r<=2;r++)
		{
			for(int c=0; c<7; c++)
			{
				if(board[r][c] == 10 &&board[r+1][c] == 10
						&&board[r+2][c] == 10 &&board[r+3][c] == 10)
					return 10;
				else if(board[r][c] == 11 &&board[r+1][c] == 11
						&&board[r+2][c] == 11 &&board[r+3][c] == 11)
					return 11;
			}
		}
		//
		//
		//
		//
		for(int r=0; r<=2;r++)
		{
			for(int c=3; c<7; c++)
			{

				if(board[r][c] == 10 &&board[r+1][c-1] == 10
						&&board[r+2][c-2] == 10 &&board[r+3][c-3] == 10)
					return 10;
				else if(board[r][c] == 11 &&board[r+1][c-1] == 11
						&&board[r+2][c-2] == 11 &&board[r+3][c-3] == 11)
					return 11;
			}
		}

		//
		//
		//
		//
		for(int r=0; r<=2;r++)
		{
			for(int c=0; c<=3; c++)
			{

				if(board[r][c] == 10 &&board[r+1][c+1] == 10
						&&board[r+2][c+2] == 10 &&board[r+3][c+3] == 10)
					return 10;
				else if(board[r][c] == 11 &&board[r+1][c+1] == 11
						&&board[r+2][c+2] == 11 &&board[r+3][c+3] == 11)
					return 11;
			}
		}

		// playing
		for(int r=0; r<6;r++)
		{
			for(int c=0; c<7; c++)
			{

				if(board[r][c] == 12)
					return 0;
			}
		}

		return 20;
	}
}
