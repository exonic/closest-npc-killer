package nodes;

import core.Node;
import data.Constants;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

public class Attack extends Node
{
    public Attack(Script s)
    {
        super(s);
    }

    @Override
    public String status()
    {
        return "Attacking NPC";
    }

    @Override
    public boolean validate() throws InterruptedException
    {
        return !s.myPlayer().isUnderAttack() && s.myPlayer().getInteracting() == null;
    }

    @Override
    public boolean execute() throws InterruptedException
    {
        final NPC TARGET = s.npcs.closest(Constants.NPCS);

        if(TARGET != null)
        {
            TARGET.interact("Attack");
        }

        return true;
    }
}
