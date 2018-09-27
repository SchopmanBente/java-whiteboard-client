package whiteboardclient;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ColorSettingsActionListener implements ActionListener {

	private JPanel cards;
	final String CHOOSE = "CHOOSE";
	final String RANDOM = "RANDOM";

	/*
	 * Constructor ColorSettingsActionListener
	 * 
	 * @param JPanel panel
	 */
	public ColorSettingsActionListener(JPanel panel) {
		this.cards = panel;
	}

	/*
	 * Methode om te switchen van het panel dat weergeven wordt afhankelijk van
	 * het ontvangen actionCommand
	 * 
	 * @param ActionEvent e
	 * 
	 * @result void, met een aangepaste card afhankelijk van het ActionCommand
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout) (cards.getLayout());
		String cmd = e.getActionCommand();
		System.out.print(cmd);
		if (cmd.equals(CHOOSE)) {
			cl.first(cards);
		} else if (cmd.equals(RANDOM)) {
			cl.last(cards);
		} else {
			System.out.println("Cmd doesn't exist");
		}

	}

}
