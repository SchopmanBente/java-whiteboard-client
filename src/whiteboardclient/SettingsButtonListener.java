package whiteboardclient;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;

public class SettingsButtonListener extends MouseAdapter {

	private WhiteboardClient client;
	private SettingsPanel panel;
	private WhiteboardClientView view;
	private boolean isIp;
	private int poort;

	/*
	 * Constructor van de SettingsButtonListener
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param SettingsPanel panel
	 * 
	 * @param WhiteboardClientView view
	 */
	public SettingsButtonListener(WhiteboardClient client, SettingsPanel panel, WhiteboardClientView view) {
		this.client = client;
		this.panel = panel;
		this.view = view;
		this.isIp = false;
		this.poort = 0;
	}

	/*
	 * Methode die wordt aangeroepen als de SettingsButton is ingedrukt. Als
	 * deze is ingedrukt, dan wordt de ingevulde input gevalideerd
	 * 
	 * @param MouseEvent e
	 * 
	 * @return void, met een call naar de view.onChange() als er aan de
	 * voorwaarden is voldaan
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			System.out.println(view.getClient());
			if (view.getClient() == null) {
				setClient();
			} else {
				System.out.println("Updating client!");
				updateClient();
			}
			break;
		default:
			break;

		}

	}

	private void updateClient() {
		String address = panel.getIpfield().getText().trim();
		isIp = isIp(address);
		if (address.contains("localhost")) {
			address = "127.0.0.1";
		}
		this.panel.setVisibleInvalidIpLabel(!isIp);
		String poortString = panel.getPortfield().getText();
		boolean poortFeedbackVisible = setPoortFeedback(poortString);
		if (!poortFeedbackVisible) {
			poort = Integer.parseInt(poortString);
		}
		String name = panel.getUsernamefield().getText();
		setNameFeedbackVisible(name);
		String cmd = getColorCommand();
		Color color = getColor(cmd);
		if (!cmd.isEmpty() && !name.isEmpty()) {
           this.view.getClient().sendChangeMessage(color, name);
           this.view.updateControlPanel();
		}
	}

	private void setClient() {
		String address = panel.getIpfield().getText().trim();
		isIp = isIp(address);
		if (address.contains("localhost")) {
			address = "127.0.0.1";
		}
		this.panel.setVisibleInvalidIpLabel(!isIp);
		String poortString = panel.getPortfield().getText();
		boolean poortFeedbackVisible = setPoortFeedback(poortString);
		if (!poortFeedbackVisible) {
			poort = Integer.parseInt(poortString);
		}
		String name = panel.getUsernamefield().getText();
		setNameFeedbackVisible(name);
		String cmd = getColorCommand();
		Color color = getColor(cmd);
		if (isIp && !poortString.isEmpty() && !name.isEmpty() && !cmd.isEmpty()) {
			this.view.onChange(address, poort, name, cmd, color);
		}
	}

	/*
	 * Methode om te bepalen of de feedback getoond moet worden over de username
	 * 
	 * @param String name
	 * 
	 * @return void, call naar de setVisibleInvalidNameLabel methode in het
	 * SettingsPanel waarbij afhankelijk van of de string leeg is of niet de
	 * feedback wordt getoond
	 */
	private void setNameFeedbackVisible(String name) {
		if (name.isEmpty()) {
			this.panel.setVisibleInvalidNameLabel(true);
		} else {
			this.panel.setVisibleInvalidNameLabel(false);
		}
	}

	/*
	 * Methode om te bepalen of de feedback getoond moet worden over het
	 * ingevoerde poortnummer
	 * 
	 * @param String poortString
	 * 
	 * @return void, call naar de setVisibleInvalidPortLabel methode in het
	 * SettingsPanel waarbij afhankelijk van of de string leeg is of niet de
	 * feedback wordt getoond
	 */
	private boolean setPoortFeedback(String poortString) {
		if (poortString.isEmpty()) {
			this.panel.setVisibleInvalidPortLabel(true);
			return true;
		} else {
			this.panel.setVisibleInvalidPortLabel(false);
			return false;
		}
	}

	/*
	 * Methode om te bepalen of het ingevoerde IPaddress een IPv4 address is
	 * 
	 * @param String address
	 * 
	 * @return boolean isValidIP
	 */
	private boolean isIp(String address) {
		Boolean isValidIp = false;
		/*
		 * Regex maken waarbij de opbouw van een IPv4 Address wordt gevolgd Aan
		 * het begin van de String wordt gekeken of er een 1, 2 of 3 cijfers
		 * staan en er wordt gekeken of er een . staat Dit gebeurdt 3 keer.
		 * Daarna wordt er gekeken of er nog 1 tot 3 cijfers staan Daarna wordt
		 * deze regex gematcht met de ingevoerde string
		 */
		Pattern p = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
		Matcher match = p.matcher(address);
		Boolean isIp = match.find();
		if (!isIp) {
			// localhost wordt ook geaccepteerd als valid ip omdat deze kan
			// worden omgezet
			if (address.contains("localhost")) {
				isValidIp = true;
			} else {
				isValidIp = false;
			}
		} else {
			isValidIp = isIp;
		}
		return isValidIp;
	}

	/*
	 * Methode om de kleur te bepalen afhankelijk van de gekozen methode
	 * 
	 * @param String cmd
	 * 
	 * @return Color color;
	 */
	private Color getColor(String cmd) {
		Color color = new Color(0, 0, 0);
		switch (cmd) {
		case "RANDOM":
			color = new Color(0, 0, 0);
		case "CHOOSE":
			color = panel.getColorLabel().getBackground();
		}
		return color;
	}

	/*
	 * Methode om te bepalen of er een kleur gegenereert moet worden of dat deze
	 * moet worden opgehaald
	 * 
	 * @param nothing
	 * 
	 * @return String cmd
	 */
	private String getColorCommand() {
		String cmd = "RANDOM";
		for (Enumeration<AbstractButton> buttons = panel.getSelectColor().getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				cmd = button.getActionCommand();
			}
		}
		return cmd;
	}

}
