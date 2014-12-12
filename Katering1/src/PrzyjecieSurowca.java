import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;


public class PrzyjecieSurowca extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<?> comboListaSurowcow;
	private JSpinner wartosc;
	private SpinnerNumberModel modelWartosc;
	private PrzyjecieSurowca tenForm;
	private JTabbedPane pane;
	private ArrayList<String> listaSurowcow;
	private ArrayList<Long> idSurowcow;
	protected Connection connection;
	public PrzyjecieSurowca(final JTabbedPane pan) {
		tenForm=this;
		pane=pan;
		listaSurowcow=new ArrayList<String>();
		idSurowcow=new ArrayList<Long>();
		setLayout(null);
		try{
			
			connection=postgresConnection.dbConnector();
			String query1="select * from \"Surowce\";";
			PreparedStatement pst1=connection.prepareStatement(query1);
			ResultSet rs = pst1.executeQuery();
			while(rs.next()){
				listaSurowcow.add(new String(rs.getString(2)+" ("+rs.getString(3)+")"));
				idSurowcow.add(new Long(rs.getLong(1)));
			}
			connection.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
	//	String [] tymczasowaLista={"asdf","asdf"};
		comboListaSurowcow = new JComboBox<Object>(listaSurowcow.toArray());
		comboListaSurowcow.setBounds(12, 40, 159, 24);
		add(comboListaSurowcow);
		
		modelWartosc=new SpinnerNumberModel(new Double(0),new Double(-1000000000),new Double(1000000000),new Double(0.001));
		wartosc = new JSpinner(modelWartosc);
		wartosc.setBounds(183, 40, 126, 24);
		add(wartosc);
		
		JButton btnNewButton = new JButton("Przyjmij");
		btnNewButton.setBounds(321, 40, 117, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Long id_surowca=idSurowcow.get(listaSurowcow.indexOf(comboListaSurowcow.getSelectedItem()));
					Double wartosc_s=(Double)wartosc.getValue();
					if(wartosc_s==0) throw new Exception("Nie Ustawiono wartości");
					connection=postgresConnection.dbConnector();
					String query1="select \"bierzący_stan\"+? from \"BierzącyStanSurowców\" where id_surowca=?;";
					PreparedStatement pst=connection.prepareStatement(query1);
					pst.setDouble(1,wartosc_s);
					pst.setLong(2, id_surowca );
					ResultSet rs=pst.executeQuery();
					if(rs.next()){
						if(rs.getLong(1)<0) throw new Exception("Nie Można zmniejszyć stanu poniżej zera");
					} else{
						if(wartosc_s<0) throw new Exception("Nie Można zmniejszyć stanu poniżej zera");
					}
					String query2="insert into \"StanSurowca\" values(default,?,?,default);";
					PreparedStatement pst2=connection.prepareStatement(query2);
					pst2.setLong(1, id_surowca);
					pst2.setDouble(2, wartosc_s);
					pst2.execute();
					connection.close();
					int i = pane.indexOfComponent(tenForm);
		            if (i != -1) {
		                pane.remove(i);
		                }
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		add(btnNewButton);
	}
}
