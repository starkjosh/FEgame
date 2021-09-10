package blocks;

import utilities.Tile;

public class Battleground {

	private int x_MAX;
	private int y_MAX;
	public Tile[] dimensions;

	public Battleground() {

	}

	public Battleground(int xMax, int yMax) {
		this.dimensions = new Tile[xMax * yMax];
		populateBattleground(this.dimensions, xMax, yMax);

		this.x_MAX = xMax;
		this.y_MAX = yMax;
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



	public Battleground updateFighter(Fighter f, Battleground bg) {
		int location = f.getCurrentLocation();
		Tile[] dimensions = bg.getDimensions();

		if(!f.getStatus()) {
			dimensions[location].setFighterOnTile(null).setOpen(true);
			f.setCurrentLocation(8000000);
		}

		bg.setDimensions(dimensions);
		return bg;
	}

	public Fighter[] findAdjacentEnemies(Fighter f, Battleground bg) {
		int fighterLocation = f.getCurrentLocation();
		int enemyCount = 0;
		Fighter[] enemies = new Fighter[4];
		Tile[] dimensions = bg.getDimensions();

		if(isEnemyHere(fighterLocation - 1, f)) {
			enemyCount += 1;
			enemies[0] = dimensions[fighterLocation - 1].getFighterOnTile();
		}

		if(isEnemyHere(fighterLocation + 1, f)) {
			enemyCount += 1;
			enemies[1] = dimensions[fighterLocation + 1].getFighterOnTile();
		}

		if(isEnemyHere(fighterLocation + bg.getX_Max(), f)) {
			enemyCount += 1;
			enemies[2] = this.dimensions[fighterLocation + bg.getX_Max()].getFighterOnTile();
		}

		if(isEnemyHere(fighterLocation - bg.getX_Max(), f)) {
			enemyCount += 1;
			enemies[3] = this.dimensions[fighterLocation - bg.getX_Max()].getFighterOnTile();
		}

		if(enemyCount == 0) {
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
	}

}
