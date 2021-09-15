package blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import actions.Fight;
import actions.Move;
import enums.Difficulty;
import enums.Team;
import org.apache.commons.lang3.StringUtils;
import utilities.CircularList;
import utilities.Tile;

public class Battle {

	private boolean isWon = false;
	private Difficulty difficulty;
	private Battleground bg;
	private CircularList turn = new CircularList();
	private Move move = new Move();
	private Fight fight = new Fight();
	private List<Fighter> goodies = new ArrayList<>();
	private List<Fighter> baddies = new ArrayList<>();


	public void buildGame(String diff) {
		setDifficulty(diff);
		if (this.difficulty == Difficulty.valueOf("EASY")) {

			int numOfHeroes = 3;
			int numOfVillains = 5;

			bg = new Battleground(6, 6, numOfHeroes, numOfVillains);

			Fighter h1 = new Fighter(30, 5, "GOOD", "Pacman");
			Fighter h2 = new Fighter(30, 5, "GOOD", "Mario");
			Fighter h3 = new Fighter(30, 5, "GOOD", "Batman");
			Fighter v1 = new Fighter(20, 4, "BAD", "Goomba", 70);
			Fighter v2 = new Fighter(20, 4, "BAD", "Penguin", 70);
			Fighter v3 = new Fighter(20, 4, "BAD", "Clyde", 70);
			Fighter v4 = new Fighter(20, 4, "BAD", "Ghoul", 70);
			Fighter v5 = new Fighter(20, 4, "BAD", "Goblin", 70);

			bg.placeFighter(h1, 26);
			bg.placeFighter(h2, 33);
			bg.placeFighter(h3, 28);
			bg.placeFighter(v1, 3);
			bg.placeFighter(v2, 4);
			bg.placeFighter(v3, 5);
			bg.placeFighter(v4, 8);
			bg.placeFighter(v5, 11);

			turn.addNode(h1);
			turn.addNode(h2);
			turn.addNode(h3);
			turn.addNode(v1);
			turn.addNode(v2);
			turn.addNode(v3);
			turn.addNode(v4);
			turn.addNode(v5);

			goodies.add(h1);
			goodies.add(h2);
			goodies.add(h3);

			baddies.add(v1);
			baddies.add(v2);
			baddies.add(v3);
			baddies.add(v4);
			baddies.add(v5);

		}
	}

	public void nextTurn(Scanner input) {

		Fighter f = turn.getNextTurn();
		if(!f.isAlive()){
			turn.deleteNode(f);
			return;
		}

		printMap();

		if (f.getTeam() == Team.GOOD) {
			System.out.println(f.getName() + ", what would you like to do?");
			ArrayList<String> validCommands = new ArrayList<>();
			if (bg.findAdjacentEnemies(f) != null) {
				validCommands.add("Attack");
			}
			validCommands.add("Move");
			validCommands.add("Idle");
			while (true) {
				System.out.println("Command options: " + validCommands.toString());
				String action = input.next().toUpperCase();
				switch (action) {
					case "ATTACK":
						attackLogic(f, input);
						validCommands.remove("Attack");
						validCommands.remove("Move");
						if (isWon == true) {
							return;
						}
						break;

					case "MOVE":
						moveLogic(f, input);
						validCommands.remove("Move");
						if (bg.findAdjacentEnemies(f) != null && !validCommands.contains("Attack")) {
							validCommands.add(0, "Attack");
						}
						break;

					case "IDLE":
						return;

					default:
						System.out.println("Please enter a valid command");
				}
			}
		} else {
			if (bg.findAdjacentEnemies(f) != null) {
				enemyAttackEasy(f, bg);
			} else {
				int closestEnemy = findClosestEnemy(f, this.goodies);
				if(enemyMoveEasy(f, closestEnemy)){
					enemyAttackEasy(f, bg);
				}
			}
		}
	}

