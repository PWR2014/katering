import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;


public class FormularzSurowiec extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textSurowiec;
	private JComboBox<?> comboJednostka;
	protected Connection connection;
	private FormularzSurowiec tenForm;
	private JTabbedPane pane;
	
	 public FormularzSurowiec(final JTabbedPane pan) {
			tenForm=this;
			pane=pan;
			setBounds(0,0, 600, 450);
			setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nazwa Surowca");
			lblNewLabel.setBounds(24, 38, 126, 15);
			add(lblNewLabel);
			
			JLabel lblJednostakMiary = new JLabel("Jednostka miary");
			lblJednostakMiary.setBounds(24, 70, 114, 15);
			add(lblJednostakMiary);
			String [] jednostki={"szt","kg","m","l"};
			comboJednostka = new JComboBox<Object>(jednostki);
			comboJednostka.setBounds(152, 65, 67, 24);
			add(comboJednostka);
			
			textSurowiec = new JTextField();
			textSurowiec.setBounds(152, 36, 198, 19);
			add(textSurowiec);
			textSurowiec.setColumns(10);
			

		 
	}
	 public void czystyFormularz(){
			JButton btnNewButton = new JButton("Zapisz");
			btnNewButton.setBounds(231, 65, 119, 25);
			add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						if (textSurowiec.getText().isEmpty()){
							throw new Exception("Puste pole nazwy surowca");
						}						
						connection=postgresConnection.dbConnector();
						String query1="insert into \"Surowce\" values(default,?,?);";
						PreparedStatement pst1=connection.prepareStatement(query1);
						pst1.setString(1,textSurowiec.getText());
						pst1.setString(2,comboJednostka.getSelectedItem().toString());
						pst1.execute();
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
	 }
	public void wypelnijFormularz(Long id,String sur,String jed){
		textSurowiec.setText(sur);
		comboJednostka.setSelectedItem(jed);
		JButton btnNewButton = new JButton("Aktualizuj");
		btnNewButton.setBounds(231, 65, 119, 25);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					connection=postgresConnection.dbConnector();
					String query1="update \"Surowce\" set nazwa_surowca=?, jednostka_miary=? where id=?";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,textSurowiec.getText());
					pst1.setString(2,comboJednostka.getSelectedItem().toString());
					pst1.setLong(3,id);
					pst1.execute();
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
		
	
	 }
}
