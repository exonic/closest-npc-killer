package nodes;

import core.Node;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;

public class Eat extends Node
{

    public Eat(Script s)
    {
        super(s);
    }

    @Override
    public String status()
    {
        return "Eating";
    }

    @Override
    public boolean validate() throws InterruptedException {
        return s.skills.getDynamic(Skill.HITPOINTS) <= s.random(19, 25);
    }

    @Override
    public boolean execute() throws InterruptedException {
        return s.getInventory().interact("Eat", "Lobster");
    }
}
