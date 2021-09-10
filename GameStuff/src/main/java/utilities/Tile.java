package utilities;

import blocks.Fighter;
import enums.TerrainType;

public class Tile {

	private Fighter fighterOnTile;
	private TerrainType terrainType;
	private int location;
	private int x_val;
	private int y_val;
	private boolean open = true;

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

	public String printTile(Tile tile){
		String printer = "";
		if(tile.getFighterOnTile() == null) {
			printer = "|\t" + tile.getLocation() + "\t";
		} else {
			printer = "|\t" + tile.getFighterOnTile().getName() + "\t";
		}

		return printer;
	}
}
