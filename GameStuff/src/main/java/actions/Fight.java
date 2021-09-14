package actions;

import java.util.Random;

import blocks.Battleground;
import blocks.Fighter;

public class Fight {

	private Random chance = new Random();

	public Battleground melee(Fighter attacker, Fighter defender, Battleground bg) {
		double attackChance = chance.nextDouble() * 100;
		if(attacker.getAccuracy() > attackChance) {
			defender.takeHit(attacker.getStrength());

		} else{
			System.out.println("You missed.");
		}

		bg = bg.updateFighter(defender, bg);
		return bg;

	}

//	public Battleground magic(Fighter attacker, Fighter defender, Battleground bg) {
//		double attackChance = chance.nextDouble() * 100;
//
//		bg = bg.updateFighter(defender, bg);
//		return bg;
//	}

	public Battleground range(Fighter attacker, Fighter defender, Battleground bg) {
		double attackChance = chance.nextDouble() * 100;

		bg = bg.updateFighter(defender, bg);
		return bg;
	}

}
