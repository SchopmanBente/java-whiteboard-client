package whiteboardclient;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import shared.messages.client.DrawingMessage;
import shared.model.Ring;

public class RingDrawingController extends MouseAdapter implements DrawingController {
	
	private WhiteboardClient client;
	private WhiteboardClientView clientView;

	/*
	 * Constructor van de RingDrawingController
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param WhiteboardClientView clientView
	 * 
	 */
	public RingDrawingController(WhiteboardClient client, WhiteboardClientView clientView) {
		this.client = client;
		this.clientView = clientView;
	}
	
	/*
	 * Methode om een DrawingMessage met het type drawing Ring te versturen
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
			placeRing(e);
			break;
		default:
			break;
		}

	}
	
	private void placeRing(MouseEvent e){
		Point point = e.getPoint();
		Ring ring = new Ring(point,1);
		DrawingMessage ringMessage = new DrawingMessage(this.client.getUser(), ring);
		client.sendMessage(ringMessage);	
	}


}
