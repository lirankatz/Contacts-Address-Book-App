package LIRAN_KATZ_HW4;

public class ContactFieldCounterDecorator implements Comparable<ContactFieldCounterDecorator>, Ifinals {

	private Contact con;
	private int count;

	private String field;

	public ContactFieldCounterDecorator(Contact con, String field) {

		this.con = con;
		this.field = field;
		this.count = 0;

	}

	public String getCountableField() {
		switch (this.field) {
		case TYPE_OF_FIELDS_1: {

			return con.getfirstName();
		}
		case TYPE_OF_FIELDS_2: {

			return con.getlastName();
		}
		case TYPE_OF_FIELDS_3: {

			return con.getphoneNumber();
		}

		}
		return null;
	}

	public Contact getContact() {
		return (Contact) this.con;
	}

	@Override
	public int compareTo(ContactFieldCounterDecorator c) {

		return this.getCountableField().compareTo(c.getCountableField());

	}

}
