package shared.model;

import java.awt.Point;

public class Stamp extends Drawing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6178968351866793507L;
	private boolean[][] stamp;

	public Stamp(Point location, boolean[][] stamp) {
		super(location);
		this.setStamp(stamp);
	}

	public boolean[][] getStamp() {
		return stamp;
	}

	public void setStamp(boolean[][] stamp) {
		this.stamp = stamp;
	}

}
