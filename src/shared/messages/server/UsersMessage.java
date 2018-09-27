package shared.messages.server;

import java.util.List;

import shared.model.User;

public class UsersMessage extends ServerMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7848881453246492185L;
	private List<User> users;

	public UsersMessage(List<User> users) {
		super();
		this.users = users;
	}

	public List<User> getUsers() {
		return this.users;
	}

}
