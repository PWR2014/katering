import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Zamowienia extends JFrame {

	private JPanel contentPane;
	private JFrame frame1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zamowienia frame = new Zamowienia();
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
	
	
	
	
	
	public Zamowienia() {
		connection=postgresConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 694, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Wyświetl dane o produktach");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				try {
				String query = "Select * from Produkty";
				PreparedStatement pst = connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				}
					
				
				
				
				
				
			}
		});
		btnNewButton.setBounds(343, 32, 255, 50);
		contentPane.add(btnNewButton);
		JSpinner spinner = new JSpinner();
		spinner.setBounds(115, 32, 76, 20);
		contentPane.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(115, 62, 76, 20);
		contentPane.add(spinner_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(268, 93, 394, 336);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(115, 128, 126, 20);
		contentPane.add(editorPane);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setBounds(115, 158, 126, 20);
		contentPane.add(editorPane_1);
		
		JEditorPane editorPane_2 = new JEditorPane();
		editorPane_2.setBounds(115, 188, 126, 20);
		contentPane.add(editorPane_2);
		
		JEditorPane editorPane_3 = new JEditorPane();
		editorPane_3.setBounds(115, 218, 126, 20);
		contentPane.add(editorPane_3);
		
		JEditorPane editorPane_4 = new JEditorPane();
		editorPane_4.setBounds(115, 248, 126, 20);
		contentPane.add(editorPane_4);
		JButton btnNewButton_1 = new JButton("Wprowadź produkty");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="Insert into Pozycjezamowien(id_zamówienia,id_produktu,ilość) "
							+ "values ((Select MAX(id) from zamowienia),?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, (int) spinner.getValue());
					pst.setInt(2, (int) spinner_1.getValue());
				    pst.execute();
					JOptionPane.showMessageDialog(null, "Produkt dodany,jeżeli chcesz dodaj kolejne");
					
					pst.close();
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				
}
				
				
				
				
				
			}
		});
		btnNewButton_1.setBounds(16, 93, 226, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Wprowadź dane adresowe");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="Insert into adresy(miejscowość,kod_pocztowy,ulica,nr_domu,nr_lokalu) values (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, editorPane.getText());
					pst.setString(2, editorPane_1.getText());
					pst.setString(3, editorPane_2.getText());
					pst.setString(4, editorPane_3.getText());
					pst.setString(5, editorPane_4.getText());
				
					
				    pst.execute();
					JOptionPane.showMessageDialog(null, "Dane adresowe wprowadzone");
					
					pst.close();
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				
}
				
				
				
				
				
			}
		});
		btnNewButton_2.setBounds(16, 283, 226, 23);
		contentPane.add(btnNewButton_2);
		
		JEditorPane editorPane_6 = new JEditorPane();
		editorPane_6.setBounds(115, 319, 126, 20);
		contentPane.add(editorPane_6);
		
		JEditorPane editorPane_7 = new JEditorPane();
		editorPane_7.setBounds(115, 349, 126, 20);
		contentPane.add(editorPane_7);
		
		JEditorPane editorPane_8 = new JEditorPane();
		editorPane_8.setBounds(115, 379, 126, 20);
		contentPane.add(editorPane_8);
		
		JButton btnNewButton_3 = new JButton("Wprowadź dane personalne");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query="Insert into klienci(imię,nazwisko,telefon,adres,id_zamowienia) "
							+ "values(?,?,?,(Select max(id) from adresy),(Select max(id) from zamowienia))";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, editorPane_6.getText());
					pst.setString(2, editorPane_7.getText());
					pst.setString(3, editorPane_8.getText());
				
					
				    pst.execute();
					JOptionPane.showMessageDialog(null, "Dane adresowe wprowadzone");
					
					pst.close();
				} catch (Exception f) {
					JOptionPane.showMessageDialog(null, "Ups,jakiś błąd!");
					f.printStackTrace();
				
}
				
				
				
				
				
			}
		});
		btnNewButton_3.setBounds(16, 409, 226, 23);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Id produktu:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(16, 32, 91, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ilość:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(16, 62, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Miejscowość:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(16, 129, 79, 19);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Kod pocztowy:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(16, 159, 106, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Ulica:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(16, 189, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Nr domu:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(16, 219, 67, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Nr lokalu:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(16, 249, 79, 14);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Imię:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setBounds(16, 319, 46, 14);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Nazwisko:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_8.setBounds(16, 349, 67, 14);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Telefon:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setBounds(16, 379, 46, 14);
		contentPane.add(lblNewLabel_9);
		
		
		
		
		
		setTitle("Szczegółowe zamówienia");
	}
	}
