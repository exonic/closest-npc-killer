import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;
import java.awt.*;
import java.util.List;

@ScriptManifest(author = "Jordan Francis", info = "Kills the closest NPC.", name = "Closest NPC Killer", version = 0, logo = "")
public class Main extends Script
{
    private int kills = 0;

    private long start, now, hours, minutes, seconds;

    @Override
    public void onStart()
    {
        start = System.currentTimeMillis();
        experienceTracker.start(Skill.ATTACK);
        experienceTracker.start(Skill.STRENGTH);
        experienceTracker.start(Skill.DEFENCE);
        experienceTracker.start(Skill.MAGIC);
        experienceTracker.start(Skill.RANGED);
    }

    @Override
    public int onLoop() throws InterruptedException
    {
        if(skills.getDynamic(Skill.HITPOINTS) <= random(19, 25))
        {
            log("Eat -> lobster");
            getInventory().interact("Eat", "Lobster");
        }

        List<NPC> targets = getNpcs().getAll();
        targets.remove("Rat");
        final NPC TARGET = npcs.closest(targets);

        if (TARGET != null && !combat.isFighting())
        {
            if(!TARGET.isVisible())
            {
                log("Camera -> Move");
                camera.toEntity(TARGET);
            }

            log("Attack -> Target");
            TARGET.interact("Attack");

            this.kills++;

            new ConditionalSleep(random(500, 2000))
            {
                @Override
                public boolean condition() throws InterruptedException
                {
                    return antiBan();
                }
            }.sleep();
        }

        return random(600, 1700);
    }

    public boolean antiBan() throws InterruptedException
    {
        boolean task = false;

        switch (random(0, 500))
        {
            case 0:
                log("Open -> Skills tab");
                tabs.open(Tab.SKILLS);
                camera.movePitch(random(30, 80));
                mouse.moveOutsideScreen();
                task = true;
                break;
            case 1:
                log("Open -> Quest tab");
                tabs.open(Tab.QUEST);
                task = true;
                break;
            case 2:
                log("Open -> Friends tab");
                tabs.open(Tab.FRIENDS);
                camera.movePitch(random(30, 80));
                mouse.moveOutsideScreen();
                sleep(random(3000, 5000));
                task = true;
                break;
            case 3:
                log("Hover -> Strength skill");
                skills.hoverSkill(Skill.STRENGTH);
                task = true;
                break;
            case 4:
                log("Hover -> Attack skill");
                skills.hoverSkill(Skill.ATTACK);
                camera.movePitch(random(30, 80));
            case 5:
                log("Hover -> Defence skill");
                skills.hoverSkill(Skill.DEFENCE);
                task = true;
                break;
            case 6:
                new ConditionalSleep(random(2000, 15000))
                {
                    @Override
                    public boolean condition() throws InterruptedException
                    {
                        return mouse.moveOutsideScreen();
                    }
                }.sleep();

                break;
        }

        return task;
    }

    @Override
    public void onExit()
    {
        log("Number of kills: " + kills);
        log("Attack XP gained: " + experienceTracker.getGainedXP(Skill.ATTACK));
        log("Strength XP gained: " + experienceTracker.getGainedXP(Skill.STRENGTH));
        log("Defence XP gained: " + experienceTracker.getGainedXP(Skill.DEFENCE));
        log("Magic XP gained: " + experienceTracker.getGainedXP(Skill.MAGIC));
        log("Range XP gained: " + experienceTracker.getGainedXP(Skill.RANGED));
    }

    @Override
    public void onPaint(Graphics2D g)
    {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 13));

        this.now = System.currentTimeMillis() - this.start;
        this.hours = this.now / 3600000L;
        this.minutes = this.now / 60000L;
        this.seconds = this.now / 1000L;

        g.drawString("Number of kills: " + this.kills, 10, 200);
        g.drawString("Attack XP gained: " + experienceTracker.getGainedXP(Skill.ATTACK), 10, 220);
        g.drawString("Strength XP gained: " + experienceTracker.getGainedXP(Skill.STRENGTH), 10, 240);
        g.drawString("Defence XP gained: " + experienceTracker.getGainedXP(Skill.DEFENCE), 10, 260);
        g.drawString("Magic XP gained: " + experienceTracker.getGainedXP(Skill.MAGIC), 10, 280);
        g.drawString("Range XP gained: " + experienceTracker.getGainedXP(Skill.RANGED), 10, 300);
        g.drawString("Runtime: " + this.hours + ":" + this.minutes + ":" + seconds, 10, 330);
    }
} 