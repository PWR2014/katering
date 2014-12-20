import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;


public class Dostawca extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dostawca frame = new Dostawca();
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
	public Dostawca() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("Dostawca");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		JMenuItem mntmWyloguj = new JMenuItem("Wyloguj");
		mntmWyloguj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login window = new Login();
				window.pokaz();
			}
		});
		mnPlik.add(mntmWyloguj);		
		JMenuItem mntmWyjdz = new JMenuItem("Wyjdź");
		mntmWyjdz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnPlik.add(mntmWyjdz);

		
		
		JMenu mnDostawy = new JMenu("Dostawy");
		JMenuItem mntmZarzadzanieDostawami=new JMenuItem("Zarządzaj Dostawami");
		mnDostawy.add(mntmZarzadzanieDostawami);
		mntmZarzadzanieDostawami.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TabelaDostawy td= new TabelaDostawy(tabbedPane);
				tabbedPane.add("Zarządzaj Zamówieniami",td);
				int index = tabbedPane.indexOfComponent(td);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		menuBar.add(mnDostawy);
		

		
		setContentPane(contentPane);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
