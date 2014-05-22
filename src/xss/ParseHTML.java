package xss;

import junit.framework.Assert;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;  
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;  

public class ParseHTML 
{
	@Test
	public void getHTML() throws Exception 
	{
	    File file = new File("facebook.txt");
        file.createNewFile();
        
		// Simulate chrome browser
	    final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	    final HtmlPage page = webClient.getPage("http://www.facebook.com");
	    
	    final String pageAsXml = page.asXml();
	    //System.out.println(pageAsXml);
	    
        BufferedWriter out = new BufferedWriter(new FileWriter(file));  
        out.write(pageAsXml);
        out.close();
        
	    webClient.closeAllWindows();
	}
	
	public static void main(String args[]) throws Exception
	{
		ParseHTML parse = new ParseHTML();
		parse.getHTML();
	}
}
