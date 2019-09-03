package adiitya.stardust;

import adiitya.stardust.player.Player;

import java.io.File;

public class Main {

	public static void main(String[] args) {

		File playerFile = new File("./player.plr");
		Player player = new Player(playerFile);

		System.out.printf("Player version: %d%n", player.version);
	}
}
