package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminPanel extends User {

	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.conDb();
	PreparedStatement preparedStatement = null;

	public AdminPanel() {
		super();
	}

	public AdminPanel(int id, String tcno, String name, String password, int maas, String type) {
		super(id, tcno, name, password, maas, type);
	}

	public ArrayList<User> getUserList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			Connection con = conn.conDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM public.user ORDER BY id");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
						rs.getInt("maas"), rs.getString("type"));
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean addDoctor(String tcno, String password, String name, String type, int maas) throws SQLException {
		String query = "INSERT INTO public.user" + "(tcno, password, name, type, maas) VALUES" + "(?,?,?,?,?)";
		boolean key = false;
		
		try {
			st = con.createStatement();
			
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, type);
			preparedStatement.setInt(5, maas);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (key)
			return true;
		else
			return false;

	}

	public boolean updateUserList(int id, String tcno, String password, String name, int salary) {
		String query = "UPDATE public.user SET name = ?, tcno = ?, password = ?, maas = ? WHERE id = ?";
		boolean key = false;

		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, salary);
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (key)
			return true;
		else
			return false;

	}

	public boolean deleteUser(int id) {
		String query = "DELETE FROM public.user WHERE id = ?";
		boolean key = false;

		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key)
			return true;
		else
			return false;
	}
}
