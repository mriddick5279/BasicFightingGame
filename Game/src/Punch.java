import java.util.Random;
public class Punch implements Attack {

    private int damage;
    private boolean playerTurn;
    public Punch(boolean pTurn)
    {
        this.playerTurn = pTurn;
        setDamage();
        attackMessage();
    }
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

    private void setDamage()
    {
        int minDamage = 5;
        int maxDamage = 8;
        Random r = new Random();
        this.damage = r.nextInt(maxDamage - minDamage + 1) + minDamage;
    }

    public int getDamage() { return damage; }
}
