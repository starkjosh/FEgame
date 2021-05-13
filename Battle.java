package utilities;

import java.util.Scanner;
import java.util.Stack;

import actions.Fight;
import actions.Move;
import enums.Difficulty;
import enums.Team;

public class Battle {

	
	private boolean isWon = false;
	private Difficulty difficulty;
	private int numOfHeroes;
	private int numOfVillains;
	private Battleground bg;
	private Stack<Fighter> turn = new Stack<Fighter>();
	private Stack<Fighter> backup = new Stack<Fighter>();
	private Move move = new Move();
	private Fight fight = new Fight();
	
	
	public void buildGame(String diff) {
		setDifficulty(diff);
		if(this.difficulty == Difficulty.valueOf("EASY")) {
			
			numOfHeroes = 3;
			numOfVillains = 3;
			
			bg = new Battleground(5, 5);
			
			Fighter h1 = new Fighter(30, 5, "GOOD", "Roland");
			Fighter h2 = new Fighter(30, 5, "GOOD", "Noctis");
			Fighter h3 = new Fighter(30, 5, "GOOD", "Vin");
			Fighter v1 = new Fighter(20, 4, "BAD", "Ardyn", 70);
			Fighter v2 = new Fighter(20, 4, "BAD", "Zaheer", 70);
			Fighter v3 = new Fighter(20, 4, "BAD", "Ashnard", 70);
			
			bg.placeFighter(h1, 2, 4);
			bg.placeFighter(h2, 3, 5);
			bg.placeFighter(h3, 4, 4);
			bg.placeFighter(v1, 2, 1);
			bg.placeFighter(v2, 3, 1);
			bg.placeFighter(v3, 4, 1);
			
			turn.push(v3);
			turn.push(v2);
			turn.push(v1);
			turn.push(h3);
			turn.push(h2);
			turn.push(h1);
			
		}
		
		
	}
	
	public void nextTurn(Scanner input) {
		if(turn.empty()) {
			while(!backup.empty()) {
				turn.push(backup.pop());
			}
		}
		
//		printMap();
		Fighter f = turn.pop();
		backup.push(f);
		
		if(f.getTeam() == Team.GOOD) {
		
			System.out.println(f.getName() + " would you like to move? (y/n)");
			if(input.next().equalsIgnoreCase("y")) {
				System.out.println("What space would you like to move to?");
				while(true) {
					System.out.println("X coordinate? (1, 2, 3, 4, 5)");
					int x = input.nextInt();
					
					
					System.out.println("Y coordinate? (1, 2, 3, 4, 5)");
					int y = input.nextInt();
					
					Battleground placeholder = move.moveFighter(f, bg, x, y);
					if(placeholder != null) {
						bg = placeholder;
						break;
					} else {
						System.out.println("Please choose another space to move to.");
					}
				}
			}
			
		
		
			System.out.println("Would you like to attack? (y/n)");
			if(input.next().equalsIgnoreCase("y")) {
				
				Fighter[] enemies = bg.findAdjacentEnemies(f, bg);
				if(enemies == null) {
					System.out.println("There is no one you can fight.");
				} else {
					
					System.out.println("Who would you like to fight?");
					
					for(int i = 0; i < enemies.length - 1; i++) {
						if(enemies[i] != null) {
							System.out.println(i + ") '" + enemies[i].getName() + "' HP: " + enemies[i].getHitpoints() 
									/*+ " Accuracy: " + enemies[i].getAccuracy() + " Strength: " + enemies[i].getStrength()*/);
						}
					}
					
					int e = input.nextInt();
					
					bg = fight.melee(f, enemies[e], bg);
					
				}
				
			}
		
			checkIfWon();
			printMap();
		}
		
	}
	
	public boolean getIsWon() {
		
		return isWon;
		
	}
	
	public Difficulty getDifficulty() {
		
		return difficulty;
		
	}

	public int getNumOfHeroes() {
		
		return numOfHeroes;
		
	}

	public void setNumOfHeroes(int numOfHeroes) {
		
		this.numOfHeroes = numOfHeroes;
		
	}

	public int getNumOfVillains() {
		
		return numOfVillains;
		
	}

	public void setNumOfVillains(int numOfVillains) {
		
		this.numOfVillains = numOfVillains;
		
	}

	public void setWon(boolean isWon) {
		
		this.isWon = isWon;
		
	}

	public void setDifficulty(String difficulty) {
		
		Difficulty d = Difficulty.valueOf(difficulty);
		
		this.difficulty = d;
		
	}
	
	public void checkIfWon() {
		numOfHeroes = 0;
		numOfVillains = 0;
		for(int i = 0; i < bg.getX_Max(); i++) {
			
			for(int j = 0; j < bg.getY_Max(); j++) {
				
				if(bg.dimensions[i][j] != null) {
					
					if(bg.dimensions[i][j].getTeam() == Team.GOOD) {
						numOfHeroes += 1;
					} else {
						numOfVillains += 1;
					}
					
				}
				
			}
		}
		
		if(numOfHeroes == 0) {
			System.out.println("You have been defeated.");
			isWon = true;
		} else if(numOfVillains == 0) {
			System.out.println("Congrats! You won! Come play again soon.");
			isWon = true;
		}
		
	}
	
	public void printMap() {
		
		for(int i = 0; i < bg.getX_Max() +1; i++) {
			System.out.println("");
			for(int j = 0; j < bg.getY_Max() + 1; j++) {
				if(bg.dimensions[j][i] != null) {
					System.out.print("| " + bg.dimensions[j][i].getName() + " |");
				} else {
					System.out.print("| \t |");
				}
				
			}
		}
		
		System.out.println("");
		System.out.println("");
	}
	
	
}
