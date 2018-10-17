package LIRAN_KATZ_HW4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ListIterator;

import javax.swing.Spring;

public class FileListIterator<T extends IContact> implements ListIterator<T>, Ifinals {

	private RandomAccessFile raf;

	public FileListIterator(RandomAccessFile raf) throws IOException {
		this.raf = raf;
		this.raf.seek(0);

	}

	@Override
	public void add(T Contact) {
		try {
			this.raf.seek(this.raf.length()); // put on the end of file
			Contact.writeObject(this.raf);
		} catch (IOException e1) {
			System.out.println("can't add new contact to the fole");
		}
	}

	@Override
	public boolean hasNext() {
		try {
			if (this.raf.length() - this.raf.getFilePointer() > 0) {
				return true; // we have next position
			}
		} catch (IOException e) {
			System.out.println("can't cheack has next");
		}
		System.out.println("You showing the last contact");
		return false;// we have'nt next position
	}

	@Override
	public boolean hasPrevious() {
		try {
			if (this.raf.getFilePointer() - RECORD_SIZE >= 0) {
				return true; // we have previous
			}
		} catch (IOException e) {
			System.out.println("There is a problem to read from this file");
		}

		System.out.println("You showing the first contact, can't show the previous contact"); // No
																								// previous
																								// -
																								// we

		return false; // we are in the start, no previous

	}

	@Override
	public T next() {

		if (hasNext()) {

			String id, firstName, lastName, phoneNumber;
			try {

				id = FixedLengthStringIO.readFixedLengthString(ID_SIZE, this.raf);
				firstName = (FixedLengthStringIO.readFixedLengthString(NAME_SIZE, this.raf));
				lastName = (FixedLengthStringIO.readFixedLengthString(NAME_SIZE, this.raf));
				phoneNumber = (FixedLengthStringIO.readFixedLengthString(PHONE_SIZE, this.raf));
				return initContactWorkaround(id, firstName, lastName, phoneNumber);
			} catch (IOException e) {
				System.out.println("There is a problem to read from this file");

			}
		}
		return null;
	}

	@Override
	public T previous() {
		if (this.hasPrevious()) {

			String id, firstName, lastName, phoneNumber;
			try {

				this.raf.seek(this.raf.getFilePointer() - RECORD_SIZE);
				id = FixedLengthStringIO.readFixedLengthString(ID_SIZE, this.raf);
				firstName = (FixedLengthStringIO.readFixedLengthString(NAME_SIZE, this.raf));
				lastName = (FixedLengthStringIO.readFixedLengthString(NAME_SIZE, this.raf));
				phoneNumber = (FixedLengthStringIO.readFixedLengthString(PHONE_SIZE, this.raf));

				this.raf.seek(this.raf.getFilePointer() - RECORD_SIZE); // we
																		// put
																		// the
																		// pointer
																		// before
																		// the
																		// contact
																		// that
																		// we
																		// read
				return initContactWorkaround(id, firstName, lastName, phoneNumber);

			} catch (IOException e) {
				System.out.println("There is a problem to read from this file");
			}
		}
		return null;
	}

	@Override
	public void set(T e) {
		try {
			((Contact) e).updateContact(this.raf, this.raf.getFilePointer() - RECORD_SIZE);
		} catch (IOException e1) {
			System.out.println("Can't doing set action");
		}

	}

	public T initContactWorkaround(String id, String firstName, String lastName, String phoneNumber) {
		return (T) new Contact(id, firstName, lastName, phoneNumber);
	}

	private Contact read() {
		return null;

	}

	// generic using - reflection (not in syllabus)
	// @SuppressWarnings("unchecked")
	// public T initContactWorkaround() {
	// Class<T> mClass = null; // this should be received in the constructor
	// try {
	// mClass = (Class<T>) Class.forName(t.getClass().getName());
	// } catch (ClassNotFoundException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// return null;
	// }
	// ;
	// try {
	// Class<?> stringClass = Class.forName("String");
	//
	// try {
	// return mClass.getDeclaredConstructor(Integer.TYPE, stringClass,
	// stringClass, stringClass).newInstance(10);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// } catch (ClassNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// return null;
	//
	// }

	// simple approach
	@Override
	public int nextIndex() {
		return 0;
	}

	@Override
	public int previousIndex() {
		return 0;
	}

	@Override
	public void remove() {
	}
}
