import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

class ExtensionFileFilter extends FileFilter
{
	private String description;
	private ArrayList<String> extensions=new ArrayList<>();
	public void addExtension(String extension)
	{
		if(!extension.startsWith("."))
		{
			extension="."+extension;
			extensions.add(extension.toLowerCase());
		}
	}
	public void setDescription(String aDescription)
	{
		description=aDescription;
	}
	public String getDescription()
	{
		return description;
	}
	public boolean accept(File f)
	{
		if(f.isDirectory())return true;
		String name=f.getName().toLowerCase();
		for(String extension : extensions)
		{
			if(name.endsWith(extension))
			{
				return true;
			}
		}
		return false;
	}
}
