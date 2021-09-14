package driver;

import java.util.Scanner;

import blocks.Battle;

public class PlayGame {

	static Battle battle = new Battle();

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);

		System.out.println("What difficulty would you like to play?");
		String diff = reader.next().toUpperCase();

		battle.buildGame(diff);

		while(!battle.getIsWon()) {
			battle.nextTurn(reader);
		}
		reader.close();
	}

}
