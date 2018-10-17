package LIRAN_KATZ_HW4;

import javax.swing.JTextField;

public interface ILiranKatzListener {

	public void informTheModelExport(String fileExtension, String FirstName, String LastName, String PhoneNumber);

	public void informTheModelCreateOrUpdate(String command, String FirstName, String LastName, String PhoneNumber);

	public void informTheView(String command, String[] dataContact);

	public void informTheModelNPFL(String command);

	public void infromTheModelSort(String sortType, String sortField, String ascOrDesc);

	public void informTheModelLoadFile(String nameFile, String format);

	public void setModel(IContactManagerModel model);

	public void addView(IContactManagerFrameView view);

	public void registerIfPossible(Object o);

}
