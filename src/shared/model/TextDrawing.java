package shared.model;

import java.awt.Point;

public class TextDrawing extends Drawing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13200;
	private String text;

	public TextDrawing(Point location, String text) {
		super(location);
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
