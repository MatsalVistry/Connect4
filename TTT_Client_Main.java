import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TTT_Client_Main
{
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		try
		{
			System.out.print("Enter the ip address of the server: ");
			String ip = keyboard.next();
			Socket connectionToServer 
				= new Socket(ip,8001);
		
			ObjectInputStream is = new 
				ObjectInputStream(connectionToServer.getInputStream());
					
			ObjectOutputStream os = new 
				ObjectOutputStream(connectionToServer.getOutputStream());
			
			Command_From_Server cfs = (Command_From_Server)is.readObject();
			
			if(cfs.getCommand()==cfs.CONNECTED_AS_RED)
			{
				System.out.println("logging in as RED");
				Clients_Listener cl = new Clients_Listener(os,is,cfs.getGame());
				Thread t = new Thread(cl);
				t.start();
				new TTT_Frame(cfs.getGame(),os,'R');
			}
			else
			{
				System.out.println("logging in as BLACK");
				Clients_Listener cl = new Clients_Listener(os,is,cfs.getGame());
				Thread t = new Thread(cl);
				t.start();
				new TTT_Frame(cfs.getGame(),os,'B');
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in main: "+e.getMessage());
			e.printStackTrace();
		}
	}
}