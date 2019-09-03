package adiitya.stardust;

import adiitya.stardust.player.Player;
import adiitya.stardust.player.PlayerMetadata;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;

public class Main {

	public static void main(String[] args) throws BadPaddingException, IllegalBlockSizeException {

		File playerFile = new File("./player.plr");
		Player player = new Player(playerFile);
		PlayerMetadata metadata = player.getMetadata();

		System.out.printf("Player version: %d%n", metadata.getRelease());
		System.out.printf("Revision: %d%n", metadata.getRevision());
		System.out.printf("Favourite?: %b%n", metadata.isFavorite());
		System.out.printf("Name: %s%n", metadata.getName());
		System.out.printf("Difficulty: %s%n", metadata.getDifficulty().name().toLowerCase());
		System.out.printf("Playtime: %s%n", metadata.getPlaytime().toString());
	}
}
