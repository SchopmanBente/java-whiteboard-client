package whiteboardclient;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import shared.messages.client.DrawingMessage;
import shared.model.Line;

public class LineDrawingController extends MouseAdapter implements DrawingController {

	private WhiteboardClient client;
	private WhiteboardClientView clientView;
	private Point start;

	/*
	 * Constructor LineDrawingController
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param WhiteboardClientView clientView
	 */
	public LineDrawingController(WhiteboardClient client, WhiteboardClientView clientView) {
		this.client = client;
		this.clientView = clientView;
	}

	/*
	 * Deze methode wordt gebruikt om het start-Point van de Line te bepalen Als
	 * de rechter muisknop is ingedrukt dan wordt het startpunt ingevukd
	 * 
	 * @param MouseEvent e
	 * 
	 * @return void, ingesteld start-point
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:
			start = e.getPoint();
			break;
		default:
			break;
		}
	}

	/*
	 * Deze methode wordt gebruikt om het end-Point te bepalen van de line en om
	 * de DrawingMesage te verzenden
	 * 
	 * @param MouseEvent e
	 * 
	 * @return void, client verstuurd een DrawingMessage met een Line-object
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		Point end = e.getPoint();
		Line line = new Line(start, end);
		DrawingMessage drawing = new DrawingMessage(this.client.getUser(), line);
		client.sendMessage(drawing);
	}

}
