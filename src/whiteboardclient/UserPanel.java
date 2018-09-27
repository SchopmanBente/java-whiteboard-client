package whiteboardclient;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shared.model.User;

public class UserPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2858407897837168541L;
	private DefaultListModel<User> userListModel = new DefaultListModel<User>();
	private JList<User> users = new JList<>(userListModel);
	private JPanel userPanel = new JPanel();

	/*
	 * Constructor UserPanel, wordt aangeroepen als er nog geen users zijn
	 * 
	 * @param nothing
	 */
	public UserPanel() {
		this.users = new JList<User>(userListModel);
		this.userPanel.add(this.users);
		setVisible(true);
	}

	/*
	 * Constructor UserPanel, wordt aangeroepen elke keer dat er een
	 * UsersMessage naar de client verstuurd is
	 * 
	 * @param List<User> users;
	 */
	public UserPanel(List<User> users) {
		for (User user : users) {
			this.userListModel.addElement(user);
		}
		this.users = new JList<User>(userListModel);
		this.users.setCellRenderer(new UserListItemRenderer());
		JScrollPane scrollPane = new JScrollPane(this.users, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.LINE_START);

	}

}
