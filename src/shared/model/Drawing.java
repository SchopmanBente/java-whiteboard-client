package shared.model;

import java.awt.Point;

import shared.model.Action;;

public class Drawing extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2812761458809498589L;
	protected Point location;

	public Drawing(Point location) {
		this.setLocation(location);
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
