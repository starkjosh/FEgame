package blocks;

import java.util.ArrayList;
import java.util.List;

import enums.Team;
import utilities.Tile;

public class Battleground {

	private int x_MAX;
	private int y_MAX;
	private int numOfHeroes;
	private int numOfVillains;
	private Tile[] dimensions;

	public Battleground() {

	}

	public Battleground(int xMax, int yMax, int numOfHeroes, int numOfVillains) {
		this.dimensions = new Tile[xMax * yMax];
		this.x_MAX = xMax;
		this.y_MAX = yMax;
		populateBattleground(this.dimensions, xMax, yMax);

		this.numOfHeroes = numOfHeroes;
		this.numOfVillains = numOfVillains;
	}


	public int getX_Max() {
		return x_MAX;
	}

	public Battleground setX_max(int x_Coordinate) {
		this.x_MAX = x_Coordinate;
		return this;
	}

	public int getY_Max() {
		return y_MAX;
	}

	public Battleground setY_max(int y_Coordinate) {
		this.y_MAX = y_Coordinate;
		return this;
	}

	public Tile[] getDimensions() {
		return dimensions;
	}

	public Battleground setDimensions(Tile[] dimensions) {
		this.dimensions = dimensions;
		return this;
	}

	public boolean isSpaceOpen(int location, Battleground bg) {
		if(location <= 0 || location > bg.getX_Max()*bg.getY_Max() ) {
			return false;
		}
		Tile[] dimensions = bg.getDimensions();

		if(dimensions[location - 1].isOpen()) {
			return true;
		} else {
			return false;
		}
	}

	public Battleground placeFighter(Fighter f, int location) {
		f.setCurrentLocation(location - 1);
		this.dimensions[location - 1].setFighterOnTile(f).setOpen(false);

		return this;
	}

	public int getNumOfHeroes() {
		return numOfHeroes;
	}

	public int getNumOfVillains() {
		return numOfVillains;
	}

	public Battleground updateFighter(Fighter f, Battleground bg) {
		int location = f.getCurrentLocation();
		Tile[] dimensions = bg.getDimensions();

		if(!f.isAlive()) {
			dimensions[location].setFighterOnTile(null).setOpen(true);
			f.setCurrentLocation(8000000);
			if(f.getTeam() == Team.GOOD){
				this.numOfHeroes -= 1;
			} else if(f.getTeam() == Team.BAD){
				this.numOfVillains -= 1;
			}
		}

		bg.setDimensions(dimensions);
		return bg;
	}

	public List<Fighter> findAdjacentEnemies(Fighter f) {
		int fighterLocation = f.getCurrentLocation();
		List<Fighter> enemies = new ArrayList<>();

		if(isEnemyHere(fighterLocation - 1, f)) {
			enemies.add(this.dimensions[fighterLocation - 1].getFighterOnTile());
		}
		if(isEnemyHere(fighterLocation + 1, f)) {
			enemies.add(this.dimensions[fighterLocation + 1].getFighterOnTile());
		}
		if(isEnemyHere(fighterLocation + this.x_MAX, f)) {
			enemies.add(this.dimensions[fighterLocation + this.x_MAX].getFighterOnTile());
		}
		if(isEnemyHere(fighterLocation - this.x_MAX, f)) {
			enemies.add(this.dimensions[fighterLocation - this.x_MAX].getFighterOnTile());
		}

		if(enemies.isEmpty()) {
			return null;
		}
		return enemies;
	}

	public boolean isEnemyHere(int location, Fighter f) {
		if(location < 0 || location >= this.getX_Max() * this.getY_Max()) {
			return false;
		}

		Fighter potentialEnemy = dimensions[location].getFighterOnTile();

		if(potentialEnemy != null && potentialEnemy.getTeam() != f.getTeam()) {
			return true;
		} else {
			return false;
		}
	}

	private void populateBattleground(Tile[] bg, int xMax, int yMax) {
		for(int i = 0; i < bg.length; i++){
			Tile tile = new Tile();
			tile.setLocation(i + 1)
					.setX_val(i % xMax)
					.setY_val(i / yMax);
			bg[i] = tile;
		}

		for(int j = 0; j < bg.length; j++){
			bg[j].setAdjacentTiles(this);
		}
	}

}
