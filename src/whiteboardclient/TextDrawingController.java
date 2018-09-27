package whiteboardclient;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import shared.messages.client.DrawingMessage;
import shared.model.TextDrawing;

public class TextDrawingController extends MouseAdapter implements DrawingController {

	private WhiteboardClient client;
	private WhiteboardClientView clientView;

	/*
	 * Constructor voor TextDrawingController
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param WhiteboardClientView clientView
	 */
	public TextDrawingController(WhiteboardClient client, WhiteboardClientView clientView) {
		this.client = client;
		this.clientView = clientView;
	}

	/*
	 * Methode om een DrawingMessage met het type drawing Text te versturen naar
	 * de server
	 * 
	 * @param MouseEvent e
	 * 
	 * @return void, verzonden DrawingMessage
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			placeText(e);
			break;
		default:
			break;
		}

	}

	/*
	 * Methode om de DrawingMessage met de gekozen tekst te verzenden
	 * 
	 * @param MouseEvent e
	 */
	private void placeText(MouseEvent e) {
		// Punt bepalen waar de tekst getoond moet worden
		Point location = e.getPoint();
		// Text op halen en leegmaken van het textField
		String text = clientView.getTextDrawing();
		clientView.clearMessage();

		// TextDrawing maken en verzenden
		TextDrawing drawing = new TextDrawing(location, text);
		System.out.println("Sending a DrawingMessage with Drawing: Text");
		DrawingMessage textMessage = new DrawingMessage(client.getUser(), drawing);
		client.sendMessage(textMessage);
	}

}
