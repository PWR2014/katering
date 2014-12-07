import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

class TabelaPracownikow extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<Object[]> array;
	private Object[][] data;
	private JTabbedPane pane;
	public TabelaPracownikow(final JTabbedPane pan) {
		pane=pan;
		setLayout(null);
		setBounds(0,0, 700, 450);
		Object [] columnNames={"LP","Imię","Nazwisko","Login","Manager","Kucharz","Dostawca","Obsługa","Edycja","Usuwanie"};
		try{
		Connection connection=postgresConnection.dbConnector();
		String query="select * from \"Pracownicy\";";
		PreparedStatement pst1=connection.prepareStatement(query);
		ResultSet rs = pst1.executeQuery();
		array=new ArrayList<Object[]>();
		int counter=0;
		while(rs.next()){
			
			
			String query2="select * from \"Uprawnienie\" where id_pracownika="+rs.getLong(1);
			PreparedStatement pst2=connection.prepareStatement(query2);
			ResultSet rs2 = pst2.executeQuery();
			Hashtable<String,Boolean> uprawnienia=new Hashtable<String,Boolean>();
			uprawnienia.put("Manager", false);
			uprawnienia.put("Kucharz", false);
			uprawnienia.put("Dostawca", false);
			uprawnienia.put("Obsługa", false);
			while(rs2.next()){
				uprawnienia.replace(rs2.getString(3),true);
			}
			Object [] row={(long)(counter+1),rs.getString(4),rs.getString(5),rs.getString(2),
					(Boolean)uprawnienia.get("Manager"),(Boolean)uprawnienia.get("Kucharz"),(Boolean)uprawnienia.get("Dostawca"),(Boolean)uprawnienia.get("Obsługa"),"Edytuj","Usuń"};
			array.add(row);
			counter++;
		}
		} catch(Exception e){
			e.printStackTrace();
		}
		/*Object [][] data= {
				{1,"testimienia","testNazwiska","testLoginu",true,false,true,false,"Edytuj","Usuń"},
		};*/
		data=new Object[array.size()][10];
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
		        String login=(String)tab.getValueAt(modelRow, 3);
				try{
					Connection connection=postgresConnection.dbConnector();
					String query="delete from \"Pracownicy\" where login='"+login+"'";	
					PreparedStatement pst=connection.prepareStatement(query);
					pst.execute();
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
				FormularzPracownik fp=new FormularzPracownik(pane);
				JTable tab = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String login=(String)tab.getValueAt(modelRow, 3);
		        String imi = null,naz = null,log = null,has = null,pes = null,tel = null;
		        Long id = null;
		        Hashtable<String,Boolean> upr = null;
				try{
					Connection connection=postgresConnection.dbConnector();
					String query="select * from \"Pracownicy\" where login='"+login+"'";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs =pst.executeQuery();
					rs.next();
					id=rs.getLong(1);
					imi=rs.getString(4);
					naz=rs.getString(5);
					 log=rs.getString(2);
					 has=rs.getString(3);
					 pes=rs.getString(6);
					 tel=rs.getString(7);
					String query2="select * from \"Uprawnienie\" where id_pracownika="+id;
					PreparedStatement pst2=connection.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					upr=new Hashtable<String,Boolean>();
					upr.put("Manager", false);
					upr.put("Kucharz", false);
					upr.put("Dostawca", false);
					upr.put("Obsługa", false);
					while(rs2.next()){
						upr.replace(rs2.getString(3),true);
					}
					}catch(Exception e1){
						e1.printStackTrace();
					}
				fp.wypelnionyFormularz(id, imi, naz, log, has, pes, tel, upr);
				pane.add("Edytuj Pracownika",fp);
				int index=pane.indexOfComponent(fp);
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
				 if(column==8|| column==9) return true;
				 else
		          return false;  
		      }
	            @SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int column) {
	                switch (column) {
	                    case 0: return Long.class;
	                    case 1: return String.class;
	                    case 2: return String.class;
	                    case 3: return String.class;
	                    case 4: return Boolean.class;
	                    case 5: return Boolean.class;
	                    case 6: return Boolean.class;
	                    case 7: return Boolean.class;
	                    case 8: return String.class;
	                    case 9: return String.class;
	                    default: return String.class;
	                }
	            }
		};
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(55);
		table.getColumnModel().getColumn(5).setPreferredWidth(55);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setPreferredWidth(55);
		new ButtonColumn(table,edytuj,8);
		new ButtonColumn(table,usun,9);
		table.setBounds(12, 12, 680, 430);
		scrollPane.setViewportView(table);
	}
}