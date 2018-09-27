package shared.messages.client;

import shared.model.User;
import shared.model.ColorChange;

public class UserMessage extends ClientMessage
{
    private static final long serialVersionUID = 11500;

    protected ColorChange colorChange;

    public UserMessage( User sender, ColorChange colorChange )
    {
        super( sender );
        this.colorChange = colorChange;
    }

    public ColorChange getColorChange()
    {
        return colorChange;
    }
}
