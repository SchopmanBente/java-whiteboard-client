package whiteboardclient;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import shared.model.User;

public class UserListItemRenderer extends JLabel implements ListCellRenderer<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7137648068760769849L;

	/*
	 * Methode om een cel in de JList aan te passen naar de naam van de User in
	 * de lijst en de tekstkleur aan te passen naar de kleur van de gebruiker
	 * 
	 * @param JList<? extends User> users;
	 * 
	 * @param User user
	 * 
	 * @param int index
	 * 
	 * @param boolean isSelected
	 * 
	 * @param boolean cellHasFocus
	 * 
	 * @return Component, Elk listItem in de userlijst toont de naam van de user
	 * en zijn of haar gebruikte kleur
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends User> users, User user, int index, boolean isSelected,
			boolean cellHasFocus) {

		setText(user.getName());
		setForeground(user.getColor());
		return this;
	}

}
