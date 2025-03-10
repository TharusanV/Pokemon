package jUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import battle.Pokemon;
import main.GamePanel;
import utility.KeyHandler;

class PokemonTests {

	private GamePanel gp = new GamePanel();
	
	@BeforeEach
    void setUp() { 
		gp.setUpGame();
    }
	
	 @Test
	 void testPokemonStats() {
		 
		Pokemon charizard = gp.getPlayer().getTeam().get(0);
		//0 IV Charizard stats at level 36 - 102HP 	65ATK	61DEF	83SP.ATK	66SP.DEF	77SPD
		 
		assertAll(
			() -> assertEquals(charizard.getHealth(), 102),
			() -> assertEquals(charizard.getAttackPower(), 65),
			() -> assertEquals(charizard.getDefensePower(), 61),
			() -> assertEquals(charizard.getSpecialAttackPower(), 83),
			() -> assertEquals(charizard.getSpecialDefensePower(), 66),
			() -> assertEquals(charizard.getSpeed(), 77)
		);   
	 }
	 
	 

}
