package battle;

import java.util.Random;

public class Attack {
    private String name;
    private Type typeOfMove;
    private String category;
    private int power;
    private int accuracy;
    private int powerPoint;
    private String description;
    private Pokemon pokemon;
    
    public Attack(String p_name, Type p_typeOfMove, String p_category, int p_power, int p_acc, int p_pp, String p_description, Pokemon p_pokemon) {
        this.name = p_name;
        this.typeOfMove = p_typeOfMove;
        this.category = p_category;
        this.power = p_power;
        this.accuracy = p_acc;
        this.powerPoint = p_pp;
        this.description = p_description;
        this.pokemon = p_pokemon;
    }
    
    public String getName() { 
    	return name; 
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
	public Type getTypeOfMove() {
		return typeOfMove;
	}

	public void setTypeOfMove(Type typeOfMove) {
		this.typeOfMove = typeOfMove;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getPowerPoint() {
		return powerPoint;
	}

	public void setPowerPoint(int powerPoint) {
		this.powerPoint = powerPoint;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
	
	
	
	
	public int calculateDamage(Pokemon enemy) {
    	int level = 100;
    	
    	int userAttack; 
    	int enemyDefense;
    	
    	if(this.category == "Physical") {
    		userAttack = this.pokemon.getAttackPower();
    		enemyDefense = enemy.getDefensePower();
    	}
    	else {
    		userAttack = this.pokemon.getSpecialAttackPower();
    		enemyDefense = enemy.getSpecialDefensePower();    		
    	}
    	
    	
    	double damage_base = Math.floor((2*level) / 5) + 2; 
    	damage_base *= this.power * userAttack/enemyDefense; 
    	damage_base /= 50; 
    	damage_base = Math.floor(damage_base) + 2;
    	
    	if(pokemon.getType1().ordinal() == this.typeOfMove.ordinal()) {
    		damage_base *= 1.5; 
    	}
    	
    	double effectiveness1 = Effectiveness.getEffectiveness(this.typeOfMove, enemy.getType1());
    	double effectiveness2 = 1.00;
    	
    	if(enemy.getType2() != null) {
    		effectiveness2 = Effectiveness.getEffectiveness(this.typeOfMove, enemy.getType2());
    	}
    	
    	int damage = (int) (damage_base * effectiveness1 * effectiveness2);
    	
    	Random random = new Random();
    	int range = random.nextInt((255 - 217) + 1) + 217;
    	damage = (int) Math.floor((damage * range) / 255); 
    	
    	return damage; 
    }
}
