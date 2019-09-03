package adiitya.stardust;

import adiitya.stardust.player.Player;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;

public class Main {

	public static void main(String[] args) throws BadPaddingException, IllegalBlockSizeException {

		File playerFile = new File("./player.plr");
		Player player = new Player(playerFile);

		System.out.printf("Player version: %d%n", player.getVersion());
		System.out.printf("Revision: %d%n", player.getRevision());
		System.out.printf("Favourite?: %b%n", player.isFavourite());
	}
}
