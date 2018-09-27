package whiteboardclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlActionListener implements ActionListener {

	private WhiteboardClient client;
	private WhiteboardClientView clientView;
	private final String LINE = "LINE";
	private final String TEXT = "TEXT";
	private final String SMILEY = "SMILEY";
	private final String BLOKJE = "BLOKJE";
	private final String CIRKEL = "CIRKEL";
	private final String SOLID = "SOLID";
	private final String RONDJE = "RONDJE";
	private final String RING = "RING";

	/*
	 * Constructor ControlActionListener
	 * 
	 * @param WhiteboardClient client
	 * 
	 * @parma WhiteboardClientView clientView
	 */
	public ControlActionListener(WhiteboardClient client, WhiteboardClientView clientView) {
		this.client = client;
		this.clientView = clientView;
	}

	/*
	 * Methode om er voor te zorgen dat er gewisseld wordt van MouseListener op
	 * het whitboard
	 * 
	 * @param ActionEvent e
	 * 
	 * @return void, met een andere MouseListener toegevoegd afhankelijk van het
	 * command
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		System.out.println(cmd);
		switch (cmd) {
		case LINE:
			this.clientView.switchMouseListenerToLine();
			break;
		case TEXT:
			this.clientView.switchMouseListenerToText();
			break;
		case SMILEY:
			this.clientView.switchMouseListenerToStamp("smiley.stp");
			break;
		case BLOKJE:
			this.clientView.switchMouseListenerToStamp("blokje.stp");
			break;
		case CIRKEL:
			this.clientView.switchMouseListenerToStamp("cirkel.stp");
			break;
		case SOLID:
			this.clientView.switchMouseListenerToStamp("solid.stp");
			break;
		case RONDJE:
			this.clientView.switchMouseListenerToStamp("rondje.stp");
			break;
		case RING:
			this.clientView.switchMouseListenerToRing();
		default:
			break;

		}

	}

}
