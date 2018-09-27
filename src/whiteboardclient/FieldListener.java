package whiteboardclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class FieldListener implements ActionListener {

	private JTextField field;
	private String input;

	/*
	 * Constructor FieldListener
	 * 
	 * @param JTextField field
	 */
	public FieldListener(JTextField field) {
		this.field = field;
	}

	/*
	 * Methode om de ingevoerde tekst op te halen uit een field De input wordt
	 * ingesteld afhankelijk van wat er ingevoerd is
	 * 
	 * @param ActionEvent e
	 * 
	 * @return void, met een ingestelde String input voor de tekst
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setInput(field.getText());

	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
