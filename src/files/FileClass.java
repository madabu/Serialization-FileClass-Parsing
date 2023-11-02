package files;

import java.io.File;
//file/directory in the file system
//interacting with file system
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class FileClass {
	private File file;
	//new instance of File type.

	public FileClass(String filePath) {
		this.file = new File(filePath);
	}
	//take filePath as a parameter,
	//associate FileClass with the specific file.

	public File getFile() {
		return file;
	}


	public void write(String content) {
		//writing content to the associated file
		try (FileWriter writer = new FileWriter(file)) {
			//create a FileWriter for the file and
			writer.write(content);
			//write the content to the file
		} catch (IOException e) {
			e.printStackTrace();
			//if error occurs, print message
		}
	}

	public void clean() {
		if (file.exists()) {
			//if file exists,
			file.delete();
			//delete it.
		}
		//must clean-up file.
	}
}

