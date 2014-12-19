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
   private JFrame frame;
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
	/**
	 * Create the frame.
	 */
	public ObslugaKlienta() {
		setFont(new Font("Dialog", Font.BOLD, 12));
		connection=postgresConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 273, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Wyświetl wszystkie zamówienia");
		btnLoadTable.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Wszystkiezamowienia wz = new Wszystkiezamowienia();
				wz.setVisible(true);
			}
		});
		btnLoadTable.setBounds(10, 33, 220, 50);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(59, 249, 365, -162);
		contentPane.add(scrollPane);
		
		
		JButton btnUtworz = new JButton("Utwórz zamówienie");
		btnUtworz.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUtworz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="insert into zamowienia(data_zamowienia,termin_realizacji,przyjal) values (localtimestamp,localtimestamp + time '00:30',default)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					Zamowienia zamowienia = new Zamowienia();
					zamowienia.setVisible(true);
					pst.close();
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				}
				
			}
		});
		btnUtworz.setBounds(10, 93, 220, 50);
		contentPane.add(btnUtworz);
		
		JButton btnNewButton_1 = new JButton("Modyfikuj");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
			/*	try {
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
				
				
*/JOptionPane.showMessageDialog(null, "in progress...");

					
			}
		});
		
		btnNewButton_1.setBounds(10, 213, 220, 50);
		contentPane.add(btnNewButton_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 257, 21);
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
		
		JButton btnNewButton = new JButton("Anuluj zamówienie");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Wszystkiezamowienia wz = new Wszystkiezamowienia();
				wz.setVisible(true);	
				
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(10, 153, 220, 50);
		contentPane.add(btnNewButton);
		
		setTitle("Obsługa Klienta");
	}
}
