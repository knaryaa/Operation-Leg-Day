package legDay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;



public class User 
{
	private String user;// uname
	private String password; // upassword
	private String eMail;
	private Connection c;
	private String phone;

	public User(String user, String password) 
	{
		this.user = user;
		this.password = password;
	}

	private String uTier;

	public String getTier()
	{
		return uTier;
	}

	public void connect() throws SQLException
	{
		//String dburl = "jdbc:postgresql://broner.servebeer.com:35151/gymdatabase";
		//String dbuser = "projectlogin";
		//String dbpassword = "projectlogin";

		String dburl = "jdbc:postgresql://localhost:5432/gymdatabase";
		String dbuser = "postgres";
		String dbpassword = "SQL";
		c = DriverManager.getConnection(dburl, dbuser, dbpassword);
		if (c != null) 
		{
			System.out.println("Connected to Database");
		}
	}

	public boolean isEmployee() throws SQLException
	{
		connect();
		String query = "SELECT employee FROM client WHERE Email = '" + eMail + "'";
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);

		while(rs.next())
		{
			String check = "0";
			String isEmp = rs.getString(1);

			if(isEmp.equals(check))
			{
				//user is not employee
				return false;
			}
		}
		//user is employee
		c.close();
		return true;
	}

	//Checks for the user name in the database and compares the data input to the data stored to determine if the login is successful or not
	public boolean loginVerify() throws SQLException
	{
		connect();
		boolean login = false;
		String query = "SELECT * FROM login WHERE uname = '" + user + "'";
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);

		while(rs.next())
		{
			String testuser = rs.getString(1);
			String testpassword = rs.getString(2);              // testpassword IS the saved hash in the database
			System.out.println(testpassword);

			if(testuser.equals(user) && (BCrypt.checkpw(password, testpassword))){      // password is the plaintext pulled from JTextfield
				System.out.println("Login Successful");                                 // used being compared in BCrypt.checkpw Method
				login = true;
			}
		}
		String query2 = "SELECT email FROM relationship WHERE uname = '" + user + "'";
		ResultSet rs2 = st.executeQuery(query2);

		while(rs2.next())
		{
			this.eMail = rs2.getString(1);
			System.out.println(eMail);
		}
		rs2.close();

		rs.close();
		c.close();
		return login;
	}

	public  void getAllInfo(JTable tablePopulateData) throws SQLException
	{
		connect();
		JTable tpd = tablePopulateData;
		String query = "TABLE client";

		Statement st = c.createStatement();

		ResultSet rs = st.executeQuery(query);
		while(rs.next())
		{
			String a = rs.getString(1);
			String b = rs.getString(2);
			String c = rs.getString(3);
			String d = rs.getString(4);
			String e = rs.getString(5);
			String f = rs.getString(6);

			DefaultTableModel dftable = (DefaultTableModel) tpd.getModel();
			Object[] obj =  {a,b,c,d,e,f};

			dftable.addRow(obj);
		}
		rs.close();
		c.close();
	}

	//search query
	public  void getSearchedInfo(JTable tablePopulateData, String searchMail) throws SQLException
	{
		connect();
		JTable tpd = tablePopulateData;
		String query = "SELECT * FROM client WHERE Email LIKE '%"+searchMail+"%'";
		System.out.println(query);

		Statement st = c.createStatement();

		ResultSet rs = st.executeQuery(query);

		while(rs.next())
		{
			String a = rs.getString(1);
			String b = rs.getString(2);
			String c = rs.getString(3);
			String d = rs.getString(4);
			String e = rs.getString(5);
			String f = rs.getString(6);

			DefaultTableModel dftable = (DefaultTableModel) tpd.getModel();
			Object[] obj =  {a,b,c,d,e,f};

			dftable.addRow(obj);
		}
		rs.close();
		c.close();
	}

	public ArrayList<String> getInfo() throws SQLException
	{

		System.out.println("Get Info Start");
		connect();
		ArrayList<String> uinfo = new ArrayList<String>();
		String query = "SELECT * FROM client WHERE Email ='" + eMail + "'";
		System.out.println("Get Info: " + eMail);

		Statement st = c.createStatement();

		ResultSet rs = st.executeQuery(query);

		while(rs.next())
		{
			for(int i=1;i<=6;i++)
			{
				uinfo.add(rs.getString(i));
			}
		}
		this.phone = uinfo.get(4);
		this.uTier = uinfo.get(3);
		rs.close();
		c.close();
		return uinfo;
	}

	public ArrayList<String> getInfo2() throws SQLException
	{

		System.out.println("Get Info Start");
		connect();
		ArrayList<String> uinfo = new ArrayList<String>();
		String query = "SELECT * FROM tier_options WHERE phone_number = (SELECT phone_number "
				+ "FROM client WHERE email ='" + eMail + "')";
		System.out.println("Get Info: " + eMail);

		Statement st = c.createStatement();

		ResultSet rs = st.executeQuery(query);

		while(rs.next())
		{
			for(int i=3;i<=7;i++)
			{
				uinfo.add(rs.getString(i));
			}
		}
		rs.close();
		c.close();
		return uinfo;
	}

	public boolean userExists() throws SQLException
	{
		String cUser=null;
		connect();

		String query = "SELECT uname FROM login WHERE uname='" + user + "'"; 
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);
		c.close();

		while(rs.next())
		{
			cUser=rs.getString(1);
		}
		rs.close();
		if(user.equals(cUser))
		{
			//user does exist
			return true;
		}
		//user does not exist
		return false;
	}


	public void updateTierOptions(ArrayList<String> update) throws SQLException
	{
		connect();

		/* takes user input from GUI for respective variables and inputs the value into the SQL query to update the information
           in the database. Information that is not changed in the GUI is left as the existing value.
		 */
		String query = "UPDATE tier_options set pool = '" + update.get(0) + "' where phone_number = '" + phone + "';"
				+ "UPDATE tier_options set tennis_court = '" + update.get(1) + "' where phone_number = '" + phone + "';"
				+ "UPDATE tier_options set football_field = '" + update.get(2) + "' where phone_number = '" + phone + "';"
				+ "Update tier_options set zoomba_class = '" + update.get(3) + "' where phone_number = '" + phone + "';"
				+ "UPDATE tier_options set yoga_class = '" + update.get(4) + "' where phone_number = '" + phone + "';";


		Statement st = c.createStatement();
		st.executeUpdate(query);

		c.close();

	}

	//populates user information.
	public void createUserInfo(String fName, String lName, String email,String tier, String phoneNumber,String employee) throws SQLException
	{
		String firstName=fName,lastName=lName,eMail2=email,phoneNumber2=phoneNumber,tier2=tier,employee2=employee;
		connect();
		String query = "INSERT INTO client VALUES ('"+firstName+"','"+lastName+"','"+eMail2+"','"+tier2+"','"+phoneNumber2+"','"+employee2+"');"
				+ "INSERT INTO relationship VALUES ('"+user+"','"+eMail2+"');"
				+ "INSERT INTO tier_options VALUES ('"+phoneNumber2+"','"+tier2+"','0','0','0','0','0')";
		Statement st= c.createStatement();
		st.executeUpdate(query);

		c.close();
	}


	public boolean createNewUser() throws SQLException
	{

		boolean flag = false;

		flag = userExists();

		if(flag==false)
		{
			connect();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());                       // BCrypt.hashpw hashes password pulled from JTextfield
			String query = "INSERT INTO login VALUES ('" + user + "', '" + hashed + "')";    // BCrypt.gensalt uses default value 10 for parameter
			Statement st = c.createStatement();                                              // String strong_salt = BCrypt.gensalt(log_rounds)
			st.executeUpdate(query);                                                         // String stronger_salt = BCrypt.gensalt(12)
			c.close();                                                                       // Workload increases exponentially, 2**log_rounds , ranges from 4-31
			return true;
		}
		System.out.println("User Name already exists");
		return false;

	}

	public void update(String fname, String lname, String eMail2, String phone) throws SQLException
	{
		connect();

		String query = "UPDATE client SET first_name = '" + fname + "' WHERE email = '" + eMail + "';"
				+ "UPDATE client SET last_name = '" + lname + "' WHERE email = '" + eMail + "';"
				+ "UPDATE client SET email = '" + eMail2 + "' WHERE email = '" + eMail + "';"
				+ "UPDATE client SET phone_number = '" + phone + "' WHERE email = '" + eMail + "';";
		Statement st = c.createStatement();
		st.executeUpdate(query);
		this.eMail = eMail2;
		c.close();
	}

	public void removeUser(String email, String phone) throws SQLException
	{
		connect();
		String query = "DELETE FROM login WHERE uname = '" + user + "';"
				+ "DELETE FROM client WHERE email = '" + email + "';"
				+ "DELETE FROM relationship WHERE uname = '" + user + "';"
				+ "DELETE FROM tier_options WHERE phone_number = '" + phone + "';";
		Statement st = c.createStatement();
		st.executeUpdate(query);
		c.close();
	}
}
