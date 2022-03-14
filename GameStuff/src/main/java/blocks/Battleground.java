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
	//Y value first, then X value
	private Tile[][] dimensions;

	public Battleground() {

	}

	public Battleground(int xMax, int yMax, int numOfHeroes, int numOfVillains) {
		this.dimensions =  new Tile[yMax][xMax];
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

	public Tile[][] getDimensions() {
		return dimensions;
	}

	public Battleground setDimensions(Tile[][] dimensions) {
		this.dimensions = dimensions;
		return this;
	}

	public boolean isSpaceOpen(int location) {
		if(location <= 0 || location > this.getX_Max()*this.getY_Max() ) {
			return false;
		}
		Tile[][] dimensions = this.getDimensions();

		if(dimensions[(location - 1) / this.getX_Max()][(location - 1) % this.getX_Max()].isOpen()) {
			return true;
		} else {
			return false;
		}
	}

	public Battleground placeFighter(Fighter f, int location) {
		f.setCurrentLocation(location);
		this.dimensions[(location - 1) / this.getX_Max()][(location - 1) % this.getX_Max()].setFighterOnTile(f).setOpen(false);

		return this;
	}

	public int getNumOfHeroes() {
		return numOfHeroes;
	}

	public int getNumOfVillains() {
		return numOfVillains;
	}

	public Battleground updateFighter(Fighter f) {
		int location = f.getCurrentLocation();
		Tile[][] dimensions = this.getDimensions();

		if(!f.isAlive()) {
			dimensions[(location - 1) / this.getX_Max()][(location - 1) % this.getX_Max()].setFighterOnTile(null).setOpen(true);
			f.setCurrentLocation(8000000);
			if(f.getTeam() == Team.GOOD){
				this.numOfHeroes -= 1;
			} else if(f.getTeam() == Team.BAD){
				this.numOfVillains -= 1;
			}
		}

		this.setDimensions(dimensions);
		return this;
	}

	public List<Fighter> findAdjacentEnemies(Fighter f) {
		int fighterXVal = (f.getCurrentLocation() - 1) % this.getX_Max();
		int fighterYVal = (f.getCurrentLocation() - 1) / this.getX_Max();
		Tile currentTile = this.getDimensions()[fighterYVal][fighterXVal];
		List<Fighter> enemies = new ArrayList<>();

		for (Tile t : currentTile.getAdjacentTiles()) {
			if (t == null){
				continue;
			}
			if(t.getFighterOnTile() != null && t.getFighterOnTile().getTeam() != f.getTeam()){
				enemies.add(t.getFighterOnTile());
			}
		}
//		if(isEnemyHere(fighterLocation - 1, f)) {
//			enemies.add(this.dimensions[fighterLocation - 1].getFighterOnTile());
//		}
//		if(isEnemyHere(fighterLocation + 1, f)) {
//			enemies.add(this.dimensions[fighterLocation + 1].getFighterOnTile());
//		}
//		if(isEnemyHere(fighterLocation + this.x_MAX, f)) {
//			enemies.add(this.dimensions[fighterLocation + this.x_MAX].getFighterOnTile());
//		}
//		if(isEnemyHere(fighterLocation - this.x_MAX, f)) {
//			enemies.add(this.dimensions[fighterLocation - this.x_MAX].getFighterOnTile());
//		}

		if(enemies.isEmpty()) {
			return null;
		}
		return enemies;
	}

	public Tile getTileByLocation(int location){
		return dimensions[(location - 1) / x_MAX][(location - 1) % x_MAX];
	}

//	public boolean isEnemyHere(int location, Fighter f) {
//		if(location < 0 || location >= this.getX_Max() * this.getY_Max()) {
//			return false;
//		}
//
//		Fighter potentialEnemy = dimensions[location].getFighterOnTile();
//
//		if(potentialEnemy != null && potentialEnemy.getTeam() != f.getTeam()) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	private void populateBattleground(Tile[][] map, int xMax, int yMax) {
		for(int i = 0; i < xMax * yMax; i++){
			Tile tile = new Tile();
			tile.setLocation(i + 1)
					.setX_val(i % xMax)
					.setY_val(i / xMax);
			map[tile.getY_val()][tile.getX_val()] = tile;
		}

		for(int j = 0; j < xMax * yMax; j++){
			map[j / xMax][j % xMax].setAdjacentTiles(this);
		}
	}


}
