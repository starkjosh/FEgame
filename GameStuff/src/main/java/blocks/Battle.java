package blocks;

import java.util.Scanner;

import actions.Fight;
import actions.Move;
import enums.Difficulty;
import enums.Team;
import utilities.CircularList;

public class Battle {

	private boolean isWon = false;
	private Difficulty difficulty;
	private int numOfHeroes;
	private int numOfVillains;
	private Battleground bg;
	private CircularList turn = new CircularList();
	private Move move = new Move();
	private Fight fight = new Fight();


	public void buildGame(String diff) {
		setDifficulty(diff);
		if (this.difficulty == Difficulty.valueOf("EASY")) {

			numOfHeroes = 3;
			numOfVillains = 3;

			bg = new Battleground(5, 5);

			Fighter h1 = new Fighter(30, 5, "GOOD", "Roland");
			Fighter h2 = new Fighter(30, 5, "GOOD", "Noctis");
			Fighter h3 = new Fighter(30, 5, "GOOD", "Batman");
			Fighter v1 = new Fighter(20, 4, "BAD", "Goomba", 70);
			Fighter v2 = new Fighter(20, 4, "BAD", "Penguin", 70);
			Fighter v3 = new Fighter(20, 4, "BAD", "Clyde", 70);

			bg.placeFighter(h1, 17);
			bg.placeFighter(h2, 23);
			bg.placeFighter(h3, 19);
			bg.placeFighter(v1, 2);
			bg.placeFighter(v2, 3);
			bg.placeFighter(v3, 4);

			turn.addNode(h1);
			turn.addNode(h2);
			turn.addNode(h3);
			turn.addNode(v1);
			turn.addNode(v2);
			turn.addNode(v3);

		}
	}

	public void nextTurn(Scanner input) {

		printMap();
		Fighter f = turn.getNextTurn();

		if (f.getTeam() == Team.GOOD) {

			System.out.println(f.getName() + " would you like to move? (y/n)");
			if (input.next().equalsIgnoreCase("y")) {							//switch statement
				while (true) {
					System.out.println("What space would you like to move to?");
					int location = input.nextInt();

					Battleground placeholder = move.moveFighter(f, bg, location);
					if (placeholder != null) {
						bg = placeholder;
						break;
					} else {
						System.out.println("Please choose another space to move to.");
						printMap();
					}
				}
			}

			printMap();

			System.out.println("Would you like to attack? (y/n)");
			if (input.next().equalsIgnoreCase("y")) {
				Fighter[] enemies = bg.findAdjacentEnemies(f, bg);
				if (enemies == null) {
					System.out.println("There is no one you can fight.");
				} else {
					System.out.println("Who would you like to fight?");

					for (int i = 0; i < enemies.length; i++) {
						if (enemies[i] != null) {
							System.out.println(i + ") '" + enemies[i].getName() + "' HP: " + enemies[i].getHp()
									/*+ " Accuracy: " + enemies[i].getAccuracy() + " Strength: " + enemies[i].getStrength()*/);
						}
					}
					int e = input.nextInt();
					bg = fight.melee(f, enemies[e], bg);
				}
			}
			checkIfWon();
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
		for (int i = 0; i < bg.getX_Max() * bg.getY_Max(); i++) {
			if (bg.dimensions[i].getFighterOnTile() != null) {
				if (bg.dimensions[i].getFighterOnTile().getTeam() == Team.GOOD) {
					numOfHeroes += 1;
				} else {
					numOfVillains += 1;
				}
			}

		}

		if (numOfHeroes == 0) {
			System.out.println("You have been defeated.");
			isWon = true;
		} else if (numOfVillains == 0) {
			System.out.println("Congrats! You won! Come play again soon.");
			isWon = true;
		}

	}

	public void printMap() {

		for (int i = 0; i < bg.getX_Max() * bg.getY_Max(); i++) {
			if(i % bg.getX_Max() == 0){
				System.out.println();
			}
			if (bg.dimensions[i].getFighterOnTile() != null) {
				System.out.print("| " + bg.dimensions[i].getFighterOnTile().getName() + "\t |");
			} else {
				System.out.print("| \t" + bg.dimensions[i].getLocation() + "\t |");
			}

		}

		System.out.println("");
		System.out.println("");
	}


}
