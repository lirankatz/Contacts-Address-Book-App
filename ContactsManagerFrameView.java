package LIRAN_KATZ_HW4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.omg.Messaging.SyncScopeHelper;

public class ContactsManagerFrameView extends JFrame
		implements Ifinals, IContactManagerFrameView, ILiranKatzRegistrable {

	private static final long serialVersionUID = 1L;
	private JPanel panelNorth; // the Upper panel
	private JPanel panelCenter; // the Middle Panel
	private JPanel panelSouth; // the Bottom panel
	private JTextField jtfFN, jtfLN, jtfPN, jtfFPath;
	private JLabel detail1, detail2, detail3;
	private JButton jbtCreate, jbtUpdate, jbtShow, jbtSort, jbtExport;
	private JComboBox<String> jcboxReOrganize, jcboxfields, jcboxUpDownRight, jcboxUpDownLeft, jcbox;
	private String[] typeOfFile = { FILENAMEÉ_EXTENSION1, FILENAMEÉ_EXTENSION2, FILENAMEÉ_EXTENSION3 };
	private boolean firstTime = true; // when the timer work first time
	private Timer timer = new Timer(SPEED, new TimerListener());
	private ArrayList<ILiranKatzListener> listeners = new ArrayList<ILiranKatzListener>();

	public ContactsManagerFrameView(ContactsManagerModel cm) throws IOException { // we
		// do
		// throws
		// Exception
		// for
		// constructor
		// and
		// not
		// try+catch

		this.setLayout((new BorderLayout()));

		panelNorth = new JPanel((new GridLayout(4, 2, 10, 10)));
		this.getContentPane().add(panelNorth, BorderLayout.NORTH);
		panelNorth();

		panelCenter = new JPanel((new BorderLayout()));
		this.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter();

		panelSouth = new JPanel((new BorderLayout()));
		this.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth();

		jbtUpdate.setEnabled(false); // can't click on update button
		goodProperties();

	}

	/** get a string array and put text to the specific labels **/
	public void setTextToTheLabels(String[] str) {

		if (str != null) {
			detail1.setText(str[1]);
			detail2.setText(str[2]);
			detail3.setText(str[3]);
		} else
			return;
	}

	public void init() { // put here set visible
		/** At the beginning: Display the first record if exists **/
		informTheControllerNPFL("FIRST");
		this.setVisible(true);
	}

	/** the north panel **/
	public void panelNorth() {
		JLabel jFname = new JLabel(FIRST_NAME_LABEL);
		jtfFN = new JTextField(SIZE_TEXT_FIELD);
		jtfFN.setHorizontalAlignment(JTextField.LEFT);
		JLabel jLname = new JLabel(LAST_NAME_LABEL);
		jtfLN = new JTextField(SIZE_TEXT_FIELD);
		jtfLN.setHorizontalAlignment(JTextField.LEFT);
		JLabel jPnumber = new JLabel(PHONE_NUMBER_LABEL);
		jtfPN = new JTextField(SIZE_TEXT_FIELD);
		jtfPN.setHorizontalAlignment(JTextField.LEFT);
		jbtCreate = new JButton(CREATE_BUTTON);
		jbtUpdate = new JButton(UPDATE_BUTTON);
		panelNorth.add(jFname);
		panelNorth.add(jtfFN);
		panelNorth.add(jLname);
		panelNorth.add(jtfLN);
		panelNorth.add(jPnumber);
		panelNorth.add(jtfPN);
		panelNorth.add(jbtCreate);
		panelNorth.add(jbtUpdate);
		jbtUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		jbtCreate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cheackIfTheFieldsEmpty()) {// check if the fields are empty
					System.out.println("you need to fill all the fields");
				} else {
					Create();
				}
			}
		});
	}

	/** the center panel **/
	public void panelCenter() {
		JPanel panelUnitButtonRight = new JPanel((new GridLayout(2, 1, 5, 5)));
		JButton jbtPrevious = new JButton(LEFT_BUTTON);
		jbtPrevious.setSize(SIZE_FRAME_HEIGHT_BUTTON, SIZE_FRAME_FRAME_WEIGHT_BUTTON);
		jbtPrevious.addActionListener(new ActionListener() // Action listener
															// for the button
		{
			public void actionPerformed(ActionEvent e) {
				previousContact();

			}

		});
		JButton jbtFirst = new JButton(FIRST_BUTTON);
		jbtFirst.setSize(SIZE_FRAME_HEIGHT_BUTTON, SIZE_FRAME_FRAME_WEIGHT_BUTTON);
		jbtFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				first();
			}
		});
		panelUnitButtonRight.add(jbtPrevious);
		panelUnitButtonRight.add(jbtFirst);
		panelCenter.add(panelUnitButtonRight, BorderLayout.WEST);

		JPanel panelUnit = new JPanel((new GridLayout(4, 2, 7, 7)));
		JLabel jFnamecenter = new JLabel(FIRST_NAME_LABEL);
		detail1 = new JLabel(FILE_EMPTY);
		JLabel jLnamecenter = new JLabel(LAST_NAME_LABEL);
		detail2 = new JLabel(FILE_EMPTY);
		JLabel jPnumercenter = new JLabel(PHONE_NUMBER_LABEL);
		detail3 = new JLabel(FILE_EMPTY);
		JButton jbtEdit = new JButton(EDIT_CONTACT_BUTTON);
		jbtEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});

		JPanel panelEditContact = new JPanel((new BorderLayout()));
		panelEditContact.add(jbtEdit, BorderLayout.SOUTH); // put the button in
															// a panel
		panelUnit.add(jFnamecenter);
		panelUnit.add(detail1);
		panelUnit.add(jLnamecenter);
		panelUnit.add(detail2);
		panelUnit.add(jPnumercenter);
		panelUnit.add(detail3);
		panelUnit.add(panelEditContact);
		panelCenter.add(panelUnit, BorderLayout.CENTER);

		JPanel panelUnitButtonLeft = new JPanel((new GridLayout(2, 1, 7, 7)));
		JButton jbtNext = new JButton(RIGHT_BUTTON);
		jbtNext.setSize(SIZE_FRAME_HEIGHT_BUTTON, SIZE_FRAME_FRAME_WEIGHT_BUTTON);
		jbtNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextContact();
			}
		});

		JButton jbtLast = new JButton(LAST_BUTTON);
		jbtLast.setSize(SIZE_FRAME_HEIGHT_BUTTON, SIZE_FRAME_FRAME_WEIGHT_BUTTON);
		jbtLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTheLastContact();
			}
		});
		panelUnitButtonLeft.add(jbtNext);
		panelUnitButtonLeft.add(jbtLast);
		panelCenter.add(panelUnitButtonLeft, BorderLayout.EAST);
	}

	/** the south panel **/
	public void panelSouth() {

		jcbox = new JComboBox<String>(typeOfFile);
		JPanel panelComboBox = new JPanel((new FlowLayout())); // Panel for
																// combo box
		panelComboBox.add(jcbox);
		jbtExport = new JButton(EXPORT_BUTTON);
		jbtExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				export();
			}
		});
		JPanel panelLeftUpSouth = new JPanel((new GridLayout(3, 1, 10, 10)));
		JLabel jFilePath = new JLabel(FILE_PATH_LABEL);
		jtfFPath = new JTextField(SIZE_TEXT_FIELD);
		JButton jbtloadFile = new JButton(LOAD_FILE_BUTTON);
		jbtloadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		JPanel panelSouthUp = new JPanel((new GridLayout(1, 3, 10, 10)));
		JPanel panelSouthDown = new JPanel((new GridLayout(2, 1, 10, 10)));

		panelLeftUpSouth.add(jFilePath);
		panelLeftUpSouth.add(jtfFPath);
		panelLeftUpSouth.add(jbtloadFile);

		panelSouthUp.add(panelComboBox);
		panelSouthUp.add(jbtExport);
		panelSouthUp.add(panelLeftUpSouth);

		setComboBox();

		panelSouth.add(panelSouthUp, BorderLayout.NORTH);
		jbtShow = new JButton(SHOW_BUTTON);
		jbtSort = new JButton(SORT_BUTTON);
		jbtSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sort();
			}
		});
		jbtShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			}
		});

		JPanel panelLeftDownSouth1 = new JPanel((new GridLayout(1, 4, 50, 50)));
		JPanel panelLeftDownSouth2 = new JPanel((new GridLayout(1, 2, 50, 50)));

		panelLeftDownSouth1.add(jcboxReOrganize);
		panelLeftDownSouth1.add(jcboxfields);
		panelLeftDownSouth1.add(jcboxUpDownRight);
		panelLeftDownSouth1.add(jbtSort);
		panelLeftDownSouth2.add(jcboxUpDownLeft);
		panelLeftDownSouth2.add(jbtShow);

		panelSouthDown.add(panelLeftDownSouth1);
		panelSouthDown.add(panelLeftDownSouth2);

		panelSouth.add(panelSouthDown, BorderLayout.SOUTH);

	}

	/** sort the contact on the file **/
	public void sort() {
		informTheControllerSort("" + jcboxReOrganize.getSelectedItem(), "" + jcboxfields.getSelectedItem(),
				"" + jcboxUpDownRight.getSelectedItem());
		informTheControllerNPFL(FIRST_COMMAND); // display the first contact
												// after the sort

	}

	/** load file action **/
	public void loadFile() {
		int index = jcbox.getSelectedIndex();
		String nameFile = jtfFPath.getText();
		informTheControllerLoadFile(nameFile, typeOfFile[index]);
	}

	/** export action **/
	public void export() {
		informTheControllerExport(detail1.getText(), detail2.getText(), detail3.getText());

	}

	/** present the previous contact on the file **/
	public void previousContact() {
		informTheControllerNPFL(PREVIOUS_COMMAND);

	}

	/** if timer is on, and We reach to the end of the next or previous **/
	public void ifTimerIsRunning() {
		timer.stop();
		firstTime = true; // to initialize for the next time that we want to use
	}

	/** get the last contact **/
	public void getTheLastContact() {
		informTheControllerNPFL(LAST_COMMAND);

	}

	/** present the next contact on the file **/
	public void nextContact() {
		informTheControllerNPFL(NEXT_COMMAND);
	}

	/**
	 * edit the present contact, andrey allow: not yell to the controller when i
	 * click on edit button
	 **/
	public void edit() {
		jbtUpdate.setEnabled(true);
		jtfFN.setText(detail1.getText());
		jtfLN.setText(detail2.getText());
		jtfPN.setText(detail3.getText());

	}

	/** write the new contact on the file **/
	public void writeContact() {
		informTheControllerCreateOrUpdate(CREATE_COMMAND, jtfFN.getText(), jtfLN.getText(), jtfPN.getText());
	}

	/**
	 * // check if one of the fields is empty,check if we get all the
	 * information that we need
	 **/
	public boolean cheackIfTheFieldsEmpty() {
		if ((jtfFN.getText().equals("")) || (jtfLN.getText().equals("")) || (jtfPN.getText().equals(""))) {

			return true;
		} else
			return false;
	}

	/** clean the fields **/
	public void removeField() {
		jtfFN.setText("");
		jtfLN.setText("");
		jtfPN.setText("");
		jbtUpdate.setEnabled(false);
	}

	/** create action, create new contact **/
	public void Create() {

		writeContact();
		removeField(); // clean the field after we add people
	}

	/** present the first contact **/
	public void first() {
		informTheControllerNPFL(FIRST_COMMAND);

	}

	public void update() {
		informTheControllerCreateOrUpdate(UPDATE_COMMAND, jtfFN.getText(), jtfLN.getText(), jtfPN.getText());

	}

	class TimerListener implements ActionListener {
		/** Handle the action event */
		public void actionPerformed(ActionEvent e) {
			if (("" + jcboxUpDownLeft.getSelectedItem()).equals(UP) && firstTime == true) {
				first();
				firstTime = false;
			} else if (("" + jcboxUpDownLeft.getSelectedItem()).equals(UP) && firstTime != true) {
				nextContact();
			} else if (("" + jcboxUpDownLeft.getSelectedItem()).equals(DOWN) && firstTime == true) {
				getTheLastContact();
				firstTime = false;
			} else if ((("" + jcboxUpDownLeft.getSelectedItem()).equals(DOWN) && firstTime != true)) {
				previousContact();
			}
		}
	}

	/**
	 * this method takes the information from the controller, and present the
	 * date on the view
	 **/
	public void presentTheDateOnTheFrame(String command, String[] dataContact) {
		switch (command) {
		case NEXT_COMMAND:
			if (dataContact == null) {
				if (timer.isRunning()) { // if the timer is on
					ifTimerIsRunning();
				}

			} else {
				System.out.println("Next command susceed");
				setTextToTheLabels(dataContact);
			}
			break;

		case PREVIOUS_COMMAND:
			if (dataContact == null) {

				if (timer.isRunning()) { // if the timer is on
					ifTimerIsRunning();
				}
				return;
			} else
				System.out.println("Previous command susceed");
			setTextToTheLabels(dataContact);
			break;

		case FIRST_COMMAND:
			if (dataContact == null)
				System.out.println("you are already on the first contact");
			else
				System.out.println("First command susceed");
			setTextToTheLabels(dataContact);
			break;

		case LAST_COMMAND:
			if (dataContact == null) {
				System.out.println(" you are already on the last contact");
				return;
			} else
				System.out.println("Last command susceed");
			setTextToTheLabels(dataContact);
			break;
		case UPDATE_COMMAND:
			System.out.println("Update command susceed");
			setTextToTheLabels(dataContact);
			break;
		case CREATE_COMMAND:
			System.out.println("Create command susceed");
			setTextToTheLabels(dataContact);
			break;
		case SORT_COMMAND:
			System.out.println("Sort command susceed");
			break;

		case EXPORT_COMMAND:
			System.out.println("Export command susceed");
			break;

		case LOAD_FILE_COMMAND:
			System.out.println("Load file command susceed");
			jtfFN.setText(dataContact[1]);
			jtfLN.setText(dataContact[2]);
			jtfPN.setText(dataContact[3]);
			break;
		}
	}

	/** transfer the load file commands the to controller **/
	private void informTheControllerLoadFile(String nameFile, String format) {
		removeField();
		for (ILiranKatzListener listener : listeners) {
			listener.informTheModelLoadFile(nameFile, format);
		}
	}

	/** transfer the sort commands the to controller **/
	private void informTheControllerSort(String sortType, String sortField, String ascOrDesc) {
		removeField();
		for (ILiranKatzListener listener : listeners) {
			listener.infromTheModelSort(sortType, sortField, ascOrDesc);
		}
	}

	/** transfer the export commands the to controller **/
	private void informTheControllerExport(String FirstName, String LastName, String PhoneNumber) {
		removeField();
		int index = jcbox.getSelectedIndex();
		for (ILiranKatzListener listener : listeners) {
			listener.informTheModelExport(typeOfFile[index], FirstName, LastName, PhoneNumber);
		}
	}

	/** transfer the commands(Create,Update) to the controller **/
	private void informTheControllerCreateOrUpdate(String command, String FirstName, String LastName,
			String PhoneNumber) {
		removeField();
		switch (command) {
		case CREATE_COMMAND:
			for (ILiranKatzListener listener : listeners) {
				listener.informTheModelCreateOrUpdate(command, FirstName, LastName, PhoneNumber);
			}
			break;
		case UPDATE_COMMAND:
			for (ILiranKatzListener listener : listeners) {
				listener.informTheModelCreateOrUpdate(command, FirstName, LastName, PhoneNumber);
			}
			break;

		}

	}

	/** transfer the commands(Next,Previous,First,Last) to the controller **/
	private void informTheControllerNPFL(String command) {
		removeField();
		for (ILiranKatzListener listener : listeners) {
			listener.informTheModelNPFL(command);
		}
	}

	public void setComboBox() {
		String[] state = { TYPE_OF_SORT_1, TYPE_OF_SORT_2, TYPE_OF_SORT_3 };
		jcboxReOrganize = new JComboBox<String>(state);
		String[] fields = { TYPE_OF_FIELDS_1, TYPE_OF_FIELDS_2, TYPE_OF_FIELDS_3 };
		jcboxfields = new JComboBox<String>(fields);
		String[] UpDown1 = { UP, DOWN };
		jcboxUpDownRight = new JComboBox<String>(UpDown1);
		String[] UpDown2 = { UP, DOWN };
		jcboxUpDownLeft = new JComboBox<String>(UpDown2);
	}

	public void goodProperties() {
		this.setTitle("Contacts Manager HW2");
		this.setSize(FRAME_WEIGHT, FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}

	@Override
	public void registerListener(ILiranKatzListener iLiranKatzListener) {
		listeners.add(iLiranKatzListener);

	}

}
