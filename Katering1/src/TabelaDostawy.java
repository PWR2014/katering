import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

class TabelaDostawy extends JPanel{
	/**
	 * 
	 */
	//private int jeden = 1;
	private String textTAK = "TAK";
	protected Connection connection;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<Object[]> array;
	private Object[][] data;
	private JTabbedPane pane;
	private TabelaDostawy tenForm;
	public TabelaDostawy(final JTabbedPane pan) {
		pane=pan;
		tenForm=this;
		setLayout(null);
		setBounds(0,0, 700, 450);
	//	Long id=null;
		Object [] columnNames={"LP","Ilość","Nazwa produktu","Cena","Ulica","Nr_domu","Nr_lokalu","Imię","Nazwisko","Przyjmij","Czy Przyjęte", "Zakończ", "Czy zakończone"};
		try{
			Connection connection=postgresConnection.dbConnector();
			String query="select \"PozycjeZamówień\".id,ilość, \"Produkty\".nazwa_produktu,\"Zamówienie\".suma_brutto,odebral,dostarczyl \"Adresy\".ulica,nr_domu,nr_lokalu, \"Klienci\".imię,nazwisko, \"Zamówienia\".odebral,dostarczył from \"PozycjeZamówień\", \"Produkty\",\"Zamówienia\",\"Adresy\", \"Klienci\";";
			PreparedStatement pst1=connection.prepareStatement(query);
			ResultSet rs = pst1.executeQuery();
			array=new ArrayList<Object[]>();
			int counter=1;
				while(rs.next()){
				Object [] row={counter,rs.getInt(2),rs.getString(3),rs.getDouble(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),"Przyjmij",rs.getString(10),"Zakoncz",rs.getString(11)};
				array.add(row);
				counter++;
//				id=rs.getLong(1);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		data=new Object[array.size()][15];
		data=array.toArray(data);
		model=new DefaultTableModel(data,columnNames);

		Action Przyjmij= new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try{						
					connection=postgresConnection.dbConnector();
					String query1="update \"Zamówienia\" set odebral=? where id=?";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,textTAK);				
					pst1.setLong(2,1);//row()  );			/// trzeba zrobić, żeby tylko w tym rzędzie się zmieniała 
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
		};
		
		Action Zakoncz= new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e){
				try{						
					connection=postgresConnection.dbConnector();
					String query1="update \"Zamówienia\" set dostarczyl=? where id=?";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,textTAK);				
					pst1.setLong(2,1);//row()  );			/// trzeba zrobić, żeby tylko w tym rzędzie się zmieniała 
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
		};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 688, 438);
		add(scrollPane);
		table = new JTable(model){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){ 
				 if(column==3|| column==4|| column==5|| column==13) return true;
				 else
		          return false;  
		      }
		};

		new ButtonColumn(table,Przyjmij,10);
		new ButtonColumn(table,Zakoncz,12);
		table.setBounds(12, 12, 680, 430);
		scrollPane.setViewportView(table);
	}
}