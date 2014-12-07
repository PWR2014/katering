import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Hashtable;





class FormularzPracownik extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textNazwisko;
	private JTextField textImie;
	private JLabel lblNewLabel;
	private JButton btnZapisz;
	private JLabel lblPesel;
	private JFormattedTextField textPesel;
	private JLabel lblTelefon;
	private JFormattedTextField textTelefon;
	private JTextField textLogin;
	private JCheckBox chckbxKucharz;
	private JCheckBox chckbxDostawca;
	private JCheckBox chckbxObsugaKlienta;
	private JCheckBox chckbxManager;
	private JPasswordField passwordField;
	private Connection connection;
	private FormularzPracownik tenForm;
	private JTabbedPane pane;
	public FormularzPracownik(final JTabbedPane pan) {
		tenForm=this;
		this.pane=pan;
		setLayout(null);
		setBounds(0,0, 600, 450);
		textNazwisko = new JTextField();
		textNazwisko.setBounds(100, 12, 143, 19);
		add(textNazwisko);
		textNazwisko.setColumns(10);
		
		textImie = new JTextField();
		textImie.setBounds(100, 43, 143, 19);
		add(textImie);
		textImie.setColumns(10);
		
		JLabel lblNewLabnazwiskoel = new JLabel("Nazwisko");
		lblNewLabnazwiskoel.setBounds(12, 14, 70, 15);
		add(lblNewLabnazwiskoel);
		
		lblNewLabel = new JLabel("Imię");
		lblNewLabel.setBounds(12, 45, 70, 15);
		add(lblNewLabel);
		
		lblPesel = new JLabel("Pesel");
		lblPesel.setBounds(12, 76, 70, 15);
		add(lblPesel);
		try{
		MaskFormatter mf1 = new MaskFormatter("###########");
		mf1.setPlaceholderCharacter('_');
		
		textPesel = new JFormattedTextField(mf1);
		textPesel.setColumns(10);
		textPesel.setBounds(100, 74, 143, 17);
		add(textPesel);
		} catch(ParseException e){
			e.printStackTrace();
		}
		lblTelefon = new JLabel("Telefon");
		lblTelefon.setBounds(311, 13, 70, 15);
		add(lblTelefon);
		try{
			
		MaskFormatter mf2 = new MaskFormatter("(+48) ##-##-##-###");
		mf2.setPlaceholderCharacter('_');
		textTelefon = new JFormattedTextField(mf2);
		textTelefon.setColumns(10);
		textTelefon.setBounds(399, 11, 143, 19);
		add(textTelefon);
		}  catch(ParseException e){
			e.printStackTrace();
		}
		textLogin = new JTextField();
		textLogin.setColumns(10);
		textLogin.setBounds(399, 43, 143, 19);
		add(textLogin);
		
		JLabel lblLogin_1 = new JLabel("Login");
		lblLogin_1.setBounds(311, 45, 70, 15);
		add(lblLogin_1);
		
		JLabel lblHaso = new JLabel("Hasło");
		lblHaso.setBounds(311, 76, 70, 15);
		add(lblHaso);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(399, 74, 143, 19);
		add(passwordField);
		
		chckbxManager = new JCheckBox("Manager");
		chckbxManager.setBounds(22, 99, 88, 23);
		add(chckbxManager);
		
		chckbxKucharz = new JCheckBox("Kucharz");
		chckbxKucharz.setBounds(136, 99, 88, 23);
		add(chckbxKucharz);
		
		chckbxDostawca = new JCheckBox("Dostawca");
		chckbxDostawca.setBounds(247, 99, 104, 23);
		add(chckbxDostawca);
		
		chckbxObsugaKlienta = new JCheckBox("Obsługa Klienta");
		chckbxObsugaKlienta.setBounds(382, 99, 143, 23);
		add(chckbxObsugaKlienta);
	}
	public void czystyFormularz(){
		btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					long loginID=0;
					connection=postgresConnection.dbConnector();
					String query1="insert into \"Pracownicy\" values (default,?,?,?,?,?,?) returning \"Pracownicy\".id;";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,textLogin.getText());
					pst1.setString(2,passwordField.getText());
					pst1.setString(3,textImie.getText());
					pst1.setString(4,textNazwisko.getText());
					pst1.setString(5,textPesel.getText());
					pst1.setString(6,textTelefon.getText());
					ResultSet rs = pst1.executeQuery();
					if(rs.next()){
					loginID= rs.getLong(1);
					}
					Statement pst2=connection.createStatement();
					
					if(chckbxDostawca.isSelected()){
						String SQL="insert into \"Uprawnienie\" values (default,"+loginID+",'Dostawca');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxKucharz.isSelected()){
						String SQL="insert into \"Uprawnienie\" values (default,"+loginID+",'Kucharz');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxObsugaKlienta.isSelected()){
						String SQL="insert into \"Uprawnienie\" values (default,"+loginID+",'Obsługa');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxManager.isSelected()){
						String SQL="insert into \"Uprawnienie\" values (default,"+loginID+",'Manager');";
						pst2.executeUpdate(SQL);
					}
					connection.close();
					int i = pane.indexOfComponent(tenForm);
		            if (i != -1) {
		                pane.remove(i);
		                }
				}catch(Exception e1)
				{
					   JOptionPane.showMessageDialog(null,e1);
				}
		}
		});
		btnZapisz.setBounds(247, 143, 117, 25);
		add(btnZapisz);
	}
	public void wypelnionyFormularz(Long id,String imi,String naz,String log,String has, String pes, String tel, Hashtable<String,Boolean> upr){
		textNazwisko.setText(naz);
		textImie.setText(imi);
		textLogin.setText(log);
		passwordField.setText(has);
		textPesel.setText(pes);
		textTelefon.setText(tel);
		try{
			chckbxManager.setSelected(upr.get("Manager"));
			chckbxObsugaKlienta.setSelected(upr.get("Obsługa"));
			chckbxDostawca.setSelected(upr.get("Dostawca"));
			chckbxKucharz.setSelected(upr.get("Kucharz"));
		}catch(Exception e){
			 JOptionPane.showMessageDialog(null,e);
		}
		btnZapisz = new JButton("Aktualizuj");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					connection=postgresConnection.dbConnector();
					String query1="update \"Pracownicy\" set login=?,\"hasło\"=?,\"imię\"=?,nazwisko=?,pesel=?,telefon=? where id=?;";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,textLogin.getText());
					pst1.setString(2,passwordField.getText());
					pst1.setString(3,textImie.getText());
					pst1.setString(4,textNazwisko.getText());
					pst1.setString(5,textPesel.getText());
					pst1.setString(6,textTelefon.getText());
					pst1.setLong(7,id);
					pst1.executeQuery();
					Statement pst2=connection.createStatement();
					
					if(chckbxDostawca.isSelected()&& !upr.get("Dostawca")){
						String SQL="insert into \"Uprawnienie\" values (default,"+id+",'Dostawca');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxKucharz.isSelected()&& !upr.get("Kucharz")){
						String SQL="insert into \"Uprawnienie\" values (default,"+id+",'Kucharz');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxObsugaKlienta.isSelected()&& !upr.get("Obsługa")){
						String SQL="insert into \"Uprawnienie\" values (default,"+id+",'Obsługa');";
						pst2.executeUpdate(SQL);
					}
					if(chckbxManager.isSelected() &&!upr.get("Manager")){
						String SQL="insert into \"Uprawnienie\" values (default,"+id+",'Manager');";
						pst2.executeUpdate(SQL);
					}
					if(!chckbxDostawca.isSelected()&& upr.get("Dostawca")){
						String SQL="delete from \"Uprawnienie\" where id_pracownika="+id+" and typ_uprawnienia='Dostawca';";
						pst2.executeUpdate(SQL);
					}
					if(!chckbxKucharz.isSelected()&& upr.get("Kucharz")){
						String SQL="delete from \"Uprawnienie\" where id_pracownika="+id+" and typ_uprawnienia='Kucharz';";
						pst2.executeUpdate(SQL);
					}
					if(!chckbxObsugaKlienta.isSelected()&& upr.get("Obsługa")){
						String SQL="delete from \"Uprawnienie\" where id_pracownika="+id+" and typ_uprawnienia='Obsługa';";
						pst2.executeUpdate(SQL);
					}
					if(!chckbxManager.isSelected() &&upr.get("Manager")){
						String SQL="delete from \"Uprawnienie\" where id_pracownika="+id+" and typ_uprawnienia='Manager';";
						pst2.executeUpdate(SQL);
					}
					connection.close();					
					int i = pane.indexOfComponent(tenForm);
		            if (i != -1) {
		                pane.remove(i);
		                }
				}catch(Exception e1)
				{
					   JOptionPane.showMessageDialog(null,e1);
				}
		}
		});
		btnZapisz.setBounds(247, 143, 117, 25);
		add(btnZapisz);
	}
}
