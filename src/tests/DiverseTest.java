package tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.Test;

import files.Cat;
import files.FileClass;
import files.ImmutableStrings;
import files.MyWriter;
import files.ParsingClass;
import files.Strings;

public class DiverseTest {

	@Test
	public void testImmutableString() {
		ImmutableStrings is = new ImmutableStrings("abc");
		//create instance with an initial value.
		assertTrue(is.getValue() == "abc");
		//is the initial value correctly set?

		is.add("def");
		//calls add method to concatenate to the existing value.
		assertTrue(is.getValue().equals("abcdef"));
		//has the string been correctly modified?
		assertTrue(is.getValue() != "abcdef");
		//has the object's immutability been maintained?
		//is the value stored in the ImmutableStrings not the same as the given?
		is.setTo("abcdef", true);
		//did the setTo method set correctly?
		assertTrue(is.getValue() == "abcdef");
		//is the value stored in ImmutableStrings object equal to the string?
		assertTrue(is.getValue().equals("abcdef"));
		////another check to see if the value has been correctly modified.
		//using equals method.
		is.setTo("abcdef", false);
		//false flag indicates that the object should not change its internal state,
		//but return a new instance instead.
		assertTrue(is.getValue() != "abcdef");
		//check that the value stored in object is not the same as the given string.
		//a new instance must be returned by the flag without modifying the original obj.
		assertTrue(is.getValue().equals("abcdef"));
	}

	@Test
	public void testStrings() {
		Strings s1 = new Strings(" a*b*c*d ");
		s1.process();
		assertTrue(s1.equals("dycybxa"));

		Strings s2 = new Strings(" \ncocoroc \t ");
		s2.process();
		assertTrue(s2.equals("cyrycxc"));
	}

	@Test
	public void testFiles() {
		FileClass f = new FileClass("test.txt");
		//created an instance of the FileClass with a specific file path

		assertTrue(f.getFile().exists() == false);
		//checking whether the file associated with the FileClass("test.txt")
		//does not exist at this point.

		f.write("hello\nworld\n");
		//writing the string to the file in question

		assertTrue(f.getFile().exists() == true);
		//checks if the file exists now, after writing to it.

		boolean failed = false;
		//declaring a false boolean

		FileReader fr = null;
		//class in Java
		//reading text from the file
		//initializing with null so that it can be accessed both
		//inside and outside the try block - even if an exception
		//occurs inside the block.
		try {
			fr = new FileReader(new File("test.txt"));
		} catch (FileNotFoundException e) {
			//if the file does not exist,
			e.printStackTrace();
			//error message is printed,
			failed = true;
			//sets the failed variable to true.
		}
		assertTrue(failed == false);
		//checking that the failed variable is still false,
		//no exception occurred.

		assertTrue(fr != null);
		//FileReader instance was successfully opened for reading.

		char[] in = new char[50];
		//created array to read data from file
		//with 50 elements, each element capable of
		//holding a single character.
		//array size is chosen based on the expected size of the data.

		int size = 0;
		//to keep track of the characters read.

		failed = false;

		try {
			size = fr.read(in);
			fr.close();
			//reading data from file into the in array
			//then close FileReader.
		} catch (IOException e) {
			e.printStackTrace();
			failed = true;
		}
		assertTrue(failed == false);
		//if failed == false => successful reading and closing.
		assertTrue(String.valueOf(in, 0, size).equals("hello\nworld\n"));
		//checks whether actual content matches expected content.
		f.clean();
		assertTrue(f.getFile().exists() == false);
		//checks that the file does not exist after clean-up.
	}

