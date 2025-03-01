package utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import battle.Move;
import battle.Pokemon;
import battle.Type;

public class LoadExcelFilesTool {

	public LoadExcelFilesTool() {};
	
	String movesFile = "/csvFiles/allMoves.csv";
	String baseStateFile = "/csvFiles/pokeBaseStats.csv";
	
	String playerTeamFile = "/csvFiles/playerTeam.csv";
	String rivalTeamFile = "/csvFiles/rivalTeam.csv";
	
	
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
	
	
	
}
