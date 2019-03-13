package file_process;

//import static java.util.Calendar.*;

public class file_process 
{
	public static void main(String[] args)throws Exception
	{
		
		//System.out.println(c.get(YEAR)+","+c.get(MONTH)+","+c.get(DATE));
		//System.out.println(c.get(HOUR)+","+c.get(MINUTE)+","+c.get(SECOND));
		//System.out.println(Calendar.DATE);
		
		Copy copy_program=new Copy();
		copy_program.setsrc("G:\\file_test");
		copy_program.setdes("G:\\file_to");
		copy_program.setCopyStart();
				
		//System.out.println(c.getTime());
		//c.get(YEAR)+"y"+c.get(MONTH)+"m"+c.get(DATE)+"d"
	}
}
