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
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
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
		setFont(new Font("Dialog", Font.BOLD, 12));
		connection=postgresConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1120, 353);
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
		scrollPane_1.setBounds(342, 33, 752, 231);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBounds(107, 172, 124, 20);
		contentPane.add(editorPane_2);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBounds(107, 203, 124, 20);
		contentPane.add(editorPane_3);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(107, 203, 124, 20);
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
		editorPane_4.setBounds(107, 233, 124, 20);
		contentPane.add(editorPane_4);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(107, 139, 124, 20);
		contentPane.add(spinner);
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(107, 110, 124, 20);
		contentPane.add(spinner_1);
		JSpinner spinner_3 = new JSpinner();
		spinner_3.setBounds(107, 264, 124, 20);
		contentPane.add(spinner_3);
		
		
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
		btnSave.setBounds(241, 108, 91, 43);
		contentPane.add(btnSave);
		
		Label label = new Label("Nazwisko:");
		label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(10, 231, 62, 22);
		contentPane.add(label);
		
		JButton btnNewButton_1 = new JButton("Modyfikuj");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				try {
					String query="Update zamowienia set id_produktu='"+spinner_1.getValue()+"', ilosc='"+spinner.getValue()+"'"
							+ ",Adres='"+editorPane_2.getText()+"',imie='"+editorPane_3.getText()+"', nazwisko='"+editorPane_4.getText()+"' where id="
									+ "'"+spinner_3.getValue()+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Dane o zamówieniu zostały zaktualizowane!");
					pst.close();
					
				} catch (Exception f) {
					f.printStackTrace();
				}
				
				
					
				
				
				
				
			}
		});
		btnNewButton_1.setBounds(241, 210, 91, 43);
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
		
		JButton btnNewButton = new JButton("Anuluj");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					String query="Delete from zamowienia where id=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, (int) spinner_3.getValue());
					pst.execute();
					JOptionPane.showMessageDialog(null, "Zamówienie zostało anulowane!");
					pst.close();
					
				} catch (Exception f) {
					f.printStackTrace();
				}
				
				
				
				
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(241, 155, 91, 50);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Id zamówienia:");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel.setBounds(10, 262, 91, 22);
		contentPane.add(lblNewLabel);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(114, 110, 29, 20);
		contentPane.add(spinner_2);
		
		
		
	
		
		
		
		
		
		
		
		
		
		setTitle("Obsługa Klienta");
	}
}
