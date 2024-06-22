package Chapter11.videogame;
//author: Jonah Dalton
//Date: 2/16/2024
public class Item {
    //initialize Item variables
    private String itemName;
    private String magicProperty;
    private int powerLevel;
    private String description;

    //constructor
    public Item(String itemName, String magicProperty, int powerLevel, String description) {
        this.itemName = itemName;
        this.magicProperty = magicProperty;
        this.powerLevel = powerLevel;
        this.description = description;
    }

    //use method  depends on the magicProperty of the item. The magicProperty is set upon construction. This method
    //accesses and change characters stats based on the item used
    public void use(Character character){
        if (magicProperty.equals("Health Pack")){
            if(character.getHealthPoints() < character.getMaxHealth()){
                character.setHealth(character.getMaxHealth());
            } else {
                character.addToHealth(10 * powerLevel);
            }
        }else if(magicProperty.equals("Armour Pack")){
            if(powerLevel > 10){
                character.addToDefense(2);
            } else {
                character.addToDefense(1);
            }
        }else if(magicProperty.equals("XP Potion")){
            if(character instanceof Mage){
                ((Mage)character).levelUp();
            } else {
                ((Warrior)character).levelUp();
            }
        } else {
            System.out.println("Error, invalid item");
        }
    }

    //accessor methods
    public String inspect(){
        return itemName + "'s description reads: " + description + ". \nIts Power Level is: " + powerLevel + ", and its Property is " + magicProperty;
    }
    public String getItemName() {return itemName;}
    public String getMagicProperty() {return magicProperty;}
    public int getPowerLevel() {return powerLevel;}

    @Override
    public String toString(){
        return "Name: " + itemName + ", Magic Property: " + magicProperty + ", Power Level: " + powerLevel;
    }
}
