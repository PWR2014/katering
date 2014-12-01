import java.awt.EventQueue;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection=null;
	private JTextField textFieldUN;
	private JButton btnLogin;
	private JPasswordField passwordField;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection=postgresConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("U\u017Cytkownik");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(207, 31, 74, 26);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Has\u0142o");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(207, 80, 74, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUN = new JTextField();
		textFieldUN.setBounds(291, 30, 117, 29);
		frame.getContentPane().add(textFieldUN);
		textFieldUN.setColumns(10);
		
		btnLogin = new JButton("Login");
		Image img1 = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		btnLogin.setIcon(new ImageIcon(img1));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String query="select * from employee where uzytkownik=? and haslo=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1,textFieldUN.getText());
					pst.setString(2,passwordField.getText());
					
					ResultSet rs=pst.executeQuery();
					
					int count =0;
					while(rs.next()) {
						count = count + 1;
						
					}
					
					if(count == 1)
					{
						
						JOptionPane.showMessageDialog(null,"Poprawna nazwa u¿ytkownika i has³o");
						frame.dispose();
					//	JOptionPane.showMessageDialog(null,rs);
			            Menager mr=new Menager(); /* tutaj przyklad na menagera, trzrba wstawic pozostalych uzytkownikow i zrobic jakis warunek
			            							ze w zaleznosci od wyboru otwierac sie bedzie inne okno, np. na bazie dodac kolumne uprawnienia i tak przypisaæ odpowiednie */
						mr.setVisible(true);
					}
					// jakis blad na bazie np. zduplikowane nazwy uzytkownikow
					else if(count > 1) {
						JOptionPane.showMessageDialog(null,"Zdublowana nazwa u¿ytkownika w bazie");

					}
					else{
						JOptionPane.showMessageDialog(null,"Has³o i nazwa u¿ytkownika nieprawid³owa. Proszê spróbowaæ ponownie");
	
						
					}
					rs.close(); // zamykanie polaczenia z bazka
					pst.close();
				   } catch(Exception e1)
				{
					   JOptionPane.showMessageDialog(null,e1);
				}
				
				
				
				
			}
		});
		btnLogin.setBounds(264, 177, 117, 46);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(291, 79, 117, 31);
		frame.getContentPane().add(passwordField);
		
		label = new JLabel(""); // okreslamy sciezke dla zdjec 
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(10, 30, 187, 193);
		frame.getContentPane().add(label);
		
		label_1 = new JLabel("");
		
		label_1.setBounds(362, 161, 46, 46);
		frame.getContentPane().add(label_1);
	}
}
