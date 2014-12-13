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


public class Menager extends JFrame {

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
					Menager frame = new Menager();
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
	public Menager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setTitle("Manager");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);
		
		JMenuItem mntmWyjdz = new JMenuItem("Wyjdź");
		mntmWyjdz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnPlik.add(mntmWyjdz);
		JMenu mnPracownicy=new JMenu("Pracownicy");
		menuBar.add(mnPracownicy);
		JMenuItem mntmNowy = new JMenuItem("Nowy Pracownik");
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		mntmNowy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormularzPracownik fp=new FormularzPracownik(tabbedPane);
				fp.czystyFormularz();
				tabbedPane.add("Nowy Pracownik",fp);
				int index=tabbedPane.indexOfComponent(fp);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
			}
		});
		mnPracownicy.add(mntmNowy);
		JMenuItem mntmLista = new JMenuItem("Lista Pracowników");
		mntmLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			TabelaPracownikow tp=new TabelaPracownikow(tabbedPane);
			tabbedPane.add("Tabela Pracowników",tp);
			int index=tabbedPane.indexOfComponent(tp);
			tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
			tabbedPane.setSelectedIndex(index);
			}
		});
		mnPracownicy.add(mntmLista);
		
		JMenu mnMagazyn = new JMenu("Magazyn");
		JMenuItem mntmDodajSurowiec=new JMenuItem("Dodaj Surowiec");
		mnMagazyn.add(mntmDodajSurowiec);
		mntmDodajSurowiec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FormularzSurowiec fm=new FormularzSurowiec(tabbedPane);
				fm.czystyFormularz();
				tabbedPane.add("Nowy Surowiec",fm);
				int index=tabbedPane.indexOfComponent(fm);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		JMenuItem mntmListaSurowcow=new JMenuItem("Lista Surowców");
		mnMagazyn.add(mntmListaSurowcow);
		mntmListaSurowcow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TabelaSurowcow ts = new TabelaSurowcow(tabbedPane);
				tabbedPane.add("Tabela Surowców",ts);
				int index = tabbedPane.indexOfComponent(ts);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
		JMenuItem mntmPrzyjmijSurowiec=new JMenuItem("Przyjmij Surowiec");
		mnMagazyn.add(mntmPrzyjmijSurowiec);
		mntmPrzyjmijSurowiec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrzyjecieSurowca ps = new PrzyjecieSurowca(tabbedPane);
				tabbedPane.add("Przyjmij/Odejmij Surowiec",ps);
				int index = tabbedPane.indexOfComponent(ps);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				
			}
		});
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
		JMenuItem mntmDodajRecepture = new JMenuItem("Dodaj Recepturę");
		mntmDodajRecepture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FormularzReceptura fr=new FormularzReceptura();
				tabbedPane.add("Dodaj Recepturę",fr);
				int index = tabbedPane.indexOfComponent(fr);
				tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(index);
				// TODO Auto-generated method stub
				
			}
		});
		mnReceptury.add(mntmDodajRecepture);
		JMenuItem mntmListaReceptur = new JMenuItem("Lista Receptur");
		mntmListaReceptur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mnReceptury.add(mntmListaReceptur);
		JMenu mnRaporty = new JMenu("Raporty");
		menuBar.add(mnRaporty);
		JMenuItem mntmRaportCzasu = new JMenuItem("Czas Pracy");
		mntmRaportCzasu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
				
			}
		});
		mnRaporty.add(mntmRaportCzasu);
		JMenuItem mntmZuzycieProduktow = new JMenuItem("Zużycie Produtków");
		mntmZuzycieProduktow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mnRaporty.add(mntmZuzycieProduktow);
		JMenuItem mntmIloscZamowien = new JMenuItem("Ilość Zamówień");
		mntmIloscZamowien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
				
			}
		});
		mnRaporty.add(mntmIloscZamowien);
		JMenuItem mntmRaportSprzedazy = new JMenuItem("Sprzedaż");
		mntmRaportSprzedazy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		mnRaporty.add(mntmRaportSprzedazy);
		setContentPane(contentPane);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
