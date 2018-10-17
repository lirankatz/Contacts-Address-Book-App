package LIRAN_KATZ_HW4;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

public class Controller implements ILiranKatzListener, Ifinals {

	private ArrayList<IContactManagerFrameView> views = new ArrayList<IContactManagerFrameView>();
	private IContactManagerModel model;
	private int count = 0;

	@Override
	public void setModel(IContactManagerModel model) {
		this.model = model;
		registerIfPossible(this.model);
	}

	@Override
	public void addView(IContactManagerFrameView view) {
		this.views.add(view);
		registerIfPossible(view);
	}

	@Override
	public void registerIfPossible(Object o) {
		if (o instanceof ILiranKatzRegistrable) {
			ILiranKatzRegistrable registrable = (ILiranKatzRegistrable) o;
			registrable.registerListener(this);

		}
	}

	/** transfer the load file commands the to model **/
	@Override
	public void informTheModelLoadFile(String nameFile, String format) {
		this.model.loadFile(nameFile, format);
	}

	/** transfer the export commands the to model **/
	@Override
	public void informTheModelExport(String fileExtension, String FirstName, String LastName, String PhoneNumber) {
		this.model.saveContact(fileExtension, FirstName, LastName, PhoneNumber);
	}

	/** transfer the commands(Create,Update) to the model **/
	@Override
	public void informTheModelCreateOrUpdate(String command, String FirstName, String LastName, String PhoneNumber) {
		switch (command) {
		case CREATE_COMMAND:
			this.model.createContact(FirstName, LastName, PhoneNumber);
			break;
		case UPDATE_COMMAND:
			this.model.update(FirstName, LastName, PhoneNumber);
			break;
		}
	}

	/** transfer the sort commands the to model **/
	@Override
	public void infromTheModelSort(String sortType, String sortField, String ascOrDesc) {
		this.model.sortBy(sortType, sortField, ascOrDesc);
	}

	/** transfer the commands(Next,Previous,First,Last) to the model **/
	@Override
	public void informTheModelNPFL(String command) {
		switch (command) {
		case NEXT_COMMAND:
			this.model.getTheNextContact();
			break;
		case PREVIOUS_COMMAND:
			this.model.getThePreviousContact();
			break;
		case FIRST_COMMAND:
			this.model.getTheFirstContact();
			break;
		case LAST_COMMAND:
			this.model.getTheLastContact();
			break;

		}

	}

	/** transfer details and update the view **/
	@Override
	public void informTheView(String command, String[] dataContact) {
		for (IContactManagerFrameView view : views) {
			view.presentTheDateOnTheFrame(command, dataContact);
		}
	}

}
