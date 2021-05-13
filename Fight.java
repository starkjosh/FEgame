package actions;

import java.util.Random;

import utilities.Battleground;
import utilities.Fighter;

public class Fight {
	
	private Random chance = new Random();
	
	public Battleground melee(Fighter attacker, Fighter defender, Battleground bg) {
		
		int attack = chance.nextInt(100);
		
		if(attacker.getAccuracy() > attack) {
			
			defender.takeHit(attacker.getStrength());
			
		}
		
		bg = bg.updateFighter(defender, bg);
		
		return bg;
		
	}

}
