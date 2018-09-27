package whiteboardclient;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Random;

import shared.messages.Message;
import shared.messages.client.InitialMessage;
import shared.messages.client.StopMessage;
import shared.messages.client.UserMessage;
import shared.model.ColorChange;
import shared.model.User;

public class WhiteboardClient extends Observable {
	private User user;
	private Socket socket;
	private ObjectOutputStream writer;

	/*
	 * Constructor voor de WhiteboardClient
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
	 */
	public WhiteboardClient(String address, int port, String name, String cmd, Color color) {
		this.user = createUser(name, cmd, color);
		setUpNetworking(address, port);
		System.out.println("Sending Initialmessage");
		sendMessage(new InitialMessage(this.getUser()));
		System.out.println("Adding incomingreader");
		new IncomingReader(socket, this);
		setChanged();
		notifyObservers(this);
	}

	/*
	 * Methode om een user aan te maken
	 * 
	 * @param String name
	 * 
	 * @param String cmd
	 * 
	 * @param Color color
	 * 
	 * @return User
	 */
	private User createUser(String name, String cmd, Color color) {
		User newUser = new User(name, color);
		if (cmd == "CHOOSE") {
			newUser = new User(name, color);
		}
		if (cmd == "RANDOM") {
			newUser = new User(name, generateColor());
		}
		return newUser;
	}

	/*
	 * Methode om random een kleur te genereren tussen de 1 en 255
	 * 
	 * @param nothing
	 * 
	 * @return Color
	 */
	private Color generateColor() {
		Random random = new Random();
		int r = random.nextInt(254) + 1;
		int g = random.nextInt(254) + 1;
		int b = random.nextInt(254) + 1;
		return new Color(r, g, b);
	}

	/*
	 * Methode om een verbinding met de server op te zetten
	 * 
	 * @param String address
	 * 
	 * @param int port
	 * 
	 * @return void, geslaagd networkconnectie of niet
	 */
	public void setUpNetworking(String address, int port) {
		try {
			socket = new Socket(address, port);
			writer = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("network connection established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Methode om als een client een Message te versturen
	 * 
	 * @param Message message
	 * 
	 * @return void, message verstuurd naar de server
	 */
	public void sendMessage(Message message) {
		try {
			writer.writeObject(message);
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Methode om een inkomend bericht te weergeven in de WhiteboardClientView
	 * 
	 * @param Message message
	 * 
	 * @return void, WhiteboardClientView op de hoogte van de wijzigingen
	 */
	public void addIncoming(Message message) {
		System.out.println("Message incoming");
		setChanged();
		notifyObservers(message);
	}

	public User getUser() {
		return this.user;
	}

	public Color getColor() {
		return this.getUser().getColor();
	}

	/*
	 * Methode om een StopMessage te verturen naar de Server
	 * 
	 * @param: nothing
	 * 
	 * @return: void, User afgemeld bij de server
	 */
	public void sendStopMessage() {
		StopMessage message = new StopMessage(this.getUser());
		sendMessage(message);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void sendChangeMessage(Color color, String name) {
		System.out.println("Hier kom ik wel hoor!");
		if(this.user.getColor() != color){
			System.out.println("The color of the user is changed!");
			Color kleur = color;
			ColorChange change = new ColorChange(kleur);
			sendUserMessage(change);
			
		}
		
	}

	private void sendUserMessage(ColorChange color) {
		this.user = new User(user.getName(),color.getKleur());
		System.out.println("Send usermessage!");
		UserMessage message = new UserMessage(this.getUser(),color);
		sendMessage(message);
		
	}

}
