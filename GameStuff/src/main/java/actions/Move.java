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
		}

		if (targetLocation > bg.getX_Max() * bg.getY_Max()) {
			System.out.println("Please specify a valid coordinate on the map.");
			return null;
		}

		int distance = calculateDistance(bg.getDimensions()[f.getCurrentLocation()], bg.getDimensions()[targetLocation - 1], f.getTeam());
		if (distance > f.getMovement()) {
			System.out.println("That is farther than you are allowed to move.");
			return null;
		}

		if (bg.isSpaceOpen(targetLocation, bg)) {
			bg.getDimensions()[f.getCurrentLocation()].setFighterOnTile(null).setOpen(true);
			bg.placeFighter(f, targetLocation);
		} else {
			System.out.println("Someone is already in that spot.");
			return null;
		}

		return bg;
	}

	public int calculateDistance(Tile startingTile, Tile destination, Team myTeam) {
		List<Tile> alreadyVisited = new ArrayList<>();
		return calculateDistanceRecursive(startingTile, destination, myTeam, 0, alreadyVisited);
	}

//	private List<Tile> findValidSpacesToMove(int currentLocation, Battleground bg) {
//		Tile[] dimensions = bg.getDimensions();
//		List<Tile> spacesToMove = new ArrayList<>();
//		Fighter mover = dimensions[currentLocation].getFighterOnTile();
//
//		for (int movementLocation = 0; movementLocation < dimensions.length; movementLocation++) {
//			if (calculateDistance(currentLocation, movementLocation, bg) <= mover.getMovement()
//					&& dimensions[movementLocation].isOpen()) {
//				spacesToMove.add(dimensions[movementLocation]);
//			}
//		}
//		return spacesToMove;
//	}

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
