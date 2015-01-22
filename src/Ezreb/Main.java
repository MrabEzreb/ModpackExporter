package Ezreb;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;



public class Main {

	public static void main(String[] args) {
		try {
			modsFolder.toPath().copyTo(modsgit.toPath(), StandardCopyOption.REPLACE_EXISTING);
			configFolder.toPath().copyTo(configgit.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File dotminecraft = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\.minecraft\\forge");
	public static File modsFolder = new File(dotminecraft, "mods");
	public static File configFolder = new File(dotminecraft, "config");
	public static File gitminecraft = new File(System.getProperty("user.home")+"\\Documents\\Github\\EzrebPack");
	public static File modsgit = new File(gitminecraft, "mods");
	public static File configgit = new File(gitminecraft, "config");

}
