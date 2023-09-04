import java.util.Random;
public class Punch implements Attack {

    private int damage; // Keeps track of the amount of damage the Punch attack did
    private boolean playerTurn; // Determines what attack message is output to the console

    /* Constructor for Punch class */
    public Punch(boolean pTurn)
    {
        this.playerTurn = pTurn;
        setDamage();
        attackMessage();
    }

    /* See Attack interface for documentation*/
    private void attackMessage()
    {
        if (playerTurn)
        {
            System.out.println("You use Punch! You did " + damage + " damage to your opponent.\n");
        }
        else
        {
            System.out.println("They use Punch! You take " + damage + " damage.\n");
        }
    }

    /* See Attack interface for documentation*/
    private void setDamage()
    {
        int minDamage = 5;
        int maxDamage = 8;
        Random r = new Random();
        this.damage = r.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    /* See Attack interface for documentation*/
    public int getDamage() { return damage; }
}
