package xss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File.*;  

public class connect {
	// the length of tag is 12
	static String[] tag = new String[]{"default-src", "script-src", "style-src", "img-src", "connect-src", "font-src", "object-src", "media-src", "frame-src", "sandbox", "report-uri"};
	
	
	// delete front space of string
	public static String delFrontSpace(String input)
	{
		String result = "";
		String regStartSpace = "^[　 ]*"; 
		result = input.replaceAll(regStartSpace, "");
		return result; 
	}
	
    public static void parseHead(String id, String wid, String url, String head, String pid, BufferedWriter out) throws IOException
    {
    	System.out.println(id);
    	//System.out.println(head);
    	String[] split = head.split(";");
    	for (int i = 0; i < split.length; i++)
    	{
    		String noSpace = delFrontSpace(split[i]);
    		for (int x = 0; x < 11; x++)
    		{
    			if (noSpace.indexOf(tag[x]) != -1)
    			{
    				//System.out.println(noSpace);
    				out.write(noSpace + "\r\n");
    			}
    		}
    		//System.out.println(noSpace);
    	}
    }
    public static void main(String args[]) throws IOException {

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
        String dbURL = "jdbc:sqlserver://iw74js5v20.database.windows.net:1433;DatabaseName=Alexa"; 
    
        String userName = "eecs450@iw74js5v20"; // 用户名
        String userPwd = "p@ssw0rd"; // 密码
        //File file = new File("output.txt");
        File file = new File("parse.txt");
        file.createNewFile(); // 创建新文件  
        BufferedWriter out = new BufferedWriter(new FileWriter(file));  
        //out.write("我会写入文件啦\r\n"); // \r\n即为换行

        try {
            // 加载SQLSERVER JDBC驱动程序
            Class.forName(driverName);
            //建立数据库连接
            Connection connect = DriverManager.getConnection(dbURL, userName,userPwd);
            Statement stmt = connect.createStatement();
            //ResultSet rs = stmt.executeQuery("select top 10 * from [dbo].[Page] nolock where Head like '%X-Webkit-CSP%';");
            //ResultSet rs = stmt.executeQuery("select  count(*) from [dbo].[Page] nolock where Head like '%X-Webkit-CSP%';");
            ResultSet rs = stmt.executeQuery("select * from [dbo].[Page] nolock where Head like '%Content-Security-Policy%';");
            ResultSetMetaData metaData = rs.getMetaData();
            // get the count of column
            int columnCount = metaData.getColumnCount();
            /**
            for (int i = 1; i <= columnCount; i++)
            {
            	if (i < 1)
            	{
            		System.out.print(",");
            	}
            	System.out.print(metaData.getColumnLabel(i));
            }
            System.out.println();
            while (rs.next()) {
            	for (int i = 1; i <= columnCount; i++)
            	{
            		if (i < 1)
                		System.out.print(",");
                	System.out.print(rs.getString(i));
            	}
            }
            **/
            while(rs.next())
            {
            	//System.out.println(rs.getString(1));
            	// write into txt file
            	
            	out.write(metaData.getColumnLabel(1) + ", \r\n" + rs.getString(1) + "\r\n");
            	out.write(metaData.getColumnLabel(2) + ", \r\n" + rs.getString(2) + "\r\n");
            	out.write(metaData.getColumnLabel(3) + ", \r\n" + rs.getString(3) + "\r\n");
            	//out.write(metaData.getColumnLabel(6) + ", \r\n" + rs.getString(6) + "\r\n");
            	out.write(metaData.getColumnLabel(8) + ", \r\n" + rs.getString(8) + "\r\n");
            	
            	/**
            	System.out.println(metaData.getColumnLabel(1) + ", \r\n" + rs.getString(1) + "\r\n");
            	System.out.println(metaData.getColumnLabel(2) + ", \r\n" + rs.getString(2) + "\r\n");
            	System.out.println(metaData.getColumnLabel(3) + ", \r\n" + rs.getString(3) + "\r\n");
            	System.out.println(metaData.getColumnLabel(6) + ", \r\n" + rs.getString(6) + "\r\n");
            	System.out.println(metaData.getColumnLabel(8) + ", \r\n" + rs.getString(8) + "\r\n");
            	System.out.println("\r\n");
            	System.out.println("\r\n");
            	**/
            	out.write("Parse Outcome: \r\n");
            	parseHead(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6), rs.getString(8), out);
            	out.write("\r\n");
            	out.write("\r\n");
            }
            out.close();
            //System.out.println("finished!!");
            
            
            
        } catch (ClassNotFoundException e) {
            System.out.print("Error loading SQLServer Driver!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print("get data error!");
            e.printStackTrace();
        }
       
    }
}
