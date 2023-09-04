public class Player {

    private int health; // Keeps track of opponent health
    private int prevAttackDamage; // Keeps track of the amount of damage the previous attack did
    private int level; // Keeps track of what level the player is on
    private int KCooldown; // Keeps track of the cooldown on the Kick move
    private int FKCooldown; // Keeps track of the cooldown on the Flying Kick move

    /* Constructor for Player class */
    public Player()
    {
        this.health = 100;
        this.KCooldown = 0;
        this.FKCooldown = 0;
        this.level = 1;
    }

    /* Carries out the result of using the Punch attack */
    public void usePunch(boolean pTurn)
    {
        Punch p = new Punch(pTurn);
        prevAttackDamage = p.getDamage();
    }

    /* Carries out the result of using the Kick attack */
    public void useKick(boolean pTurn)
    {
        Kick k = new Kick(pTurn);
        prevAttackDamage = k.getDamage();
    }

    /* Carries out the result of using the Flying Kick attack */
    public void useFlyingKick(boolean pTurn)
    {
        FlyingKick fk = new FlyingKick(pTurn);
        prevAttackDamage = fk.getDamage();
    }

    /**
     * Setters and Getters for class variables
     * NOTE: Setter for prevAttackDamage did not feel needed
     */
    public void setHealth(int newHealth) { this.health = newHealth; }
    public int getHealth() { return health; }

    public void setLevel() {this.level += 1; }
    public int getLevel() { return level; }

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

    /* Sets cooldowns back to 0 and Player health back to 100 */
    public void reset()
    {
        health = 100;
        KCooldown = 0;
        FKCooldown = 0;
    }
}
