package whiteboardclient;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChangeListener implements ChangeListener {

	private JSlider slider;
	private String colorChange;
	private JLabel color;
	private SettingsPanel settingsPanel;

	/*
	 * Constructor ColorChangeListener
	 * 
	 * @param String colorChange
	 * 
	 * @param SettingsPanel settingsPanel
	 * 
	 * @param JSlider slider
	 * 
	 * @param JLabel color
	 */
	public ColorChangeListener(String colorChange, SettingsPanel settingsPanel, JSlider slider, JLabel color) {
		this.colorChange = colorChange;
		this.settingsPanel = settingsPanel;
		this.slider = slider;
		this.color = color;
	}

	/*
	 * Methode om het colorLabel aan te passen naar de ingestelde kleur voor r ,
	 * g of b.
	 * 
	 * @param: ChangeEvent e
	 * 
	 * @return: void, colorLabel aangepast naar de wijziging
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int value = slider.getValue();
		Color colorNow = this.color.getBackground();
		int r = colorNow.getRed();
		int g = colorNow.getGreen();
		int b = colorNow.getBlue();
		Color colorNew = new Color(0, 0, 0);
		switch (colorChange) {
		case "red":
			r = value;
			break;
		case "green":
			g = value;
			break;
		case "blue":
			b = value;
			break;
		default:
			break;

		}
		colorNew = new Color(r, g, b);
		JLabel colorLabel = this.settingsPanel.getColorLabel();
		this.settingsPanel.remove(this.settingsPanel.getColorLabel());
		colorLabel.setBackground(colorNew);
		this.settingsPanel.getColorsChooserPanel().add(colorLabel, BorderLayout.NORTH);
		this.settingsPanel.revalidate();
		this.settingsPanel.repaint();
	}

}
