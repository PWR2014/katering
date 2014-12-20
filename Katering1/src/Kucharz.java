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


public class Kucharz extends JFrame {

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
					Kucharz frame = new Kucharz();
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
	public Kucharz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("Kucharz");
		
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

		
		
		JMenu mnZamowienia = new JMenu("Zamówienia");
		JMenuItem mntmZrobic=new JMenuItem("Zarządzaj Zamowieniami");
		mnZamowienia.add(mntmZrobic);
		mntmZrobic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TabelaZamowieniaKucharz tzk= new TabelaZamowieniaKucharz(tabbedPane);
				tabbedPane.add("Zarządzaj Zamówieniami",tzk);
				int index = tabbedPane.indexOfComponent(tzk);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		menuBar.add(mnZamowienia);
		
		JMenu mnMagazyn = new JMenu("Magazyn");
		JMenuItem mntmStanSurowcow=new JMenuItem("Stan Surowców");
		mnMagazyn.add(mntmStanSurowcow);
		mntmStanSurowcow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StanSurowcow ss= new StanSurowcow(tabbedPane);
				tabbedPane.add("Stan Surowców",ss);
				int index = tabbedPane.indexOfComponent(ss);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		menuBar.add(mnMagazyn);
		
		JMenu mnReceptury = new JMenu("Receptury");
		menuBar.add(mnReceptury);
		JMenuItem mntmListaReceptur = new JMenuItem("Lista Receptur");
		mntmListaReceptur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TabelaReceptur tr = new TabelaReceptur(tabbedPane);
				tabbedPane.add("Tabela Receptur",tr);
				int index = tabbedPane.indexOfComponent(tr);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		mnReceptury.add(mntmListaReceptur);
		menuBar.add(mnReceptury);
		
		setContentPane(contentPane);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
