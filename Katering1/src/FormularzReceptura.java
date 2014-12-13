import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private JTextField textOpis;
	private JTextPane nazwaProduktu;
	private JScrollPane containerPane;
	private JSpinner spinnerCena;
	public FormularzReceptura() {
		setLayout(null);
		containerPane=new JScrollPane();
		containerPane.setBounds(0, 0, 688, 438);
		add(containerPane);
		nazwaProduktu= new JTextPane();
		nazwaProduktu.setBounds(10, 141, 354, 148);
		containerPane.add(nazwaProduktu);
		
		textOpis = new JTextField();
		textOpis.setBounds(88, 14, 149, 20);
		containerPane.add(textOpis);
		textOpis.setColumns(10);
		
		JLabel lblNazwaProduktu = new JLabel("Nazwa produktu");
		lblNazwaProduktu.setBounds(0, 17, 78, 14);
		containerPane.add(lblNazwaProduktu);
		
		JLabel lblCena = new JLabel("cena");
		lblCena.setBounds(10, 48, 46, 14);
		containerPane.add(lblCena);
		SpinnerNumberModel modelWartosc=new SpinnerNumberModel(new Double(0),new Double(-10000),new Double(10000),new Double(0.01));
		spinnerCena = new JSpinner(modelWartosc);
		spinnerCena.setBounds(88, 45, 57, 20);
		containerPane.add(spinnerCena);
				
		JLabel lblOpis = new JLabel("Opis");
		lblOpis.setBounds(10, 76, 46, 14);
		add(lblOpis);

	}
}
