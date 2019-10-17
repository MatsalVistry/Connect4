import java.io.Serializable;

public class Command_From_Server implements Serializable
{
	private int command = 0;
	private Game_Data game =null;
	public static final int UPDATE_GAME 	= 1;
	public static final int CONNECTED_AS_RED 	= 2;
	public static final int CONNECTED_AS_BLACK 	= 3;
	
	public Command_From_Server(int command, Game_Data game)
	{
		this.command	= command;
		this.game		= game;
	}
	
	public int getCommand()
	{
		return command;
	}
	
	public Game_Data getGame()
	{
		return game;
	}
}