import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TTT_Frame extends JFrame 
		implements Runnable, MouseListener
{
	private Game_Data game = null;
	private ObjectOutputStream os = null;
	private BufferedImage buffer = null;
	private char letter;
	private long closeTimerStart = -1; 
	
	
	public TTT_Frame(Game_Data game,ObjectOutputStream os, char letter)
	{
		super("C4");
		this.letter=letter;
		setIgnoreRepaint(true);
		setSize(800,700);
		addMouseListener(this);
		setVisible(true);
		buffer = new BufferedImage(getWidth(),getHeight(), 
			BufferedImage.TYPE_4BYTE_ABGR);
		Thread t = new Thread(this);
		t.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game 	= game;
		this.os		= os;
	}
	
	public void paint(Graphics m)
	{
		Graphics g = buffer.getGraphics();

		g.setColor(Color.YELLOW);
		g.fillRect(0,0,800,700);

		for(int x=0;x<7;x++)
		{
			for(int y=0;y<6;y++)
			{
				if(game.getBoard()[y][x] == 12)
				{
					g.setColor(Color.WHITE);
					g.fillOval((x*100)+50, (y*100)+50, 75, 75);
				}
				if(game.getBoard()[y][x] == 10)
				{
					g.setColor(Color.RED);
					g.fillOval((x*100)+50, (y*100)+50, 75, 75);
				}
				if(game.getBoard()[y][x] == 11)
				{
					g.setColor(Color.BLACK);
					g.fillOval((x*100)+50, (y*100)+50, 75, 75);
				}
			}
		}
		Font f = new Font("Times New Roman",Font.BOLD,25);
		g.setFont(f);
		
		int textX = 200;
		int textY = 650;
		
		if(game.getStatus() == game.WAITING_FOR_BLACK)
			g.drawString("Waiting for BLACK to connect",textX,textY);
		//1	
		else if(game.getStatus() == game.PLAYING && game.getTurn() == 10
			&& letter=='R')
			g.drawString("Your turn.",textX,textY);
		//2	
		else if(game.getStatus() == game.PLAYING && game.getTurn() == 10
			&& letter=='B')
			g.drawString("RED's Move.",textX,textY);
		//3	
		else if(game.getStatus() == game.PLAYING && game.getTurn() == 11
			&& letter=='B')
			g.drawString("Your turn.",textX,textY);
		//4
		else if(game.getStatus() == game.PLAYING && game.getTurn() == 11
			&& letter=='R')
			g.drawString("BLACK's Turn.",textX,textY);
		//5
		else if(game.getStatus() == game.RED_WINS && letter=='R')
			g.drawString("Your Win! Right click for new Game.",textX-100,textY);
		//6
		else if(game.getStatus() == game.RED_WINS && letter=='B')
			g.drawString("Your Lose! Right click for new Game.",textX-100,textY);
		//7
		else if(game.getStatus() == game.BLACK_WINS && letter=='B')
			g.drawString("Your Win! Right click for new Game.",textX-100,textY);
		//8
		else if(game.getStatus() == game.BLACK_WINS && letter=='R')
			g.drawString("Your Lose! Right click for new Game.",textX-100,textY);
		//9
		else if(game.getStatus() == game.CAT)
			g.drawString("Tie Game. Right click for new Game.",textX-100,textY);
		//10	
		else if(game.getStatus()==game.WAITING_RESTART_BLACK && letter=='B')
			g.drawString("RED is ready, right click for new Game.",textX-100,textY);
		//11	
		else if(game.getStatus()==game.WAITING_RESTART_RED && letter=='R')
			g.drawString("BLACK is ready, right click for new Game.",textX-100,textY);
		//12	
		else if(game.getStatus()==game.WAITING_RESTART_BLACK && letter=='R')
			g.drawString("Waiting for BLACK to agree to a new game.",textX-100,textY);
		//13	
		else if(game.getStatus()==game.WAITING_RESTART_RED && letter=='B')
			g.drawString("Waiting for RED to agree to a new game.",textX-100,textY);
		//14	
		else if(game.getStatus()==game.PLAYER_LEFT && letter=='B')
			g.drawString("RED quit. Shuting Down in: "
				+(5-(System.nanoTime()-closeTimerStart)/1000000000L),textX-150,textY);
		//15		
		else if(game.getStatus()==game.PLAYER_LEFT && letter=='R')
			g.drawString("BLACK quit. Shuting Down in: "
				+(5-(System.nanoTime()-closeTimerStart)/1000000000L),textX-150,textY );
		// WRONG
		else
			g.drawString("Program Fails!",textX,textY);
			
		m.drawImage(buffer,0,0,null);
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				Thread.sleep(50);
				paint(this.getGraphics());
				
				if(game.getStatus()==Game_Data.PLAYER_LEFT && closeTimerStart==-1)
				{
					closeTimerStart=System.nanoTime();
				}
				else if(game.getStatus()==Game_Data.PLAYER_LEFT 
					&& (5-(System.nanoTime()-closeTimerStart)/1000000000L)<=0)
				{
					System.exit(4);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in Frame run: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void mouseExited(MouseEvent e)
	{}
	
	public void mouseEntered(MouseEvent e)
	{}
	
	public void mouseClicked(MouseEvent e)
	{}
	
	public void mousePressed(MouseEvent e)
	{
		try
		{
			if(e.getButton()==e.BUTTON1)
			{
			
				int x = e.getX();
				int y = e.getY();
				
				/*if( x>0 && x<200)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,0));
					os.reset();
				}
				else if(x>210 && x<410 && y >0 && y<200)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,0,1));
					os.reset();
				}
				else if(x>420 && x<620 && y >0 && y<200)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,0,2));
					os.reset();
				}
				else if(x>0 && x<200 && y >210 && y<410)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,1,0));
					os.reset();
				}
				else if(x>210 && x<410 && y >210 && y<410)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,1,1));
					os.reset();
				}
				else if(x>420 && x<620 && y >210 && y<410)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,1,2));
					os.reset();
				}
				else if(x>0 && x<200 && y >420 && y<620)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,2,0));
					os.reset();
				}
				else if(x>210 && x<410 && y >420 && y<620)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,2,1));
					os.reset();
				}
				else if(x>420 && x<620 && y >420 && y<620)
				{
					os.writeObject(
						new Command_To_Server(Command_To_Server.MOVE,2,2));
					os.reset();
				}*/
				if (e.getX() > 50 && e.getX() < 125 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,0));
					os.reset();
				}

				else if (e.getX() > 150 && e.getX() < 225) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,1));
					os.reset();
				}

				else if (e.getX() > 250 && e.getX() < 325 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,2));
					os.reset();
				}

				else if (e.getX() > 350 && e.getX() < 425 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,3));
					os.reset();
				}

				else if (e.getX() > 450 && e.getX() < 525 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,4));
					os.reset();
				}

				else if (e.getX() > 550 && e.getX() < 625 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,5));
					os.reset();
				}

				else if (e.getX() > 650 && e.getX() < 725 ) {
					os.writeObject(
							new Command_To_Server(Command_To_Server.MOVE,6));
					os.reset();
				}
			}
			else if(e.getButton()==e.BUTTON3)
			{
				os.writeObject(
					new Command_To_Server(Command_To_Server.NEW_GAME));
				os.reset();
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error in Frame-KeyTyped: "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{}
	
	
}