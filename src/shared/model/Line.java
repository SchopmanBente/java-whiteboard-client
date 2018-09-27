package shared.model;

import java.awt.Point;

public class Line extends Drawing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4937804766817345179L;
	private Point end;

	public Line(Point location, Point end) {
		super(location);
		this.setEnd(end);
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

}
