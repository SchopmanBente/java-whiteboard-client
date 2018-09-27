package shared.model;

import java.awt.Point;

public class Ring extends Drawing{

	private int diameter;
	
	public Ring(Point location, int diameter) {
		super(location);
		this.diameter = diameter;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

}
