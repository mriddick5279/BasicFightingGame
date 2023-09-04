import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

    private static final Scanner userInput = new Scanner(System.in); // Takes in Player attack choice

    public static void main(String[] args) {

        // Creating Player and Opponent
        Player p = new Player();
        Opponent o = new Opponent();

        // Welcome message
        System.out.println("Welcome to the Fighting Game! Your journey to the top begins now. You will start on Level 1");

        boolean playerDead = false; // Will determine when the game ends
        boolean playerTurn = true; // Keeps track of whose turn it is

        ArrayList<Integer> attacksAvailable; // Holds what attacks are available to the Player
        ArrayList<Integer> opponentAttacksAvailable; // Holds what attacks are available to the Opponent

        int damageOutput = 0;

        while (!playerDead)
        {
            if (playerTurn) // Player's turn
            {
                // Choice dialogue is presented to Player and the Player then chooses the attack they want to use
                attacksAvailable = new ArrayList<>();
                String playerChoices = presentAttackChoices(p, attacksAvailable);
                System.out.println(playerChoices);
                int attackChoice = Integer.parseInt(userInput.nextLine());

                // Player's turn will not continue until an available choice is chosen by them
                while (attackChoice == 0 || attackChoice > 3 || !(attacksAvailable.contains(attackChoice)))
                {
                    System.out.println("That choice is not available. Please select an attack that is available to you.");
                    System.out.println(playerChoices);

                    attackChoice = Integer.parseInt(userInput.nextLine());
                }

                boolean kUsed = false; // Keeps track of if the Kick move was just used
                boolean fkUsed = false; // Keeps track of if the Flying Kick move was just used

                // Will execute the attack the Player's attack
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

                delay(2); // Allows Player time to read text

                damageOutput += p.getPrevAttackDamage();

                // Update cooldowns on Kick and Flying Kick moves for Player
                if (!kUsed && p.getKCooldown() > 0)
                {
                    p.setKCooldown();
                }
                if (!fkUsed && p.getFKCooldown() > 0)
                {
                    p.setFKCooldown();
                }

                playerTurn = false; // Tells that it is the Opponent's turn now

                // Checks for LEVEL END scenario where Opponent is defeated and moves Player to next level
                if (o.getHealth() <= 0)
                {
                    System.out.println("Congratulations! You have beaten this level, now onto the next!");
                    delay(2); // Allows Player time to read text
                    nextLevel(p, o);
                    playerTurn = true;
                    System.out.println("Level " + p.getLevel() + ": FIGHT!");
                    delay(3);
                }
            }
            else // Opponent's turn
            {
                // Opponent sees what moves it can use and chooses the move it wants to use
                opponentAttacksAvailable = new ArrayList<>();
                opponentAttacks(o, opponentAttacksAvailable);
                System.out.println("It's the opponent's turn!");
                Random r = new Random();
                int compChoice = r.nextInt(3) + 1;

                // Opponent's turn will not continue until they choose a choice available to them
                while (!opponentAttacksAvailable.contains(compChoice))
                {
                    compChoice = r.nextInt(3) + 1;
                }

                delay(3); // Allows Player time to read text

                boolean kUsed = false; // Keeps track of if the Kick move was just used
                boolean fkUsed = false; // Keeps track of if the Flying Kick move was just used

                // Will execute the Opponent's attack
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

                delay(3); // Allows Player time to read text

                // Update cooldowns on Kick and Flying Kick moves for Opponent
                if (!kUsed && o.getKCooldown() > 0)
                {
                    o.setKCooldown();
                }
                if (!fkUsed && o.getFKCooldown() > 0)
                {
                    o.setFKCooldown();
                }

                playerTurn = true; // Tells that it is the Player's turn now

                // Checks for GAME OVER scenario where Player is defeated and prompts Player on if they want to restart
                if (p.getHealth() <= 0 )
                {
                    // "Defeated" prompt and check for high score
                    System.out.println("You have been defeated.");
                    delay(3);
                    saveHighScores(p, damageOutput);

                    // "Restart" prompt and Player input for restarting
                    System.out.println("Would you like to restart? Y or N");
                    String userRestart = userInput.nextLine();

                    boolean valid; // Keeps track of if the Player has entered a valid response to "Restart Game" prompt

                    // Detects if Player entered correct response of "Y" or "N" (case is ignored)
                    if (!userRestart.equalsIgnoreCase("y"))
                    {
                        if (!userRestart.equalsIgnoreCase("n"))
                        {
                            valid = false; // Shows Player has not entered a valid response
                            while (!valid) // Will loop until Player has entered a valid response ("Y" or "N")
                            {
                                // "Invalid Response" prompt and Player input
                                System.out.println("Sorry. That response is not valid. Please indicate that you would like to\n" +
                                        "restart by typing either Y or N");
                                userRestart = userInput.nextLine();

                                // Checks to see if Player has entered a valid response
                                if (userRestart.equalsIgnoreCase("y") || userRestart.equalsIgnoreCase("n"))
                                {
                                    valid = true; // Shows Player has entered correct response of "Y" or "N"
                                }
                            }
                        }
                    }

                    // Checks whether Player wanted to restart or not
                    if (userRestart.equalsIgnoreCase("y")) // Player opted to restart
                    {
                        restart(p, o); // Resets Player/Opponent health and cooldowns and resets Level to Level 1
                        delay(2); // Allows Player time to read text
                    }
                    else // Player opted not to restart
                    {
                        playerDead = true; // Will end the game loop
                    }
                }
            }
        }

        // GAME OVER message
        System.out.println("GAME OVER! Better luck next time!");
        delay(2); // Allows Player time to read text
    }

    /**
     * Gives user the correct prompt for what attacks they have and details if any of them are on cooldown
     * @param p is the Player
     * @param attacksAvailable is the EMPTY list of attacks available to the Player that will be set in this method
     * @return gives the string to be printed to the user that details their attacks and which ones are on cooldown
     */
    private static String presentAttackChoices(Player p, ArrayList<Integer> attacksAvailable)
    {
        String theChoices = ""; // String that will be returned
        attacksAvailable.add(1); // Punch never has cooldown
        if (p.getFKCooldown() == 0 && p.getKCooldown() == 0) // No cooldown on Kick or Flying Kick
        {
            attacksAvailable.add(2);
            attacksAvailable.add(3);
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick\n" +
                    "3. Flying Kick\n";
        }
        else if (p.getFKCooldown() == 0 && p.getKCooldown() > 0) // No cooldown on Flying Kick, Cooldown on Kick
        {
            attacksAvailable.add(3);
           theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick (Can use in " + p.getKCooldown() + " turn(s))\n"+
                    "3. Flying Kick\n";
        }
        else if (p.getFKCooldown() > 0 && p.getKCooldown() == 0) // No cooldown on Kick, Cooldown on Flying Kick
        {
            attacksAvailable.add(2);
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick\n" +
                    "3. Flying Kick (Can use in " + p.getFKCooldown() + " turn(s))\n";
        }
        else if (p.getFKCooldown() > 0 && p.getKCooldown() > 0) // Cooldown on Kick and Flying Kick
        {
            theChoices = "It's your turn! What attack would you like to use?\n" +
                    "1. Punch\n" +
                    "2. Kick (Can use in " + p.getKCooldown() + " turn(s))\n" +
                    "3. Flying Kick (Can use in " + p.getFKCooldown() + " turn(s))\n";
        }
        return theChoices;
    }

    /**
     * Tells the opponent what attacks are available to them
     * @param o is the Opponent
     * @param attacksAvailable is the EMPTY list of attacks available to the Opponent that will be set in this method
     */
    private static void opponentAttacks(Opponent o, ArrayList<Integer> attacksAvailable)
    {
        attacksAvailable.add(1); // Punch has no cooldown
        if (o.getKCooldown() == 0 && o.getFKCooldown() == 0 ) // No cooldown on Kick and Flying Kick
        {
            attacksAvailable.add(2);
            attacksAvailable.add(3);
        }
        else if (o.getKCooldown() == 0 && o.getFKCooldown() > 0 ) // No cooldown on Kick, Cooldown on Flying Kick
        {
            attacksAvailable.add(2);
        }
        else if (o.getKCooldown() > 0 && o.getFKCooldown() == 0 ) // No cooldown on Flying Kick, Cooldown on Kick
        {
            attacksAvailable.add(3);
        }
    }

    /**
     * Moves the game onto the next level by resetting Player and Opponent classes and increasing the current level
     * the Player is on by 1
     * @param p is the Player
     * @param o is the Opponent
     */
    private static void nextLevel(Player p, Opponent o)
    {
        p.reset(); // Resets Player health and cooldowns
        o.reset(); // Resets Opponent health and cooldowns
        p.setLevel(); // Increases current level Player is on by 1
    }

    /**
     * Restarts the game by resetting Player/Opponent stats and setting Level back to Level 1
     * @param p is the Player
     * @param o is the Opponent
     */
    private static void restart(Player p, Opponent o)
    {
        p.setLevel(1);
        p.reset();
        o.reset();
        System.out.println("Welcome back! You'll start from Level 1 again.");
    }

    /**
     * Will save the score the Player achieved if they got a score higher than one that already exists
     * @param p is the Player
     * @param damageOutput is the amount of damage the Player did to the Opponent
     */
    private static void saveHighScores(Player p, int damageOutput)
    {
        System.out.println(damageOutput);
        try
        {
            // Getting file and setting up file reader
            File highScores = new File("HighScores.json");
            Reader r = new FileReader(highScores);

            // Setting up parser for the JSON file
            JSONParser parser = new JSONParser();
            JSONArray JSONscores = (JSONArray) parser.parse(r);

            int totalScore = damageOutput + p.getLevel(); // The total score for the Player

            /* Loop will go through the list of high scores and see if the Player's score is higher than an existing one*/
            for (int i = 0 ; i < JSONscores.size() ; i++)
            {
                // Getting the score from certain location in the array
                JSONObject currScore = (JSONObject) JSONscores.get(i);
                String score = (String) currScore.get("high score");

                int compareScore = Integer.parseInt(score); // Simplifies comparison in if statement

                if (totalScore > compareScore) // Player's score is higher than the one found
                {
                    // "New High Score" prompt and Player name input
                    System.out.println("Congratulations! You've set a new High Score!\n" +
                            "Enter a name below so it can be saved and looked at later!\n");
                    String name = userInput.nextLine();
                    String enterScore = Integer.toString(totalScore);

                    // Putting the Player's score into the JSON file
                    JSONObject newScore = new JSONObject();
                    newScore.put("name", (String) name);
                    newScore.put("high score", (String) enterScore);
                    JSONscores.add(newScore);
                    FileWriter w = new FileWriter("HighScores.json");
                    w.write(JSONscores.toJSONString());
                    w.close();
                    break;
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delays output to console by 3 seconds to give the user time to read messages between levels and during Opponent attacks
     */
    private static void delay(int seconds)
    {
        try
        {
            TimeUnit.SECONDS.sleep(seconds); // Line that delays console output by 3 seconds
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
