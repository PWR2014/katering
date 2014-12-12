import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

class TabelaSurowcow extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private ArrayList<Object[]> array;
	private Object[][] data;
	private JTabbedPane pane;
	private TabelaSurowcow tenForm;
	public TabelaSurowcow(final JTabbedPane pan) {
		pane=pan;
		tenForm=this;
		setLayout(null);
		setBounds(0,0, 700, 450);
		Object [] columnNames={"LP","Nazwa","Jednsotka","Edycja","Usuwanie"};
		try{
		Connection connection=postgresConnection.dbConnector();
		String query="select * from \"Surowce\";";
		PreparedStatement pst1=connection.prepareStatement(query);
		ResultSet rs = pst1.executeQuery();
		array=new ArrayList<Object[]>();
		int counter=1;
			while(rs.next()){
			Object [] row={counter,rs.getString(2),rs.getString(3),"Edytuj","Usuń"};
			array.add(row);
			counter++;
			}
		} catch(Exception e){
			e.printStackTrace();
		}

		data=new Object[array.size()][5];
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
		        String nazwa=(String)tab.getValueAt(modelRow, 1);
		        String jednostka=(String)tab.getValueAt(modelRow,2);
				try{
					Connection connection=postgresConnection.dbConnector();
					String query="delete from \"Surowce\" where nazwa_surowca='"+nazwa+"' and jednostka_miary ='"+jednostka+"';";	
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
				FormularzSurowiec fp=new FormularzSurowiec(pane);
				JTable tab = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        String nazwa=(String)tab.getValueAt(modelRow, 1);
		        String jednostka=(String)tab.getValueAt(modelRow,2);
		        Long id=null;
				try{
					Connection connection=postgresConnection.dbConnector();
					String query="select * from \"Surowce\" where nazwa_surowca='"+nazwa+"' and jednostka_miary ='"+jednostka+"';";
					PreparedStatement pst=connection.prepareStatement(query);
					ResultSet rs =pst.executeQuery();
					rs.next();
					id=rs.getLong(1);
					
					}catch(Exception e1){
						e1.printStackTrace();
					}
				int i=pane.indexOfComponent(tenForm);
				if(i!=-1){
					pane.remove(i);
					i=pane.indexOfComponent(tenForm);
				}
				fp.wypelnijFormularz(id, nazwa,jednostka);
				pane.add("Edytuj Surowiec",fp);
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
				 if(column==3|| column==4) return true;
				 else
		          return false;  
		      }
		};

		new ButtonColumn(table,edytuj,3);
		new ButtonColumn(table,usun,4);
		table.setBounds(12, 12, 680, 430);
		scrollPane.setViewportView(table);
	}
}