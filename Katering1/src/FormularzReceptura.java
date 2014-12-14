
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class FormularzReceptura extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nazwaProduktu;
	private JTextPane opisProduktu;
	private JPanel containerPane;
	private JSpinner spinnerCena;
	private Image usunImage;
	private Image dodajImage;
	private FormularzReceptura tenForm;
	private Hashtable <JComboBox<?>,JSpinner> uzyteSurowce;
	private Hashtable <String,Long> nazwaSurowcaDoId;
	private ArrayList<String> listaWybranychSurowcow; 
	private int wierszoPixel;
	private JTabbedPane pane;
	private Long idProduktu;
	public FormularzReceptura(final JTabbedPane pan) {
		idProduktu=null;
		pane=pan;
		tenForm=this;
		setLayout(null);
		
		setBounds(0, 0, 688, 438);
		setLayout(null);
		
	
		nazwaProduktu = new JTextField();
		nazwaProduktu.setBounds(110, 14, 149, 20);
		add(nazwaProduktu);
		nazwaProduktu.setColumns(10);
		
		JLabel lblNazwaProduktu = new JLabel("Nazwa produktu");
		lblNazwaProduktu.setBounds(10, 17, 100, 20);
		add(lblNazwaProduktu);
		
		JLabel lblCena = new JLabel("cena");
		lblCena.setBounds(10, 48, 46, 20);
		add(lblCena);
		SpinnerNumberModel modelWartosc=new SpinnerNumberModel(new Double(0),new Double(0),new Double(10000),new Double(0.01));
		spinnerCena = new JSpinner(modelWartosc);
		spinnerCena.setBounds(110, 45, 149, 20);
		add(spinnerCena);
		JLabel lblOpis = new JLabel("Przepis");
		lblOpis.setBounds(10, 76, 46, 20);
		add(lblOpis);
		opisProduktu= new JTextPane();
		JScrollPane scrollText=new JScrollPane(opisProduktu);
		scrollText.setBounds(10, 100, 354, 148);
		add(scrollText);
		JLabel lblSurowce=new JLabel("Surowce");
		lblSurowce.setBounds(10,248,66,20);
		add(lblSurowce);
		containerPane=new JPanel();
		containerPane.setLayout(null);
		
		JScrollPane scroll=new JScrollPane(containerPane);
		scroll.setBounds(10,274,360,200);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		usunImage=new ImageIcon(this.getClass().getResource("/Remove.png")).getImage();
		dodajImage=new ImageIcon(this.getClass().getResource("/Add.png")).getImage();
		uzyteSurowce=new Hashtable<JComboBox<?>,JSpinner>();
		listaWybranychSurowcow=new ArrayList<String>();
		nazwaSurowcaDoId=new Hashtable<String,Long>();
		try{
			
			Connection connection = postgresConnection.dbConnector();
			String query1="select * from \"Surowce\";";
			PreparedStatement pst1=connection.prepareStatement(query1);
			ResultSet rs = pst1.executeQuery();
			while(rs.next()){
				String dodawany=new String(rs.getString(2)+" ("+rs.getString(3)+")");
				nazwaSurowcaDoId.put(dodawany, rs.getLong(1));
								
			}
			connection.close();
		}catch(Exception e1){
				 JOptionPane.showMessageDialog(null,e1);
			}
		przepiszUzyteSurowce();
	}

	public void czystyFormularz(){
		JButton btnNewButton = new JButton("Zapisz");
		btnNewButton.setBounds(10, 475, 117, 25);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(nazwaProduktu.getText().isEmpty()){
						throw new Exception("Brak nazwy produktu");
					}
					if(uzyteSurowce.isEmpty()){
						throw new Exception("Brak surowców");
					}
					Connection connection = postgresConnection.dbConnector();
					String query1="insert into \"Produkty\" values (default,?,?,?)returning \"Produkty\".id;;";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,nazwaProduktu.getText());
					pst1.setDouble(2,(Double) spinnerCena.getValue());
					pst1.setString(3, opisProduktu.getText());
					ResultSet rs = pst1.executeQuery();
					if(rs.next()){
						idProduktu= rs.getLong(1);
						}
					Statement pst2=connection.createStatement();
					Enumeration<JComboBox<?>> enumeracjaSurowcow = uzyteSurowce.keys();
					while(enumeracjaSurowcow.hasMoreElements()){
						JComboBox<?> nazwaSurowca=enumeracjaSurowcow.nextElement();
						String SQL="insert into \"Składniki\" values(default,"+idProduktu+","+
						(Long)nazwaSurowcaDoId.get(nazwaSurowca.getSelectedItem().toString())+","+
						(Double)uzyteSurowce.get(nazwaSurowca).getValue()+")  ";
						pst2.executeUpdate(SQL);
					}
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
		
	}
	public void przepiszUzyteSurowce(){
		containerPane.removeAll();
		containerPane.revalidate();
		containerPane.repaint();

		listaWybranychSurowcow=new ArrayList<String>();
		Enumeration<JComboBox<?>> enumeracjaSurowcow = uzyteSurowce.keys();
		wierszoPixel=5;
		while(enumeracjaSurowcow.hasMoreElements()){
			JComboBox<?> nazwaSurowca=enumeracjaSurowcow.nextElement();
			JSpinner iloscSurowca=uzyteSurowce.get(nazwaSurowca);
			nazwaSurowca.setBounds(10,wierszoPixel,159,26);
			nazwaSurowca.setEnabled(false);
			iloscSurowca.setEnabled(false);
			nazwaSurowca.setRenderer(new DefaultListCellRenderer() {
		        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
		        public void paint(Graphics g) {
		            setForeground(Color.BLACK);
		            super.paint(g);
		        }
		    });
		    ((JTextField) iloscSurowca.getEditor().getComponent(0)).setDisabledTextColor(Color.BLACK);
			iloscSurowca.setBounds(175, wierszoPixel, 126, 26);
			listaWybranychSurowcow.add(nazwaSurowca.getSelectedItem().toString());
			containerPane.add(nazwaSurowca);
			containerPane.add(iloscSurowca);
			JButton usun=new JButton();
			usun.setIcon(new ImageIcon(usunImage));
			usun.setBounds(306,wierszoPixel,34,26);
			usun.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					uzyteSurowce.remove(nazwaSurowca);
					tenForm.przepiszUzyteSurowce();
					
				}
			});
			containerPane.add(usun);
			wierszoPixel+=30;
			
		}
	dodajNiezapisanySurowiec();	
	}
	public void dodajNiezapisanySurowiec(){
			ArrayList<String> listaSurowcow=new ArrayList<String>();
			Enumeration<String> enumeracjaListy = nazwaSurowcaDoId.keys();
			while(enumeracjaListy.hasMoreElements()){
				String el=enumeracjaListy.nextElement();
				if(!listaWybranychSurowcow.contains(el))
					listaSurowcow.add(el);
			}
			JComboBox<?> nazwaSurowca=new JComboBox<Object>(listaSurowcow.toArray());
		    SpinnerNumberModel modelWartosc=new SpinnerNumberModel(new Double(0),new Double(0),new Double(10000),new Double(0.01));
		    JSpinner iloscSurowca=new JSpinner(modelWartosc);
		    nazwaSurowca.setBounds(10,wierszoPixel,159,26);
			iloscSurowca.setBounds(175, wierszoPixel, 126, 26);
			containerPane.add(nazwaSurowca);
			containerPane.add(iloscSurowca);
			JButton dodaj=new JButton();
			dodaj.setIcon(new ImageIcon(dodajImage));
			dodaj.setBounds(306,wierszoPixel,34,26);
			dodaj.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	try{
					if((Double)iloscSurowca.getValue()==0 ||nazwaSurowca.getSelectedItem().toString().isEmpty())
						throw new Exception("Nie można dodawać zerowych wartości do receptury");
					uzyteSurowce.put(nazwaSurowca, iloscSurowca);
					listaWybranychSurowcow.add(nazwaSurowca.getSelectedItem().toString());
					przepiszUzyteSurowce();
					
					
				} catch(Exception e1){
					JOptionPane.showMessageDialog(null,e1);
				}
				}
			});
			containerPane.add(dodaj);
			wierszoPixel+=30;
			containerPane.setPreferredSize(new Dimension(340,wierszoPixel));
		    
	}
	public void wypelnijFormularz(Long id,String naz, Double cen,String opi){
		nazwaProduktu.setText(naz);
		spinnerCena.setValue(cen);
		opisProduktu.setText(opi);
		idProduktu=id;
		try{
		Connection connection=postgresConnection.dbConnector();
		String query2="select \"Składniki\".ilosc,\"Surowce\".nazwa_surowca,\"Surowce\".jednostka_miary from \"Składniki\" inner join \"Surowce\" on \"Surowce\".id=\"Składniki\".id_surowca where id_produktu="+idProduktu;
		PreparedStatement pst2=connection.prepareStatement(query2);
		ResultSet rs2 = pst2.executeQuery();
		while(rs2.next()){
			ArrayList<String> listaSurowcow=new ArrayList<String>();
			Enumeration<String> enumeracjaListy = nazwaSurowcaDoId.keys();
			while(enumeracjaListy.hasMoreElements()){
				listaSurowcow.add(enumeracjaListy.nextElement());
			}
			JComboBox<Object> nazwaSurowca=new JComboBox<Object>(listaSurowcow.toArray());
			nazwaSurowca.setSelectedItem(rs2.getString(2)+" ("+rs2.getString(3)+")");
			SpinnerNumberModel modelWartosc=new SpinnerNumberModel(new Double(rs2.getDouble(1)),new Double(0),new Double(10000),new Double(0.01));
		    JSpinner iloscSurowca=new JSpinner(modelWartosc);
		    uzyteSurowce.put(nazwaSurowca, iloscSurowca);
		    przepiszUzyteSurowce();
		}
		} catch (Exception e){
			e.printStackTrace();
		}
		JButton btnNewButton = new JButton("Aktualizuj");
		btnNewButton.setBounds(10, 475, 117, 25);
		add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(nazwaProduktu.getText().isEmpty()){
						throw new Exception("Brak nazwy produktu");
					}
					if(uzyteSurowce.isEmpty()){
						throw new Exception("Brak surowców");
					}
					Connection connection = postgresConnection.dbConnector();
					System.out.println("1");
					String query1="update \"Produkty\" set nazwa_produktu=?, cena=?, opis=? where id=?;";
					PreparedStatement pst1=connection.prepareStatement(query1);
					pst1.setString(1,nazwaProduktu.getText());
					pst1.setDouble(2,(Double) spinnerCena.getValue());
					pst1.setString(3, opisProduktu.getText());
					pst1.setLong(4, idProduktu);
					pst1.execute();
					String query2="delete from \"Składniki\" where id_produktu="+idProduktu;
					System.out.println("2");
					Statement pst3=connection.createStatement();
					pst3.execute(query2);
					System.out.println("3");
					Statement pst2=connection.createStatement();
					Enumeration<JComboBox<?>> enumeracjaSurowcow = uzyteSurowce.keys();
					while(enumeracjaSurowcow.hasMoreElements()){
						JComboBox<?> nazwaSurowca=enumeracjaSurowcow.nextElement();
						String SQL="insert into \"Składniki\" values(default,"+idProduktu+","+
						(Long)nazwaSurowcaDoId.get(nazwaSurowca.getSelectedItem().toString())+","+
						(Double)uzyteSurowce.get(nazwaSurowca).getValue()+")  ";
						pst2.executeUpdate(SQL);
					}
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
		
	}
}
