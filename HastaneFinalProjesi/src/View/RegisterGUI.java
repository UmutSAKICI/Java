package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_adsoyad = new JLabel("Ad Soyad");
		lbl_adsoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lbl_adsoyad.setBounds(10, 11, 175, 20);
		w_pane.add(lbl_adsoyad);

		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 35, 264, 25);
		w_pane.add(fld_name);

		JLabel llb_tcno = new JLabel("T.C. Numarası");
		llb_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		llb_tcno.setBounds(10, 71, 175, 20);
		w_pane.add(llb_tcno);

		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 95, 264, 25);
		w_pane.add(fld_tcno);

		JLabel lbl_Sifre = new JLabel("Şifre");
		lbl_Sifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lbl_Sifre.setBounds(10, 131, 175, 20);
		w_pane.add(lbl_Sifre);

		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 162, 264, 25);
		w_pane.add(fld_pass);

		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_tcno.getText().length() == 0 || fld_pass.getText().length() == 0
						|| fld_name.getText().length() == 0) {
					Helper.showMessage("fill");
				} else {
					try {
						boolean control = hasta.register(
								fld_tcno.getText(), 
								fld_pass.getText(),
								fld_name.getText());
						if (control) {
							Helper.showMessage("success");
							LoginGUI loginGUI = new LoginGUI();
							loginGUI.setVisible(true);
							dispose();
						} else {
							Helper.showMessage("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_register.setBounds(10, 208, 264, 25);
		w_pane.add(btn_register);

		JButton btn_back = new JButton("Geri Dön");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_back.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_back.setBounds(10, 244, 264, 25);
		w_pane.add(btn_back);
	}
}
