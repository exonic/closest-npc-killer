package nodes;

import core.Node;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class AntiBan extends Node
{

    private int random = 0;

    public AntiBan(Script s)
    {
        super(s);
    }

    @Override
    public String status()
    {
        return "Anti-ban";
    }

    @Override
    public boolean validate() throws InterruptedException
    {
        this.random = s.random(500);
        return random <= 6;
    }

    @Override
    public boolean execute() throws InterruptedException
    {
        switch (this.random)
        {
            case 0:
                s.tabs.open(Tab.SKILLS);
                s.camera.movePitch(s.random(30, 80));
                s.mouse.moveOutsideScreen();
                break;
            case 1:
                s.tabs.open(Tab.QUEST);
                break;
            case 2:
                s.tabs.open(Tab.FRIENDS);
                s.camera.movePitch(s.random(30, 80));
                s.mouse.moveOutsideScreen();
                s.sleep(s.random(3000, 5000));
                break;
            case 3:
                s.skills.hoverSkill(Skill.STRENGTH);
                break;
            case 4:
                s.skills.hoverSkill(Skill.ATTACK);
                s.camera.movePitch(s.random(30, 80));
            case 5:
                s.skills.hoverSkill(Skill.DEFENCE);
                break;
            case 6:
                new ConditionalSleep(s.random(2000, 15000))
                {
                    @Override
                    public boolean condition() throws InterruptedException
                    {
                        return s.mouse.moveOutsideScreen();
                    }
                }.sleep();

                break;
        }

        return true;
    }
}
