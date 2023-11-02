package files;

import java.io.*;

//class hierarchy with Animal as the parent and Cat as the child
//both classes implement the Serializable interface
//thus, the instances of the classes can be serialized.
class Animal implements Serializable {
	private int legs;

	public Animal() {
		this.legs = 4; // Default value for legs.
	}

	public int getLegs() {
		return legs;
	}

	public void setLegs(int legs) {
		this.legs = legs;
	}

	//Custom serialization:

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		//call parameter with method to allow default serialization.
		out.writeInt(legs);
		//give legs to be serialized.
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		//read default object.
	}
}

public class Cat extends Animal {
	private String name;

	public Cat() {
		this.name = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//custom Deserialization:

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		//read default object,
		//set the legs explicitly to 4:
		this.setLegs(4);
	}
}
