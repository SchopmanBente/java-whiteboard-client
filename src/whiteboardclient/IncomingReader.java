package whiteboardclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import shared.messages.server.UsersMessage;
import shared.messages.server.WhiteboardMessage;

public class IncomingReader implements Runnable {
	private ObjectInputStream reader;
	private WhiteboardClient client;

	/**
	 * Constructor van IncommingReader.
	 * 
	 * @param socket
	 * @param client
	 */
	public IncomingReader(Socket socket, WhiteboardClient client) {
		this.client = client;
		try {
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	/*
	 * Methode om de ontvangen berichten van de server in te lezen en te laten
	 * zien op het ontvangen whiteboard of de userlijst in de robotclient
	 */
	@Override
	public void run() {

		Object object;
		try {
			System.out.println("Wait");

			while ((object = reader.readObject()) != null) {
				System.out.println("Retrieving an object");

				// Voeg het object toe aan de client als het om
				// een WhiteboardMessge of UsersMessage gaat
				if (object instanceof WhiteboardMessage) {
					System.out.println("WhiteboardMessage found");
					client.addIncoming((WhiteboardMessage) object);
				} else if (object instanceof UsersMessage) {
					System.out.println("Usersmessage found!");
					client.addIncoming((UsersMessage) object);
				} else {
					System.out.println("Message type is not allowed: " + object.getClass().getSimpleName());
				}

			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
