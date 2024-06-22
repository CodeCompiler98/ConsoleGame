package Chapter11.videogame;
//author: Jonah Dalton
//Date: 2/16/2024
public class Warrior extends Character{
    //initialize unique warrior variables
    private int strength;
    private String weaponType;
    //constructor
    Warrior(String name, int health, int level, int strength, String weaponType){
        super(name, level, health);
        this.strength = strength;
        this.weaponType = weaponType;
    }
    //action methods

    //attack method deals damage to the other target using strength and the level
    // Stronger weapons have a drawback of taking off a % of health. Pick the weapon based on the weaponType String
    public void attack(Character target){
        if (weaponType.equals("Axe")){
            target.takeDamage((4 * level) + strength);
            this.healthPoints -= (int) (healthPoints * 0.10);
        }else if (weaponType.equals("Sword")){
            target.takeDamage((3 * level) + strength);
            this.healthPoints -= (int) (healthPoints * 0.05);
        }else if (weaponType.equals("Spear")){
            target.takeDamage((2* level) + strength);
        } else {
            //*fist default weapon*
            System.out.println("Invalid weapon, using fist");
            target.takeDamage((level) + (int)(strength * 0.5));
        }
    }
    //warrior level up
    @Override
    public void levelUp(){
        super.levelUp();
        strength += 10;
    }

    //accessor methods
    public int getStrength() {return strength;}
    public String getWeaponType() {return weaponType;}

    //mutator methods
    //change the weapon type string, changing the weapon used in the next attack
    public void changeWeaponType(String weaponType){
        this.weaponType = weaponType;
    }
    //add 1 to defense, taking 1/2 of the next attack to the player
    //defenses can stack. Takes 1 off every attack if defense > 0
    public void defend(){
        this.defense++;
    }

    //to String override
    @Override
    public String toString(){
        return super.toString() + ", Strength: " + strength + ", Weapon: " + weaponType;
    }
}
