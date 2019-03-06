package utility;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator 
{
	// Define xml files name here to load 
	public static final String Welcome_SCENE = "WelcomeScene.fxml";
	

	public static Stage mainStage;

	public static void setStage(Stage stage) 
	{
		mainStage = stage;
	}

	// Static method to handle loading new scenes 
	public static void loadScene(String title, String sceneFXML) 
	{

		try 
		{
			mainStage.setTitle(title);
			Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setScene(scene);
			mainStage.show();
		} 
		catch (IOException e) 
		{
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}