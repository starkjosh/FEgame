package utilities;

public class Stats {

	private int accuracyChange;
	private int damageChange;
	private int speedChange;
	private int movementChange;
	private int defenceChange;

	public int getAccuracyChange() {
		return accuracyChange;
	}

	public Stats setAccuracyChange(int accuracyChange) {
		this.accuracyChange = accuracyChange;
		return this;
	}

	public int getDamageChange() {
		return damageChange;
	}

	public Stats setDamageChange(int damageChange) {
		this.damageChange = damageChange;
		return this;
	}

	public int getSpeedChange() {
		return speedChange;
	}

	public Stats setSpeedChange(int speedChange) {
		this.speedChange = speedChange;
		return this;
	}

	public int getMovementChange() {
		return movementChange;
	}

	public Stats setMovementChange(int movementChange) {
		this.movementChange = movementChange;
		return this;
	}

	public int getDefenceChange() {
		return defenceChange;
	}

	public Stats setDefenceChange(int defenceChange) {
		this.defenceChange = defenceChange;
		return this;
	}
}
