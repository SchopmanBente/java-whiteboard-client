package whiteboardclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WhiteboardClientViewListener extends WindowAdapter {

	private WhiteboardClient client;
	private WhiteboardClientView clientView;

	/*
	 * Constructor van de WhiteboardClientViewListener
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @param WhiteboardClientView view
	 */
	public WhiteboardClientViewListener(WhiteboardClient client, WhiteboardClientView view) {
		this.client = client;
		this.clientView = view;

	}

	/*
	 * Als de Window gesloten wordt en er is een client aanwezig verstuur dan
	 * een StopMessage
	 * 
	 * @param WindowEvent e
	 * 
	 * @return void, er wordt een StopMessage verstuurd als er een connectie is
	 * met de server
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		if (this.client != null) {
			client.sendStopMessage();
		}
	}

}
