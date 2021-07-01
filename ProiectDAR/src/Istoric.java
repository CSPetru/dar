import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Istoric extends JFrame {

	private JPanel contentPane;
	private JTextArea jurnal_text;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Istoric frame = new Istoric();
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
	public Istoric() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 500, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ISTORIC");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(10, 11, 211, 50);
		contentPane.add(lblNewLabel);
		
		JTextArea jurnal_text = new JTextArea();
		jurnal_text.setBounds(10, 72, 803, 299);
		contentPane.add(jurnal_text);
		
		JButton istoric_afis = new JButton("Afiseaza datele");
		istoric_afis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = jurnal_text.getText();
				String user = Login.getNume();
				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
					Statement st = con.createStatement();
					String sql = " Select Text from jurnal where Username = '"+user+"' ";
					
					
					ResultSet rez = st.executeQuery(sql);
					
					Login.getNume();
					while(rez.next())
					{
						
						text = rez.getString("Text");
						String parola_criptata = Criptare.decriptare(text);
						
						jurnal_text.setText(parola_criptata);
						JOptionPane.showMessageDialog(null, "Datele s-au afisat");
					}
					
				} catch (Exception ex) {
					
				}
			}
		});
		istoric_afis.setBounds(50, 407, 136, 43);
		contentPane.add(istoric_afis);
		
		JButton istoric_inapoi = new JButton("Inapoi");
		istoric_inapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Jurnal inapoi =  new Jurnal();
				inapoi.setVisible(true);
				
			}
		});
		istoric_inapoi.setBounds(631, 407, 136, 43);
		contentPane.add(istoric_inapoi);
		
	}

}
