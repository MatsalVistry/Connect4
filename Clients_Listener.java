import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Clients_Listener implements Runnable
{
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Game_Data game;
	
	public Clients_Listener(ObjectOutputStream os, ObjectInputStream is
		,Game_Data game)
	{
		this.is		=is;
		this.os		=os;
		this.game 	= game;
	}
	
	public ObjectInputStream getIS()
	{	return is;	}
	
	public ObjectOutputStream getOS()
	{	return os;	}
	
	public Game_Data getGame()
	{	return game;	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				Command_From_Server x
					=(Command_From_Server)is.readObject();
				
				if(x.getCommand()==x.UPDATE_GAME)
				{
					game.update(x.getGame());
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in Clients Listener: "+ e.getMessage());
			System.out.println("Lost Connection to the Server.");
			System.out.println("Disconnecting");
			e.printStackTrace();
			System.exit(0);
		}
		
	}
}


