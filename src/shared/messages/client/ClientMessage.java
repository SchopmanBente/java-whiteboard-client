package shared.messages.client;

import shared.messages.Message;
import shared.model.User;

public class ClientMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1943053976487452148L;
	protected User sender;

	public ClientMessage(User sender) {
		this.sender = sender;
	}

	public ClientMessage(User sender, String text) {
		this.sender = sender;
	}

	public User getSender() {
		return sender;
	}
}
