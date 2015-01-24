package Ezreb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class Main {

	public static void main(String[] args) {
		System.out.println(gitminecraft.toPath().relativize(modsgit.toPath()).toFile().toString());
		FileVisitOption[] f = FileVisitOption.values();
		System.out.println(f.length);
		for (FileVisitOption fileVisitOption : f) {
			System.out.println(fileVisitOption.name());
			System.out.println(fileVisitOption.toString());
		}
		File zip = new File(gitminecraft, "EzrebPack - Latest.zip");
		try {
			zip.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OutputStream rawOut = null;
		try {
			rawOut = new BufferedOutputStream(new FileOutputStream(zip));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final ZipOutputStream out = new ZipOutputStream(rawOut);
		FileVisitor<Path> fv = new FileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.err.println("ERROR: "+file.getFileName().getName(0)+" could not be visited.");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException {
				Files.copy(file, gitminecraft.toPath().resolve(dotminecraft.toPath().relativize(file)), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Successfully copied "+file.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				// TODO Auto-generated method stub
				if(dir.toFile().exists() == false) {
					Files.copy(dir, gitminecraft.toPath().resolve(dotminecraft.toPath().relativize(dir)), StandardCopyOption.REPLACE_EXISTING);
					System.err.println("Directory "+dir.getFileName().getName(0)+" did not exist, copied from source.");
					System.out.println("Intitialized directory "+dir.getFileName().getName(0));
				} else {
					System.out.println("Directory "+dir.getFileName().getName(0)+" already exists, skipping initialization.");
				}
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("Completed directory "+dir.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
		};
		FileVisitor<Path> fv2 = new FileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.err.println("ERROR: "+file.getFileName().getName(0)+" could not be visited.");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException {
				File git = file.toFile();
				File dot = dotminecraft.toPath().resolve(gitminecraft.toPath().relativize(file)).toFile();
				if(git.exists() == true && dot.exists() == false) {
					System.out.println("Found stray \""+file.getFileName().getName(0)+",\" deleting.");
					git.delete();
				} else if(git.exists() == false && dot.exists() == true) {
					System.err.println("ERROR: file missed, please restart the exporter.");
					System.exit(0);
				}
				System.out.println("Successfully checked "+file.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				// TODO Auto-generated method stub
				System.out.println("Checking directory "+dir.getFileName().getName(0)+" for stray files.");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("Completed directory "+dir.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
		};
		FileVisitor<Path> fv3 = new FileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.err.println("ERROR: "+file.getFileName().getName(0)+" could not be visited.");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException {
				ZipEntry testFile = new ZipEntry(gitminecraft.toPath().relativize(file).toFile().toString());
				out.putNextEntry(testFile);
				InputStream in = new BufferedInputStream(new FileInputStream(file.toFile()));
				byte[] input = new byte[1024];
				int len;
				while((len = in.read(input)) > 0) {
					out.write(input, 0, len);
				}
				in.close();
				out.closeEntry();
				System.out.println("Successfully copied "+file.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("Completed directory "+dir.getFileName().getName(0));
				return FileVisitResult.CONTINUE;
			}
		};
		try {
			Files.walkFileTree(modsFolder.toPath(), fv);
			System.out.println("Successfully copied "+modsFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not copy "+modsFolder.getName());
		}
		try {
			Files.walkFileTree(configFolder.toPath(), fv);
			System.out.println("Successfully copied "+configFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not copy "+configFolder.getName());
		}
		try {
			Files.walkFileTree(flan.toPath(), fv);
			System.out.println("Successfully copied "+flan.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not copy "+flan.getName());
		}
		try {
			Files.walkFileTree(npcs.toPath(), fv);
			System.out.println("Successfully copied "+npcs.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not copy "+npcs.getName());
		}
		System.out.println("Completed copy, checking for stray files.");
		try {
			Files.walkFileTree(modsgit.toPath(), fv2);
			System.out.println("Successfully stray-checked "+modsFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not stray-check "+modsFolder.getName());
		}
		try {
			Files.walkFileTree(configgit.toPath(), fv2);
			System.out.println("Successfully stray-checked "+configFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not stray-check "+configFolder.getName());
		}
		try {
			Files.walkFileTree(flangit.toPath(), fv2);
			System.out.println("Successfully stray-checked "+flan.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not stray-check "+flan.getName());
		}
		try {
			Files.walkFileTree(npcsgit.toPath(), fv2);
			System.out.println("Successfully stray-checked "+npcs.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not stray-check "+npcs.getName());
		}
		System.out.println("Completed stray-check.");
		try {
			Files.walkFileTree(modsgit.toPath(), fv3);
			System.out.println("Successfully compressed "+modsFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not compress "+modsFolder.getName());
		}
		try {
			Files.walkFileTree(configgit.toPath(), fv3);
			System.out.println("Successfully compressed "+configFolder.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not compress "+configFolder.getName());
		}
		try {
			Files.walkFileTree(flangit.toPath(), fv3);
			System.out.println("Successfully compressed "+flan.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not compress "+flan.getName());
		}
		try {
			Files.walkFileTree(npcsgit.toPath(), fv3);
			System.out.println("Successfully compressed "+npcs.getName());
		} catch (IOException e) {
			System.err.println("ERROR: Could not compress "+npcs.getName());
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Zip Export Complete");
		System.out.println("Export Complete.");
		System.out.println("Done");
//		ZipFileSystemProvider zipfsp = new ZipFileSystemProvider();
//		try {
//			zipfsp2.
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
