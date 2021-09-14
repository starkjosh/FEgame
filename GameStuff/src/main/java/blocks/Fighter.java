package blocks;

import enums.Team;

public class Fighter {

	private String name;
	private int strength;
	private int defence;
	private int speed;
	private boolean isBoss;
	private int hp;
	private double accuracy;
	private int movement;
	private Team team;
	private int currentLocation;
	private boolean isAlive = true;

	public Fighter() {

	}

	public Fighter(int hp, int movement, String team, String name) {
		this(hp, movement, team, name, 95);
	}

	public Fighter(int hp, int movement, String team, String name, double accuracy) {
		this.setHp(hp)
			.setMovement(movement)
			.setTeam(team)
			.setName(name)
			.setAccuracy(accuracy)
			.setStrength(10)
			.setSpeed(10)
			.setDefence(0)
			.setBoss(false);

	}

	public int getHp() {
		return hp;
	}

	public Fighter setHp(int hp) {
		this.hp = hp;
		return this;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public Fighter setAccuracy(double acc) {
		accuracy = acc;
		return this;
	}

	public int getMovement() {
		return movement;
	}

	public Fighter setMovement(int move) {
		movement = move;
		return this;
	}

	public Team getTeam() {
		return team;
	}

	public Fighter setTeam(String t) {
		team = Team.valueOf(t);
		return this;
	}

	public String getName() {
		return name;
	}

	public Fighter setName(String name) {
		this.name = name;
		return this;
	}

	public int getStrength() {
		return strength;
	}

	public Fighter setStrength(int strength) {
		this.strength = strength;
		return this;
	}

	public int getDefence() {
		return defence;
	}

	public Fighter setDefence(int defence) {
		this.defence = defence;
		return this;
	}

	public int getSpeed() {
		return speed;
	}

	public Fighter setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public Fighter setBoss(boolean boss) {
		isBoss = boss;
		return this;
	}

	public Fighter fighterDies() {
		this.isAlive = false;
		return this;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public Fighter setTeam(Team team) {
		this.team = team;
		return this;
	}

	public int getCurrentLocation() {
		return currentLocation;
	}

	public Fighter setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
		return this;
	}

	public Fighter takeHit(int damage) {
		hp = hp - damage;
		if (hp <= 0) {
			fighterDies();
		}
		return this;
	}
}
