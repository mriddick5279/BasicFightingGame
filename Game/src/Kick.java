import java.util.Random;
public class Kick implements Attack {

    private int damage;
    private boolean playerTurn;
    public Kick(boolean pTurn)
    {
        this.playerTurn = pTurn;
        setDamage();
        attackMessage();
    }
    private void attackMessage()
    {
        if (playerTurn)
        {
            System.out.println("You use Kick! You did " + damage + " damage to your opponent.\n");
        }
        else
        {
            System.out.println("They use Kick! You take " + damage + " damage.\n");
        }
    }

    private void setDamage()
    {
        int minDamage = 7;
        int maxDamage = 10;
        Random r = new Random();
        this.damage = r.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    public int getDamage() { return damage; }
}
