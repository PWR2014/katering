import javax.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;


public class FormularzSurowiec extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textSurowiec;
	
	 public FormularzSurowiec() {
			setBounds(0,0, 600, 450);
			setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nazwa Surowca");
			lblNewLabel.setBounds(24, 38, 126, 15);
			add(lblNewLabel);
			
			JLabel lblJednostakMiary = new JLabel("Jednostka miary");
			lblJednostakMiary.setBounds(24, 70, 114, 15);
			add(lblJednostakMiary);
			
			JComboBox comboJednostka = new JComboBox();
			comboJednostka.setBounds(152, 65, 67, 24);
			add(comboJednostka);
			
			textSurowiec = new JTextField();
			textSurowiec.setBounds(152, 36, 198, 19);
			add(textSurowiec);
			textSurowiec.setColumns(10);
			
			JButton btnNewButton = new JButton("Zapisz");
			btnNewButton.setBounds(231, 65, 119, 25);
			add(btnNewButton);
		 
	}
}
