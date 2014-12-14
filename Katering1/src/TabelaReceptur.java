import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TabelaReceptur extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<Object[]> array;
	private Object[][] data;
	private Hashtable <Long,Long> lpDoId;
	private JTabbedPane pane;
	private TabelaReceptur tenForm;
	public TabelaReceptur(final JTabbedPane pan) {
		pane=pan;
		tenForm=this;
		setLayout(null);
		setBounds(0,0, 700, 450);
		Object [] columnNames={"LP","Nazwa","Cena","Ile Składników","Edycja","Usuwanie"};
		try{
		Connection connection=postgresConnection.dbConnector();
		String query="select \"Produkty\".id,nazwa_produktu,cena,count(\"Składniki\".id) from \"Produkty\" inner join \"Składniki\" on \"Produkty\".id=\"Składniki\".id_produktu group by \"Produkty\".id order by \"Produkty\".id;";
		PreparedStatement pst1=connection.prepareStatement(query);
		ResultSet rs = pst1.executeQuery();
		array=new ArrayList<Object[]>();
		lpDoId=new Hashtable<Long,Long>();
		Long counter=(long) 1;
			while(rs.next()){
				lpDoId.put(counter, rs.getLong(1));
			Object [] row={counter,rs.getString(2),rs.getDouble(3),rs.getLong(4),"Edytuj","Usuń"};
			array.add(row);
			counter++;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		data=new Object[array.size()][6];
		data=array.toArray(data);
		model=new DefaultTableModel(data,columnNames);
		Action usun= new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
				JTable tab = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        Long lp=(Long)tab.getValueAt(modelRow, 0);
		        Long idProduktu=lpDoId.get(lp);
				try{
					Connection connection=postgresConnection.dbConnector();
					String query1="delete from \"Składniki\" where id_produktu="+idProduktu;
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.execute();
					String query2="delete from \"Produkty\" where id="+idProduktu;
					PreparedStatement pst2=connection.prepareStatement(query2);
					pst2.execute();
					connection.close();
		        ((DefaultTableModel)tab.getModel()).removeRow(modelRow);
				}catch(Exception e1){
					e1.printStackTrace();
				}
		    }
		};
		Action edytuj= new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
			FormularzReceptura fr=new FormularzReceptura(pane);
			JTable tab = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        Long lp=(Long)tab.getValueAt(modelRow, 0);
		        Long idProduktu=lpDoId.get(lp);
		        Double cena=null;
		        String nazwa=null,opis=null;
		 
				try{
					Connection connection=postgresConnection.dbConnector();
					String query1="select * from \"Produkty\" where id="+idProduktu;
					PreparedStatement pst1=connection.prepareStatement(query1);
					ResultSet rs1 = pst1.executeQuery();
					rs1.next();
					cena=rs1.getDouble(3);
					nazwa=rs1.getString(2);
					opis=rs1.getString(4);
					
					}catch(Exception e1){
						e1.printStackTrace();
					}
				int i=pane.indexOfComponent(tenForm);
				if(i!=-1){
					pane.remove(i);
					i=pane.indexOfComponent(tenForm);
				}
				fr.wypelnijFormularz(idProduktu, nazwa,cena,opis);
				pane.add("Edytuj Recepturę",fr);
				int index=pane.indexOfComponent(fr);
				pane.setTabComponentAt(index, new ButtonTabComponent(pane));
				pane.setSelectedIndex(index);
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
				 if(column==4|| column==5) return true;
				 else
		          return false;  
		      }
			public Class<?> getColumnClass(int column) {
                switch (column) {
                case 0: return Long.class;
                case 1: return String.class;
                case 2: return Double.class;
                case 3: return Long.class;
                default: return String.class;
                }
			}
		};
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(220);

		new ButtonColumn(table,edytuj,4);
		new ButtonColumn(table,usun,5);
		table.setBounds(12, 12, 680, 430);
		scrollPane.setViewportView(table);

}
}
