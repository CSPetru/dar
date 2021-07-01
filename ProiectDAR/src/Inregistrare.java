
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
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;
import java.util.UUID;


public class Inregistrare extends JFrame {

	private JPanel contentPane;
	protected JTextField register_username;
	protected static JPasswordField register_parola;
	protected JPasswordField register_confparola;
	Connection con = null;
	public static String parola_user;

	public void salvareParola() {
		this.parola_user = register_parola.getText();
	}
	
	public static String getParola() {
		return parola_user;
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inregistrare frame = new Inregistrare();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void validareParola() throws SQLException {
		String parola1 = register_parola.getText();
		String parola2 = register_confparola.getText();
		String user = register_username.getText();
		
		if(user.isEmpty() || parola1.isEmpty() || parola2.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Toate campurile trebuie completate");
			con.close();
		}
		else if(!parola1.equals(parola2)) {
			JOptionPane.showMessageDialog(this, "Parolele trebuie sa nu fie diferite");
			con.close();
		}
	}
	
	
	
	public void submit() {
		try {
			String sare = UUID.randomUUID().toString();
			//String sare_criptata = Criptare.criptare(sare, register_parola.getText());
			String parola_sare = register_parola.getText()+sare;
			System.out.println("parola sare: "+parola_sare);
			String parola_criptata = Criptare.criptare(register_parola.getText(), parola_sare);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

			String sql = " INSERT INTO login (Username, Parola, Sare) VALUES (?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			//String sare_criptata = Criptare.criptare(sare, register_parola.getText());
			//String parola_sare = register_parola.getText()+sare;
			//String parola_criptata = Criptare.criptare(register_parola.getText(), parola_sare); 
			st.setString(1, register_username.getText());
			st.setString(2, parola_criptata);
			st.setString(3, sare);
			
			int rez = st.executeUpdate();
			if(rez > 0)
				JOptionPane.showMessageDialog(null, "Inregistrare cu succes");
			else 
				JOptionPane.showMessageDialog(null, "Inregistrarea a esuat");
		con.close();
			
		} catch(Exception ex) { 
			System.out.println(ex);
		} 
		}
	


	public Inregistrare() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("INREGISTRARE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(25, 11, 260, 80);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(29, 140, 181, 43);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Parola");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(29, 205, 181, 43);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Confirmare parola");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(25, 267, 181, 43);
		contentPane.add(lblNewLabel_1_2);
		
		register_username = new JTextField();
		
		register_username.setBounds(220, 140, 197, 43);
		contentPane.add(register_username);
		register_username.setColumns(10);
		
		register_parola = new JPasswordField();
		
		register_parola.setBounds(220, 207, 197, 43);
		contentPane.add(register_parola);
		
		register_confparola = new JPasswordField();
		register_confparola.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					validareParola();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		register_confparola.setBounds(220, 267, 197, 43);
		contentPane.add(register_confparola);
		
		JButton btnRegister2 = new JButton("OK");
		btnRegister2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String parola_user = Inregistrare.getParola();
				try {
					validareParola();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				submit();
			}
		});
		
		
		btnRegister2.setBounds(137, 356, 136, 43);
		contentPane.add(btnRegister2);
		
		JButton btnInapoi = new JButton("Inapoi");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login inapoi =  new Login();
				inapoi.setVisible(true);
			}
		});
		btnInapoi.setBounds(390, 356, 136, 43);
		contentPane.add(btnInapoi);
	}

}
