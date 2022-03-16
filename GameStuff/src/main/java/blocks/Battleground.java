package blocks;

import java.util.ArrayList;
import java.util.List;

import enums.Team;
import utilities.Tile;

public class Battleground {

	private int x_MAX;
	private int y_MAX;
	private List<Fighter> goodies = new ArrayList<>();
	private List<Fighter> baddies = new ArrayList<>();
	//Y value first, then X value
	private Tile[][] dimensions;

	public Battleground() {

	}

	public Battleground(int xMax, int yMax) {
		this.dimensions =  new Tile[yMax][xMax];
		this.x_MAX = xMax;
		this.y_MAX = yMax;
		populateBattleground(this.dimensions, xMax, yMax);

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

	public List<Fighter> getGoodies() {
		return goodies;
	}

	public void setGoodies(List<Fighter> goodies) {
		this.goodies = goodies;
	}

	public List<Fighter> getBaddies() {
		return baddies;
	}

	public void setBaddies(List<Fighter> baddies) {
		this.baddies = baddies;
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

	public Battleground placeFighterByLocation(Fighter f, int location) {
		f.setCurrentLocation(location);
		this.dimensions[(location - 1) / this.getX_Max()][(location - 1) % this.getX_Max()].setFighterOnTile(f).setOpen(false);

		return this;
	}

	public Battleground placeFighterByCoordinates(Fighter f, int xCor, int yCor){
		this.dimensions[yCor][xCor].setFighterOnTile(f).setOpen(false);
		f.setCurrentLocation(this.dimensions[yCor][xCor].getLocation());
		return this;
	}

	public Battleground updateFighter(Fighter f) {
		int location = f.getCurrentLocation();
		Tile[][] dimensions = this.getDimensions();

		if(!f.isAlive()) {
			dimensions[(location - 1) / this.getX_Max()][(location - 1) % this.getX_Max()].setFighterOnTile(null).setOpen(true);
			f.setCurrentLocation(8000000);
			if(f.getTeam() == Team.GOOD){
				goodies.remove(f);
			} else if(f.getTeam() == Team.BAD){
				baddies.remove(f);
			}
		}

		this.setDimensions(dimensions);
		return this;
	}

	public List<Fighter> findAdjacentEnemies(Fighter f) {
		Tile currentTile = getTileByLocation(f.getCurrentLocation());
		List<Fighter> enemies = new ArrayList<>();

		for (int position = 0; position < 4; position++) {
			if (currentTile.getAdjacentTiles()[position] == null){
				continue;
			}
			if(currentTile.getAdjacentTiles()[position].getFighterOnTile() != null &&
					currentTile.getAdjacentTiles()[position].getFighterOnTile().getTeam() != f.getTeam()){
				enemies.add(currentTile.getAdjacentTiles()[position].getFighterOnTile());
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
