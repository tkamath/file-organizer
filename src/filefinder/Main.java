package filefinder;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "C:\\Users\\TK2012\\Desktop\\New folder";
		Path path = FileSystems.getDefault().getPath(str);
		
		String newstr = path.getParent().toString() + "\\New list";
		Path newdir = FileSystems.getDefault().getPath(newstr);
		try {
			Files.createDirectory(newdir);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		helper(path, newdir);
	}

	static void helper(Path path, Path folder) {
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(path);
			for (Path file: stream) {
				if (!Files.isDirectory(file)) {

					int start = file.toString().lastIndexOf("\\");
					String newname = folder.toString() + "\\" + file.toString().substring(start + 1);

					Files.copy(file, FileSystems.getDefault().getPath(newname));
					return;
				} else {
					helper(file, folder);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
