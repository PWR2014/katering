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
		frame.setTitle("Logowanie");
		JLabel lblNewLabel = new JLabel("U\u017Cytkownik");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(215, 30, 89, 26);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Has\u0142o");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(217, 69, 74, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		textFieldUN = new JTextField();
		textFieldUN.setBounds(314, 30, 117, 29);
		frame.getContentPane().add(textFieldUN);
		textFieldUN.setColumns(10);
		
		btnLogin = new JButton("Login");
		Image img1 = new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		btnLogin.setIcon(new ImageIcon(img1));
		String[] ListaUprawnien={"Manager","Kucharz", "Obsługa","Dostawca",};
		JComboBox<?> comboBox = new JComboBox<Object>(ListaUprawnien);
		comboBox.setBounds(314, 109, 117, 24);
		frame.getContentPane().add(comboBox);
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				try{
					String query="select \"Pracownicy\".id from \"Uprawnienie\" inner join \"Pracownicy\" on \"Pracownicy\".id=\"Uprawnienie\".id_pracownika where \"Pracownicy\".login=? and \"Pracownicy\".\"haslo\"=? and \"Uprawnienie\".typ_uprawnienia=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1,textFieldUN.getText());
					pst.setString(2,passwordField.getText());
					pst.setString(3, comboBox.getSelectedItem().toString());
					ResultSet rs=pst.executeQuery();
					
					int count =0;
					while(rs.next()) {
						count = count + 1;
						
					}
					
					if(count == 1)
					{
						
						JOptionPane.showMessageDialog(null,"Poprawna nazwa użytkownika i hasło");
						frame.dispose();
						connection.close();
						switch(comboBox.getSelectedItem().toString()){
						case "Manager":	Menager mr=new Menager();  mr.setVisible(true); break;
						case "Kucharz": Kucharz kch=new Kucharz(); kch.setVisible(true); break;
						case "Dostawca": Dostawca dst=new Dostawca(); dst.setVisible(true); break;
						case "Obsługa": ObslugaKlienta obs=new ObslugaKlienta(); obs.setVisible(true); break;
						default: JOptionPane.showMessageDialog(null,"Coś poszło nie tak!");
					}
					}
					// jakis blad na bazie np. zduplikowane nazwy uzytkownikow
					else if(count > 1) {
						JOptionPane.showMessageDialog(null,"Zdublowana nazwa użytkownika w bazie");

					}
					else{
						JOptionPane.showMessageDialog(null,"Hasło i nazwa użytkownika nieprawidłowa. Proszę spróbować ponownie");
	
						
					}
					
				   } catch(Exception e1)
				{
					   JOptionPane.showMessageDialog(null,e1);
				}
				
				
				
				
			}
		});
		btnLogin.setBounds(287, 177, 144, 46);
		frame.getContentPane().add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(314, 67, 117, 31);
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

	public void pokaz() {
		frame.setVisible(true);
		
	}
}
