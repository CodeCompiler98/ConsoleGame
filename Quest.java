package Chapter11.videogame;

import java.util.Scanner;

//Author: Jonah Dalton
//Date: 2/16/2024
public class Quest {
    //initialize variables
    private final String name;
    private final String description;
    private final Item reward;
    private final int level;

    //constructor, takes in the quest "enemy" to track HP, takes in item to distribute reward
    public Quest(String name, String description, Item reward, int level) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.level = level;
    }

    //signals the official start of a quest, checks if level is high enough. If not enemy health is set to zero.
    //This canceling the quest with no reward by not fulfilling the while statement required to start the game loop
    public void beginQuest(Character player, Character enemy){
        if (player.getLevel()<level){
            System.out.println("You do not meet the recommended levels. Do not proceed");
            enemy.setHealth(0);

        } else {
            System.out.println("The quest has begun. " + description);
        }

    }

    //checks to see if enemy HP is <= 0. If so the method increases the level based on the player subclass
    // and prompts the user for and index to place the users reward into
    public void completeQuest(Character enemy, Character Player){
        if(enemy.getHealthPoints() <= 0){
            System.out.println("The enemy has been defeated and the quest has been completed! Congrats!");

            System.out.println("LEVEL UP");
            if(Player instanceof Mage){
                ((Mage)Player).levelUp();
                System.out.println("Your new stats are: " + ((Mage)Player).toString());
            } else {
                ((Warrior)Player).levelUp();
                System.out.println("Your new stats are: " + ((Warrior)Player).toString());
            }

            System.out.println("\nHere is your reward:\nName: " + reward.getItemName() + ", " + reward.inspect());
            System.out.println("Please add it to your inventory, What slot?");
            System.out.println("Slot 1: " + Player.getInventory(1) + "\nSlot 2: " +  Player.getInventory(2) + "\nSlot 3: " + Player.getInventory(3) + "\nSlot 4: "
                    + Player.getInventory(4) + "\nSlot 5: " + Player.getInventory(5));
            Scanner input = new Scanner(System.in);
            boolean valid = false;
            while(!valid){
                int index = input.nextInt();
                if(index > 5){
                    System.out.println("Invalid choice.");
                } else {
                    valid = true;
                    Player.addToInventory(reward, index);
                }
            }
        }
    }

    //accessor methods
    public String getName() {return name;}
    public Item getReward() {return reward;}

    @Override
    public String toString(){
        return "The Quest is " + name + ". The reward is: " + reward;
    }

}
