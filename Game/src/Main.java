import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        Player p = new Player();
        Opponent o = new Opponent();
        System.out.println("Welcome to the Fighting Game! Your journey to the top begins now. You will start on Level 1");

        boolean playerDead = false;
        boolean playerTurn = true;

        ArrayList<Integer> attacksAvailable;
        ArrayList<Integer> opponentAttacksAvailable;

        while (!playerDead)
        {
            if (playerTurn)
            {
                attacksAvailable = new ArrayList<>();
                String playerChoices = presentAttackChoices(p, attacksAvailable);
                System.out.println(playerChoices);
                int attackChoice = Integer.parseInt(userInput.nextLine());

                while (attackChoice == 0 || attackChoice > 3 || !(attacksAvailable.contains(attackChoice)))
                {
                    System.out.println("That choice is not available. Please select an attack that is available to you.");
                    System.out.println(playerChoices);

                    attackChoice = Integer.parseInt(userInput.nextLine());
                }

                boolean kUsed = false;
                boolean fkUsed = false;

                switch (attackChoice)
                {
                    case 1:
                        p.usePunch(playerTurn);
                        o.setHealth(o.getHealth() - p.getPrevAttackDamage());
                        break;
                    case 2:
                        p.useKick(playerTurn);
                        o.setHealth(o.getHealth() - p.getPrevAttackDamage());
                        p.setKCooldown();
                        kUsed = true;
                        break;
                    case 3:
                        p.useFlyingKick(playerTurn);
                        o.setHealth(o.getHealth() - p.getPrevAttackDamage());
                        p.setFKCooldown();
                        fkUsed = true;
                        break;
                }

                if (!kUsed && p.getKCooldown() > 0)
                {
                    p.setKCooldown();
                }
                if (!fkUsed && p.getFKCooldown() > 0)
                {
                    p.setFKCooldown();
                }

                playerTurn = false;

                if (o.getHealth() <= 0)
                {
                    System.out.println("Congratulations! You have beaten this level, now onto the next!");
                    nextLevel(p, o);
                    playerTurn = true;
                    System.out.println("Level " + p.getLevel() + ": FIGHT!");
                    delay();
                }
            }
            else
            {
                opponentAttacksAvailable = new ArrayList<>();
                opponentAttacks(o, opponentAttacksAvailable);
                System.out.println("It's the opponent's turn!");
                Random r = new Random();
                int compChoice = r.nextInt(3) + 1;

                while (!opponentAttacksAvailable.contains(compChoice))
                {
                    compChoice = r.nextInt(3) + 1;
                }

                delay();

                boolean kUsed = false;
                boolean fkUsed = false;

                switch (compChoice)
                {
                    case 1:
                        o.usePunch(playerTurn);
                        p.setHealth(p.getHealth() - o.getPrevAttackDamage());
                        break;
                    case 2:
                        o.useKick(playerTurn);
                        p.setHealth(p.getHealth() - o.getPrevAttackDamage());
                        o.setKCooldown();
                        kUsed = true;
                        break;
                    case 3:
                        o.useFlyingKick(playerTurn);
                        p.setHealth(p.getHealth() - o.getPrevAttackDamage());
                        o.setFKCooldown();
                        fkUsed = true;
                        break;
                }

                if (!kUsed && o.getKCooldown() > 0)
                {
                    o.setKCooldown();
                }
                if (!fkUsed && o.getFKCooldown() > 0)
                {
                    o.setFKCooldown();
                }

                playerTurn = true;

                if (p.getHealth() <= 0 )
                {
                    playerDead = true;
                }
            }
        }

        System.out.println("GAME OVER! Better luck next time!");
    }

    private static String presentAttackChoices(Player p, ArrayList<Integer> attacksAvailable)
    {
        String theChoices = "";
        attacksAvailable.add(1);
        if (p.getFKCooldown() == 0 && p.getKCooldown() == 0)
        {
            attacksAvailable.add(2);
            attacksAvailable.add(3);
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick\n" +
                    "3. Flying Kick\n";
        }
        else if (p.getFKCooldown() == 0 && p.getKCooldown() > 0)
        {
            attacksAvailable.add(3);
           theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick (Can use in " + p.getKCooldown() + " turn(s))\n"+
                    "3. Flying Kick\n";
        }
        else if (p.getFKCooldown() > 0 && p.getKCooldown() == 0)
        {
            attacksAvailable.add(2);
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick\n" +
                    "3. Flying Kick (Can use in " + p.getFKCooldown() + " turn(s))\n";
        }
        else if (p.getFKCooldown() > 0 && p.getKCooldown() > 0)
        {
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick (Can use in " + p.getKCooldown() + " turn(s))\n" +
                    "3. Flying Kick (Can use in " + p.getFKCooldown() + " turn(s))\n";
        }
        return theChoices;
    }

    private static void opponentAttacks(Opponent o, ArrayList<Integer> attacksAvailable)
    {
        attacksAvailable.add(1);
        if (o.getKCooldown() == 0 && o.getFKCooldown() == 0 )
        {
            attacksAvailable.add(2);
            attacksAvailable.add(3);
        }
        else if (o.getKCooldown() == 0 && o.getFKCooldown() > 0 )
        {
            attacksAvailable.add(2);
        }
        else if (o.getKCooldown() > 0 && o.getFKCooldown() == 0 )
        {
            attacksAvailable.add(3);
        }
    }

    private static void nextLevel(Player p, Opponent o)
    {
        p.reset();
        o.reset();
        p.setLevel();
    }

    private static void delay()
    {
        try
        {
            TimeUnit.SECONDS.sleep(3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
