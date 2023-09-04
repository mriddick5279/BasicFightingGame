public class Opponent {

    private int health;
    private int prevAttackDamage;
    private int FKCooldown;
    private int KCooldown;

    public Opponent()
    {
        this.health = 100;
        this.KCooldown = 0;
        this.FKCooldown = 0;
    }

    public void usePunch(boolean pTurn)
    {
        Punch p = new Punch(pTurn);
        prevAttackDamage = p.getDamage();
    }

    public void useKick(boolean pTurn)
    {
        Kick k = new Kick(pTurn);
        prevAttackDamage = k.getDamage();
    }

    public void useFlyingKick(boolean pTurn)
    {
        FlyingKick fk = new FlyingKick(pTurn);
        prevAttackDamage = fk.getDamage();
    }

    public void setHealth(int newHealth) { this.health = newHealth; }
    public int getHealth() { return health; }

    public int getPrevAttackDamage() { return prevAttackDamage; }

    public void setFKCooldown()
    {
        if (FKCooldown == 0)
        {
            this.FKCooldown = 3;
        }
        else
        {
            this.FKCooldown -= 1;
        }
    }
    public int getFKCooldown() { return FKCooldown; }

    public void setKCooldown()
    {
        if (KCooldown == 0)
        {
            this.KCooldown = 2;
        }
        else
        {
            this.KCooldown -= 1;
        }
    }
    public int getKCooldown() { return KCooldown; }

    public void reset()
    {
        health = 100;
        KCooldown = 0;
        FKCooldown = 0;
    }
}
