package application.utility;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Author: Jason Alaya
 */

/**
 * 
 * 	// Set stage only needs to be called once for the view navigator
 *    to be called from the main class or entrance 
		ViewNavigator.setStage(primaryStage);

       this how other classes call it load new scene 
       // first param the title of the scence 
       // second param  the scene to load 
		ViewNavigator.loadScene("Welcome to TravelAid", ViewNavigator.Welcome_SCENE);
	}
 *
 */
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
			Scene scene = new Scene((Parent)FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
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