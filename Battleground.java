package utilities;

public class Battleground {
	
	private int x_MAX;
	private int y_MAX;
	public Fighter[][] dimensions;
	
	public Battleground() {
		
	}
	
	public Battleground(int x, int y) {
		this.dimensions = new Fighter[x][y];
		
		this.x_MAX = x - 1;
		this.y_MAX = y - 1;
	}
	
	
	public int getX_Max() {
		
		return x_MAX;
		
	}
	
	public void setX_max(int x_Coordinate) {
		
		this.x_MAX = x_Coordinate;
		
	}
	
	public int getY_Max() {
		
		return y_MAX;
		
	}
	
	public void setY_max(int y_Coordinate) {
		
		this.y_MAX = y_Coordinate;
		
	}
	
	public Fighter[][] getDimensions() {
		
		return dimensions;
		
	}
	
	public void setDimensions(Fighter[][] dimensions) {
		
		this.dimensions = dimensions;
		
	}
	
	public boolean isSpaceOpen(int x_Val, int y_Val, Battleground bg) {
		
		if(x_Val <= 0 || y_Val <= 0 || x_Val > this.getX_Max()+1 || y_Val > this.getY_Max()+1 ) {
			return false;
		}
		Fighter[][] dimensions = bg.getDimensions();
		
		
		if(dimensions[x_Val -1][y_Val - 1] == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void placeFighter(Fighter f, int x_Val, int y_Val) {
		
			this.dimensions[x_Val -1][y_Val-1] = f;		
	}
	
	public int findFighterXVal(Fighter f, Battleground bg) {
		
		Fighter[][] location = bg.getDimensions();
		
		for(int i = 0; i < bg.getX_Max(); i++) {
			for(int j = 0; j < bg.getY_Max(); j++) {
				if(location[i][j] == null) {
					continue;
				} else if(location[i][j].getName().equals(f.getName())) {
					return i;
				}
			}
		}
		
		return 0;
	}
	
	public int findFighterYVal(Fighter f, Battleground bg) {
		
		Fighter[][] location = bg.getDimensions();
		
		for(int i = 0; i < bg.getX_Max(); i++) {
			for(int j = 0; j < bg.getY_Max(); j++) {
				if(location[i][j] == null) {
					continue;
				} else if(location[i][j].getName().equals(f.getName())) {
					return j;
				}
			}
		}
		
		return 0;
	}
	
	public Battleground updateFighter(Fighter f, Battleground bg) {
		
		Fighter[][] location = bg.getDimensions();
		
		for(int i = 0; i < bg.getX_Max(); i++) {
			for(int j = 0; j < bg.getY_Max(); j++) {
				if(location[i][j] == null) {
					continue;
				} else if(location[i][j].getName().equals(f.getName())) {
					if(f.getStatus()) {
						location[i][j] = f;
					} else {
						location[i][j] = null;
					}
				}
			}
		}
		
		bg.setDimensions(location);
		
		return bg;
	}
	
	public Fighter[] findAdjacentEnemies(Fighter f, Battleground bg) {
		
		int x = findFighterXVal(f, bg);
		int y = findFighterYVal(f, bg);
		int enemyCount = 0;
		Fighter[] enemies = new Fighter[4];
		Fighter[][] dimensions = bg.getDimensions();
		
		if(isEnemyHere(x-1, y) && x > 0) {
			
			if(dimensions[x-1][y].getTeam() != f.getTeam()){
				enemyCount += 1;
				enemies[0] = this.dimensions[x-1][y];
			}
			
			
		}
		
		if(isEnemyHere(x+1, y) && x+1 <= bg.getX_Max()) {
			if(dimensions[x+1][y].getTeam() != f.getTeam()){
				enemyCount += 1;
				enemies[1] = this.dimensions[x+1][y];
			}
			
		}
		
		if(isEnemyHere(x, y-1) && y > 0) {
			if(dimensions[x][y-1].getTeam() != f.getTeam()){
				enemyCount += 1;
				enemies[2] = this.dimensions[x][y-1];
			}
			
		}
		
		if(isEnemyHere(x, y+1) && y+1 <= bg.getY_Max()) {
			if(dimensions[x][y+1].getTeam() != f.getTeam()){
				enemyCount += 1;
				enemies[3] = this.dimensions[x][y+1];
			}
			
		}
		
		if(enemyCount == 0) {
			return null;
		}
		
		return enemies;
		
	}
	
	public boolean isEnemyHere(int x_Val, int y_Val) {
		
		if(x_Val < 0 || y_Val < 0 || x_Val > this.getX_Max() || y_Val > this.getY_Max() ) {
			return false;
		}
		
		
		if(dimensions[x_Val][y_Val] == null) {
			return false;
		} else {
			return true;
		}
	}

}
