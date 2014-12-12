import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StanSurowcow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<Object[]> array;
	private Object[][] data;
	private JTabbedPane pane;
	private StanSurowcow tenForm;
	public StanSurowcow(final JTabbedPane pan) {
		pane=pan;
		tenForm=this;
		setLayout(null);
		setBounds(0,0, 700, 450);
		Object [] columnNames={"LP","Nazwa","Jednostka","Ilość", "Czyszczenie stanu"};
		try{
			Connection connection=postgresConnection.dbConnector();
			String query="select id,\"nazwa_surowca\",\"jednostka_miary\",\"bierzący_stan\" from \"Surowce\""
					+ " left join \"BierzącyStanSurowców\" on \"Surowce\".id=\"BierzącyStanSurowców\".id_surowca order by id;";
			PreparedStatement pst1=connection.prepareStatement(query);
			ResultSet rs = pst1.executeQuery();
			array=new ArrayList<Object[]>();
			int counter=1;
				while(rs.next()){
				Object [] row={counter,rs.getString(2),rs.getString(3),rs.getDouble(4),"Wyczyść"};
				array.add(row);
				counter++;
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		data=new Object[array.size()][5];
		data=array.toArray(data);
		model=new DefaultTableModel(data,columnNames);
		Action wyczysc=new AbstractAction(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e)
		    {
				 JTable tab = (JTable)e.getSource();
				 int modelRow = Integer.valueOf( e.getActionCommand() );
				 String nazwa=(String)tab.getValueAt(modelRow, 1);
			      String jednostka=(String)tab.getValueAt(modelRow,2);
			      Long id=null;
				 try{
						Connection connection=postgresConnection.dbConnector();
						String query1="select id from \"Surowce\" where nazwa_surowca='"+nazwa+"' and jednostka_miary ='"+jednostka+"';";	
						PreparedStatement pst1=connection.prepareStatement(query1);
						ResultSet rs =pst1.executeQuery();
						rs.next();
						id=rs.getLong(1);
						String query2="delete from \"StanSurowca\" where id_surowca="+id+";";	
						PreparedStatement pst2=connection.prepareStatement(query2);
						pst2.execute();
						connection.close();
				 } catch (Exception e1){
					 e1.printStackTrace();
				 }
				StanSurowcow ss= new StanSurowcow(pane);
				pane.add("Stan Surowców",ss);
				int index = pane.indexOfComponent(ss);
				pane.setTabComponentAt(index, new ButtonTabComponent(pane));
				pane.setSelectedIndex(index);
				int i=pane.indexOfComponent(tenForm);
				if(i!=-1){
					pane.remove(i);
					i=pane.indexOfComponent(tenForm);
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
				 if(column==4) return true;
				 else
		          return false;  
		      }
		};
		new ButtonColumn(table,wyczysc,4);
		table.setBounds(12, 12, 680, 430);
		scrollPane.setViewportView(table);
		}

}
