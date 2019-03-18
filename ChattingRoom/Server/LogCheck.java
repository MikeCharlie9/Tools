

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class LogCheck 
{
	private String accountPath="E:\\test\\socket\\account";
	public String sortFileName(String str)
	{
		String FileName=new String();
		FileName=str+".id";
		return FileName;
	}
	public boolean login(String userName,String userPassword)
	{
		String path=accountPath+"\\"+userName;
		File user=new File(path);
		if(!user.exists())return false;
		else 
		{
			String str="";
			try {
				BufferedReader fi=new BufferedReader(new FileReader(path));
				try {
					str=fi.readLine();
					
					fi.close();
									
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(str);
			if(str.equals(userPassword))return true;
			else return false;
		}
		
	}
	public boolean newAccount(String userName,String userPassword)
	{
		String path=accountPath+"\\"+userName;
		File user=new File(path);
		if(user.exists())return false;
		else 
		{
			try {
				user.createNewFile();
				PrintWriter fi=new PrintWriter(new FileWriter(path));
				fi.println(userPassword);
				fi.append(LogCheck.show_time()+" New");
				fi.close();				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}
	
	public static String show_time()
	{
		Calendar c=Calendar.getInstance();
		String str=new String();
		String str1=new String();
		String str2=new String();
		str1=c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE);
		str2=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
		str=str1+" "+str2;
		return str;
	}
}
