package LIRAN_KATZ_HW4;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.sun.corba.se.impl.orbutil.graph.Node;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ContactsManagerFrameViewJFX implements Ifinals, IContactManagerFrameView, ILiranKatzRegistrable {

	private Stage primaryStage;

	private BorderPane primaryPane, southPane;

	private HBox topPaneDown, centerPaneDown, bottomPaneDown;

	private GridPane northPane, centerPane;

	private Scene primaryScene;

	private Label firstNameLabel, lastNameLabel, phoneNumberLabel, jtfFPath;

	private ComboBox<String> jcbox, jcboxfields, jcboxUpDownRight, jcboxUpDownLeft, jcboxReOrganize;

	private Button jbtExport, jbtLoad, jbtSort, jbtShow, jbtUpdate, jbtCreate, jbtLeft, jbtFirst, jbtEdit, jbtRight,
			jbtLast;

	private TextField jtfFN, jtfLN, jtfPN;

	private ArrayList<ILiranKatzListener> listeners = new ArrayList<ILiranKatzListener>();

	private boolean firstTime = true; // when the timer work first time

	private Timeline animation, color;

	private TextColors textColor = TextColors.BLACK_COLOR; // the default color
															// for the start

	public ContactsManagerFrameViewJFX(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryPane = new BorderPane(); // the primary pane

		EventHandler eventHandlerTime = e -> setColorsToLabels();

		color = new Timeline(new KeyFrame(Duration.millis(SPEED), eventHandlerTime));
		color.setCycleCount(Timeline.INDEFINITE);
		color.play();

		/** north pane **/
		northPane = new GridPane();
		paneNorth();
		primaryPane.setTop(northPane);

		/** center pane **/
		centerPane = new GridPane();
		paneCenter();
		primaryPane.setCenter(centerPane);

		/** south pane **/
		southPane = new BorderPane();
		paneSouth();
		primaryPane.setBottom(southPane);

		primaryScene = new Scene(primaryPane, FRAME_WEIGHT, FRAME_HEIGHT);

		/** At the beginning: Display the first record if exists **/
		informTheControllerNPFL("FIRST");

		jbtUpdate.setDisable(true); // can't click on update button

		setColorsToLabels();

		goodProperties();
	}

	/** the north panel **/
	public void paneNorth() {
		northPane.setAlignment(Pos.CENTER);
		northPane.setHgap(H_GAP_PANE_NORTH);
		northPane.setVgap(V_GAP_PANE_NORTH);
		northPane.add(new Label("First Name:"), 0, 0);
		jtfFN = new TextField();
		northPane.add(jtfFN, 1, 0);
		northPane.add(new Label("Last Name:"), 0, 1);
		jtfLN = new TextField();
		northPane.add(jtfLN, 1, 1);
		northPane.add(new Label("Last Name:"), 0, 2);
		jtfPN = new TextField();
		northPane.add(jtfPN, 1, 2);
		jbtUpdate = new Button(UPDATE_BUTTON);
		jbtCreate = new Button(CREATE_BUTTON);
		northPane.add(jbtCreate, 0, 3);
		northPane.add(jbtUpdate, 1, 3);
		northPane
				.setPadding(new Insets(PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS));
		jbtUpdate.setOnAction(e -> update());
		jbtCreate.setOnAction(e -> {
			if (cheackIfTheFieldsEmpty()) {// check if the fields are empty
				System.out.println("you need to fill all the fields");
			} else {
				Create();
			}
		});
	}

	/** the center panel **/
	public void paneCenter() {
		centerPane.setAlignment(Pos.CENTER);
		centerPane
				.setPadding(new Insets(PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS));
		centerPane.setHgap(H_GAP_PANE_CENTER);
		centerPane.setVgap(V_GAP_PANE_CENTER);
		jbtLeft = new Button(LEFT_BUTTON);
		jbtFirst = new Button(FIRST_BUTTON);
		jbtEdit = new Button(EDIT_CONTACT_BUTTON);
		jbtRight = new Button(RIGHT_BUTTON);
		jbtLast = new Button(LAST_BUTTON);
		centerPane.add(jbtLeft, 0, 0);
		centerPane.add(jbtFirst, 0, 1);
		centerPane.add(jbtEdit, 1, 4);
		centerPane.add(jbtRight, 4, 0);
		centerPane.add(jbtLast, 4, 1);

		centerPane.add(new Label("First Name:"), 1, 0);
		centerPane.add(new Label("Last Name:"), 1, 1);
		centerPane.add(new Label("Phone Number:"), 1, 2);

		firstNameLabel = new Label("File Empty");
		lastNameLabel = new Label("File Empty");
		phoneNumberLabel = new Label("File Empty");
		setFont();
		centerPane.add(firstNameLabel, 2, 0);
		centerPane.add(lastNameLabel, 2, 1);
		centerPane.add(phoneNumberLabel, 2, 2);
		jbtLeft.setOnAction(e -> previousContact());
		jbtFirst.setOnAction(e -> first());
		jbtEdit.setOnAction(e -> edit());
		jbtRight.setOnAction(e -> nextContact());
		jbtLast.setOnAction(e -> getTheLastContact());

	}

	/** the south panel **/
	public void paneSouth() {
		setComboBox();
		jbtExport = new Button(EXPORT_BUTTON);
		jbtLoad = new Button(LOAD_FILE_BUTTON);
		jbtSort = new Button(SORT_BUTTON);
		jbtShow = new Button(SHOW_BUTTON);
		topPaneDown = new HBox(SPACE_FOR_HB0X);
		topPaneDown();

		centerPaneDown = new HBox(SPACE_FOR_HB0X);
		centerPaneDown();
		bottomPaneDown = new HBox(SPACE_FOR_HB0X);
		bottomPaneDown();
		jbtExport.setOnAction(e -> export());
		jbtLoad.setOnAction(e -> loadFile());
		jbtSort.setOnAction(e -> sort());
		EventHandler eventHandler = e -> {
			if (("" + jcboxUpDownLeft.getValue()).equals(UP) && firstTime == true) {
				first();
				firstTime = false;
			} else if (("" + jcboxUpDownLeft.getValue()).equals(UP) && firstTime != true) {
				nextContact();
			} else if (("" + jcboxUpDownLeft.getValue()).equals(DOWN) && firstTime == true) {
				getTheLastContact();
				firstTime = false;
			} else if ((("" + jcboxUpDownLeft.getValue()).equals(DOWN) && firstTime != true)) {
				previousContact();
			}
		};
		animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
		jbtShow.setOnAction(e -> {
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.play(); // Start animation);
		});
	}

	public void topPaneDown() {

		topPaneDown.getChildren().add(jcbox);
		topPaneDown.getChildren().add(jbtExport);

		VBox topRightPane = new VBox(SPACE_FOR_VB0X);

		TextField topRightPaneText = new TextField();
		jtfFPath = new Label("file path");
		topRightPane.getChildren().add(jtfFPath);
		topRightPane.getChildren().add(topRightPaneText);
		topRightPane.getChildren().add(jbtLoad);
		topPaneDown.getChildren().add(topRightPane);
		topPaneDown
				.setPadding(new Insets(PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS));
		southPane.setTop(topPaneDown);
	}

	public void centerPaneDown() {

		centerPaneDown.getChildren().add(jcboxReOrganize);
		centerPaneDown.getChildren().add(jcboxfields);
		centerPaneDown.getChildren().add(jcboxUpDownRight);
		centerPaneDown.getChildren().add(jbtSort);
		centerPaneDown
				.setPadding(new Insets(PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS));
		southPane.setCenter(centerPaneDown);
	}

	public void bottomPaneDown() {
		bottomPaneDown.getChildren().add(jcboxUpDownLeft);
		bottomPaneDown.getChildren().add(jbtShow);
		bottomPaneDown
				.setPadding(new Insets(PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS, PADDING_FOR_LABELS));
		southPane.setBottom(bottomPaneDown);
	}

	public void setComboBox() {

		String[] typeOfFile = { FILENAMEÉ_EXTENSION1, FILENAMEÉ_EXTENSION2, FILENAMEÉ_EXTENSION3 };
		jcbox = new ComboBox<>();
		ObservableList<String> items = FXCollections.observableArrayList(typeOfFile);
		jcbox.setValue(FILENAMEÉ_EXTENSION1);
		jcbox.getItems().addAll(items);
		String[] state = { TYPE_OF_SORT_1, TYPE_OF_SORT_2, TYPE_OF_SORT_3 };
		jcboxReOrganize = new ComboBox<>();
		ObservableList<String> items1 = FXCollections.observableArrayList(state);
		jcboxReOrganize.setValue(TYPE_OF_SORT_1);
		jcboxReOrganize.getItems().addAll(items1);
		String[] fields = { TYPE_OF_FIELDS_1, TYPE_OF_FIELDS_2, TYPE_OF_FIELDS_3 };
		jcboxfields = new ComboBox<>();
		ObservableList<String> items2 = FXCollections.observableArrayList(fields);
		jcboxfields.setValue(TYPE_OF_FIELDS_1);
		jcboxfields.getItems().addAll(items2);
		String[] UpDown1 = { UP, DOWN };
		jcboxUpDownRight = new ComboBox<>();
		ObservableList<String> items3 = FXCollections.observableArrayList(UpDown1);
		jcboxUpDownRight.setValue(UP);
		jcboxUpDownRight.getItems().addAll(items3);
		String[] UpDown2 = { UP, DOWN };
		jcboxUpDownLeft = new ComboBox<>();
		ObservableList<String> items4 = FXCollections.observableArrayList(UpDown2);
		jcboxUpDownLeft.setValue(UP);
		jcboxUpDownLeft.getItems().addAll(items4);
	}

	/** sort the contact on the file **/
	public void sort() {

		informTheControllerSort("" + jcboxReOrganize.getValue(), "" + jcboxfields.getValue(),
				"" + jcboxUpDownRight.getValue());
		informTheControllerNPFL(FIRST_COMMAND); // display the first contact
												// after the sort

	}

	/** load file action **/
	public void loadFile() {
		String nameFile = jtfFPath.getText();
		informTheControllerLoadFile(nameFile, " " + jcbox.getValue());
	}

	/** export action **/
	public void export() {
		informTheControllerExport(firstNameLabel.getText(), lastNameLabel.getText(), phoneNumberLabel.getText());

	}

	/** present the previous contact on the file **/
	public void previousContact() {
		informTheControllerNPFL(PREVIOUS_COMMAND);

	}

	/** if timer is on, and We reach to the end of the next or previous **/
	public void ifTimerIsRunning() {
		animation.pause();
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
		jbtUpdate.setDisable(false);
		jtfFN.setText(firstNameLabel.getText());
		jtfLN.setText(lastNameLabel.getText());
		jtfPN.setText(phoneNumberLabel.getText());

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
		jbtUpdate.setDisable(true);
	}

	/** create action, create new contact **/
	public void Create() {

		writeContact();
		removeField(); // clean the field after we add people
	}

	/** set which font, size do you like **/
	public void setFont() {
		firstNameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, SIZE_FONT));
		lastNameLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, SIZE_FONT));
		phoneNumberLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, SIZE_FONT));
	}

	/** present the first contact **/
	public void first() {
		informTheControllerNPFL(FIRST_COMMAND);

	}

	public void update() {
		informTheControllerCreateOrUpdate(UPDATE_COMMAND, jtfFN.getText(), jtfLN.getText(), jtfPN.getText());

	}

	/**
	 * this method takes the information from the controller, and present the
	 * date on the view
	 **/
	public void presentTheDateOnTheFrame(String command, String[] dataContact) {
		switch (command) {
		case NEXT_COMMAND:
			if (dataContact == null) {
				if (animation.getStatus() == Animation.Status.RUNNING) // if the
																		// timer
																		// is on
					ifTimerIsRunning();

			} else {
				System.out.println("Next command susceed");
				this.textColor = textColor.GREEN_COLOR;
				setTextToTheLabels(dataContact);
			}
			break;

		case PREVIOUS_COMMAND:
			if (dataContact == null) {
				if (animation.getStatus() == Animation.Status.RUNNING) // if the
																		// timer
																		// is on
					ifTimerIsRunning();
			} else {
				System.out.println("Previous command susceed");
				this.textColor = textColor.RED_COLOR;
				setTextToTheLabels(dataContact);
			}
			break;

		case FIRST_COMMAND:
			if (dataContact == null)
				System.out.println("you are already on the first contact");
			else
				System.out.println("First command susceed");
			this.textColor = textColor.RED_COLOR;
			setTextToTheLabels(dataContact);
			break;

		case LAST_COMMAND:
			if (dataContact == null) {
				System.out.println(" you are already on the last contact");
				return;
			} else
				System.out.println("Last command susceed");
			this.textColor = textColor.GREEN_COLOR;
			setTextToTheLabels(dataContact);
			break;
		case UPDATE_COMMAND:
			System.out.println("Update command susceed");
			this.textColor = textColor.BLUE_COLOR;
			setTextToTheLabels(dataContact);
			break;

		case CREATE_COMMAND:
			System.out.println("Create command susceed");
			this.textColor = textColor.BLUE_COLOR;
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
		for (ILiranKatzListener listener : listeners) {
			listener.informTheModelExport("" + jcbox.getValue(), FirstName, LastName, PhoneNumber);
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

	@Override
	public void registerListener(ILiranKatzListener iLiranKatzListener) {
		listeners.add(iLiranKatzListener);

	}

	/** set the colors to the text in the labels **/
	private void setColorsToLabels() {
		firstNameLabel.setTextFill(textColor.getTheColorEnum());
		lastNameLabel.setTextFill(textColor.getTheColorEnum());
		phoneNumberLabel.setTextFill(textColor.getTheColorEnum());

	}

	/** get a string array and put text to the specific labels **/
	@Override
	public void setTextToTheLabels(String[] str) {
		if (str != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					firstNameLabel.setText(str[1]);
					lastNameLabel.setText(str[2]);
					phoneNumberLabel.setText(str[3]);
					firstNameLabel.setContentDisplay(ContentDisplay.CENTER);
					lastNameLabel.setContentDisplay(ContentDisplay.CENTER);
					phoneNumberLabel.setContentDisplay(ContentDisplay.CENTER);
				}

			});
		} else
			return;
	}

	public void goodProperties() {
		primaryStage.setTitle("Contacts Manager JFX"); // Set the stage title
		primaryStage.setScene(primaryScene); // Place the scene in the stage
		primaryStage.setAlwaysOnTop(true);
	}

	public void init() {
		informTheControllerNPFL("FIRST");
		this.textColor = TextColors.BLACK_COLOR;
		primaryStage.show(); // Display the stage
	}

}
