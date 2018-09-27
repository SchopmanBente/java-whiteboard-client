package shared.model;

import java.awt.Color;

public class ColorChange extends Action
{
    protected Color kleur;

    public ColorChange( Color kleur )
    {
        this.kleur = kleur;
    }

    public Color getKleur()
    {
        return kleur;
    }

}