	public boolean getIsWon() {
		return isWon;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {

		Difficulty d = Difficulty.valueOf(difficulty);

		this.difficulty = d;

	}

	public void checkIfWon() {
		int heroes = this.bg.getNumOfHeroes();
		int baddies = this.bg.getNumOfVillains();

		if (heroes <= 0) {
			System.out.println("You have been defeated.");
			isWon = true;
		} else if (baddies <= 0) {
			System.out.println("Congrats! You won! Come play again soon.");
			isWon = true;
		}

	}

	public void printMap() {

		for (int i = 0; i < bg.getX_Max() * bg.getY_Max(); i++) {
			if (i % bg.getX_Max() == 0) {
				System.out.println();
			}
			if (bg.getDimensions()[i].getFighterOnTile() != null) {
				System.out.print("|"+ StringUtils.center( bg.getDimensions()[i].getFighterOnTile().getName()
						+ " (" + bg.getDimensions()[i].getFighterOnTile().getHp() + "/"
						+ bg.getDimensions()[i].getFighterOnTile().getMaxHP() + ")", 18) + "|");
			} else {
				System.out.print("|" + StringUtils.center(Integer.toString(bg.getDimensions()[i].getLocation()), 18) + "|");
			}
		}
		System.out.println();
		System.out.println();
	}

	private void attackLogic(Fighter f, Scanner input) {
		List<Fighter> enemies = bg.findAdjacentEnemies(f);
		while (true) {
			System.out.println("Who would you like to attack?");

			for (int i = 0; i < enemies.size(); i++) {
				System.out.println(i + ") '" + enemies.get(i).getName() + "' HP: " + enemies.get(i).getHp()
						/*+ " Accuracy: " + enemies.get(i).getAccuracy() + " Strength: " + enemies.get(i).getStrength()*/);
			}
			int enemyToFight = input.nextInt();
			if (0 <= enemyToFight && enemyToFight <= 3) {
				bg = fight.melee(f, enemies.get(enemyToFight), bg);

				if (!enemies.get(enemyToFight).isAlive()) {
					turn.deleteNode(enemies.get(enemyToFight));
				}
				break;
			} else {
				System.out.println("Please pick a valid opponent.");
			}
		}
		checkIfWon();
	}

	private void moveLogic(Fighter f, Scanner input) {
		while (true) {
			System.out.println("What space would you like to move to?");
			if(input.hasNextInt()) {
				int location = input.nextInt();

				Battleground placeholder = move.moveFighter(f, bg, location);
				if (placeholder != null) {
					bg = placeholder;
					printMap();
					break;
				} else {
					System.out.println("Please choose another space to move to.");
					printMap();
				}
			} else {
				System.out.println("Please enter the number of the space you want to move to.");
				input.next();
			}
		}
	}

	private void enemyAttackEasy(Fighter f, Battleground bg) {
		List<Fighter> targets = bg.findAdjacentEnemies(f);
		for (int i = 0; i < targets.size(); i++) {
			if (i == targets.size() - 1) {            //Always attack the last person if you make it this far.
				System.out.println(f.getName() + " is attacking " + targets.get(i).getName());
				fight.melee(f, targets.get(i), bg);
				return;
			}

			Random random = new Random();
			if (random.nextInt(2) == 1) {
				System.out.println(f.getName() + " is attacking " + targets.get(i).getName());
				fight.melee(f, targets.get(i), bg);
				return;
			}
		}
	}

	private int findClosestEnemy(Fighter f, List<Fighter> targets) {
		int closestTarget = 100;
		int compareValue = 0;
		int targetLocation = 100;
		for (int i = 0; i < targets.size(); i++) {
			for(int j = 0; j < 4; j++) {
				compareValue = move.calculateDistance(bg.getDimensions()[f.getCurrentLocation()],
						bg.getDimensions()[targets.get(i).getCurrentLocation()].getAdjacentTiles()[j], f.getTeam());
				if (compareValue < closestTarget) {
					closestTarget = compareValue;
					targetLocation = bg.getDimensions()[targets.get(i).getCurrentLocation()].getAdjacentTiles()[j].getLocation();
				}
			}
		}
		return targetLocation;
	}

	private boolean enemyMoveEasy(Fighter f, int closestTarget) {
		if (move.calculateDistance(bg.getDimensions()[f.getCurrentLocation()], bg.getDimensions()[closestTarget - 1], f.getTeam()) > f.getMovement() + 1) {
			return false; //Don't move if nothing in range
		}

		Tile fighterTile = bg.getDimensions()[f.getCurrentLocation()];
		Tile targetTile = bg.getDimensions()[closestTarget - 1];

		if(move.moveFighter(f, bg, closestTarget) != null){
			return true;
		}
		return false;
	}
}
