package utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import battle.Move;
import battle.Pokemon;
import battle.Type;

public class LoadAllFilesTool {

	public LoadAllFilesTool() {};
	
	String movesFile = "/csvFiles/allMoves.csv";
	String baseStateFile = "/csvFiles/pokeBaseStats.csv";
	
	
	public ArrayList<Move> loadMoves() {
		ArrayList<Move> moveList = new ArrayList<Move>();
		
	    try {
	        InputStream is = getClass().getResourceAsStream(movesFile);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        br.readLine(); // Read and discard the first row
    
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(","); 
	            
	            /*
	            == checks if the two string references point to the same object.
				.equals() checks if the actual content of the strings is the same. 
	            */
	            
	            String name = columns[0].trim(); 
	            String moveType = columns[1].trim();
	            String category = columns[2].trim();
	            
	            int power;
	            if(columns[3].trim().equals("Null") || columns[3].trim().equals("Infinite")) {
	                power = 0;
	            }
	            else {
	            	power = Integer.parseInt(columns[3].trim());
	            }
	            
	            int acc;
	            if(columns[4].trim().equals("Null") || columns[4].trim().equals("Infinite")) {
	                acc = 0;
	            }
	            else {
	            	acc = Integer.parseInt(columns[4].trim());
	            }
	          
	            
	            int pp;
	            if(columns[5].trim().equals("Null") || columns[5].trim().equals("Infinite")) {
	                pp = 0;
	            }
	            else {
	            	pp = Integer.parseInt(columns[5].trim());
	            }
	            
	            String description = columns[6].trim();
	            
	            Move move = new Move(name, moveType, category, power, acc, pp, description);
	            moveList.add(move);

	        }
	       
	        br.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return moveList;
	}
	
	public ArrayList<Pokemon> loadPokemons() {
		ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
		
	    try {
	        InputStream is = getClass().getResourceAsStream(baseStateFile);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        br.readLine(); // Read and discard the first row
    
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(","); 
	            
	            
	            String name = columns[1].trim(); 
	            int hp = Integer.parseInt(columns[2].trim());
	            int att = Integer.parseInt(columns[3].trim());
	            int def = Integer.parseInt(columns[4].trim());
	            int spa = Integer.parseInt(columns[5].trim());
	            int spd = Integer.parseInt(columns[6].trim());
	            int speed = Integer.parseInt(columns[7].trim());
	            String type1 = columns[8].trim(); 
	            String type2 = columns[9].trim(); 
	            
	            if(type2.equals("Null")) {
	            	Pokemon poke = new Pokemon(name, hp, att, def, spa, spd, speed, type1);
		            pokemonList.add(poke);
	            }
	            else {
	            	Pokemon poke = new Pokemon(name, hp, att, def, spa, spd, speed, type1, type2);
		            pokemonList.add(poke);	
	            }
	            

	        }
	       
	        br.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return pokemonList;
	}
	
	public ArrayList<Pokemon> loadTrainersTeam(ArrayList<Pokemon> allPoke, ArrayList<Move> allMoves, String trainerFile) {
		ArrayList<Pokemon> teamList = new ArrayList<Pokemon>();
		
	    try {
	        InputStream is = getClass().getResourceAsStream(trainerFile);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        br.readLine(); // Read and discard the first row
    
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(","); 
	            
	            //System.out.println(line);
	            
	            
	            int id = Integer.parseInt(columns[0].trim()) - 1;
	            Pokemon poke = new Pokemon(allPoke.get(id));
	            
	            int level = Integer.parseInt(columns[2].trim());
	            poke.setLevel(level);
	            
	            int healthPercentage = Integer.parseInt(columns[1].trim());
	            poke.setHealthFromPercentage(healthPercentage);
	            
	            int currentEXP = Integer.parseInt(columns[3].trim());
	            poke.setExp(currentEXP);
	            
	            ArrayList<Move> pokesMoves = new ArrayList<Move>();
	            int m1 = Integer.parseInt(columns[4].trim()) - 1;
	            int m2 = Integer.parseInt(columns[5].trim()) - 1;
	            int m3 = Integer.parseInt(columns[6].trim()) - 1;
	            int m4 = Integer.parseInt(columns[7].trim()) - 1;
	            pokesMoves.add(new Move(allMoves.get(m1)));
	            pokesMoves.add(new Move(allMoves.get(m2)));
	            pokesMoves.add(new Move(allMoves.get(m3)));
	            pokesMoves.add(new Move(allMoves.get(m4)));
	            poke.setAttacks(pokesMoves);
	            
	            teamList.add(poke);
	        }
	       
	        br.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return teamList;	
	}
	
}
