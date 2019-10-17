import java.io.*;
import java.net.*;

public class TTT_Server_Main
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(8001);
			Game_Data game = new Game_Data(Game_Data.WAITING_FOR_BLACK);
			
			// x connect Code
			Socket xconnectionToClient = serverSocket.accept();
			ObjectOutputStream xos = new 
				ObjectOutputStream(xconnectionToClient.getOutputStream());
			ObjectInputStream xis = new 
				ObjectInputStream(xconnectionToClient.getInputStream());
			
			xos.writeObject(
				new Command_From_Server(Command_From_Server.CONNECTED_AS_RED,game));
			xos.reset();
			
			Thread t = new Thread(new Servers_Listener(xos,xis,game,10));
			t.start();
			System.out.println("RED has connected");

			// o connect Code
			Socket oconnectionToClient = serverSocket.accept();
			ObjectOutputStream oos = new 
				ObjectOutputStream(oconnectionToClient.getOutputStream());
			ObjectInputStream ois = new 
				ObjectInputStream(oconnectionToClient.getInputStream());
			
			oos.writeObject(new Command_From_Server(Command_From_Server.CONNECTED_AS_BLACK,game));
			oos.reset();
			t = new Thread(new Servers_Listener(oos,ois,game,11));
			t.start();
			System.out.println("BLACK has connected");
			
			game.setStatus(game.PLAYING);
			game.setTurn(10);
			
			Command_From_Server a =
				new Command_From_Server(Command_From_Server.UPDATE_GAME,game);
		
			xos.writeObject(a);
			xos.reset();
			oos.writeObject(a);
			oos.reset();
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}