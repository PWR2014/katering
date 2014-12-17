import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.Label;
import java.awt.Button;
import java.awt.Font;
import java.awt.Choice;
public class ObslugaKlienta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ObslugaKlienta frame = new ObslugaKlienta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
   Connection connection=null;
   private JTable table;
	/**
	 * Create the frame.
	 */
	public ObslugaKlienta() {
		connection=postgresConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1120, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Wyświetl dane o zamówieniach");
		btnLoadTable.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="Select z.id,z.id_produktu Produkt,z.termin_realizacji Termin ,z.ilosc Ilość,p.cena*z.ilosc as Cena,z.imie Imię,z.nazwisko Nazwisko,z.adres Adres from zamowienia z join produkty p on z.id_produktu=p.id order by z.id desc";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (Exception f) {
					f.printStackTrace();
				}
				
				
			}
		});
		btnLoadTable.setBounds(10, 33, 221, 50);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(59, 249, 365, -162);
		contentPane.add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(241, 33, 853, 290);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBounds(97, 172, 125, 20);
		contentPane.add(editorPane_2);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBounds(97, 203, 125, 20);
		contentPane.add(editorPane_3);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(97, 203, 106, 20);
		contentPane.add(editorPane_1);
		
		Label label_id = new Label("Id produktu:");
		label_id.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label_id.setBounds(10, 108, 81, 22);
		contentPane.add(label_id);
		
		Label label_haslo = new Label("Ilość:");
		label_haslo.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label_haslo.setBounds(10, 139, 62, 22);
		contentPane.add(label_haslo);
		
		Label label_uzytkownik = new Label("Adres:");
		label_uzytkownik.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label_uzytkownik.setBounds(10, 172, 62, 22);
		contentPane.add(label_uzytkownik);
		
		Label label_uprawnienia = new Label("Imię:");
		label_uprawnienia.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label_uprawnienia.setBounds(10, 203, 62, 22);
		contentPane.add(label_uprawnienia);
		
		JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setBounds(97, 233, 125, 20);
		contentPane.add(editorPane_4);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(97, 139, 125, 20);
		contentPane.add(spinner);
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(97, 110, 125, 20);
		contentPane.add(spinner_1);
		
		
		JButton btnSave = new JButton("Zapisz");
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="insert into zamowienia(id_produktu ,ilosc,adres,imie,nazwisko,data_zamowienia,termin_realizacji,przyjal) values (?,?,?,?,?,localtimestamp,localtimestamp + time '00:30',default)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, (int) spinner_1.getValue());
					pst.setInt(2, (int) spinner.getValue());
					pst.setString(3, editorPane_2.getText());
					pst.setString(4, editorPane_3.getText());
					pst.setString(5, editorPane_4.getText());
				
				    
					pst.execute();
					JOptionPane.showMessageDialog(null, "Dane o zamówieniach zapisane pomyślnie!");
					pst.close();
					
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				}
				
				
				
				
				
			}
		});
		btnSave.setBounds(10, 273, 105, 50);
		contentPane.add(btnSave);
		
		Label label = new Label("Nazwisko:");
		label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(10, 231, 62, 22);
		contentPane.add(label);
		
		JButton btnNewButton_1 = new JButton("Zmodyfikuj");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				try {
					String query="Update zamowienia set id_produktu='"+spinner_1.getValue()+"', ilosc='"+spinner.getValue()+"'"
							+ ",Adres='"+editorPane_2.getText()+"',imie='"+editorPane_3.getText()+"', nazwisko='"+editorPane_4.getText()+"' where id_produktu="
									+ "'"+spinner_1.getValue()+"'    ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Dane o zamówieniu zostały zaktualizowane!");
					pst.close();
					
				} catch (Exception f) {
					f.printStackTrace();
				}
				
				
					
				
				
				
				
			}
		});
		btnNewButton_1.setBounds(125, 273, 106, 50);
		contentPane.add(btnNewButton_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1104, 21);
		contentPane.add(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Wyloguj");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				Login window = new Login();
				window.pokaz();
			}
		});
		mnPlik.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Wyjdź");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnPlik.add(mntmNewMenuItem_1);
		
	
		
		
		
		
		
		
		
		
		
		setTitle("Obsługa Klienta");
	}
}
