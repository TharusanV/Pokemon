package battle;
import java.util.*;

public class Pokemon {

    private String name;
    private Type pokemonType1;
    private Type pokemonType2;
    private int health;
    private int attackPower;
    private int defensePower;
    private int specialAttackPower;
    private int specialDefensePower;
    private List<Attack> attacks;
    
    
	public Pokemon(String p_name, Type p_type1, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower) {
    	this.name = p_name;
    	this.pokemonType1 = p_type1;
    	this.pokemonType2 = null;
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.attacks = new ArrayList<>();
    }

	public Pokemon(String p_name, Type p_type1, Type p_type2, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower) {
    	this.name = p_name;
    	this.setPokemonType1(p_type1);
    	this.setPokemonType2(p_type2);
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.attacks = new ArrayList<>();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
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
    
    
    
	public List<Attack> getAttacks() {
		return attacks;
	}
	
    public void setAttacks(ArrayList<Attack> moves) { 
    	this.attacks = moves; 
    }

	public Type getPokemonType1() {
		return pokemonType1;
	}

	public void setPokemonType1(Type pokemonType1) {
		this.pokemonType1 = pokemonType1;
	}

	public Type getPokemonType2() {
		return pokemonType2;
	}

	public void setPokemonType2(Type pokemonType2) {
		this.pokemonType2 = pokemonType2;
	}


    
}
