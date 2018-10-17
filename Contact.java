package LIRAN_KATZ_HW4;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Random;

public class Contact implements IContact, Ifinals {

	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Random theRandomId;

	/** a constructor that get id **/
	public Contact(String id, String firstName, String lastName, String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		System.out.println(" id: " + id + " first name: " + this.firstName + " LastName: " + this.lastName
				+ " PhoneNumber: " + this.phoneNumber);
	}

	/** a constructor that not get id, create a new id **/
	public Contact(String firstName, String lastName, String phoneNumber) {
		this.id = getTheNewRandomId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		System.out.println(" id: " + id + " first name: " + this.firstName + " LastName: " + this.lastName
				+ " PhoneNumber: " + this.phoneNumber);
	}

	/**
	 * Andrey accept to use String id and use this method, this method return a
	 * new random id
	 **/
	private String getTheNewRandomId() {

		theRandomId = new Random();
		return String.format("%09d", theRandomId.nextInt(NUMBER_OF_DIGIT_IN_ID)); // id
																					// is
																					// 9
																					// digit
	}

	public String getId() {
		return id;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getlastName() {
		return lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String getphoneNumber() {
		return phoneNumber;
	}

	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void updateContact(RandomAccessFile randomAccessFile, long position) throws IOException {
		if (position > 0) {
			randomAccessFile.seek(position);
		}
		FixedLengthStringIO.writeFixedLengthString("" + this.id, ID_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.firstName, NAME_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.lastName, NAME_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.phoneNumber, PHONE_SIZE, randomAccessFile);
	}

	@Override
	public void writeObject(RandomAccessFile randomAccessFile) throws IOException {
		FixedLengthStringIO.writeFixedLengthString("" + this.id, ID_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.firstName, NAME_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.lastName, NAME_SIZE, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.phoneNumber, PHONE_SIZE, randomAccessFile);
	}

	@Override
	public void export(String format, File file) throws IOException {

		switch (format) {
		case FILENAMEÉ_EXTENSION1:
			PrintWriter pw = new PrintWriter(file);
			pw.println(this.getId());
			pw.println(this.getfirstName());
			pw.println(this.getlastName());
			pw.println(this.getphoneNumber());
			pw.close();
			break;
		case FILENAMEÉ_EXTENSION2:
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeUTF(this.id + "");
			output.writeUTF(this.firstName);
			output.writeUTF(this.lastName);
			output.writeUTF(this.phoneNumber);
			output.close();
			break;
		case FILENAMEÉ_EXTENSION3:
			DataOutputStream outputData = new DataOutputStream(new FileOutputStream(file));
			outputData.writeUTF(this.id + "");
			outputData.writeUTF(this.firstName);
			outputData.writeUTF(this.lastName);
			outputData.writeUTF(this.phoneNumber);
			outputData.close();
			break;
		}
	}

	@Override
	public String[] getUiData() { // return the fields of the class
		String[] uiDate = { this.id, this.firstName, this.lastName, this.phoneNumber };
		return uiDate;
	}

	public int compareTo(Contact con) {
		if (con != null) {
			if ((this.getfirstName().trim().equals(con.getfirstName().trim())
					&& (this.getlastName().trim().equals(con.getlastName().trim()))
					&& (this.getphoneNumber().trim().equals(con.getphoneNumber().trim())
							&& (this.getId().trim().equals(con.id.trim()))))) {

				System.out.println("The same Contact");
				return 1;
			} else {
				System.out.println("Not the same Contact");
				return -1;
			}
		}
		return 0;

	}

	@Override
	public int getObjectSize() {
		return RECORD_SIZE;
	}

	/** this method is just for checks **/
	public String toString() {
		return ("\n" + " id: " + this.id + "\n" + " first name: " + this.firstName + "\n" + " last name: "
				+ this.lastName + "\n" + " phone number: " + this.phoneNumber);

	}
}
