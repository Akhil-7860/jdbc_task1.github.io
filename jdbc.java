import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class basic_task {

	private static Connection connection;
	private static Statement stmt;
	private static PreparedStatement prep;
	private static ResultSet res;
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String url = "jdbc:mysql://localhost:3306/basic_task";
		String username = "root";
		String password = "root";
		
		try {
			connection = DriverManager.getConnection(url,username,password);
			
			do {
				System.out.println("Click the options to select which operation to perform \n 1.Insert\n 2.Update\n 3.Delete\n 4.Display\n 5.Exit\n");
				int op = scan.nextInt();
				if(op==1) {
					Insert();
				}
				else if(op==2) {
					Update();
				}
				else if(op==3) {
					Delete();
				}
				else if(op == 4) {
					Display();
				}
				else if(op==5){
					System.exit(0);
				}
				else {
					System.err.println("Enter a valid Option");
					System.exit(0);
				}
				
			}
			while(true);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				res.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				prep.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				connection.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void Insert() throws SQLException {
		Scanner scan = new Scanner(System.in);
		String sql = "INSERT into task_1(id,name,email,department,salary) values (?,?,?,?,?)";
		prep = connection.prepareStatement(sql);
		System.out.println("Enter Id");
		int n = scan.nextInt();
		scan.nextLine();
		prep.setInt(1,n);
		System.out.println("Enter Name");
		String nam = scan.nextLine();
		prep.setString(2, nam);
		System.out.println("Enter Email");
		String ema = scan.nextLine();
		prep.setString(3,ema);
		System.out.println("Enter Dept");
		String dep = scan.nextLine();
		prep.setString(4, dep);
		System.out.println("Enter sal");
		int sal = scan.nextInt();
		prep.setInt(5,sal);
		int i = prep.executeUpdate();
		System.out.println(i);
	}
	
	
	public static void Update() throws SQLException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the column name");
		String col = scan.nextLine();
		String sql = "UPDATE task_1 set "+col+" =?  where id= ? ";
		prep = connection.prepareStatement(sql);
		System.out.println("Enter the value");
		
		if(col.equals("id") || col.equals("salary")) {
			prep.setInt(1, scan.nextInt());
			scan.nextLine();
		}
		
		else {
			prep.setString(1, scan.nextLine());
		}
		
		System.out.println("Enter the ID");
		prep.setInt(2, scan.nextInt());
		int i = prep.executeUpdate();
		System.out.println(i);
		
	}
	
	
	public static void Delete() throws SQLException {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the column name you want to delete ");
		String col = scan.nextLine();
		String sql = "DELETE from task_1 where "+col+" = ? and id = ? ";
		prep = connection.prepareStatement(sql);
		System.out.println("Enter the value you want to delete ");
		
		if(col.equals("id") || col.equals("salary")) {
			prep.setInt(1, scan.nextInt());
			scan.nextLine();
		}
		
		else {
			prep.setString(1, scan.nextLine());
		}
		
		System.out.println("Enter the ID");
		prep.setInt(2,scan.nextInt());
		int i = prep.executeUpdate();
		System.out.println(i);
	}
	
	public static void Display() throws SQLException {
		
		stmt = connection.createStatement();
		String sql = "select * from task_1";
		res = stmt.executeQuery(sql);
		System.out.println("----------------------------------------------------------------");
		System.out.println("| id | name    |  email              | Department  |  salary   |"); 
		System.out.println("----------------------------------------------------------------");
		
		while(res.next()) {
			int id = res.getInt("id");
			String name = res.getString("name");
			String email = res.getString("email");
			String department = res.getString("department");
			int salary = res.getInt("salary");
			
			System.out.printf("|%-3d |%-9s| %-20s| %-12s| %-9d |\n",id,name,email,department,salary);
		}
		
		System.out.println("----------------------------------------------------------------");
	}

}
