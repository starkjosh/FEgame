package actions;

import java.util.ArrayList;
import java.util.List;

import blocks.Battleground;
import blocks.Fighter;
import enums.Team;
import utilities.Tile;

public class Move {

	/**
	 * @param f
	 * @param bg
	 * @param targetLocation location provided by map. Actual place in the array will be -1
	 * @return
	 */
	public Battleground moveFighter(Fighter f, Battleground bg, int targetLocation) {

		if (targetLocation < 1) {
			System.out.println("Please specify a valid coordinate on the map.");
			return null;
		} else if (targetLocation > bg.getX_Max() * bg.getY_Max()) {
			System.out.println("Please specify a valid coordinate on the map.");
			return null;
		} else if(!bg.isSpaceOpen(targetLocation)){
			System.out.println("Someone is already in that spot.");
			return null;
		}
		Tile fighterTile = bg.getTileByLocation(f.getCurrentLocation());
		Tile targetTile = bg.getTileByLocation(targetLocation);

		int distance = calculateDistance(bg.getDimensions()[fighterTile.getY_val()][fighterTile.getX_val()],
				bg.getDimensions()[targetTile.getY_val()][targetTile.getX_val()], f.getTeam());
		if (distance > f.getMovement()) {
			System.out.println("That is farther than you are allowed to move.");
			return null;
		}

		fighterTile.setFighterOnTile(null).setOpen(true);
		bg.placeFighter(f, targetLocation);

		return bg;
	}

	public int calculateDistance(Tile startingTile, Tile destination, Team myTeam) {
		List<Tile> alreadyVisited = new ArrayList<>();
		return calculateDistanceRecursive(startingTile, destination, myTeam, 0, alreadyVisited);
	}

	public List<Tile> findValidSpacesToMove(Battleground bg, Fighter fighter) {
		Tile[][] dimensions = bg.getDimensions();
		List<Tile> spacesToMove = new ArrayList<>();
		int maxMovement = fighter.getMovement();
		Tile fighterTile = bg.getTileByLocation(fighter.getCurrentLocation());

		for (int xDist = -maxMovement; xDist <= maxMovement; xDist++){

			if((0 > fighterTile.getX_val() + xDist) || (fighterTile.getX_val() + xDist >= bg.getX_Max())) {
				continue;
			}
			if(xDist != 0) {
				Tile potentialMove = dimensions[fighterTile.getY_val()][fighterTile.getX_val() + xDist];

				if (calculateDistance(fighterTile, potentialMove, fighter.getTeam()) <= fighter.getMovement()) {
					spacesToMove.add(potentialMove);
				}
			}

			for(int yDist = 1; yDist <= maxMovement - Math.abs(xDist); yDist++){
				//checking up
				if(0 <= fighterTile.getY_val() - yDist){
					Tile potentialMove = dimensions[fighterTile.getY_val() - yDist][fighterTile.getX_val() + xDist];

					if(calculateDistance(fighterTile, potentialMove, fighter.getTeam()) <= fighter.getMovement()){
						spacesToMove.add(potentialMove);
					}
				}
				//checking down
				if(fighterTile.getY_val() + yDist < bg.getY_Max()){
					Tile potentialMove = dimensions[fighterTile.getY_val() + yDist][fighterTile.getX_val() + xDist];

					if(calculateDistance(fighterTile, potentialMove, fighter.getTeam()) <= fighter.getMovement()){
						spacesToMove.add(potentialMove);
					}
				}
			}
		}

		return spacesToMove;
	}

	private int calculateDistanceRecursive(Tile currentTile, Tile destination, Team team, int distance, List<Tile> alreadyVisited) {
		if (currentTile.equals(destination) && currentTile.isOpen()) {
			return distance;
		}
		List<Tile> tempVisited = new ArrayList<>(alreadyVisited);
		tempVisited.add(currentTile);
		int calc = 8000000;
		Tile[] adjacentTiles = currentTile.getAdjacentTiles();
		for (int i = 0; i < adjacentTiles.length; i++) {
			if (adjacentTiles[i] == null) {
				continue;
			} else if (alreadyVisited.contains(adjacentTiles[i])) {
				continue;
			}
			if (adjacentTiles[i].isOpen() || adjacentTiles[i].getFighterOnTile().getTeam() == team) {
				int temp = calculateDistanceRecursive(adjacentTiles[i], destination, team, distance + 1, tempVisited);
				if (calc > temp) {
					calc = temp;
				}
			}
		}
		return calc;
	}

}
