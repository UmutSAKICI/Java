package Model;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Helper.DBConnection;

public class Clinic {
	private int id;
	private String name;
	
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.conDb();
	PreparedStatement preparedStatement = null;
	
	public Clinic() {}
	
	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			Connection con = conn.conDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id, u.tcno, u.type, u.name, u.password FROM public.worker w LEFT JOIN public.user u ON w.user_id = u.id WHERE clinic_id =" + clinic_id);
			while (rs.next()) {
				obj = new User(
						rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), 
						rs.getString("password"), rs.getString("type"));
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Clinic> getPoliklinikList() throws SQLException {
		ArrayList<Clinic> list = new ArrayList<>();
		Clinic obj;
		Connection con = conn.conDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM public.clinic ORDER BY id");
			while (rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		
		
		return list;
	}
	
	public Clinic getFetch(int id) {
		Connection con = conn.conDb();
		Clinic c = new Clinic();
		try {
			st =con.createStatement();
			rs = st.executeQuery("SELECT * FROM public.clinic WHERE id =" + id);
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
		
	}
	
	public boolean addClinic(String name) throws SQLException {
		String query = "INSERT INTO public.clinic" + "(name) VALUES" + "(?)";
		boolean key = false;
		Connection con = conn.conDb();
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
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
	
	public boolean deleteClinic(int id) throws SQLException {
		String query = "DELETE FROM public.clinic WHERE id = ?";
		boolean key = false;
		Connection con = conn.conDb();
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
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
	
	public boolean updateClinic(int id, String name) throws SQLException {
		String query = "UPDATE public.clinic SET name = ? WHERE id = ?";
		boolean key = false;
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
