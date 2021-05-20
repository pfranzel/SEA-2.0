package de.telekom.sea2.lookup;
public enum Salutation {
	MR, MRS, OTHER;

	public static Salutation fromString(final String value) {

		switch (value.toUpperCase()) {

		case "MRS":
		case "MRS.":
		case "FEMALE":
		case "F":
		case "FRAU":
			return MRS;
		case "MR":
		case "MR.":
		case "MALE":
		case "M":
		case "MANN":
			return MR;
		case "OTHER":
		case "DIVERSE":
		case "D":
			return OTHER;

		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
	}
	
	public Byte toByte() {
		switch (this) {
		case MRS:
			return 1;
		case MR:
			return 2;
		case OTHER:
			return 3;
		default:
			throw new RuntimeException("Unexpected value: " + toByte());
		}
	}

	public static Salutation fromByte(byte b) {
		switch (b) {
		case 1:
			return MRS;
		case 2:
			return MR;
		case 3:
			return OTHER;
		default:
			throw new RuntimeException("Unexpected case! " + fromByte(b));
		}
	}
	
	@Override
	public String toString() {
		switch (this) {
		case MRS:
			return "Mrs.";
		case MR:
			return "Mr.";
		case OTHER:
			return "Other";
		default:
			throw new IllegalArgumentException("Unexpected Case - please check...");
		}
	} 
}