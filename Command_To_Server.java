import java.io.Serializable;

public class Command_To_Server implements Serializable
{
	private int command 					= 0;
	private int c 							= 0;
	public static final int MOVE 			= 1;
	public static final int NEW_GAME 		= 2;

	public Command_To_Server(int command)
	{
		this.command	= command;
		this.c			= -1;
	}
	
	public Command_To_Server(int command, int c)
	{
		this.command	= command;
		this.c			= c;
	}
	
	public int getCommand()
	{	return command;	}


	
	public int getCol()
	{	return c;	}
}