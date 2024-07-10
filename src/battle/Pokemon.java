package battle;
import java.util.*;

public class Pokemon {

    private String name;
    private int level;
    private int exp;
    
    private Type type1;
    private Type type2;
    
    private int health;
    private int attackPower;
    private int defensePower;
    private int specialAttackPower;
    private int specialDefensePower;
    
    private List<Attack> attacks;
    
	public Pokemon(String p_name, int p_level, int p_exp, Type p_type1, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower) {
    	this.name = p_name;
    	this.level = p_level;
    	this.exp = p_exp;
    	this.type1 = p_type1;
    	this.type2 = null;
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.attacks = new ArrayList<>();
    }

	public Pokemon(String p_name, int p_level, int p_exp, Type p_type1, Type p_type2, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower) {
    	this.name = p_name;
    	this.level = p_level;
    	this.exp = p_exp;
    	this.type1 = p_type1;
    	this.type2 = p_type2;
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.attacks = new ArrayList<>();
    }

	//Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//Level
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	//Exp
	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}

	//Types
	public Type getType1() {
		return type1;
	}

	public void setType1(Type pokemonType1) {
		this.type1 = pokemonType1;
	}

	public Type getType2() {
		return type2;
	}

	public void setType2(Type pokemonType2) {
		this.type2 = pokemonType2;
	}
	
	//Health
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
        	this.health = 0;
        }
    }
    
    public boolean hasFainted() {
        return this.health <= 0;
    }
    
    //combat stats
    public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getDefensePower() {
		return defensePower;
	}

	public void setDefensePower(int defensePower) {
		this.defensePower = defensePower;
	}

	public int getSpecialAttackPower() {
		return specialAttackPower;
	}

	public void setSpecialAttackPower(int specialAttackPower) {
		this.specialAttackPower = specialAttackPower;
	}

	public int getSpecialDefensePower() {
		return specialDefensePower;
	}

	public void setSpecialDefensePower(int specialDefensePower) {
		this.specialDefensePower = specialDefensePower;
	}
    
    
    //attack
	public List<Attack> getAttacks() {
		return attacks;
	}
	
    public void setAttacks(ArrayList<Attack> moves) { 
    	this.attacks = moves; 
    }




    
}
