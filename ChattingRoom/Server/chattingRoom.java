

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class chattingRoom 
{
	private int PORT=6789;
	private String ServerIP="127.0.0.1";
	private PrintWriter sendMess=null;
	
	
	
	private Frame LoginFrame;
	private Label LoginLa1;
	private Label LoginLa2;
	private Label LoginLa3;
	private Label LoginCheck;
	private TextField LoginID;
	private TextField LoginPassword;
	private Button LoginLogin;
	private Button LoginNew;
	private Button LoginExit;
	
	
	private Frame ChattingFrame;
	private Panel ChattingLeft;
	private Panel ChattingRight;
	private Panel ChattingButton;
	private TextArea ChattingMessage;
	private TextArea ChattingTextbox;
	private TextArea ChattingRightLabel;
	private Button ChattingRefresh=new Button("Refresh");
	private Button ChattingClear=new Button("Clear");
	private Button ChattingSend=new Button("Send");
	private Button ChattingExit=new Button("Exit");
	private List personOnline=new List();
	
	
	private void Chatting_init()
	{
		ChattingFrame=new Frame("Chatting");
		ChattingFrame.setBounds(200,150,800,500);
		ChattingFrame.setLayout(new GridLayout(1,2));
		
		ChattingLeft=new Panel();
		ChattingLeft.add(new Label("Message"));
		ChattingMessage=new TextArea(15,50);
		ChattingLeft.add(ChattingMessage);//
		ChattingLeft.add(new Label("Textbox"));
		ChattingTextbox=new TextArea(5,50);
		ChattingLeft.add(ChattingTextbox);
		
		ChattingRight=new Panel();
		ChattingRight.setLayout(new GridLayout(3,1));
			
		ChattingRightLabel=new TextArea();
		ChattingRightLabel.setEditable(false);
		ChattingRightLabel.append("    Welcome to MikeCharlie's chatting room\n");
		ChattingRightLabel.append("    Connecting to the server:"+ServerIP);
			
		ChattingButton=new Panel();
		ChattingButton.setLayout(new GridLayout(2,2));
		ChattingButton.add(ChattingRefresh);
		ChattingButton.add(ChattingClear);
		ChattingButton.add(ChattingSend);		
		ChattingButton.add(ChattingExit);
		
		ChattingRight.add(ChattingRightLabel);
		//personOnline.setName("Online:");
		ChattingRight.add(personOnline);
		ChattingRight.add(ChattingButton);
		
		ChattingFrame.add(ChattingLeft);//,BorderLayout.WEST
		ChattingFrame.add(ChattingRight);
		
		addListener_ChattingwindowClosing();
		addListener_ChattingRefreshClick();
		addListener_ChattingClearClick();
		addListener_ChattingSendClick();
		addListener_ChattingExitClick();
		
		ChattingFrame.setVisible(true);
	}
	
	private void addListener_ChattingRefreshClick()
    {
        ChattingRefresh.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
            	personOnline.add("hello");
            }
        });
    }
	
	private void addListener_ChattingClearClick()
    {
        ChattingClear.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
            	ChattingMessage.setText("");
            	ChattingTextbox.setText("");
            }
        });
    }
	
	private void addListener_ChattingSendClick()
    {
        ChattingSend.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
            	ChattingMessage.append(LogCheck.show_time()+"\n");
            	ChattingMessage.append("->"+ChattingTextbox.getText()+"\n");
            	sendMess.println(ChattingTextbox.getText());
            	sendMess.flush();
            	ChattingTextbox.setText("");
            }
        });
    }
	
	private void addListener_ChattingExitClick()
    {
        ChattingExit.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
    }
		
	private void addListener_ChattingwindowClosing()
	{
		ChattingFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
	        {
				System.exit(0);
	        }
		});
	}
	
	private void Login_init()
	{
		LoginFrame=new Frame("Chatting Room");
		LoginFrame.setBounds(500,300,300,300);
		LoginFrame.setLayout(new GridLayout(9,5));
		
		LoginLa3=new Label("    Welcome!   Log in or create a new account!");
		LoginLa1=new Label("ID");
		LoginLa2=new Label("password");
		LoginID=new TextField();
		LoginCheck=new Label(" ");
		LoginPassword=new TextField();
		LoginPassword.setEchoChar('*');
		LoginLogin=new Button("Log in");
		LoginNew=new Button("New");
		LoginExit=new Button("Exit");
		
		LoginFrame.add(LoginLa3);
		LoginFrame.add(LoginLa1);
		LoginFrame.add(LoginID);
		LoginFrame.add(LoginLa2);		
		LoginFrame.add(LoginPassword);
		LoginFrame.add(LoginCheck);
		LoginFrame.add(LoginLogin);
		LoginFrame.add(LoginNew);
		LoginFrame.add(LoginExit);
		

		
		addListener_LoginwindowClosing();
		addListener_LoginLoginClick();
		addListener_LoginNewClick();
		addListener_LoginExitClick();
		
		LoginFrame.setVisible(true);
	}
	
	
	private void addListener_LoginLoginClick()
    {
        LoginLogin.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                LogCheck lc=new LogCheck();
                if(lc.login(lc.sortFileName(LoginID.getText()),LoginPassword.getText()))
                {
                	
                	LoginFrame.setVisible(false);
                	Chatting_init();
                	personOnline.add(LoginID.getText());
                }
                else LoginCheck.setText("password wrong");
            }
        });
    }
	
	private void addListener_LoginNewClick()
    {
        LoginNew.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                LogCheck lc=new LogCheck();
                if(lc.newAccount(lc.sortFileName(LoginID.getText()),LoginPassword.getText()))
                {
                	LoginFrame.setVisible(false);
                	Chatting_init();
                	personOnline.add(LoginID.getText());
                }
                else LoginCheck.setText("The user name already exists");
            }
        });
    }
	
	private void addListener_LoginExitClick()
    {
        LoginExit.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });
    }
	
	private void addListener_LoginwindowClosing()
	{
		LoginFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
	        {
				System.exit(0);
	        }
		});
	}
	
	private void Server()
	{
		try(ServerSocket ss=new ServerSocket(PORT))
		{
			sl.init();
			for(int i=0;i<5;i++)
			{
				Socket socket=ss.accept();
				sl.add(socket);
				new ServerThread(socket).start();
			}
		}
		catch(IOException ex)
		{
			System.out.println("fail to start the server");
		}
	}
	
	public static SocketList sl=new SocketList();
	
	public static void main(String[] args) throws Exception
	{
			
		chattingRoom cr=new chattingRoom();
		cr.Server();
		
	}
}
