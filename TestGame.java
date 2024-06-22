package Chapter11.videogame;
import java.util.Objects;
import java.util.Scanner;
//author: Jonah Dalton
//Date: 2/16/24

//package use

public class TestGame {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        //intake player information. Create a subclass of player depending on the players choice.
        System.out.println("Welcome to RPG Command Line Simulator!");
        System.out.println("Hello Player, what is your name?");
        String name = input.nextLine();
        System.out.println("Hello " + name + ", do you want to be a mage or a barbarian?\nInput '0' for mage and '1' for barbarian");
        int choice = input.nextInt();
        Character Player;
        if(choice == 0){
            System.out.println("Pick a spell: Self-heal, Damage or Explosion");
            String spellType = input.next();
            Player = new Mage(name, 1, 100, 25, spellType);
        } else {
            System.out.println("Pick a weapon: Axe, Sword, or Spear");
            String weaponType = input.next();
            Player = new Warrior(name, 100, 1, 25, weaponType);
        }

        //give the player a default inventory item
        Item health = new Item("Health", "Health Pack", 2, "A jug from a bag, why would you drink this?");
        Player.addToInventory(health, 1);


        //initialize variables that track event repetition and when to end the game
        boolean end = false;
        int choiceChances;
        int backgroundChances;
        int lastChoiceChances = -1;
        int lastBackgroundChances = -1;

