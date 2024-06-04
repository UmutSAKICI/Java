package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private JPasswordField fld_hastaPass;
	private DBConnection conn = new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Projesi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel label_logo = new JLabel(new ImageIcon(getClass().getResource("medicine.png")));
		label_logo.setBounds(209, 21, 64, 64);
		w_pane.add(label_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetimine Hoş Geldiniz");
		lblNewLabel.setBounds(114, 83, 255, 37);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 144, 464, 206);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTc = new JLabel("T.C. Numaranız:");
		lblTc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblTc.setBounds(10, 11, 126, 37);
		w_hastaLogin.add(lblTc);
		
		JLabel lblifre = new JLabel("Şifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblifre.setBounds(10, 59, 126, 37);
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setForeground(Color.BLACK);
		fld_hastaTc.setBounds(146, 11, 303, 37);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setBounds(10, 124, 142, 23);
		w_hastaLogin.add(btn_register);
		
		JButton btn_hastaLogin = new JButton("Giriş Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMessage("fill");
				} else {
					boolean key = true;
					try {
						Connection con = conn.conDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM public.user");
						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) 
							{
								if (rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					if (key) {
						Helper.showMessage("Böyle bir kullanıcı yok, lütfen kayıt olunuz!");
					}
				}
			}
		});
		btn_hastaLogin.setBounds(162, 124, 142, 23);
		w_hastaLogin.add(btn_hastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(146, 59, 303, 37);
		w_hastaLogin.add(fld_hastaPass);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Girişi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JLabel lblTc_1 = new JLabel("T.C. Numaranız:");
		lblTc_1.setBounds(10, 11, 126, 37);
		lblTc_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		w_doctorLogin.add(lblTc_1);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setBounds(146, 14, 303, 37);
		fld_doctorTc.setForeground(Color.BLACK);
		fld_doctorTc.setColumns(10);
		w_doctorLogin.add(fld_doctorTc);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setBounds(10, 59, 126, 37);
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		w_doctorLogin.add(lblifre_1);
		
		JButton btn_DoctorLogin = new JButton("Giriş Yap");
		btn_DoctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMessage("fill");
				} else {
					try {
						Connection con = conn.conDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM public.user");
						System.out.println(rs);
						while (rs.next()) {
							if (fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) 
							{
								if (rs.getString("type").equals("bashekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									
									BashekimGUI bashekimGUI = new BashekimGUI(bhekim);
									bashekimGUI.setVisible(true);
									dispose();
								}
								
								if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_DoctorLogin.setBounds(10, 122, 142, 23);
		w_doctorLogin.add(btn_DoctorLogin);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(146, 59, 303, 37);
		w_doctorLogin.add(fld_doctorPass);
	}
}
