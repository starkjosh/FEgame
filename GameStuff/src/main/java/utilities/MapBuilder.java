package utilities;

import java.util.Random;

import blocks.Battleground;
import blocks.Fighter;

public class MapBuilder {

	private Random randomGenerator = new Random();
	private int heroCount = 0;
	private int villainCount = 0;

	public Battleground buildEasyGame(){
		int xDimension = randomGenerator.nextInt(5) + 6;
		int yDimension = randomGenerator.nextInt(5) + 6;
		int numOfHeroes, numOfVillains;
		if (xDimension * yDimension <= 60){
			numOfHeroes = 4;
			numOfVillains = 6;
		} else {
			numOfHeroes = 5;
			numOfVillains = 8;
		}

		Battleground map = new Battleground(xDimension, yDimension);

		Fighter toBePlaced = null;
		while(heroCount <= numOfHeroes){
			//because potentially the last character can be placed on a spot that is taken, I need this check to make sure I don't create an extra person
			if(toBePlaced == null && heroCount == numOfHeroes){
				break;
			}
			if(toBePlaced == null){
				toBePlaced = createDummyHero();
			}
			int xLoc = randomGenerator.nextInt(xDimension);
			int yLoc = randomGenerator.nextInt(3) + yDimension - 3;
			if(map.getDimensions()[yLoc][xLoc].isOpen()){
				map.placeFighterByCoordinates(toBePlaced, xLoc, yLoc);
				map.getGoodies().add(toBePlaced);
				toBePlaced = null;
			}
		}


		while(villainCount <= numOfVillains){
			//because potentially the last character can be placed on a spot that is taken, I need this check to make sure I don't create an extra person
			if(toBePlaced == null && villainCount == numOfVillains){
				break;
			}
			if(toBePlaced == null){
				toBePlaced = createDummyVillain();
			}
			int xLoc = randomGenerator.nextInt(xDimension);
			int yLoc = randomGenerator.nextInt(3);
			if(map.getDimensions()[yLoc][xLoc].isOpen()){
				map.placeFighterByCoordinates(toBePlaced, xLoc, yLoc);
				map.getBaddies().add(toBePlaced);
				toBePlaced = null;
			}
		}

		return map;
	}

	private Fighter createDummyHero(){
		return new Fighter(30, 5, "GOOD", "Hero " + ++heroCount);
	}

	private Fighter createDummyVillain(){
		return new Fighter(20, 4, "BAD", "Villain " + ++villainCount);
	}


}
