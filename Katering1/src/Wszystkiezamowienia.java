import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
public class Wszystkiezamowienia extends JFrame {
	Connection connection=null;
	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JSpinner spinner;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Wszystkiezamowienia frame = new Wszystkiezamowienia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Wszystkiezamowienia() {
		connection=postgresConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 691, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 655, 209);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		try {
			String query="Select k.id_zamowienia,k.imię,k.nazwisko,id_produktu,pz.ilość,p.cena*pz.ilość as kwota From klienci k join adresy a on k.adres=a.id join pozycjeZamowien pz on k.id_zamowienia=pz.id_zamówienia join zamowienia z on pz.id_zamówienia=z.id join Produkty p on pz.id_produktu=p.id";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			btnNewButton = new JButton("Anuluj zamówienie nr:");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						String query="Delete from zamowienia where id=?";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setInt(1, (int) spinner.getValue());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Zamówienie zostało anulowane!");
						pst.close();
						
					} catch (Exception f) {
						f.printStackTrace();
					}
				}
			});
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
			btnNewButton.setBounds(10, 231, 213, 23);
			contentPane.add(btnNewButton);
			
			spinner = new JSpinner();
			spinner.setBounds(230, 233, 40, 20);
			contentPane.add(spinner);
			
		} catch (Exception f) {
			f.printStackTrace();
		}
		setTitle("Wszystkie zamówienia");
	}

}

