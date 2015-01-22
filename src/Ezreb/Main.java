package Ezreb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;



public class Main {

	public static void main(String[] args) {
		try {
			Files.copy(modsFolder.toPath(), modsgit.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(configFolder.toPath(), configgit.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(flan.toPath(), flangit.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(npcs.toPath(), npcsgit.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File dotminecraft = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\.minecraft\\forge");
	public static File modsFolder = new File(dotminecraft, "mods");
	public static File configFolder = new File(dotminecraft, "config");
	public static File flan = new File(dotminecraft, "Flan");
	public static File npcs = new File(dotminecraft, "customnpcs");
	public static File gitminecraft = new File(System.getProperty("user.home")+"\\Documents\\Github\\EzrebPack");
	public static File modsgit = new File(gitminecraft, "mods");
	public static File configgit = new File(gitminecraft, "config");
	public static File flangit = new File(gitminecraft, "Flan");
	public static File npcsgit = new File(gitminecraft, "customnpcs");

}
