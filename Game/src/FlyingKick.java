import java.util.Random;
public class FlyingKick implements Attack {

    private int damage;
    private boolean playerTurn;
    public FlyingKick(boolean pTurn)
    {
        this.playerTurn = pTurn;
        setDamage();
        attackMessage();
    }
    private void attackMessage()
    {
        if (playerTurn)
        {
            System.out.println("You use Flying Kick! You did " + damage + " damage to your opponent.\n");
        }
        else
        {
            System.out.println("They use Flying Kick! You take " + damage + " damage.\n");
        }
    }

    private void setDamage()
    {
        int minDamage = 11;
        int maxDamage = 15;
        Random r = new Random();
        this.damage = r.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    public int getDamage() { return damage; }
}
