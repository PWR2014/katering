import java.sql.*;
import javax.swing.*;
public class postgresConnection {
	Connection conn=null;
	public static Connection dbConnector()
	{
		try{
			Class.forName("org.postgresql.Driver");
			Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","tutajhaslo");
			JOptionPane.showMessageDialog(null,"Conection SUCCESFUL");
			return conn;
		   } catch(Exception e)
		{
			   JOptionPane.showMessageDialog(null,e);
			   return null;
		}
		
	}
}
