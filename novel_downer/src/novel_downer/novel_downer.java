package novel_downer;
import java.util.*;
import java.io.*;
import java.net.*;

public class novel_downer {
	public static void main(String[] args)
	{
		String url = "http://www.daomubiji.com/qi-xing-lu-wang-01.html";
		html chapter = new html(url);
		//System.out.println(chapter.getTitle());
		//System.out.println(chapter.getnextURL());
		chapter.getArticle();
	}
	
}

class html{
	private URL url;
	private String nexturl;
	private String title;
	private File file;
	
	html(String url)
	{	
		try
		{
			this.url = new URL(url);
		}
		catch(Exception e)
		{
			System.out.println("Cannot create url :\n"+e);
		}
	}
	
	private String gettxt(String tag1, String tag2, BufferedReader content)
	{
		String buf;
		String txt;
		int head=-1,tail=-1;
		
		try 
		{
			while((buf=new String(content.readLine()))!=null)
			{
				if((head = buf.indexOf(tag1))!=-1)
					break;
			}
			tail = buf.indexOf(tag2, head+tag1.length());
			txt = new String(buf.substring(head+tag1.length(), tail));
		}
		catch(Exception e)
		{
			txt = new String(e.toString());
		}
		return txt;
	}

	
	public String getTitle()
	{
		BufferedReader content;
		try 
		{
			content = new BufferedReader(new InputStreamReader( this.url.openStream(),"utf-8"));
			title = new String(gettxt("<title>","</title>", content));
		}
		catch(Exception e)
		{
			title = new String(e.toString());
		}
		return title;
	}
	
	public String getArticle()
	{
		BufferedReader content;
		String arti = "";
		String buf;
		String headtag = "<article class=\"article-content\">";
		String tailtag = "</article>";

		try
		{
			content = new BufferedReader(new InputStreamReader( this.url.openStream(),"utf-8"));
			while((buf=new String(content.readLine()))!=null)
			{
				if((buf.indexOf(headtag))!=-1)
					break;
			}
			while(((buf=new String(content.readLine()))!=null)&&(buf.indexOf(tailtag)==-1))
			{
				arti += gettxt("<p>", "</p>", new BufferedReader(new InputStreamReader( new ByteArrayInputStream(buf.getBytes()))));
				arti +="\t\n";
			}
			System.out.println(arti);
		}
		catch(Exception e)
		{
			System.out.println("get article error : "+e);
		}
		
		return arti;
	}
	
	public void createFile(String path)
	{
		try
		{
			file = new File(path+title+".txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getnextURL()
	{
		BufferedReader content;
		try 
		{
			content = new BufferedReader(new InputStreamReader( this.url.openStream(),"utf-8"));
			nexturl = new String(gettxt("ÏÂÒ»Æª<br><a href=\"", "\" rel=\"next\">", content));
		}
		catch(Exception e)
		{
			nexturl = new String(e.toString());
		}

		return nexturl;
	}
}
