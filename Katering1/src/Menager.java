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

		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
