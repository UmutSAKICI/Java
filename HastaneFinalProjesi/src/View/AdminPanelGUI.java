package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import Model.AdminPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminPanelGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable aPanel_Table;
	private JTextField Tcno_TextField;
	private JTextField Sifre_TextField;
	private JTextField AdSoyad_TextField;
	private JTextField selected_DoctorID;
	private JTextField Maas_TextField;

	private AdminPanel adminPanel = new AdminPanel();
	private DefaultTableModel userModel = null;
	private Object[] userData = null;

	String secilenRutbe = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPanelGUI frame = new AdminPanelGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public AdminPanelGUI() throws SQLException {
		AdminPanel adminPanel = new AdminPanel();

		userModel = new DefaultTableModel();
		Object[] colUser = new Object[5];
		colUser[0] = "ID";
		colUser[1] = "Ad Soyad";
		colUser[2] = "TC No";
		colUser[3] = "Şifre";
		colUser[4] = "Maaş";
		userModel.setColumnIdentifiers(colUser);

		userData = new Object[5];
		for (int i = 0; i < adminPanel.getUserList().size(); i++) {
			userData[0] = adminPanel.getUserList().get(i).getId();
			userData[1] = adminPanel.getUserList().get(i).getName();
			userData[2] = adminPanel.getUserList().get(i).getTcno();
			userData[3] = adminPanel.getUserList().get(i).getPassword();
			userData[4] = adminPanel.getUserList().get(i).getMaas();

			userModel.addRow(userData);
		}

		setTitle("Admin Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 289, 393);
		contentPane.add(scrollPane);

		aPanel_Table = new JTable(userModel);
		scrollPane.setViewportView(aPanel_Table);
		aPanel_Table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					selected_DoctorID.setText(aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});

		aPanel_Table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 0).toString());
					String selectName = aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 1).toString();
					String selectTcno = aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 2).toString();
					String selectPass = aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 3).toString();
					int selectSalary = Integer
							.parseInt(aPanel_Table.getValueAt(aPanel_Table.getSelectedRow(), 4).toString());

					boolean control = adminPanel.updateUserList(selectID, selectTcno, selectPass, selectName,
							selectSalary);
					if (control) {
						Helper.showMessage("success");
					}
				}
			}
		});

		JLabel lbl_ustBaslik = new JLabel("Yönetim Paneli");
		lbl_ustBaslik.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_ustBaslik.setBounds(10, 11, 208, 20);
		contentPane.add(lbl_ustBaslik);

		JLabel lbl_doktorEkle = new JLabel("Kişi Ekle");
		lbl_doktorEkle.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_doktorEkle.setBounds(309, 37, 100, 20);
		contentPane.add(lbl_doktorEkle);

		Tcno_TextField = new JTextField();
		Tcno_TextField.setBounds(444, 70, 100, 30);
		contentPane.add(Tcno_TextField);
		Tcno_TextField.setColumns(10);

		JLabel lbl_TcNo = new JLabel("T.C. No");
		lbl_TcNo.setBounds(309, 70, 100, 30);
		contentPane.add(lbl_TcNo);

		JLabel lbl_Sifre = new JLabel("Şifre");
		lbl_Sifre.setBounds(309, 111, 100, 30);
		contentPane.add(lbl_Sifre);

		Sifre_TextField = new JTextField();
		Sifre_TextField.setColumns(10);
		Sifre_TextField.setBounds(444, 111, 100, 30);
		contentPane.add(Sifre_TextField);

		JLabel lbl_AdSoyad = new JLabel("Adı Soyadı");
		lbl_AdSoyad.setBounds(309, 152, 100, 30);
		contentPane.add(lbl_AdSoyad);

		AdSoyad_TextField = new JTextField();
		AdSoyad_TextField.setColumns(10);
		AdSoyad_TextField.setBounds(444, 152, 100, 30);
		contentPane.add(AdSoyad_TextField);

		JLabel lblDoktorId = new JLabel("Doktor ID");
		lblDoktorId.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblDoktorId.setBounds(309, 363, 100, 30);
		contentPane.add(lblDoktorId);

		selected_DoctorID = new JTextField();
		selected_DoctorID.setEnabled(false);
		selected_DoctorID.setColumns(10);
		selected_DoctorID.setBounds(444, 367, 100, 25);
		contentPane.add(selected_DoctorID);

		JButton btn_delDoctor = new JButton("Sil");
		btn_delDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected_DoctorID.getText().length() == 0) {
					Helper.showMessage("Lütfen geçerli bir doktor seçiniz.");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(selected_DoctorID.getText());
						try {
							boolean control = adminPanel.deleteUser(selectID);
							if (control) {
								Helper.showMessage("success");
								selected_DoctorID.setText(null);
								updateUserModel();
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
		btn_delDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_delDoctor.setBounds(309, 404, 235, 25);
		contentPane.add(btn_delDoctor);

		JLabel lbl_doktorEkle_1 = new JLabel("Doktor Silme Bölümü");
		lbl_doktorEkle_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_doktorEkle_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lbl_doktorEkle_1.setBounds(309, 332, 235, 20);
		contentPane.add(lbl_doktorEkle_1);

		JComboBox Type_ComboBox = new JComboBox();
		Type_ComboBox.setModel(new DefaultComboBoxModel(new String[] { "bashekim", "doktor", "hasta" }));
		Type_ComboBox.setBounds(444, 234, 100, 30);

		Type_ComboBox.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			secilenRutbe = c.getSelectedItem().toString();
			System.out.println(c.getSelectedIndex() + ". " + secilenRutbe);
		});
		contentPane.add(Type_ComboBox);

		JLabel lbl_Meslek = new JLabel("Mesleği");
		lbl_Meslek.setBounds(309, 234, 100, 30);
		contentPane.add(lbl_Meslek);

		JLabel lbl_maas = new JLabel("Maaş");
		lbl_maas.setBounds(309, 193, 100, 30);
		contentPane.add(lbl_maas);

		Maas_TextField = new JTextField();
		Maas_TextField.setColumns(10);
		Maas_TextField.setBounds(444, 193, 100, 30);
		contentPane.add(Maas_TextField);

		JButton btnNewButton = new JButton("Yeni Kişiyi Ekle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Tcno_TextField.getText().length() == 0 || Sifre_TextField.getText().length() == 0
						|| AdSoyad_TextField.getText().length() == 0 || Maas_TextField.getText().length() == 0) {
					Helper.showMessage("fill");
				} else {
					try {
						boolean control = adminPanel.addDoctor(Tcno_TextField.getText(), Sifre_TextField.getText(),
								AdSoyad_TextField.getText(), secilenRutbe, Integer.parseInt(Maas_TextField.getText()));
						if (control) {
							Helper.showMessage("success");
							Tcno_TextField.setText(null);
							Sifre_TextField.setText(null);
							AdSoyad_TextField.setText(null);
							Maas_TextField.setText(null);
							updateUserModel();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btnNewButton.setBounds(309, 283, 235, 23);
		contentPane.add(btnNewButton);

	}

	public void updateUserModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) aPanel_Table.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < adminPanel.getUserList().size(); i++) {
			userData[0] = adminPanel.getUserList().get(i).getId();
			userData[1] = adminPanel.getUserList().get(i).getName();
			userData[2] = adminPanel.getUserList().get(i).getTcno();
			userData[3] = adminPanel.getUserList().get(i).getPassword();
			userData[4] = adminPanel.getUserList().get(i).getMaas();

			userModel.addRow(userData);
		}
	}
}
