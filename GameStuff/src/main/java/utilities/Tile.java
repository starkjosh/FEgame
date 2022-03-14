package utilities;

import blocks.Battleground;
import blocks.Fighter;
import enums.TerrainType;

public class Tile {

	private Fighter fighterOnTile;
	private TerrainType terrainType = TerrainType.PLAIN;
	private int location;
	private int x_val;
	private int y_val;
	private boolean open = true;
	private Tile[] adjacentTiles = new Tile[4];
	private Stats terrainBonus;

	public Fighter getFighterOnTile() {
		return fighterOnTile;
	}

	public Tile setFighterOnTile(Fighter fighterOnTile) {
		this.fighterOnTile = fighterOnTile;
		return this;
	}

	public TerrainType getTerrainType() {
		return terrainType;
	}

	public Tile setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
		setTerrainBonus();
		return this;
	}

	public int getLocation() {
		return location;
	}

	public Tile setLocation(int location) {
		this.location = location;
		return this;
	}

	public int getX_val() {
		return x_val;
	}

	public Tile setX_val(int x_val) {
		this.x_val = x_val;
		return this;
	}

	public int getY_val() {
		return y_val;
	}

	public Tile setY_val(int y_val) {
		this.y_val = y_val;
		return this;
	}

	public boolean isOpen() {
		return open;
	}

	public Tile setOpen(boolean open) {
		this.open = open;
		return this;
	}

	public Tile[] getAdjacentTiles() {
		return adjacentTiles;
	}

	public Tile setAdjacentTiles(Battleground map) {
		if(this.location % map.getX_Max() > 1){
			this.adjacentTiles[0] = map.getDimensions()[this.y_val][this.x_val - 1];
		}
		if(this.location - map.getX_Max() > 0){
			this.adjacentTiles[1] = map.getDimensions()[this.y_val - 1][this.x_val];
		}
		if(this.location % map.getX_Max() != 0){
			this.adjacentTiles[2] = map.getDimensions()[this.y_val][this.x_val + 1];
		}
		if(this.location + map.getX_Max() < map.getX_Max() * map.getY_Max()){
			this.adjacentTiles[3] = map.getDimensions()[this.y_val + 1][this.x_val];
		}

		return this;
	}

	public Stats getTerrainBonus() {
		return terrainBonus;
	}

	public void setTerrainBonus() {
		Stats bonus = new Stats();
		switch(this.terrainType) {
			case PLAIN:
				break;
			case FOREST:
				bonus.setAccuracyChange(-10).setMovementChange(-1);
				break;
			case MOUNTAIN:
				bonus.setAccuracyChange(-30).setMovementChange(-4).setSpeedChange(-20);
				break;
			case FORTRESS:
				bonus.setAccuracyChange(-20).setMovementChange(-1).setDefenceChange(3);

		}
		this.terrainBonus = bonus;
	}

	public String printTile(){
		String printer = "";
		if(this.getFighterOnTile() == null) {
			printer = "|\t" + this.getLocation() + "\t |";
		} else {
			printer = "|\t" + this.getFighterOnTile().getName() + "\t |";
		}

		return printer;
	}
}
