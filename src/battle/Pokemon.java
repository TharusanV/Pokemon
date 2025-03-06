package battle;
import java.awt.image.BufferedImage;
import java.util.*;

public class Pokemon {

    private String name;
    private int level;
    private int exp;
    
    private String type1;
    private String type2;
    
    private int health;
    private int attackPower;
    private int defensePower;
    private int specialAttackPower;
    private int specialDefensePower;
    private int speed;
    
    private int maxHealth = 0;
    
    private List<Move> moves;
    
    private ArrayList<Integer> allBaseStats;
    
    private BufferedImage frontImage;
    private BufferedImage backImage;
    
	public Pokemon(String p_name, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower, int p_speed, String p_type1, BufferedImage frontImage, BufferedImage backImage) {
    	this.name = p_name;
    	this.level = 1;
    	this.exp = 0;
    	this.type1 = p_type1;
    	this.type2 = null;
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.moves = new ArrayList<>();
        this.speed = p_speed;
        
        this.allBaseStats = new ArrayList<Integer>();
        allBaseStats.add(p_health);
        allBaseStats.add(p_attackPower);
        allBaseStats.add(p_defensePower);
        allBaseStats.add(p_specialAttackPower);
        allBaseStats.add(p_specialDefensePower);
        allBaseStats.add(p_speed);
        
        this.frontImage = frontImage;
        this.backImage = backImage;
    }

	public Pokemon(String p_name, int p_health, int p_attackPower, int p_defensePower, int p_specialAttackPower, int p_specialDefensePower, int p_speed, String p_type1, String p_type2, BufferedImage frontImage, BufferedImage backImage) {
    	this.name = p_name;
    	this.level = 1;
    	this.exp = 0;
    	this.type1 = p_type1;
    	this.type2 = p_type2;
    	this.health = p_health;
    	this.attackPower = p_attackPower;
    	this.defensePower = p_defensePower;
    	this.specialAttackPower = p_specialAttackPower;
    	this.specialDefensePower = p_specialDefensePower;
        this.moves = new ArrayList<>();
        this.speed = p_speed;
        
        this.allBaseStats = new ArrayList<Integer>();
        allBaseStats.add(p_health);
        allBaseStats.add(p_attackPower);
        allBaseStats.add(p_defensePower);
        allBaseStats.add(p_specialAttackPower);
        allBaseStats.add(p_specialDefensePower);
        allBaseStats.add(p_speed);
        
        this.frontImage = frontImage;
        this.backImage = backImage;
    }
	
	public Pokemon(Pokemon other) {
		this.name = other.name;
    	this.level = other.level;
    	this.exp = other.exp;
    	this.type1 = other.type1;
    	this.type2 = other.type2;
    	this.health = other.health;
    	this.attackPower = other.attackPower;
    	this.defensePower = other.defensePower;
    	this.specialAttackPower = other.specialAttackPower;
    	this.specialDefensePower = other.specialDefensePower;
        this.moves = other.moves;
        this.speed = other.speed;
        
        this.maxHealth = other.maxHealth;
        this.allBaseStats = other.allBaseStats;
        
        this.frontImage = other.frontImage;
        this.backImage = other.backImage;
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
		
		double tempHP = ((2*allBaseStats.get(0) * level) / 100) + level + 10;
		int newHp = (int) Math.floor(tempHP);
		setHealth(newHp);
		setMaxHealth(newHp);
		
		double tempAtt = ((2*allBaseStats.get(1) * level) / 100) + 5;
		int newAtt = (int) Math.floor(tempAtt);
		setAttackPower(newAtt);
		
		double tempDef = ((2*allBaseStats.get(2) * level) / 100) + 5;
		int newDef = (int) Math.floor(tempDef);
		setDefensePower(newDef);
		
		double tempAttS = ((2*allBaseStats.get(3) * level) / 100) + 5;
		int newAttS = (int) Math.floor(tempAttS);
		setSpecialAttackPower(newAttS);
		
		double tempDefS = ((2*allBaseStats.get(4) * level) / 100) + 5;
		int newDefS = (int) Math.floor(tempDefS);
		setSpecialDefensePower(newDefS);
		
		double tempSpeed = ((2*allBaseStats.get(5) * level) / 100) + 5;
		int newSpeed = (int) Math.floor(tempSpeed);
		setSpeed(newSpeed);
	}
	
	//Exp
	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}

	//Types
	public String getType1() {
		return type1;
	}

	public void setType1(String pokemonType1) {
		this.type1 = pokemonType1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String pokemonType2) {
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
    
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int health) {
		this.maxHealth = health;
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
	public List<Move> getAttacks() {
		return moves;
	}
	
    public void setAttacks(ArrayList<Move> moves) { 
    	this.moves = moves; 
    }


	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	
	//health percentage
	public void setHealthFromPercentage(int percentage) {
		this.health = (int) (this.health * (percentage / 100.0));
	}

	
	//Images 
	public BufferedImage getFrontImage() {
		return frontImage;
	}

	public void setFrontImage(BufferedImage frontImage) {
		this.frontImage = frontImage;
	}

	public BufferedImage getBackImage() {
		return backImage;
	}

	public void setBackImage(BufferedImage backImage) {
		this.backImage = backImage;
	}
	
	public boolean canDoAnyMoves() {
		for(int i = 0; i<4; i++) {
			Move move = moves.get(i);
			
			if(move.getCurrentPP() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkCertainMove(int index) {
		if(moves.get(index).getCurrentPP() > 0) {
			return true;
		}
		
		return false;
	}
    
}
