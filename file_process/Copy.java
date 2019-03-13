package file_process;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Copy 
{
	/*
	private Frame fra;
	private Button bt1;
	private Button bt2;
	*/
	private String path_from;
	private String path_to;
	
	public void setsrc(String src)
	{
		//获得要复制的文件夹路径
		this.path_from=src;
	}
	public void setdes(String des)
	{
		//获得目标路径
		this.path_to=des;
	}
	public void setCopyStart() throws IOException
	{
		//开始复制，计算用时
		Calendar c=Calendar.getInstance();
		System.out.println(c.getTime());
		long start_time=System.currentTimeMillis();
		//System.out.println(start_time);
		
		copy_start();
		check_path(path_from,path_to);
		System.out.println("复制完成！");
		
		long end_time=System.currentTimeMillis();
		//System.out.println(end_time);
		System.out.println("用时："+(end_time-start_time)+"ms");
	}
	private void copy_start()
	{
		//如果目标路径不存在则新建路径
		new File(path_to).mkdir();
		System.out.println("正在开始复制:");
	}
	private void check_path(String src,String destination) throws IOException
	{
		File[] file=new File(src).listFiles();
		for(File fi:file)
		{
			//如果是文件就直接复制，如果是文件夹就进入下一层路径，然后递归
			if(fi.isFile())
			{
				copy_file(src,destination,fi);
				System.out.println("成功复制:"+fi.getPath());
			}
			else if(fi.isDirectory())
			{
				//这个地方不能对目标路径进行赋值，不然就错了，我也不知道为啥不能这样
				//destination=destination+"\\"+fi.getName();
				System.out.println("正在创建目录:"+destination);
				new File(destination+"\\"+fi.getName()).mkdir();				
				src=fi.getPath();
				check_path(src,destination+"\\"+fi.getName());
			}
			else ;
		}
	}
	private void copy_file(String src,String destination,File f) throws IOException
	{
		//进行复制
		FileInputStream f_from=new FileInputStream(f);
		FileOutputStream f_to=new FileOutputStream(destination+"\\"+f.getName());
		byte[] temp=new byte[1024];
		int hasread=0;
		while((hasread=f_from.read(temp))>0)
		{
			f_to.write(temp,0,hasread);
		}
		f_from.close();
		f_to.close();
	}
	/*
	private void init()
	{
		fra=new Frame("CopyPanel");
		fra.setBounds(500,500,500,500);
        fra.setLayout(new GridLayout(15,5));
        bt1=new Button("确定");
        bt2=new Button("取消");
        fra.add(bt1);
        fra.add(bt2);
        
        fra.setVisible(true);
	}
	*/
}
