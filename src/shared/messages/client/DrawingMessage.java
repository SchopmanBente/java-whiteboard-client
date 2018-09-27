package shared.messages.client;

import shared.model.User;

import shared.model.Drawing;

public class DrawingMessage extends ClientMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -374861542168778091L;

	private Drawing drawing;

	public DrawingMessage(User sender, Drawing drawing) {
		super(sender);

		this.drawing = drawing;
	}

	public DrawingMessage(DrawingMessage message) {
		this(message.sender, message.drawing);
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}

}
