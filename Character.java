package Chapter11.videogame;
//author: Jonah Dalton
//Date: 2/16/24
public class Character {
    //initialize common variables for all classes

    protected String name;
    protected int level;
    //active health
    protected int healthPoints;
    //stored "max" health to use a reference for level up and healing
    protected int maxHealth;
    //value made to block damage from attacks, used by warrior and by a potion.
    protected int defense;
    //inventory for carrying items
    private final Item[] inventory;
    //constructor
    Character(String name, int level, int healthPoints){
        this.healthPoints = healthPoints;
        this.level = level;
        this.name = name;
        //set max health to inputted health-points as a base
        this.maxHealth = healthPoints;
        //set inventory to 5 and base defense to zero
        this.defense = 0;
        this.inventory = new Item[5];
    }

    //action methods for all characters

    //remove health, part of damage can be nullified by defense and defense is used up.
    //Taking health from the active health not the reference "maxHealth"
    public void takeDamage(int damage){
        if(defense > 0){
            this.healthPoints -= (int) (damage * 0.9);
            defense--;
        } else {
            this.healthPoints -= damage;
        }
    }
    //level up character, restoring stats and increasing maxHealth
    public void levelUp(){
        level++;
        maxHealth += 100;
        if(healthPoints<maxHealth) {
            healthPoints = maxHealth;
        }
    }
    //checking if inventory slot is valid if it is add it, otherwise display and error
    public void addToInventory(Item add, int slot){
        if(slot <= inventory.length) {
            inventory[slot - 1] = add;
        } else {
            System.out.println("Invalid Item Slot. Your Inventory is " + inventory.length + " slots long.");
        }
    }
    //check if index is valid, if not return the last index, if it is valid, return the index.
    //in both cases, set the index to NULL before returning to "use" the item
    public Item useItem(int index){
        Item temp;
        if (index > inventory.length){
            System.out.println("The inventory is only " + inventory.length + " slots long! Returning last index");
            temp = inventory[inventory.length - 1];
            inventory[inventory.length - 1] = null;
            return temp;
        } else{
            temp = inventory[index - 1];
            inventory[index - 1] = null;
            return temp;
        }
    }

    //accessor methods
    //return the item in the inventory index without using it
    public Item getInventory(int index){
        Item temp;
        if (index > inventory.length){
            System.out.println("The inventory is only " + inventory.length + " slots long! Returning last index");
            return inventory[inventory.length - 1];
        } else{
            return inventory[index - 1];
        }
    }

    public String getName() {return name;}
    public int getLevel() {return level;}
    public int getHealthPoints() {return healthPoints;}

    public int getMaxHealth(){return maxHealth;}

    //mutator methods
    public void setHealth(int in){this.healthPoints = in;}
    public void addToHealth(int in){this.healthPoints += in;}
    public void setDefense(int in){this.defense = in;}
    public void addToDefense(int in){this.defense += in;}


    //toString override method to print details
    @Override
    public String toString(){
        return "Name: '" + name + "', Level: " + level + ", HealthPoints: " + healthPoints + "/" + maxHealth + ", Sheild: " + defense;
    }
}