	@Test
	public void testReader() throws IOException {
		MyWriter mw = new MyWriter();
		assertTrue(mw.write("a file\nwith\nfour\nlines\n") == 23);
		//calls a write method to write the given content.
		//checks if method returns the given number of characters.

		mw.close();
		//close writer.

		File dir = new File("myDir");
		//create File object that represents a directory.

		assertTrue(dir.exists());
		//does the directory exist?

		File dirFile = new File(dir, "bar.txt");
		//create another object WITHIN the directory.

		assertTrue(dirFile.exists());
		//does the file exist within the directory?

		FileReader fr = null;
		//initialize a null FileReader object.

		boolean failed = false;
		//used to test if the following code will fail.
		try {
			fr = new FileReader(dirFile);
			//create FileReader for the bar file within the dir.
		} catch (FileNotFoundException e) {
			//throw exception
			e.printStackTrace();
			failed = true;
			//if the file is not found the test failed.
		}
		assert(failed == false);
		//check that the flag is still false,
		//thus, the file reader was successfully created.

		//class in Java that reads characters from input stream:
		BufferedReader br = new BufferedReader(fr);
		//read from the bar file

		int i = 0;
		String s = br.readLine();
		//read first line and store it in s.

		//start a loop that reads from the beginning until the end of the file:
		while( s != null ) {
			assert(i == 0 ? s.equals("a file") : true);
			assert(i == 1 ? s.equals("with") : true);
			assert(i == 2 ? s.equals("four") : true);
			assert(i == 3 ? s.equals("lines") : true);
			//each line from the file should be equal to the given info.

			s = br.readLine();
			//for reading next line.

			i ++;
			//increment.
		}
		br.close();
		//close buffer.

		mw.remove();
		//remove file and dir.

		dir = new File("myDir");


		assertTrue(dir.exists() == false);
		//check that the file has been removed after calling remove method.
	}

	@Test
	public void testSerializer() {
		Cat c = new Cat();

		//Cat has default value, 4 legs.
		assertTrue(c.getLegs() == 4);
		//check if the initial value of legs is 4.

		//set method to change the number of legs and give the cat a name.
		c.setLegs(5);
		c.setName("Tom");

		try {
			//Serialization
			//cat object first "c" is serialized and saved to a file named:
			FileOutputStream fs = new FileOutputStream("cat.ser"); //writing raw bytes to a file
			//if the cat.ser file does not exist, it will be created.

			//FileOutputStream wraps ObjectOutputStream.
			//FileOutputStream is the stream.

			ObjectOutputStream os = new ObjectOutputStream(fs);
			//writing objects to a stream,
			//which can then be used to store or transmit the object data.

			os.writeObject(c);
			//serializing cat object c and writing it to the outputstream os.

			//Serialization = converting object into a format that can be
			//easily stored and transmitted.

			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(c.getLegs() == 5);
		//has the legs value been change from 4 to 5 in the "c" Cat object?
		//has the setLegs method worked?

		assertTrue(c.getName().equals("Tom"));

		//Deserialization
		try {
			FileInputStream fis = new FileInputStream("cat.ser");
			//read raw bytes from file cat.ser
			ObjectInputStream ois = new ObjectInputStream(fis);
			//read serialized object from input stream

			c = (Cat) ois.readObject();
			//read serialized data using ois object
			//readObject method reads binary data and reconstructs the original Cat object
			//complete with its data and state.
			//then, the result is assigned to the variable "c".
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Deserialization complete, check state.
		//cat has 4 legs again.
		//and the same name.
		assertTrue(c.getLegs() == 4);
		assertTrue(c.getName().equals("Tom"));

	}

	@Test
	public void testParse() {
		String xml = "<album>";
		xml += "<artist>Pink Floyd</artist>";
		xml += "<name>The Wall</name>";
		xml += "<list>";
		xml += "<track>Another Brick in the Wall</track>";
		xml += "<track>Empty Spaces</track>";
		xml += "<track>Hey You</track>";
		xml += "</list></album>";
		ParsingClass pc = new ParsingClass(xml);
		pc.parse();

		assertTrue(pc.getArtist().equals("Pink Floyd"));
		//does the getArtist method return the <artist>?
		assertTrue(pc.getName().equals("The Wall"));
		//does the getName method return the <name>?

		// A short introduction to lists:
		// http://tutorials.jenkov.com/java-collections/list.html
		// List<String> l = new ArrayList();
		// l.add("cat");
		// l.contains("cat") == true
		List<String> list = pc.getTracks();
		assertTrue(list.size() == 3); //there are 3 tracks
		assertTrue(list.contains("Another Brick in the Wall"));
		assertTrue(list.contains("Empty Spaces"));
		assertTrue(list.contains("Hey You"));
		//does the list have the size of 3 with those specific tracks
		//contained within?
	}
}
