package shared.messages.client;

import shared.model.User;

public class StopMessage extends ClientMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5166083590382754577L;

	public StopMessage(User sender) {
		super(sender);
	}

}
