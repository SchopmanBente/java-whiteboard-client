package whiteboardclient;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class SettingsPanel extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759412641104968589L;
	List<Integer> colorSetting;
	private JPanel colorsPanel = new JPanel(new CardLayout());
	private JPanel colorsChooserPanel = new JPanel();
	private JPanel randomChooserPanel = new JPanel();
	private JPanel userSettingsPanel = new JPanel();
	private JRadioButton randomButton = new JRadioButton("Random color");
	private JRadioButton chooseButton = new JRadioButton("Choose a color");
	private JPanel serverPanel = new JPanel();
	private JTextField ipField = new JTextField(15);
	private JFormattedTextField portField;
	private JTextField userNameField = new JTextField(15);
	private JLabel usercolorredlabel = new JLabel("red");
	private JLabel usercolorbluelabel = new JLabel("blue");
	private JLabel usercolorgreenlabel = new JLabel("green");
	private JLabel colorLabel = new JLabel();
	private JButton settingsButton = new JButton("Send settings");
	private ButtonGroup selectColor;
	final String CHOOSE = "CHOOSE";
	final String RANDOM = "RANDOM";
	private JLabel invalidIp = new JLabel("Please enter a valid IP!");
	private JLabel invalidPort = new JLabel("The port field can't be empty.");
	private JLabel invalidName = new JLabel("The username field can't be empty.");
	private WhiteboardClient client;
	private WhiteboardClientView clientView;

	/*
	 * Deze constructor wordt gebruikt als er nog geen connectie met een server
	 * is.
	 * 
	 * @param WhiteboardClientView
	 */
	public SettingsPanel(WhiteboardClientView clientView) {
		this.clientView = clientView;
		createGui();
	}

	/*
	 * Deze constructor wordt gebruikt als er een client is
	 * 
	 * @param WhiteboardClient client, de client verbonden aan de view
	 */
	public SettingsPanel(WhiteboardClient client) {
		this.client = client;
		client.addObserver(this);
		createGui();
	}

	/*
	 * Deze methode maakt de GUI voor het setttingsPanel aan
	 * 
	 * @param nothing
	 * 
	 * @return nothing
	 */
	private void createGui() {
		userSettingsPanel.setPreferredSize(new Dimension(200, 400));
		createColorPicker();
		createServerPanel();
		add(serverPanel);
		add(userSettingsPanel);
		SettingsButtonListener listener = new SettingsButtonListener(this.client, this, this.clientView);
		getSettingsButton().addMouseListener(listener);
		add(getSettingsButton());

		invalidIp.setVisible(false);
		invalidPort.setVisible(false);
		invalidName.setVisible(false);
		this.add(invalidIp);
		this.add(invalidPort);
		this.add(invalidName);
		this.setPreferredSize(new Dimension(225, 700));
		setVisible(true);
	}

	/*
	 * Deze methode zorgt ervoor dat er een colorpicker wordt gemaakt waarbij de
	 * gebruiker eerst een keuze maakt tussen zelf een kleur kiezen of een kleur
	 * laten genereren. Afhankelijk van de keuze wordt een ander panel getoond
	 * 
	 * @param nothing
	 * 
	 * @return: nothing
	 */
	private void createColorPicker() {
		ColorSettingsActionListener cal = new ColorSettingsActionListener(colorsPanel);
		chooseButton = createRadioButton("Choose a color", CHOOSE, cal, true);
		randomButton = createRadioButton("Random color", RANDOM, cal, false);
		selectColor = new ButtonGroup();
		selectColor.add(chooseButton);
		selectColor.add(randomButton);

		userSettingsPanel.add(chooseButton, BorderLayout.NORTH);
		userSettingsPanel.add(randomButton, BorderLayout.NORTH);
		userSettingsPanel.add(colorsPanel, BorderLayout.NORTH);

		colorsChooserPanel.setPreferredSize(new Dimension(200, 350));
		colorsChooserPanel.add(usercolorredlabel);
		JSlider red = createColorSlider("red");
		colorsChooserPanel.add(red);
		colorsChooserPanel.add(usercolorgreenlabel);
		JSlider green = createColorSlider("green");
		colorsChooserPanel.add(green);
		colorsChooserPanel.add(usercolorbluelabel);
		JSlider blue = createColorSlider("blue");
		colorsChooserPanel.add(blue);
		colorLabel.setOpaque(true);
		colorLabel.setPreferredSize(new Dimension(50, 50));
		colorLabel.setBackground(new Color(0, 0, 0));
		colorsChooserPanel.add(colorLabel);

		colorsPanel.add(colorsChooserPanel, BorderLayout.NORTH);
		colorsPanel.add(randomChooserPanel, BorderLayout.NORTH);
	}

	/*
	 * Methode om een JRadioButton met een actionCommand, actionlistener en
	 * isSelected bool te maken
	 * 
	 * @param String text
	 * 
	 * @param String actionCommand
	 * 
	 * @param ActionListener cal
	 * 
	 * @param boolean isSelected
	 */
	private JRadioButton createRadioButton(String text, String actionCommand, ActionListener cal, boolean isSelected) {
		JRadioButton button = new JRadioButton(text);
		button.setActionCommand(actionCommand);
		button.addActionListener(cal);
		button.setSelected(isSelected);
		return button;
	}

	/*
	 * Methode om een JSlider te genereren die het mogelijk maakt om een kleur
	 * binnen de range 0 tot 255 in te stellen
	 * 
	 * @param String color, volgens rgb
	 * 
	 * @return een JSlider
	 */
	private JSlider createColorSlider(String color) {
		JSlider colorSlider = new JSlider(0, 255);
		colorSlider.setValue(0);
		colorSlider.setMinorTickSpacing(5);
		colorSlider.setMajorTickSpacing(50);
		colorSlider.setPaintLabels(true);
		colorSlider.setPaintTicks(true);
		colorSlider.addChangeListener(new ColorChangeListener(color, this, colorSlider, colorLabel));
		return colorSlider;
	}

	/*
	 * Deze methode genereert 3 velden: username, ip en poort
	 * 
	 * @param nothing
	 * 
	 * @return JPanel serverPanel;
	 */
	private void createServerPanel() {
		// De formatter wordt gebruikt zodat alleen Integers zijn toegestaan
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setAllowsInvalid(false);
		// Deze wordt toegevoegd aan het portField
		portField = new JFormattedTextField(formatter);
		portField.setColumns(10);
		ipField.setEditable(true);
		portField.setEditable(true);
		// De velden worden toegevoegd aan het JPanel
		serverPanel.add(new JLabel("Username"), BorderLayout.NORTH);
		serverPanel.add(getUsernamefield(), BorderLayout.NORTH);
		serverPanel.add(new JLabel("Ip"), BorderLayout.SOUTH);
		serverPanel.add(getIpfield(), BorderLayout.SOUTH);
		serverPanel.add(new JLabel("Poort"), BorderLayout.SOUTH);
		serverPanel.add(getPortfield(), BorderLayout.SOUTH);

		serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.PAGE_AXIS));
		// De velden krijgen allemaal een FieldListener
		// @see: FieldListener
		userNameField.addActionListener(new FieldListener(userNameField));
		ipField.addActionListener(new FieldListener(ipField));
		portField.addActionListener(new FieldListener(portField));

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("update binnen");
		System.out.println(arg1);
	}

	public JTextField getIpfield() {
		return ipField;
	}

	public void setIpfield(JTextField ipfield) {
		this.ipField = ipfield;
	}

	public JTextField getUsernamefield() {
		return userNameField;
	}

	public void setUsernamefield(JTextField usernamefield) {
		this.userNameField = usernamefield;
	}

	public JFormattedTextField getPortfield() {
		return portField;
	}

	public void setPortfield(JFormattedTextField portfield) {
		this.portField = portfield;
	}

	public ButtonGroup getSelectColor() {
		return selectColor;
	}

	public void setSelectColor(ButtonGroup selectColor) {
		this.selectColor = selectColor;
	}

	public JLabel getColorLabel() {
		return colorLabel;
	}

	public void setColorLabel(JLabel colorLabel) {
		this.colorLabel = colorLabel;
	}

	public JPanel getColorsChooserPanel() {
		return colorsChooserPanel;
	}

	public void setColorsChooserPanel(JPanel colorsChooserPanel) {
		this.colorsChooserPanel = colorsChooserPanel;
	}

	/*
	 * Deze methode bepaalt of het invalidIpLabel zichtbaar wordt Als het waar
	 * is, dan wordt deze zichtbaar onder de SettingsButton
	 * 
	 * @param boolean value
	 * 
	 * @return nothing
	 */
	public void setVisibleInvalidIpLabel(boolean value) {
		this.invalidIp.setVisible(value);
	}

	/*
	 * Deze methode bepaalt of het invalidPortLabel zichtbaar wordt Als het waar
	 * is, dan wordt deze zichtbaar onder de SettingsButton
	 * 
	 * @param boolean value
	 * 
	 * @return nothing
	 */
	public void setVisibleInvalidPortLabel(boolean value) {
		this.invalidPort.setVisible(value);
	}

	/*
	 * Deze methode bepaalt of het invalidNameLabel zichtbaar wordt Als het waar
	 * is, dan wordt deze zichtbaar onder de SettingsButton
	 * 
	 * @param boolean value
	 * 
	 * @return nothing
	 */
	public void setVisibleInvalidNameLabel(boolean value) {
		this.invalidName.setVisible(value);
	}

	public WhiteboardClient getClient() {
		return client;
	}

	public void setClient(WhiteboardClient client) {
		this.client = client;
	}

	public JButton getSettingsButton() {
		return settingsButton;
	}

	public void setSettingsButton(JButton settingsButton) {
		this.settingsButton = settingsButton;
	}

}
