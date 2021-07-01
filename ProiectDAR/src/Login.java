import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.sql.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class Login extends JFrame {

	private JPanel contentPane;
	private static JTextField login_username;
	private JPasswordField login_parola;
	
	public static String nume_user;
	public static String parola_user;
	
	public void salvareNume() {
		this.nume_user=login_username.getText();
	}
	public static String getNume() {
		return login_username.getText();
	}
	
	public void salvareParola() {
		this.parola_user = login_parola.getText();
	}
	
	public static String getParola() {
		return parola_user;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Autentificare");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(172, 11, 362, 76);
		contentPane.add(lblNewLabel);
		
		JLabel lbl = new JLabel("Username");
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl.setBounds(32, 147, 222, 52);
		contentPane.add(lbl);
		
		JLabel lbl2 = new JLabel("Password");
		lbl2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl2.setBounds(28, 210, 226, 52);
		contentPane.add(lbl2);
		
		login_username = new JTextField();
		login_username.setBounds(206, 156, 241, 43);
		contentPane.add(login_username);
		login_username.setColumns(10);
		
		login_parola = new JPasswordField();
		login_parola.setBounds(206, 210, 241, 43);
		contentPane.add(login_parola);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					
					System.out.println(login_username.getText());
					
					
					
					Statement st = con.createStatement();
					String sql = " Select Username, Parola, Sare from login where Username = '"+login_username.getText()+"' ";
					
					ResultSet rez = st.executeQuery(sql);
					
					
					if(rez.next())
					{
						String username = rez.getString("Username");
				        String parola = rez.getString("Parola");
				        String sare = rez.getString("Sare");
				        System.out.println(parola);
				        String parola_decript = Criptare.decriptare(parola, login_parola.getText()+sare); // *sare
				        System.out.print(parola_decript);
						dispose();
						Jurnal jurnal =  new Jurnal();
						jurnal.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "User/parola gresita");
					}
				con.close();
					
				} catch(Exception ex) { 
					System.out.println(ex);
				}
			}
		});
		btnLogin.setBounds(99, 311, 139, 43);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("Inregistrare");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Inregistrare inreg =  new Inregistrare();
				inreg.setVisible(true);
			}
		});
		btnRegister.setBounds(339, 311, 119, 43);
		contentPane.add(btnRegister);
	}
}
