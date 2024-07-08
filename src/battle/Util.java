package battle;

import java.util.ArrayList;
import java.util.Random;

public class Util {
	static Random random = new Random();
	
	public static Type obtainType(String type) {
		if(type == "NORMAL") {
			return Type.NORMAL;
		}
		else if(type == "FIRE") {
			return Type.FIRE;
		}
		else if(type == "WATER") {
			return Type.WATER;
		}
		else if(type == "ELECTRIC") {
			return Type.ELECTRIC;
		}
		else if(type == "GRASS") {
			return Type.GRASS;
		}
		else if(type == "ICE") {
			return Type.ICE;
		}
		else if(type == "FIGHTING") {
			return Type.FIGHTING;
		}
		else if(type == "POISON") {
			return Type.POISON;
		}
		else if(type == "GROUND") {
			return Type.GROUND;
		}
		else if(type == "FLYING") {
			return Type.FLYING;
		}
		else if(type == "PSYCHIC") {
			return Type.PSYCHIC;
		}
		else if(type == "BUG") {
			return Type.BUG;
		}
		else if(type == "ROCK") {
			return Type.ROCK;
		}
		else if(type == "GHOST") {
			return Type.GHOST;
		}
		else if(type == "DRAGON") {
			return Type.DRAGON;
		}
		else if(type == "DARK") {
			return Type.DARK;
		}
		else if(type == "STEEL") {
			return Type.STEEL;
		}
		else if(type == "FAIRY") {
			return Type.FAIRY;
		}
		
		return Type.NORMAL;
	}
	
	
	public static void generateTeam(ArrayList<Pokemon> team, ArrayList<String[]> pokemonList, ArrayList<String[]> movesList) {
		for(int i = 0; i < 6; i++) {
			int randomIndex = random.nextInt(pokemonList.size());
			String name = pokemonList.get(randomIndex)[0].trim();
			String type = pokemonList.get(randomIndex)[1].trim();
			int health = Integer.parseInt(pokemonList.get(randomIndex)[2].trim());
			int att = Integer.parseInt(pokemonList.get(randomIndex)[3].trim());
			int def = Integer.parseInt(pokemonList.get(randomIndex)[4].trim());
			int specialAtt = Integer.parseInt(pokemonList.get(randomIndex)[5].trim());
			int specialDef = Integer.parseInt(pokemonList.get(randomIndex)[6].trim());
			
			Pokemon poke;
			
			if(type.contains("/")){
				String[] parts = type.split("/");
				String type1 = parts[0];
				String type2 = parts[1];
				poke = new Pokemon(name, obtainType(type1), obtainType(type2), health, att, def, specialAtt, def);
			}
			else {
				poke = new Pokemon(name, obtainType(type), health, att, def, specialAtt, def);
			}
			
			poke.setAttacks(generatePokeMoves(poke, movesList));
			team.add(poke);
		}
	}
	
	public static ArrayList<Attack> generatePokeMoves(Pokemon poke, ArrayList<String[]> movesList) {
		ArrayList<Attack> moves = new ArrayList<>();
		
		for(int j = 0; j < 4; j++) {
			int randomIndex = random.nextInt(movesList.size());
			
			String name = movesList.get(randomIndex)[0].trim();
			Type type = obtainType(movesList.get(randomIndex)[1].trim());
			String category = movesList.get(randomIndex)[2].trim();
			int power = Integer.parseInt(movesList.get(randomIndex)[3].trim());
			int acc = Integer.parseInt(movesList.get(randomIndex)[4].trim());
			int pp = Integer.parseInt(movesList.get(randomIndex)[5].trim()); 
			String description = movesList.get(randomIndex)[6].trim();
			
			Attack example = new Attack(name, type, category, power, acc, pp, description, poke);
			moves.add(example);
		}
		
		return moves;
	}
	
	
}
