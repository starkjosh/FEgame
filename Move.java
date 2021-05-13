package actions;

import utilities.*;

public class Move {
	
	public Battleground moveFighter(Fighter f, Battleground b, int x_Val, int y_Val) {
		
		if(x_Val < 1 || y_Val < 1) {
			System.out.println("Please specify a valid coordinate on the map.");
			return b;
		}
		
		if(x_Val > b.getX_Max() + 1 || y_Val > b.getY_Max()+ 1) {
			System.out.println("Please specify a valid coordinate on the map.");
			return b;
		}
		
		int x_Loc = 0;
		int y_Loc = 0;
		Fighter[][] location = b.getDimensions();
		
		for(int i = 0; i <= b.getX_Max(); i++) {
			for(int j = 0; j <= b.getY_Max(); j++) {
				if(location[i][j] == null) {
					continue;
				} else if(location[i][j].getName().equals(f.getName())) {
					x_Loc = i;
					y_Loc = j;
				}
			}
		}
		
		int x_Dist = Math.abs(x_Loc - x_Val);
		int y_Dist = Math.abs(y_Loc - y_Val);
		
		if(x_Dist + y_Dist > f.getMovement()) {
			System.out.println("That is farther than you are allowed to move.");
			return null;
		}
		
		if(b.isSpaceOpen(x_Val, y_Val, b)) {
			
			b.placeFighter(f, x_Val, y_Val);
			b.dimensions[x_Loc][y_Loc] = null;
			
		} else {
			System.out.println("Someone is already in that spot.");
			return null;
		}
		
		
		return b;
		
	}

}
