package LIRAN_KATZ_HW4;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

// Liran Katz Id:204483226

public class App extends Application   {

	public static void main(String[] args) {
		launch(args);}

	 // Override the start method in the Application class
	@Override
	public void start(Stage  primaryStage) throws Exception {
		ContactsManagerModel cm;
		try {
			cm = new ContactsManagerModel("contacts.dat");
			Controller controller= new Controller();
			controller.setModel(cm);
			ContactsManagerFrameView cmf = new ContactsManagerFrameView(cm);
			ContactsManagerFrameViewJFX cmfjx = new ContactsManagerFrameViewJFX(primaryStage); 
			controller.addView(cmfjx);
			controller.addView(cmf);
			
			
			cmf.init();
			cmfjx.init();
		} catch (FileNotFoundException e) {
			System.out.println("file not exist");
		} catch (IOException e) {
			System.out.println("There is a problem to read from this file");
		}
	}
		
	}

