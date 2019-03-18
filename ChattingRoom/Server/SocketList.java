

import java.net.Socket;

public class SocketList 
{
	public Socket[] socketarray=new Socket[5];
	
	public int size()
	{
		int num=0;
		for(int i=0;i<5;i++)
		{
			if(socketarray[i]!=null)
			{
				num++;
			}
		}
		return num;
	}
	public void init()
	{
		for(int i=0;i<5;i++)
		{
			socketarray[i]=null;
		}
	}
	public void add(Socket ss)
	{
		for(int i=0;i<5;i++)
		{
			if(socketarray[i]==null)
			{
				socketarray[i]=ss;
				break;
			}
		}
	}
	public void del(Socket ss)
	{
		int pos=0;
		for(int i=0;i<5;i++)
		{
			if(socketarray[i]==ss)
			{
				pos=i;
				break;
			}
		}
		for(int i=pos;i<4;i++)
		{
			socketarray[i]=socketarray[i+1];
		}
		socketarray[4]=null;
	}
}
