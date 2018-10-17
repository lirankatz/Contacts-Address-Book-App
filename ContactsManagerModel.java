package LIRAN_KATZ_HW4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class ContactsManagerModel implements Ifinals, IContactManagerModel, ILiranKatzRegistrable {

	private IContact con;
	private RandomAccessFile raf;
	private FileListIterator<IContact> fileListIterator;

	private List<IContact> listContcat;
	private ListIterator<IContact> listIter;

	private TreeSet<IContact> treeSet;
	private IContact currentContact; // save the current contact
	private String id, firstName, lastName, phoneNumber;
	private String[] dataContact = new String[4];

	private ArrayList<ILiranKatzListener> listeners = new ArrayList<ILiranKatzListener>();

	private Map<Integer, Contact> map2;

	public ContactsManagerModel(String fileName) throws IOException { // we
																		// do
																		// throws
																		// Exception
																		// for
																		// constructor
																		// and
																		// not
																		// try+catch

		this.raf = new RandomAccessFile(fileName, "rw");
		System.out.println("The size of the file is: " + calcaluteSizeOfFile());
		this.fileListIterator = new FileListIterator<>(this.raf);

		if (this.cheackIfThereAreExistingContactst()) {// show the first contact
														// and if the file is
														// empty not show
														// nothing
			currentContact = this.fileListIterator.next();
		}
	}

	/** check If There are existing contact in the file **/
	public boolean cheackIfThereAreExistingContactst() {
		if (calcaluteSizeOfFile() > 0)
			return true; // the file isn't empty
		else {
			System.out.println(MESSAGE_EROR_THE_FILE_IS_EMPTY);
			return false; // "the file is empty
		}
	}

	/** return the length of the file **/
	public long calcaluteSizeOfFile() {
		try {
			return this.getRaf().length();
		} catch (IOException e) {
			System.out.println("Can't doing calcalute Size Of File ");
		}
		return 0;
	}

	/** get a contact and save on a new file, doing export **/
	public void saveContact(String format, String firstName, String lastName, String phoneNumber) {

		if (cheackIfThereAreExistingContactst()) {
			try {
				id = ((Contact) currentContact).getId(); // get the id of the
															// current
															// contact
				Contact newContact = new Contact(id, firstName, lastName, phoneNumber);
				File file = new File(newContact.getId() + "." + format);
				newContact.export(format, file);
				informTheController(EXPORT_COMMAND, newContact.getUiData());
			} catch (IOException e) {
				System.out.println("can't doing export action");
			}
		}
	}

	/** put the strings value in to Array **/
	private void innerLoadFileExtension1() {
		dataContact[0] = id; // to show that position 0 is id
		dataContact[1] = firstName; // to show that position 1 is id
		dataContact[2] = lastName; // to show that position 2 is id
		dataContact[3] = phoneNumber; // to show that position 3 is id
		informTheController(LOAD_FILE_COMMAND, dataContact);
	}

	/** put the strings value in to Array **/
	private void innerLoadFileExtension2() {
		dataContact[0] = id;
		dataContact[1] = firstName;
		dataContact[2] = lastName;
		dataContact[3] = phoneNumber;
		informTheController(LOAD_FILE_COMMAND, dataContact);
	}

	/** put the strings value in to Array **/
	private void innerLoadFileExtension3() {
		dataContact[0] = id;
		dataContact[1] = firstName;
		dataContact[2] = lastName;
		dataContact[3] = phoneNumber;
		informTheController(LOAD_FILE_COMMAND, dataContact);
	}

	/** load a file action **/
	public void loadFile(/**
							 * JTextField firstName, JTextField lastName,
							 * JTextField phoneNumber,
							 **/
	String nameFile, String format) {

		if (cheackIfThereAreExistingContactst()) {

			File file = new File(nameFile + "." + format);

			switch (format) {
			case FILENAMEÉ_EXTENSION1:
				Scanner s;
				try {
					s = new Scanner(file);
					id = s.nextLine();
					firstName = s.nextLine();
					lastName = s.nextLine();
					phoneNumber = s.nextLine();
					innerLoadFileExtension1();

				} catch (FileNotFoundException e) {
					System.out.println("file not exist");

				}

				break;
			case FILENAMEÉ_EXTENSION2:
				ObjectInputStream input;
				try {
					input = new ObjectInputStream(new FileInputStream(file));
					id = input.readUTF();
					firstName = input.readUTF();
					lastName = input.readUTF();
					phoneNumber = input.readUTF();
					innerLoadFileExtension2();
					input.close();

				} catch (FileNotFoundException e) {
					System.out.println("file not exist");

				} catch (IOException e) {
					System.out.println("There is a problem to read from this file");
				}
				break;
			case FILENAMEÉ_EXTENSION3:
				DataInputStream inputData;
				try {
					inputData = new DataInputStream(new FileInputStream(file));
					try {
						id = inputData.readUTF();
						firstName = inputData.readUTF();
						lastName = inputData.readUTF();
						phoneNumber = inputData.readUTF();
						innerLoadFileExtension3();

					} catch (IOException e) {
						System.out.println("There is a problem to read from this file");
					}

				} catch (FileNotFoundException e) {
					System.out.println("file not exist");
				}
				break;

			}

		} else
			return;

	}

	/** Next action **/
	public void getTheNextContact() {

		if (cheackIfThereAreExistingContactst()) { // check if the file is empty
													// or not

			con = (Contact) this.fileListIterator.next();

			if (((Contact) (currentContact)).compareTo((Contact) (con)) == 1) { // the
																				// same
																				// contact
				System.out.println((((Contact) (currentContact)).compareTo((Contact) (con)) == 1) + "if we do 2 next");
				con = this.fileListIterator.next();
			}
			if (con != null) {
				currentContact = con;
				informTheController(NEXT_COMMAND, con.getUiData());
			} else
				informTheController(NEXT_COMMAND, null);
		}

	}

	/** Previous action **/
	public void getThePreviousContact() {

		if (cheackIfThereAreExistingContactst()) {// check if the file is empty
													// or not
			con = this.fileListIterator.previous();

			if (((Contact) (currentContact)).compareTo(((Contact) (con))) == 1) {// the
																					// same
																					// contact
				con = this.fileListIterator.previous();

				try {
					if (currentPosition() != 0) {
						this.getRaf().seek((currentPosition() + RECORD_SIZE));
					}
				} catch (IOException e) {
					System.out.println("can't doing previous action");
				}
			}
			if (con != null) {
				currentContact = con;
				informTheController(PREVIOUS_COMMAND, con.getUiData());
			} else
				informTheController(PREVIOUS_COMMAND, null);
		}

	}

	/** return the detail of the last contact **/
	public void getTheLastContact() {
		if (cheackIfThereAreExistingContactst()) {
			while (fileListIterator.hasNext()) {
				currentContact = fileListIterator.next();
			}
			if (currentContact != null) {
				informTheController(LAST_COMMAND, currentContact.getUiData());
			}
		}
		return;
	}

	/** return the detail of the first contact **/
	public void getTheFirstContact() {

		if (cheackIfThereAreExistingContactst()) {
			while (fileListIterator.hasPrevious()) {
				currentContact = fileListIterator.previous();
			}
			if (currentContact != null) {
				informTheController(FIRST_COMMAND, currentContact.getUiData());
			}

		}
		return; // if my condition not exist, my function do nothing
	}

	/** Check if the current position is on the Last contact **/
	public boolean checkIfWeNotLastContact() {

		return fileListIterator.hasNext();
	}

	/** Check if the current position is on the first contact **/
	public boolean checkIfWeNotFirst() {

		return fileListIterator.hasPrevious();
	}

	/** update the current contact **/
	public void update(String firstName, String lastName, String phoneNumber) {

		id = ((Contact) currentContact).getId(); // when we do update the id of
													// the current contact not
													// change
		currentContact = new Contact(id, firstName, lastName, phoneNumber);
		fileListIterator.set(currentContact); // because we want to update
												// instead of the
												// other thing

		informTheController(UPDATE_COMMAND, currentContact.getUiData());

	}

	/** return the Random Access File **/
	public RandomAccessFile getRaf() {
		return raf;
	}

	/** return the current position **/
	public long currentPosition() throws IOException {
		return raf.getFilePointer();
	}

	/** create a new contact **/
	public void createContact(String FirstName, String LastName, String PhoneNumber) {
		currentContact = new Contact(FirstName, LastName, PhoneNumber);
		fileListIterator.add(currentContact);
		informTheController(CREATE_COMMAND, currentContact.getUiData()); // present
																			// the
																			// last
																			// contact

	}

	/** sort the contact by condition **/
	public void sortBy(String sortType, String fieldsType, String upOrDown) {
		if (cheackIfThereAreExistingContactst()) {
			listContcat = new ArrayList<IContact>();
			copyFileToList();
			switch (sortType) {
			case TYPE_OF_SORT_1:
				System.out.println("you choose sort by: " + TYPE_OF_SORT_1);
				sortByFields(fieldsType, upOrDown);
				break;
			case TYPE_OF_SORT_2:
				System.out.println("you choose sort by: " + TYPE_OF_SORT_2);
				sortByCount(fieldsType, upOrDown);
				break;
			case TYPE_OF_SORT_3:
				System.out.println("you choose sort by: " + TYPE_OF_SORT_3);
				reverse();
				break;
			}

			informTheController(SORT_COMMAND, currentContact.getUiData());
		}
	}

	private void sortByCount(String fieldsType, String upOrDown) {

		Map<ContactFieldCounterDecorator, Integer> map1 = new TreeMap<ContactFieldCounterDecorator, Integer>(); // create
																												// a
																												// map
																												// collection

		listIter = listContcat.listIterator(); // create a listIterator

		while (listIter.hasNext()) {
			Contact newcon;

			newcon = (Contact) listIter.next();

			ContactFieldCounterDecorator cd = new ContactFieldCounterDecorator(newcon, fieldsType);

			Integer count = map1.get(cd);

			if (count == null) {
				map1.put(cd, 1);
			} else {
				map1.put(cd, count + 1);
			}
		}

		switch (upOrDown) {
		case UP:
			mapWithComparatorUp();
			break;
		case DOWN:
			mapWithComparatorDown();
			break;
		}
		for (Entry<ContactFieldCounterDecorator, Integer> e : map1.entrySet()) {
			map2.put(e.getValue(), e.getKey().getContact());
		}

		try {
			this.getRaf().setLength(0); // we want restart the file, because
										// there is some contact that wrote
										// on the file but not on the
										// treeSed(doing sort)
			toTheStartOfTheFile();
			for (Entry<Integer, Contact> e : map2.entrySet()) {

				(e.getValue()).writeObject(this.getRaf());
			}
		} catch (IOException e1) {
			System.out.println(" can't doing sort By Count action ");
		}

	}

	/** map With Comparator Up **/
	private void mapWithComparatorUp() {
		map2 = new TreeMap<Integer, Contact>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 - o2 >= 0)
					return 1;
				else {
					return -1;
				}
			}
		});
	}

	/** map With Comparator Down **/
	private void mapWithComparatorDown() {
		map2 = new TreeMap<Integer, Contact>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 - o2 >= 0)
					return -1;
				else {
					return +1;
				}
			}
		});
	}

	private void sortByFields(String fieldsType, String upOrDown) {
		treeSet = new TreeSet<IContact>(new GeneralComparator(fieldsType, upOrDown));
		copyListToTree();

		try {
			this.getRaf().setLength(0); // we want restart the file, because
										// there is some contact that wrote on
										// the file but not on the treeSed(doing
										// sort)
			toTheStartOfTheFile();
			for (IContact iContact : treeSet) {

				((Contact) (iContact)).writeObject(this.getRaf());
			}

		} catch (IOException e) {
			System.out.println("Can't doing sort By Fields file to list");
		}

	}

	/** copy all the contact in the list to the Tree **/
	private void copyListToTree() {
		listIter = listContcat.listIterator();

		while (listIter.hasNext()) {
			treeSet.add(listIter.next());
		}
	}

	/** copy all the contact in the file to the list **/
	private void copyFileToList() {
		toTheStartOfTheFile(); // we start in position 0
		while (fileListIterator.hasNext()) {
			listContcat.add(fileListIterator.next());
		}
	}

	public void toTheStartOfTheFile() {
		try {
			this.getRaf().seek(0);
		} catch (IOException e) {
			System.out.println("can't doing this action");
		}
	}

	private void reverse() {

		listIter = listContcat.listIterator(listContcat.size()); // initialize
																	// the
																	// listIterator
																	// to start
																	// from the
																	// end
		try {
			toTheStartOfTheFile();
			while (listIter.hasPrevious()) {

				((Contact) listIter.previous()).writeObject(this.getRaf());

			}
		} catch (IOException e) {
			System.out.println("Can't doing reverse file to list");
		}

	}

	/** transfer the details of the contact to the controller **/
	private void informTheController(String command, String[] dataContact) {
		for (ILiranKatzListener listener : listeners) {
			listener.informTheView(command, dataContact);
		}
	}

	@Override
	public void registerListener(ILiranKatzListener iLiranKatzListener) {
		listeners.add(iLiranKatzListener);

	}
}
