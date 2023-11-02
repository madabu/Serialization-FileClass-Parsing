package files;

public class ImmutableStrings {
	private String value;

	public ImmutableStrings(String value) {
		this.value = value;
	}

	public void add(String val) {
		//append to the given string the existing value.
		this.value += val;
	}

	public String getValue() {
		//return current value.
		return this.value;
	}

	public void setTo(String string, boolean check) {
		if (check) {
			//if flag is true,
			this.value = string;
			//directly set the value to the provided string.
		} else {
			//if the check is false,
			//create a new instance with the provided string.
			//=>Immutability.
			this.value = new String(string);
		}
	}
}
