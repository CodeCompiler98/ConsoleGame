package Chapter11.videogame;
//author: Jonah Dalton
//Date: 2/16/24
public class Mage extends Character{

    //initialize unique warrior variables
    private int mana;
    private int maxMana;
    private String spellType;

    //constructor
    Mage(String name, int level, int healthPoints, int mana, String spellType) {
        super(name, level, healthPoints);
        this.mana = mana;
        this.maxMana = mana;
        this.spellType = spellType;
    }

    //action methods

    //castSpell method deals damage to the other target using mana and the level, and heals based on player health stats
    //Each spell has drawbacks and strengths. Pick the weapon based on the spellType String. Will check for mana, if none, spell will fail
    //default attack to catch invalid input
    public void castSpell (Character target){
        if (spellType.equals("Self-heal")){
            //check mana and use spell
            if(mana > 0) {
                this.healthPoints += (int) (maxHealth * 0.10);
                this.mana -= (int) (maxMana * 0.25);
                if (mana < 0) {
                    mana = 0;
                }
            } else {
                System.out.println(this.name + " has insufficient mana to cast. Failed to cast.");
            }
        }else if (spellType.equals("Damage")){
            //check mana and use spell
            if(mana > 0) {
                target.takeDamage((2 * level) + mana);
                this.mana -= (int) (maxMana * 0.25);
                if (mana < 0) {
                    mana = 0;
                }
            } else {
                System.out.println(this.name + " has insufficient mana to cast. Failed to cast.");
            }
        }else if (spellType.equals("Explosion")){
            //check mana and use spell
            if(mana > 0) {
                target.setDefense(0);
                target.takeDamage((4 * level) + mana);
                this.healthPoints -= (int)(healthPoints * 0.40);
                this.mana -= (int) (maxMana * 0.25);
                if (mana < 0) {
                    mana = 0;
                }
            } else {
                System.out.println(this.name + " has insufficient mana to cast. Failed to cast.");
            }
        } else {
            if(mana > 0) {
                //check mana and use spell
                //default attack
                System.out.println("Invalid spell, using banana peel");
                target.takeDamage(mana);
                this.mana -= (int) (maxMana * 0.25);
                if (mana < 0) {
                    mana = 0;
                }
            } else {
                System.out.println(this.name + " has insufficient mana to cast. Failed to cast.");
            }
        }
    }
    @Override
    public void levelUp(){
        super.levelUp();
        maxMana += 10;
        mana = maxMana;
    }
    //replenish mana for spells, counts as a turn
    public void regenerateMana(){mana = maxMana;}

    //accessor methods
    public int getMana() {return mana;}
    public String getSpellType() {return spellType;}

    //mutator methods, used to change spell type used in castSpell function
    public void changeSpellType(String spellType) {
        this.spellType = spellType;
    }

    @Override
    public String toString(){
        return super.toString() + ", Mana: " + mana + "/" + maxMana + ", SpellType: " + spellType;
    }
}
