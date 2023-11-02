package files;

import java.io.*;
import java.nio.file.Files;

public class MyWriter {
	private File directory;
	private File file;
	private FileWriter fileWriter;

	public int write(String string) {
		if (directory == null) {
			//directory does not exist,
			//create new dir using method mkdirs from File class.

			directory = new File("myDir");
			directory.mkdirs();
		}

		if (file == null) {
			//file does not exist,
			//create bar file.
			file = new File(directory, "bar.txt");
		}

		try {
			fileWriter = new FileWriter(file);
			//initialize file object
			fileWriter.write(string);
			//write content to the string parameter(keep in mind size = 0).

			//must close.
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
			//if writing operation fails due to exception.
		}

		//mind the number of characters.
		return string.length();
	}

	public boolean verifyContent(String[] expectedLines) {
		//check if the file exists.
		if (file == null || !file.exists()) {
			return false;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			//buffer to read from the input stream aka the file.
			String line;
			//hold the current line being read.
			int i = 0;
			//keep track of line.
			//as long as there are lines to be read from the file, read.
			while ((line = reader.readLine()) != null) {
				//if the line is within bounds, good. if not, stop.
				if (i < expectedLines.length && !line.equals(expectedLines[i])) {
					return false;
				}
				i++;
			}
			//if the loop is completed,
			//then the lines have been compared to the expected ones successfully.
			return i == expectedLines.length;
			//also i matches the expected number of lines.

			//if all is successful, return true.
		} catch (IOException e) {
			e.printStackTrace();
			//if something fails, return false.
			return false;
		}
	}
	//close and delete.

	public void close() {
		if (fileWriter != null) {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void remove() {
		if (file != null && file.exists()) {
			file.delete();
		}

		if (directory != null && directory.exists()) {
			try {
				Files.delete(directory.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
