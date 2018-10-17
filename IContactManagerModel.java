package LIRAN_KATZ_HW4;

import javax.swing.JTextField;

public interface IContactManagerModel {

	public void getTheNextContact();

	public void getThePreviousContact();

	public void getTheFirstContact();

	public void getTheLastContact();

	public void createContact(String FirstName, String LastName, String PhoneNumber);

	public void update(String firstName, String lastName, String phoneNumber);

	public void saveContact(String format, String firstName, String lastName, String phoneNumber);

	public void sortBy(String sortType, String fieldsType, String upOrDown);

	public void loadFile(String nameFile, String format);

}
