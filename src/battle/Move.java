package battle;

import java.util.Random;



public class Move {
    private String name;
    private String typeOfMove;
    private String category;
    private int power;
    private int accuracy;
    private int ppMax;
    private String description;
    private int currentPP;
    
    public Move(String p_name, String p_typeOfMove, String p_category, int p_power, int p_acc, int p_pp, String p_description) {
        this.name = p_name;
        this.typeOfMove = p_typeOfMove;
        this.category = p_category;
        this.power = p_power;
        this.accuracy = p_acc;
        this.ppMax = p_pp;
        this.description = p_description;
        this.currentPP = p_pp;
    }
    
    public Move(Move other) {
        this.name = other.name;
        this.typeOfMove = other.typeOfMove;
        this.category = other.category;
        this.power = other.power;
        this.accuracy = other.accuracy;
        this.ppMax = other.ppMax;
        this.description = other.description;
        this.currentPP = other.currentPP;
	}

	public String getName() { 
    	return name; 
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
	public String getTypeOfMove() {
		return typeOfMove;
	}

	public void setTypeOfMove(String typeOfMove) {
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

	public int getPPMax() {
		return ppMax;
	}

	public void setPPMax(int ppMax) {
		this.ppMax = ppMax;
	}

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	public int getCurrentPP() {
		return currentPP;
	}

	public void setCurrentPP(int currentPP) {
		this.currentPP = currentPP;
	}

	public int calculateDamage(Pokemon attacker, Pokemon reciever) {
    	
    	int userAttack; 
    	int recieverDefense;
    	
    	if(this.category == "Physical") {
    		userAttack = attacker.getAttackPower();
    		recieverDefense = reciever.getDefensePower();
    	}
    	else {
    		userAttack = attacker.getSpecialAttackPower();
    		recieverDefense = reciever.getSpecialDefensePower();    		
    	}
    	
    	//Damage formula
    	double damage_base = Math.floor((2*attacker.getLevel()) / 5) + 2; 
    	damage_base *= (this.power * userAttack) / recieverDefense; 
    	damage_base /= 50; 
    	damage_base = Math.floor(damage_base) + 2;
    	
    	//If attacker is the same type as the move it does then it gets a bonus
    	Type enumTypeAttackerT1 = Type.valueOf(attacker.getType1().toUpperCase());
    	Type enumTypeRecieverT1 = Type.valueOf(reciever.getType1().toUpperCase());
    	Type enumTypeMove = Type.valueOf(this.getTypeOfMove().toUpperCase());
    	
    	if(enumTypeAttackerT1.ordinal() == enumTypeMove.ordinal()) {
    		damage_base *= 1.5; 
    	}
    	
    	double effectiveness1 = Effectiveness.getEffectiveness(enumTypeMove, enumTypeRecieverT1);
    	double effectiveness2 = 1.00;
    	if(!reciever.getType2().equals("Null")) {
    		Type enumTypeRecieverT2 = Type.valueOf(reciever.getType2().toUpperCase()); 
    		effectiveness2 = Effectiveness.getEffectiveness(enumTypeMove, enumTypeRecieverT2);
    	}
    	
    	int damage = (int) (damage_base * effectiveness1 * effectiveness2);
    	
    	Random random = new Random();
    	int range = random.nextInt((255 - 217) + 1) + 217;
    	damage = (int) Math.floor((damage * range) / 255); 
    	
    	return damage; 
    }
	
	
}
