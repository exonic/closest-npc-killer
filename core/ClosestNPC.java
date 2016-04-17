package core;

import nodes.*;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

@ScriptManifest(author = "Jordan Francis", info = "Kills the closest NPC.", name = "Closest NPC Killer", version = 1, logo = "")
public class ClosestNPC extends Script
{
    private long start, now;
    private String status = "";
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onStart()
    {
        start = System.currentTimeMillis();

        Collections.addAll(nodes,
                new Attack(this),
                new Eat(this),
                new AntiBan(this)
        );

        experienceTracker.start(Skill.ATTACK);
        experienceTracker.start(Skill.STRENGTH);
        experienceTracker.start(Skill.DEFENCE);
        experienceTracker.start(Skill.MAGIC);
        experienceTracker.start(Skill.RANGED);
    }

    @Override
    public int onLoop() throws InterruptedException
    {
        for (Node n : nodes)
        {
            if(n.validate())
            {
                status = n.status();

                if(n.execute())
                {
                    return random(500, 1000);
                }
            }
        }

        return random(600, 1700);
    }

    @Override
    public void onExit() {}

    @Override
    public void onPaint(Graphics2D g)
    {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 13));

        this.now = System.currentTimeMillis() - this.start;
        long s = now / 1000, m = s / 60, h = m / 60;
        s %= 60; m %= 60; h %= 24;

        g.drawString("Attack XP gained: " + experienceTracker.getGainedXP(Skill.ATTACK), 10, 220);
        g.drawString("Strength XP gained: " + experienceTracker.getGainedXP(Skill.STRENGTH), 10, 240);
        g.drawString("Defence XP gained: " + experienceTracker.getGainedXP(Skill.DEFENCE), 10, 260);
        g.drawString("Magic XP gained: " + experienceTracker.getGainedXP(Skill.MAGIC), 10, 280);
        g.drawString("Range XP gained: " + experienceTracker.getGainedXP(Skill.RANGED), 10, 300);
        g.drawString(String.format("Runtime: %02d:%02d:%02d", h, m, s), 10, 330);
    }
}
