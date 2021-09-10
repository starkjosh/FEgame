package actions;

import blocks.Battleground;
import blocks.Fighter;
import utilities.Tile;

public class Move {

	/**
	 *
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

		int fighterLocation = f.getCurrentLocation();
		int currentXLocation = bg.getDimensions()[fighterLocation].getX_val();
		int currentYLocation = bg.getDimensions()[fighterLocation].getY_val();

		int targetXLocation = bg.getDimensions()[targetLocation - 1].getX_val();
		int targetYLocation = bg.getDimensions()[targetLocation - 1].getY_val();

		int x_Dist = Math.abs(currentXLocation - targetXLocation);
		int y_Dist = Math.abs(currentYLocation - targetYLocation);

		if (x_Dist + y_Dist > f.getMovement()) {
			System.out.println("That is farther than you are allowed to move.");
			return null;
		}

		if (bg.isSpaceOpen(targetLocation, bg)) {
			bg.placeFighter(f, targetLocation);
			bg.dimensions[fighterLocation].setFighterOnTile(null).setOpen(true);
		} else {
			System.out.println("Someone is already in that spot.");
			return null;
		}

		return bg;

	}

}
