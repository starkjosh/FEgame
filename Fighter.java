package utilities;
import enums.Team;

public class Fighter {

	private String name;
	private int strength;
	//private int defence;
	//private int speed;
	//private boolean isBoss;
	private int hitpoints;
	private double accuracy;
	private int movement;
	private Team team;
	private boolean isAlive = true;
	
	public Fighter() {
		
	}
	
	public Fighter(int hitpoints, int movement, String team, String name) {
		setHitpoints(hitpoints);
		setMovement(movement);
		setTeam(team);
		setName(name);
		setAccuracy(90);
		setStrength(10);
		
	}
	
	public Fighter(int hitpoints, int movement, String team, String name, double accuracy) {
		setHitpoints(hitpoints);
		setMovement(movement);
		setTeam(team);
		setName(name);
		setAccuracy(accuracy);
		setStrength(10);
		
	}
	
	public void setHitpoints(int hp) {
		
		hitpoints = hp;
		
	}
	
	public void setAccuracy(double acc) {
		
		accuracy = acc;
		
	}

	public void setMovement(int move) {
	
		movement = move;
	
	}

	public void setTeam(String t) {
	
		team = Team.valueOf(t);
	
	}
	
	public int getHitpoints() {
		
		return hitpoints;
		
	}
	
	public double getAccuracy() {
		
		return accuracy;
		
	}
	
	public int getMovement() {
		
		return movement;
		
	}
	
	public Team getTeam() {
		
		return team;
		
	}
	
	public String getName() {
		
		return name;
	
	}

	public void setName(String name) {
		
		this.name = name;
		
	}
	
	public void takeHit(int damage) {
		
		hitpoints = hitpoints - damage;
		if(hitpoints <= 0) {
			fighterDies();
		}
		
	}

	public int getStrength() {
		
		return strength;
		
	}

	public void setStrength(int strength) {
		
		this.strength = strength;
		
	}
	
	public void fighterDies() {
		
		this.isAlive = false;
		
	}
	
	public boolean getStatus() {
		
		return isAlive;
		
	}

}
