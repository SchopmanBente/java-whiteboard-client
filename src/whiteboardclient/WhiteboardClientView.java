package whiteboardclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import shared.messages.server.UsersMessage;
import shared.messages.server.WhiteboardMessage;
import shared.model.User;

public class WhiteboardClientView extends JFrame implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -953418612274248107L;
	private JLabel whiteboardLabel = new JLabel();
	private JPanel whiteboardPanel = new JPanel();
	private WhiteboardClient client;
	private JTabbedPane tabs = new JTabbedPane();
	private JTextField textDrawing = new JTextField(10);
	private SettingsPanel settingsPanel;
	private JPanel userPanel = new JPanel();
	private UserPanel userListPanel = new UserPanel();
	private JPanel controlPanel;
	private DrawingController mouseController;
	private WhiteboardClientViewListener windowListener;
	private final String LINE = "LINE";
	private final String SMILEY = "SMILEY";
	private final String BLOKJE = "BLOKJE";
	private final String CIRKEL = "CIRKEL";
	private final String RONDJE = "RONDJE";
	private final String SOLID = "SOLID";
	private final String RING = "RING";

	/*
	 * De main-methode die afhankelijk van de args-length een
	 * WhiteboardClientView met een connectie met de server genereert of een
	 * WhiteboardClientView zonder connectie met de server
	 * 
	 * @param String[] args
	 * 
	 * @return Een WhiteboardClientView
	 */
	public static void main(String[] args) {
		if (args.length == 3) {
			String address = args[0];
			if(address == "localhost"){
				address = "127.0.0.1";
			}
			int port = Integer.parseInt(args[1]);
			String name = args[2];
			new WhiteboardClientView(address, port, name);
		} else {
			new WhiteboardClientView();
		}

	}

	/*
	 * Constructor die wordt aangeroepen als er een address, port en name
	 * beschikbaar zijn
	 * 
	 * @param String address
	 * 
	 * @param int port
	 * 
	 * @param String name
	 * 
	 * @return WhiteboardClientView waarbij een random kleur wordt toegewezen
	 * aan de gebruiker
	 */
	public WhiteboardClientView(String address, int port, String name) {
		String cmd = "RANDOM";
		Color color = new Color(0, 0, 0);
		this.setClient(new WhiteboardClient(address, port, name, cmd, color));
		this.getClient().addObserver(this);
		createGUI();

	}

	/*
	 * Constructor die wordt aangeroepen als er niets wordt aangegeven
	 * 
	 * @param nothing
	 * 
	 * @return WhiteboardClientView waarbij de gebruiker alles kan instellen
	 */
	public WhiteboardClientView() {
		createGUI();
	}

	/*
	 * methode die wordt aangeroepen als de gebruiker instellingen heeft
	 * toegepast Hierdoor kan er een connectie met de server worden opgezet
	 * 
	 * @param String address
	 * 
	 * @param int port
	 * 
	 * @param String name
	 * 
	 * @param String cmd
	 * 
	 * @param Color color
	 * 
	 * @return void, maar het bereikte resultaat is dat de WhiteboardClientView
	 * aangepast is en waaraan een Client verbonden is
	 */
	public void onChange(String address, int port, String name, String cmd, Color color) {
		this.setClient(new WhiteboardClient(address, port, name, cmd, color));
		this.getClient().addObserver(this);
		updateGUI();
		System.out.print("On change succedeed!");
	}

	/*
	 * Methode die wordt aangeroepen als de WhiteboardClient een message
	 * binnenkrijgt en alle observers hierover inlicht. Deze methoe kijkt naar
	 * het type message om de juiste manier van afhandelen te regelen.
	 * 
	 * @param Observable
	 * 
	 * @param object
	 * 
	 * @return void, afhandeling naar het type message
	 */
	@Override
	public void update(Observable arg0, Object object) {

		System.out.println("Retrieving message..");

		// Whiteboard en Users messages afhandelen
		if (object instanceof WhiteboardMessage) {
			System.out.println("whiteboard message binnengekomen");
			WhiteboardMessage message = (WhiteboardMessage) object;
			whiteboardLabel.setIcon(new ImageIcon(message.getImage()));
		} else if (object instanceof UsersMessage) {
			System.out.println("Found userMessage!");
			UsersMessage message = (UsersMessage) object;
			showUsers(message);
		}
	}

	/*
	 * Methode om een GUI te maken voor de WhiteboardClientView
	 * 
	 * @param nothing
	 * 
	 * @return void
	 */
	private void createGUI() {
		setViewTitle();
		// Het whiteboard kan in dit label getoond worden
		whiteboardLabel.setPreferredSize(new Dimension(600, 800));
		whiteboardPanel = new JPanel();
		whiteboardPanel.add(whiteboardLabel, BorderLayout.CENTER);
		/*
		 * Een JTabbedPane wordt aangemaakt om een userPanel en settingsPanel
		 * aan toe te voegen
		 */
		tabs = new JTabbedPane();
		userPanel = new JPanel();
		userPanel.add(userListPanel);
		settingsPanel = new SettingsPanel(this);
		tabs.addTab("Settings", settingsPanel);
		tabs.addTab("Users", userPanel);
		mouseController = new TextDrawingController(getClient(), this);
		whiteboardPanel.addMouseListener(mouseController);
		controlPanel = createControlPanel();
		// controlPanel wordt alleen getoond als er al een client aanwezig is
		if (this.getClient() == null) {
			controlPanel.setVisible(false);
		}
		add(whiteboardPanel, BorderLayout.CENTER);
		add(tabs, BorderLayout.EAST);
		add(controlPanel, BorderLayout.PAGE_END);
		windowListener = new WhiteboardClientViewListener(getClient(), this);
		addWindowListener(windowListener);
		pack();
		setVisible(true);
	}

	/*
	 * Methode om het controlPanel mee te maken, hierdoor kan de gebruiker
	 * berichten versturen op het whiteboard
	 * 
	 * @param nothing
	 * 
	 * @return JPanel controlPanel
	 */
	private JPanel createControlPanel() {
		// Maak een listener die naar het ActionCommand luistert
		ControlActionListener listener = new ControlActionListener(getClient(), this);
		// Instellen van de ActionCommand en de listener toevoegen per optie
		textDrawing.setActionCommand("TEXT");
		textDrawing.addActionListener(listener);
		JButton line = createDrawingButton("line", LINE, listener);
		JButton smiley = createDrawingButton("smiley", SMILEY, listener);
		JButton blokje = createDrawingButton("blokje", BLOKJE, listener);
		JButton cirkel = createDrawingButton("cirkel", CIRKEL, listener);
		JButton rondje = createDrawingButton("rondje", RONDJE, listener);
		JButton solid = createDrawingButton("solid", SOLID, listener);
		JButton ring = createDrawingButton("ring", RING,listener);

		// ControlPanel maken en opbouwen
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1, 1));
		controlPanel.add(textDrawing);
		controlPanel.add(line);
		controlPanel.add(smiley);
		controlPanel.add(blokje);
		controlPanel.add(cirkel);
		controlPanel.add(rondje);
		controlPanel.add(solid);
		controlPanel.add(ring);

		return controlPanel;
	}
	
	public void updateControlPanel(){
		this.controlPanel.removeAll();
		JPanel controls = createControlPanel();
		this.controlPanel.add(controls);
		this.controlPanel.revalidate();
		this.controlPanel.repaint();
	}

	/*
	 * Methode om een JButton met actionCommand en ActionListener te maken
	 * 
	 * @param String text
	 * 
	 * @param String actionCommand
	 * 
	 * @param ActionListener listener
	 * 
	 * @return JButton button
	 */
	private JButton createDrawingButton(String text, String actionCommand, ActionListener listener) {
		JButton button = new JButton(text);
		button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		return button;
	}

	/*
	 * Methode om de WhiteboardClientView te updaten zodra er een connectie is
	 * gemaakt
	 * 
	 * @param nothing
	 * 
	 * @return nothing
	 */
	private void updateGUI() {
		setViewTitle();
		this.controlPanel.setVisible(true);
		System.out.println(this.getClient());
	    this.settingsPanel.setClient(this.getClient());
		SettingsButtonListener listener = new SettingsButtonListener(this.client, settingsPanel, this);
	    this.settingsPanel.getSettingsButton().removeMouseListener(listener);
		SettingsButtonListener newListener = new SettingsButtonListener(this.client, settingsPanel, this);
		this.settingsPanel.addMouseListener(newListener);
	    

		whiteboardPanel.removeAll();
		whiteboardLabel.setPreferredSize(new Dimension(600, 800));
		whiteboardPanel.add(whiteboardLabel, BorderLayout.CENTER);
		whiteboardPanel.revalidate();
		whiteboardPanel.repaint();

		whiteboardPanel.removeMouseListener(mouseController);
		mouseController = new TextDrawingController(this.getClient(), this);
		whiteboardPanel.addMouseListener(mouseController);

		windowListener = new WhiteboardClientViewListener(this.getClient(), this);
		addWindowListener(windowListener);

	}

	/*
	 * Methode om de titel van de clientapplicatie in te stellen
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	private void setViewTitle() {
		if (getClient() == null) {
			setTitle("Whiteboardclient");
		} else {
			setTitle("Whiteboardclient " + getClient().getUser().getName());
		}
	}

	/*
	 * Zorgt ervoor dat het textDrawing-field wordt leeggehaald
	 * 
	 * @param nothing
	 * 
	 * @return void, met de textDrawing gecleard.
	 */
	public void clearMessage() {
		this.textDrawing.setText("");
	}

	public JTextField getTextDrawingField() {
		return this.textDrawing;

	}

	public String getTextDrawing() {
		return this.textDrawing.getText();
	}

	/*
	 * Toont de users in het userListPanel
	 * 
	 * @param UsersMessage message
	 * 
	 * @return void, met een bijgewerkt userListPanel
	 */
	private void showUsers(UsersMessage message) {
		List<User> users = message.getUsers();
		System.out.println("Retrieved UsersMessage");
		userPanel.remove(userListPanel);
		userListPanel = new UserPanel(users);
		userPanel.add(userListPanel, BorderLayout.NORTH);
		userPanel.revalidate();
		userPanel.repaint();
	}

	/*
	 * Methode om te switchen van de huidige mouseController naar een
	 * LineDrawingController. Deze methode voegt de nieuwe mouseController toe
	 * aan het whiteboardPanel
	 * 
	 * @param nothing
	 * 
	 * @return void
	 */
	public void switchMouseListenerToLine() {
		whiteboardPanel.removeMouseListener(mouseController);
		mouseController = new LineDrawingController(getClient(), this);
		whiteboardPanel.addMouseListener(mouseController);
	}

	/*
	 * Methode om te switchen van de huidige mouseController naar een
	 * TextDrawingController. Deze methode voegt de nieuwe mouseController toe
	 * aan het whiteboardPanel
	 * 
	 * @param nothing
	 * 
	 * @return void
	 */
	public void switchMouseListenerToText() {
		whiteboardPanel.removeMouseListener(mouseController);
		mouseController = new TextDrawingController(getClient(), this);
		whiteboardPanel.addMouseListener(mouseController);
	}

	/*
	 * Methode om te switchen van de huidige mouseController naar een
	 * StampDrawingController voor de betreffende stempel. Deze methode voegt de
	 * nieuwe mouseController toe aan het whiteboardPanel.
	 * 
	 * Het switchen gaat eenvoudig doordat het op dezelfde type Controller
	 * gebaseerd is
	 * 
	 * @param String name
	 * 
	 * @return void
	 */
	public void switchMouseListenerToStamp(String name) {
		whiteboardPanel.removeMouseListener(mouseController);
		mouseController = new StampDrawingController(getClient(), this, name);
		whiteboardPanel.addMouseListener(mouseController);
	}

	public void switchMouseListenerToRing() {
		whiteboardPanel.removeMouseListener(mouseController);
		mouseController = new RingDrawingController(getClient(),this);
		whiteboardPanel.addMouseListener(mouseController);
		
	}

	public WhiteboardClient getClient() {
		return client;
	}

	public void setClient(WhiteboardClient client) {
		this.client = client;
	}

}
