
public class UserList 
{
	public String[] USER=new String[5];
	
	public void init()
	{
		for(int i=0;i<5;i++)
		{
			USER[i]=null;
		}
	}
	
	public int size()
	{
		int num=0;
		for(int i=0;i<5;i++)
		{
			if(USER[i]!=null)
			{
				num++;
			}
		}
		return num;
	}
	
	public void add(String str)
	{
		for(int i=0;i<5;i++)
		{
			if(USER[i]==null)
			{
				USER[i]=str;
				break;
			}
		}
	}
	
	public void del(String str)
	{
		int pos=0;
		for(int i=0;i<5;i++)
		{
			if(USER[i].equals(str))
			{
				pos=i;
				break;
			}
		}
		for(int i=pos;i<4;i++)
		{
			USER[i]=USER[i+1];
		}
		USER[4]=null;
	}
}
