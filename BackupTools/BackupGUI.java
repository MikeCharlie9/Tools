//******************************************************************
//English Description
//
//Start Date:		2018.8.21
//Last Version:		2018.8.23
//CopyRight:		MaChang
//Version:			1.1
//declaration for update:
//	1.1:2018.8.23
//		Add a function that the single file can be backed up, too.
//		Optimize the naming rule of the directory and file, time is added.
//		Attach a hint to the recover button.
//		Judge if the default path is exist. 
//			If yes, go on, else, create a new folder.
//		Add file filter
//	1.0:2018.8.21
//		The framework is established. 
//		Files can be backed up. Unable to restore the backup files.
//				
//*****************************************************************

//*****************************************************************
//用于工程文件备份的小程序	
//开始时间：	2018年8月21号
//结束时间：	2018年8月23号
//版权：		马畅
//版本：		1.1
//版本更新说明
//	1.1：2018年8月23号
//		增加一个功能，能够备份单个文件
//		优化了文件夹和文件的命名规则，增加了时间
//		在恢复按钮添加一个提示
//		判断默认备份路径是否存在，若存在则继续，否则则新建
//		增加文件过滤器
//	1.0：2018年8月21号
//		搭建用户界面初始框架，能实现文件备份的功能，没有加入恢复备份的功能
//		
//*****************************************************************


