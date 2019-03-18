


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread
{
	Socket s=null;
	//private PrintWriter sendMess;
	Scanner receiveMess=null;
	
	public ServerThread(Socket s) throws IOException
	{
		this.s=s;
		receiveMess=new Scanner(s.getInputStream());
	}
	
	public void run()
	{
		try
		{
//			UserList person=new UserList();
			String content=null;
			LogCheck lc=new LogCheck();
			while((content=readFromClient())!=null)
			{
				String[] divide=content.split("`");
				switch(divide[0])
				{
				
				case "i"://information
					System.out.println(content);
					for(int i=0;i<chattingRoom.sl.size();i++)
					{
						//System.out.println("-"+chattingRoom.sl.size()+"-");
						Socket s=chattingRoom.sl.socketarray[i];
						PrintStream ps=new PrintStream(s.getOutputStream());
						ps.println(content);
						
					}
					break;
				case "g"://log in
					System.out.println(divide[1]+" login");
					PrintStream psLogin=new PrintStream(s.getOutputStream());
					if(lc.login(divide[1],divide[2]))
					{					
//						person.add(divide[1]);
						psLogin.println("g`1");
						System.out.println("correct");
						
						for(int i=0;i<chattingRoom.sl.size();i++)
						{
							//System.out.println("-"+chattingRoom.sl.size()+"-");
							Socket s=chattingRoom.sl.socketarray[i];
							PrintStream ps=new PrintStream(s.getOutputStream());
							ps.println("a`"+divide[1]);					
							
						}
						System.out.println(divide[1]+" is online");
					}
					else
					{
						psLogin.println("w`1");
						System.out.println("wrong");
					}
					//psLogin.close();
					break;	
				case "n":
					System.out.println("new account");
					PrintStream psNew=new PrintStream(s.getOutputStream());
					if(lc.newAccount(divide[1],divide[2]))
					{
//						person.add(divide[1]);
						psNew.println("n`1");
						System.out.println("correct");
						
						for(int i=0;i<chattingRoom.sl.size();i++)
						{
							//System.out.println("-"+chattingRoom.sl.size()+"-");
							Socket s=chattingRoom.sl.socketarray[i];
							PrintStream ps=new PrintStream(s.getOutputStream());
							ps.println("a`"+divide[1]);							
							
						}
						System.out.println(divide[1]+"is online");
					}
					
					else 
					{
						psNew.println("e`1");
						System.out.println("already exists");
					}
					//psNew.close();
					break;
				case "x"://exit
//					person.del(divide[1]);
					
					for(int i=0;i<chattingRoom.sl.size();i++)
					{
						//System.out.println("-"+chattingRoom.sl.size()+"-");
						Socket s=chattingRoom.sl.socketarray[i];
						PrintStream ps=new PrintStream(s.getOutputStream());
						ps.println("r`"+divide[1]);
						
					}
					System.out.println(divide[1]+" is offline");
					chattingRoom.sl.del(s);
					break;	
				case "t":
					System.out.println("no valid log in");;
					break;
				
				default: System.out.println("error input");
				}

				
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			chattingRoom.sl.del(s);
		}
	}
	private String readFromClient()
	{
			if(receiveMess.hasNextLine())
			{
				return receiveMess.nextLine();
			}

		return null;
	}
}