        System.out.println("You wake up in a tavern ready to adventure!");
        //start the game loop until stipulations are met
        while (!end){
            System.out.println("----------------------newday----------------------");
            //these ensure that both the action events and background events are random and DO NOT repeat themselves
            choiceChances = (int)(Math.random()* 4);
            while (choiceChances == lastChoiceChances){
                choiceChances = (int)(Math.random()* 4);
            }
            lastChoiceChances = choiceChances;

            backgroundChances = (int)(Math.random()* 4);
            while (backgroundChances == lastBackgroundChances){
                backgroundChances = (int)(Math.random()* 4);
            }
            lastBackgroundChances = backgroundChances;
            Thread.sleep(1000);

            //background events, add more depth to the game
            switch (backgroundChances) {
                case 1:
                    System.out.println("You go take a walk. Its pretty nice outside. Lets nap for a little.\n");
                    break;
                case 2:
                    System.out.println("You go out and socialize at the tavern. The cookies were very good.\n");
                    break;
                case 3:
                    System.out.println("You go out and participate in a medieval raid. Thank goodness you only got an arrow in the arm.\n");
                    break;
                default:
                    System.out.println("You took a nice nap in the library (knob style!)\n");
                    break;
            }
            //main action events for the player to interact with
            switch (choiceChances) {
                //action event 1
                case 1:
                    //ask if the player wants to take action. If so, the user either is prompted with a reward
                    //item to place in their inventory, or die, ending the game
                    double bagChances = Math.random();
                    System.out.println("You find a random bag, do you pick it up? y/n");
                    String bag = input.next();
                    if (bag.equals("y") || bag.equals("yes")){
                        if(bagChances<0.75){
                            int dropChances = (int)(Math.random()*3);
                            Item bagDrop = randomize();
                            System.out.println("You found " + bagDrop.getItemName() + ". What inventory slot (1 - 5) do you put it in? Here is you inventory: ");
                            System.out.println("Slot 1: " + Player.getInventory(1) + "\nSlot 2: " +  Player.getInventory(2) + "\nSlot 3: " + Player.getInventory(3) + "\nSlot 4: "
                                    + Player.getInventory(4) + "\nSlot 5: " + Player.getInventory(5));
                            int index = input.nextInt();
                            Player.addToInventory(bagDrop, index);

                        } else {
                            System.out.println("You pick it up. The owner comes over. He is very big. You get punched. You die.");
                            Player.setHealth(0);
                        }
                    } else {
                        System.out.println("You leave it on the ground. Good job following the honor code.");
                    }
                    break;
                //action event 2
                case 2:
                    //asks if the user wants to do the quest
                    System.out.println("You find a quest board, it has a reward for a rouge wizard. Do you accept? y/n");
                    String inputQW = input.next();
                    if(inputQW.equals("yes") || inputQW.equals("y")){
                        //creates the enemy character object, pulling stats from the user subclass stats
                        Mage enemy;
                        if(Player instanceof Mage){
                            enemy = new Mage("The evil wizard", (((Mage)Player).getLevel()+3), (((Mage)Player).getHealthPoints()+20), (((Mage)Player).getMana() + 5), "damage");
                        } else {
                            enemy = new Mage("The evil wizard", (((Warrior)Player).getLevel()+3), (((Warrior)Player).getHealthPoints()+20), (((Warrior)Player).getStrength() + 5), "damage");
                        }
                        Quest mageQ = new Quest("Wizard battle", "You found the evil wizard. Start battle!", randomize(), 1);
                        //calls the quest class to begin, if user level not enough enemy health = 0, canceling the quest with no reward
                        //by not fulfilling the while statement required to start the game loop
                        mageQ.beginQuest(Player, enemy);

                        //will continue rounds until one is defeated. Each round has a prep (inventory use)
                        //and an attack/defense option to both sides
                        int round = 0;
                        //while loop checks after every round if a win/loose condition is met
                        while (enemy.getHealthPoints() > 0 && Player.getHealthPoints() >0){
                            System.out.println("--Round " + round + "--\n");

                            //gives the user some information, so they know if they want to use an item
                            System.out.println("--Battle Prep--");
                            if (Player instanceof Mage){
                                System.out.println("Player Stats- " +((Mage)Player).toString());
                            } else {
                                System.out.println("Player Stats- " +((Warrior)Player).toString() + "\n");
                            }
                            //inquires if user wants to use inventory item and shows inventory
                            System.out.println("Do you want to use an item from your inventory? (y/n) Here is what you have:");
                            System.out.println("Slot 1: " + Player.getInventory(1) + "\nSlot 2: " +  Player.getInventory(2) + "\nSlot 3: " + Player.getInventory(3) + "\nSlot 4: "
                                     + Player.getInventory(4) + "\nSlot 5: " + Player.getInventory(5));
                            round++;
                            String inventory = input.next();
                            if(inventory.equals("yes") || inventory.equals("y")){
                                //if user wants to use item, asks what slot to use. If the slot is NULL ignore to avoid error
                                //if >5, character class will automatically catch the error, using the last index
                                System.out.println("What inventory slot (1-5)");
                                int inventoryIndex = input.nextInt();
                                if(Player.getInventory(inventoryIndex) == null){
                                    System.out.println("Invalid choice, you missed your chance. Time to battle!");
                                } else {
                                    Player.useItem(inventoryIndex).use(Player);
                                }
                            }
                            //enter battle section of the quest, give stats to update the player before the battle
                            System.out.println("--Enter Battle Mode--\n");
                            System.out.println("Enemy Stats- " + enemy.toString());
                            if (Player instanceof Mage){
                                System.out.println("Player Stats- " +((Mage)Player).toString() + "\n");
                            } else {
                                System.out.println("Player Stats- " +((Warrior)Player).toString() + "\n");
                            }

                            //checks if player is mage or warrior, and gives 2 path depending on the character choice
                            if (Player instanceof Mage){
                                //Mage character. allows the user to replenish mana instead of attack. If attack is chosen, asks the user
                                //if they wish to change the spell, if so, takes in new spell. Then the spell is used on the target
                                System.out.println("Attack or Replenish mana. Current level: " + ((Mage)Player).getMana());
                                String aOrD = input.next();
                                if (aOrD.equals("Attack") || aOrD.equals("attack")){
                                    System.out.println("Do you want to change your spell from " + ((Mage)Player).getSpellType()+ " to: 'Self-heal', 'Damage', or 'Explosion'");
                                    String change = input.next();
                                    if (change.equals("yes") || change.equals("y")){
                                        System.out.println("Pick a spell");
                                        String selection = input.next();
                                        ((Mage)Player).changeSpellType(selection);
                                        if (selection.equals("Self-heal")){
                                            ((Mage)Player).castSpell(Player);
                                            System.out.println("You have self-healed");
                                        } else {
                                            ((Mage)Player).castSpell(enemy);
                                            System.out.println("You have attacked!");
                                        }
                                    } else {
                                        if (((Mage)Player).getSpellType().equals("Self-heal")){
                                            ((Mage)Player).castSpell(Player);
                                            System.out.println("You have self-healed");
                                        } else {
                                            ((Mage)Player).castSpell(enemy);
                                            System.out.println("You have attacked!");
                                        }
                                    }
                                } else {
                                    ((Mage)Player).regenerateMana();
                                    System.out.println("Mana Regenerated for the next attack");
                                }
                            } else {
                                //Warrior character. allows the user to defend instead of attack, blocking some damage. If attack is chosen, asks the user
                                //if they wish to change the weapon, if so, takes in the new weapon. Then the weapon is used on the target
                                System.out.println("Attack or Defend?");
                                String battleWarriorInput = input.next();
                                if (battleWarriorInput.equals("Attack") || battleWarriorInput.equals("attack")){
                                    System.out.println("Do you want to change your weapon from " + ((Warrior)Player).getWeaponType() + " to: 'Axe', 'Sword', or 'Spear'");
                                    String warWeaponSelection = input.next();
                                    if (warWeaponSelection.equals("yes") || warWeaponSelection.equals("y")){
                                        System.out.println("Pick a weapon");
                                        warWeaponSelection = input.next();
                                        ((Warrior)Player).changeWeaponType(warWeaponSelection);
                                        ((Warrior)Player).attack(enemy);
                                        System.out.println("You have attacked!");
                                    } else {
                                        ((Warrior)Player).attack(enemy);
                                        System.out.println("You have attacked!");
                                    }
                                } else {
                                    ((Warrior)Player).defend();
                                    System.out.println("You have activated defense for the next attack");
                                }
                            }
                            //enemy mage turn now
                            //checks if mage mana is zero, if so the mage must regenerate mana
                            if(enemy.getMana() < 0){
                                ((Mage)enemy).regenerateMana();
                                System.out.println("Enemy replenished mana");
                            } else {
                                //if the enemy has the mana for a spell, a spell is randomly picked using the Math.random and used
                                //on the target
                                int enemyAttackChances = (int)(Math.random()*3);
                                switch(enemyAttackChances){
                                    case 0:
                                        ((Mage)enemy).changeSpellType("Damage");
                                        ((Mage)enemy).castSpell(Player);
                                        System.out.println("The crazy mage has attacked player with " + ((Mage)enemy).getSpellType());
                                        break;
                                    case 1:
                                        ((Mage)enemy).changeSpellType("Explosion");
                                        ((Mage)enemy).castSpell(Player);
                                        System.out.println("The crazy mage has attacked player with " + ((Mage)enemy).getSpellType());
                                        break;
                                    case 2:
                                        ((Mage)enemy).changeSpellType("Self-heal");
                                        ((Mage)enemy).castSpell(enemy);
                                        System.out.println("The crazy mage has healed with " + ((Mage)enemy).getSpellType());
                                        break;
                                }
                            }
                            //keeps checking if player won. If player did win plays the quest rewards method before the loop terminates
                            mageQ.completeQuest(enemy, Player);
                        }
                    } else {
                        System.out.println("Well that is not very ambitious of you. How can you call yourself an adventurer");
                    }
                    break;
                //action event 3
                case 3:
                    //ask if the player wants to take action. If so, the user either is prompted with a small reward
                    //event or a small punishment event based on the math random function
                    System.out.println("You found a hole do you jump into it? y/n");
                    String inputQR = input.next();
                    if(inputQR.equals("yes") || inputQR.equals("y")){
                        int chances = (int)(Math.random() * 2);
                        if (chances == 0) {
                            System.out.println("Spooky ghost down here. Booooo!!! You took 15 damage :(");
                            Player.takeDamage(15);
                        } else {
                            System.out.println("You found a magic pill. You are compelled to eat it!");
                            Player.setHealth(Player.getHealthPoints()+5);
                            System.out.println("You gained 5 temporary HP!");
                        }
                    } else{
                        System.out.println("Well that is not very ambitious of you. How can you call yourself an adventurer");
                    }
                    break;
                //default statement, is triggered on purpose 1/4 times to add variety and catch any errors.
                default:
                    System.out.println("Hmmmm no quests on the board today...who beat all the bad guys!\n");
                    break;
            }
            //based on the subclass, checks if the user has died during the cycle to play the death-scene and end the gameplay loop
            if(Player instanceof Mage){
                if(((Mage)Player).getHealthPoints() <= 0){
                    deathScene(Player);
                    end = true;
                }
            } else {
                if(((Warrior)Player).getHealthPoints() <= 0) {
                    deathScene(Player);
                    end = true;
                }
            }
            //based on the subclass, checks if the user achieved the win stipulations to play the win scene and end the gameplay loop
            if(Player instanceof Mage){
                if(((Mage)Player).getLevel() >= 3){
                    winScene(Player);
                    end = true;
                }
            } else {
                if(((Warrior)Player).getLevel() >= 3){
                    winScene(Player);
                    end = true;
                }
            }
        }

    }

    //death scene, displays stats and trolls the player
    public static void deathScene(Character Player){
        Scanner input = new Scanner(System.in);

        System.out.println("------------------DEATH----------------");
        System.out.println("\nUh Oh, you died! That is very sad. Thank for playing RPG Command Line Simulator!");
        System.out.println("Here are some Stats:");
        System.out.println("Your level was: " + Player.getLevel());
        if (Player instanceof Mage){
            System.out.println("Your Mana was: " + ((Mage)Player).getMana());
        } else {
            System.out.println("Your Strength was: " + ((Warrior)Player).getStrength());
        }

        boolean troll = true;
        while (troll){
            System.out.println("Do you want to revive?");
            String in = input.next();
            if(Objects.equals(in, "no")){
                troll = false;
            } else {
                System.out.println("Too bad, you got to get better! Type no to move on. :)");
            }
        }
    }

    //win scene, displays stats and congratulates the user
    public static void winScene(Character Player){
        System.out.println("---------------WIN------------");
        System.out.println("Congratulations for completing the game! I hope you enjoyed!!");
        System.out.println("Your level was: " + Player.getLevel());
        if (Player instanceof Mage){
            System.out.println("Your Mana was: " + ((Mage)Player).getMana());
        } else {
            System.out.println("Your Strength was: " + ((Warrior)Player).getStrength());
        }
    }

    //method used to randomize item-drop of the events in the bag event and the wizard quest reward
    public static Item randomize(){
        int dropChances = (int)(Math.random()*3);
        Item bagDrop = null;
        switch(dropChances){
            case 0:
                bagDrop = new Item("Health", "Health Pack", 2, "A jug from a bag, why would you drink this?");
                break;
            case 1:
                bagDrop = new Item("Armour", "Armour Pack", 2, "Some stinky armour.");
                break;
            case 2:
                bagDrop = new Item("XP Potion", "XP Potion", 2, "It has a chilling glow to it.");
                break;
        }
        return bagDrop;
    }
}
