
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Jurnal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jurnal frame = new Jurnal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public Jurnal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Introduceti textul dorit");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 91, 242, 32);
		contentPane.add(lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 134, 814, 271);
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textPane.getText();
				String user = Login.getNume();
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					Statement st = con.createStatement();
					//System.out.println(text);
					String text_criptat = Criptare.getEncodedString(text);
					String sql = " INSERT INTO jurnal (Text, Username) VALUES ('"+text_criptat+"', '"+user+"') ";
					
					
					int rez = st.executeUpdate(sql);
					if(rez > 0)
						JOptionPane.showMessageDialog(null, "Completare cu succes");
					else 
						JOptionPane.showMessageDialog(null, "Completarea a esuat");
				con.close();
					
				} catch(Exception ex) { 
					System.out.println(ex);
				} 
				}
			
		});
		btnNewButton.setBounds(309, 427, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Inapoi");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login inapoi =  new Login();
				inapoi.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(646, 49, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Istoric");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Istoric istoric =  new Istoric();
				istoric.setVisible(true);
			}
		});
		btnNewButton_1_1.setBounds(511, 49, 89, 23);
		contentPane.add(btnNewButton_1_1);
	}
}