import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BackupGUI 
{
	private JFrame jf;
	//所有的组件都装在box里面垂直摆放
	private Box box;
	
	//创建文件过滤器，便于对单个文件进行备份
	ExtensionFileFilter filter=new ExtensionFileFilter();

	//在GUI最开头放一个时间
	private JLabel time;
	
	//开头声明
	private JTextArea declare;

	//源路径区域
	private JLabel La1;
	private JPanel pathFrom;
	private JTextField pathStringFrom;
	private JButton pathSelectFrom;
	private JFileChooser fileFrom;
	
	//目标路径区域
	private JLabel La2;
	private JPanel pathTo;
	private JTextField pathStringTo;
	private JButton pathSelectTo;
	private JFileChooser fileTo;
	
	//状态栏区域
	private JLabel La3;
	private JTextArea detail;
	
	//按钮区域
	private JPanel ButtonArea;
	private JButton StartBackup;
	private JButton StartRecover;
	private JButton Exit;
	
	private String defPath="E:\\programBackup";
	
	
	public void init()
	{
		jf=new JFrame("备份工具");
		box=new Box(BoxLayout.Y_AXIS);
		
		filter.addExtension("java");
		filter.addExtension("cpp");
		filter.addExtension("v");
		filter.addExtension("html");
		filter.addExtension("css");
		filter.addExtension("js");
		filter.addExtension("py");
		filter.setDescription
			("源代码(*.java,*.cpp,*.v,*.html,*.css,*.js,*.py)");
		
		//显示时间 年月日
		time=new JLabel();
		time.setText(getFolderName());
		
		//这是一个简单的介绍
		declare=new JTextArea(5,10);
		declare.setText("说明:\n"
					+ "点击<选择路径>时请选择路径或单个文件\n"
					+ "点击<开始备份>执行备份操作，结束后在状态栏会提示：全部文件备份完毕\n"
					+ "点击<还原文件>自动将文件恢复成上一个备份版本\n"
					+ "默认备份到路径"+defPath+"，可修改\n");
		declare.setEditable(false);		
		
		//源文件路径的容器
		La1=new JLabel("请选择源文件路径");
		pathFrom=new JPanel();
		pathStringFrom=new JTextField(40);
		pathSelectFrom=new JButton("选择路径");
		pathFrom.add(pathStringFrom);
		pathFrom.add(pathSelectFrom);
		fileFrom=new JFileChooser(".");
		fileFrom.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//创建文件过滤器，便于对单个文件进行备份
		fileFrom.addChoosableFileFilter(filter);

		
		//目标路径的容器
		La2=new JLabel("请选目标路径");
		pathTo=new JPanel();
		pathStringTo=new JTextField(40);
		//默认路径，如果不存在则新建
		if((new File(defPath)).exists())
			pathStringTo.setText(defPath);
		else
		{
			new File(defPath).mkdir();
			pathStringTo.setText(defPath);
		}
		pathSelectTo=new JButton("选择路径");
		pathTo.add(pathStringTo);
		pathTo.add(pathSelectTo);
		fileTo=new JFileChooser(".");
		fileTo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		//状态栏部分的容器，状态栏含有滚动条
		La3=new JLabel("状态栏");
		detail=new JTextArea(10,10);
		JScrollPane taJsp=new JScrollPane(detail);
		
		//按钮区域的容器
		ButtonArea=new JPanel();
		StartBackup=new JButton("开始备份");
		StartRecover=new JButton("还原文件");
		Exit=new JButton("退出程序");
		ButtonArea.add(StartBackup);
		ButtonArea.add(StartRecover);
		ButtonArea.add(Exit);
		
		//按钮的事件监听
		ActionListener FileFromClickListener=e->
		{
			fileFrom.setCurrentDirectory(new File("."));
			int result=fileFrom.showDialog(jf, "选择路径");
			//如果用户选择了文件，而不是点击取消或者X则可以继续
			//如果不添加这一段判断，在用户点击取消或者X时会出现异常
			if(result==JFileChooser.APPROVE_OPTION)
				pathStringFrom.setText(fileFrom.getSelectedFile().getPath());
		};
		ActionListener FileToClickListener=e->
		{
			fileTo.setCurrentDirectory(new File("E:\\programBackup"));
			int result=fileTo.showDialog(jf, "选择路径");
			if(result==JFileChooser.APPROVE_OPTION)
				pathStringTo.setText(fileTo.getSelectedFile().getPath());
		};
		
		ActionListener StartBackupListener=e->
		{
			CopyStart();
		};
		
		ActionListener StartRecoverListener=e->
		{
			JOptionPane.showConfirmDialog
				(jf,"这个按钮还没有绑定内容\n等待version2.0版本","提示",
				JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
		};
		
		ActionListener ExitListener=e->
		{
			System.exit(0);
		};
		
		
		//添加按钮的事件监听
		pathSelectFrom.addActionListener(FileFromClickListener);
		pathSelectTo.addActionListener(FileToClickListener);	
		StartBackup.addActionListener(StartBackupListener);
		StartRecover.addActionListener(StartRecoverListener);
		Exit.addActionListener(ExitListener);
		
		//按照顺序将容器放在垂直排列的box里面
		box.add(time);
		box.add(declare);
		box.add(La1);
		box.add(pathFrom);
		box.add(La2);
		box.add(pathTo);
		box.add(ButtonArea);
		box.add(La3);
		box.add(taJsp);
		
		jf.add(box);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
	}
	
	//这个方法用来获取时间：时分秒
	private String getTime()
	{
		Calendar c=Calendar.getInstance();
		String str;
		str=c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+" ";
		return str;
	}
	
	//获得年月日时间，用来给文件夹命名时作区分
	private String getFolderName()
	{
		Calendar c=Calendar.getInstance();
		String str;
//		str=c.get(Calendar.HOUR_OF_DAY)+"_"+c.get(Calendar.MINUTE)+"_"+c.get(Calendar.SECOND);
		int month=c.get(Calendar.MONTH)+1;
		str=""+c.get(Calendar.YEAR)+month+c.get(Calendar.DATE);
		return str;
	}
	
	//获得由年月日时分秒构成的时间，用来给备份的单个文件命名
	private String getHMS()
	{
		Calendar c=Calendar.getInstance();
		String str=""+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND);;
		return getFolderName()+str;
	}
	
	//检查路径是否正确，若为空则给出提示
	//因为路径是选择出来的，所以可以不用判断路径是否存在	
	private boolean CheckPath()
	{
		if(pathStringFrom.getText().isEmpty())
		{
			detail.append(getTime()+"请选择源文件路径\n");
			return false;
		}
		else if(pathStringTo.getText().isEmpty())
		{
			detail.append(getTime()+"请选择目标路径\n");
			return false;
		}
		else return true;
	}
		
	//开始拷贝-备份
	private void CopyStart()
	{
		if(CheckPath())
		{
			detail.append(getTime()+"开始备份\n");
			File source=new File(pathStringFrom.getText());
			if(source.isDirectory())
			{
				File f=new File(pathStringFrom.getText()+getFolderName());
				//新建文件夹的名字
				String nf=pathStringTo.getText()+"\\"+f.getName();
				new File(nf).mkdir();		
				detail.append(getTime()+"新建文件夹"+nf+"\n");
				try {
					Copy(pathStringFrom.getText(),nf);
					detail.append(getTime()+"全部文件备份完毕\n\n");				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			else if(source.isFile())
			{
				//nf是新建好的路径，备份文件将会拷贝到这个地方
				String nf=pathStringTo.getText()+"\\File"+getHMS();
				new File(nf).mkdir();
				detail.append(getTime()+"新建文件夹"+nf+"\n");
				//fi是要拷贝的文件
				File fi=new File(pathStringFrom.getText());			
				detail.append(getTime()+"备份"+pathStringFrom.getText()+"\n");
				try {
					copy_file(pathStringFrom.getText(),nf,fi);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				detail.append(getTime()+"备份完成"+nf+"\\"+fi.getName()+"\n");			
				detail.append(getTime()+"全部文件备份完毕\n\n");
				
			}
			else ;
		}
	}
	
	//这是一个递归
	private void Copy(String src,String destination) throws IOException
	{
		File[] file=new File(src).listFiles();
		for(File fi:file)
		{
			//如果是文件就直接复制，如果是文件夹就进入下一层路径，然后递归
			if(fi.isFile())
			{
				detail.append(getTime()+"备份"+src+"\\"+fi.getName()+"\n");
				copy_file(src,destination,fi);
				detail.append(getTime()+"备份完成"+destination+"\\"+fi.getName()+"\n");
			}
			else if(fi.isDirectory())
			{
				//这个地方不能对目标路径进行赋值，不然就错了，我也不知道为啥不能这样
				//destination=destination+"\\"+fi.getName();
				new File(destination+"\\"+fi.getName()).mkdir();
				detail.append(getTime()+"新建文件夹"+destination+"\\"+fi.getName()+"\n");
				src=fi.getPath();
				Copy(src,destination+"\\"+fi.getName());
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
}


