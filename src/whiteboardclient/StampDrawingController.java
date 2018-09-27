package whiteboardclient;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import shared.messages.client.DrawingMessage;
import shared.model.Stamp;

public class StampDrawingController extends MouseAdapter implements DrawingController {

	private WhiteboardClient client;
	private WhiteboardClientView clientView;
	private String stampFileName;

	/*
	 * Constructor van de StampDrawingController
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param WhiteboardClientView clientView
	 * 
	 * @param String stampFileName
	 */
	public StampDrawingController(WhiteboardClient client, WhiteboardClientView clientView, String stampFileName) {
		this.client = client;
		this.clientView = clientView;
		this.stampFileName = stampFileName;
	}

	/*
	 * Methode om een DrawingMessage met het type drawing Stamp te versturen
	 * naar de server
	 * 
	 * @param MouseEvent e
	 * 
	 * @return void, verzonden DrawingMessage
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			placeStamp(e);
			break;
		default:
			break;
		}

	}

	/*
	 * Methode om de DrawingMessage met de gekozen stamp te verzenden
	 * 
	 * @param MouseEvent e
	 */
	private void placeStamp(MouseEvent e) {
		Point point = e.getPoint();

		try {
			// Inladen van de stamp vanaf de opgegeven locatie
			String location = "/stamps/" + stampFileName;
			ObjectInputStream input = new ObjectInputStream(getClass().getResourceAsStream(location));
			boolean[][] pixels = (boolean[][]) input.readObject();
			input.close();
			// Nieuw Stamp object maken
			Stamp stamp = new Stamp(point, pixels);
			System.out.println("Sending a DrawingMessage with Drawing: Stamp");
			DrawingMessage stampMessage = new DrawingMessage(this.client.getUser(), stamp);
			client.sendMessage(stampMessage);

		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
