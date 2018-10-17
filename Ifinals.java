package LIRAN_KATZ_HW4;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface Ifinals {

	public final static int FRAME_HEIGHT = 700;
	public final static int FRAME_WEIGHT = 500;
	public final static int SIZE_TEXT_FIELD = 10;
	public final static int SIZE_FRAME_HEIGHT_BUTTON = 1;
	public final static int SIZE_FRAME_FRAME_WEIGHT_BUTTON = 1;
	public final static int NAME_SIZE = 9;
	public final static int PHONE_SIZE = 10;
	public final static int ID_SIZE = 9;
	public final static double H_GAP_PANE_NORTH = 5.5;
	public final static double V_GAP_PANE_NORTH = 5.5;
	public final static double H_GAP_PANE_CENTER = 40;
	public final static double V_GAP_PANE_CENTER = 10;
	public final static double SIZE_FONT = 15;
	public final static double SPACE_FOR_HB0X = 15;
	public final static double SPACE_FOR_VB0X = 5;
	public final static String FILENAME…_EXTENSION1 = "txt";
	public final static String FILENAME…_EXTENSION2 = "obj.dat";
	public final static String FILENAME…_EXTENSION3 = "byte.dat";
	public final static long FIRST_POSITION = 0;
	public final static int RECORD_SIZE = (ID_SIZE + (2 * NAME_SIZE) + PHONE_SIZE) * 2; // Bits
																						// to
																						// Char
	public final static String TYPE_OF_SORT_1 = "sort-field";
	public final static String TYPE_OF_SORT_2 = "sort-count";
	public final static String TYPE_OF_SORT_3 = "reverse";
	public final static String TYPE_OF_FIELDS_1 = "FIRST……_NAME_FIELD";
	public final static String TYPE_OF_FIELDS_2 = "LAST……_NAME_FIELD";
	public final static String TYPE_OF_FIELDS_3 = "PHONE_NUMBER_FIELD";
	public final static String UP = "asc";
	public final static String DOWN = "desc";
	public final static int SPEED = 1000;
	public final static String FIRST_NAME_LABEL = "First name:";
	public final static String LAST_NAME_LABEL = "Last name:";
	public final static String PHONE_NUMBER_LABEL = "Phone number:";
	public final static String FILE_PATH_LABEL = "file path:";
	public final static String FILE_EMPTY = "File empty";
	public final int NUMBER_OF_DIGIT_IN_ID = 100000000;
	public final int PADDING_FOR_LABELS = 30;
	/** Buttons **/
	public final static String CREATE_BUTTON = "Create";
	public final static String UPDATE_BUTTON = "Upadate";
	public final static String LEFT_BUTTON = " < ";
	public final static String FIRST_BUTTON = " First ";
	public final static String EDIT_CONTACT_BUTTON = "Edit Contact ";
	public final static String RIGHT_BUTTON = " > ";
	public final static String LAST_BUTTON = " Last ";
	public final static String EXPORT_BUTTON = " Export ";
	public final static String LOAD_FILE_BUTTON = " load file ";
	public final static String SHOW_BUTTON = "   SHOW  ";
	public final static String SORT_BUTTON = " SORT ";
	/** COMMAND **/
	public final static String NEXT_COMMAND = "NEXT";
	public final static String PREVIOUS_COMMAND = "PREVIOUS";
	public final static String FIRST_COMMAND = "FIRST";
	public final static String LAST_COMMAND = "LAST";
	public final static String CREATE_COMMAND = "CREATE";
	public final static String UPDATE_COMMAND = "UPDATE";
	public final static String EXPORT_COMMAND = "EXPORT";
	public final static String SORT_COMMAND = "SORT";
	public final static String EDIT_COMMAND = "EDIT";
	public final static String LOAD_FILE_COMMAND = "LOAD";
	public final static String MESSAGE_EROR_THE_FILE_IS_EMPTY = "The file is empty, Please add at least one contact first ";

	public enum TextColors {
		BLACK_COLOR(Color.BLACK), RED_COLOR(Color.RED), GREEN_COLOR(Color.GREEN), BLUE_COLOR(Color.BLUE);

		private Color stateColor;

		TextColors(Color color) {
			this.stateColor = color;
		}

		Color getTheColorEnum() {
			return this.stateColor;
		}

	}

}
