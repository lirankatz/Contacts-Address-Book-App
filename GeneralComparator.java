package LIRAN_KATZ_HW4;

import java.util.Comparator;

public class GeneralComparator implements Comparator<IContact>, Ifinals {

	private String upOrDown;
	private String field;
	private String field1, field2;

	public GeneralComparator(String field, String upOrDown) {
		this.field = field;
		this.upOrDown = upOrDown;
	}

	@Override
	public int compare(IContact o1, IContact o2) {
		switch (this.field) {
		case TYPE_OF_FIELDS_1:
			field1 = ((Contact) o1).getfirstName();
			field2 = ((Contact) o2).getfirstName();
			if (this.upOrDown.equals(UP)) {
				return field1.compareTo(field2); // if we sort by up
			} else {
				return (-1 * (field1.compareTo(field2))); // if we sort by down
			}
		case TYPE_OF_FIELDS_2:
			field1 = ((Contact) o1).getlastName();
			field2 = ((Contact) o2).getlastName();
			if (this.upOrDown.equals(UP)) {
				return field1.compareTo(field2); // if we sort by up
			} else {
				return -1 * (field1.compareTo(field2)); // if we sort by down
			}
		case TYPE_OF_FIELDS_3:
			field1 = ((Contact) o1).getphoneNumber();
			field2 = ((Contact) o2).getphoneNumber();
			if (this.upOrDown.equals(UP)) {
				return field1.compareTo(field2); // if we sort by up
			} else {
				return -1 * (field1.compareTo(field2)); // if we sort by down
			}

		}
		return 0;
	}

}
