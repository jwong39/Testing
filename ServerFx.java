import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerFx extends Application{
	HashMap<String, Scene> sceneMap;
	BorderPane startPane;
	Scene startScene;
	TextField enterPort, enterIp;
	VBox textfieldBox;
	Button beginBtn;
	Server serverConnection;
	ListView<String> listItems;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		// Sets the title
		primaryStage.setTitle("Server GUI");
		
		// Initialize TextFields
		enterPort = new TextField("Port # (i.e 5555)");
		
		// Initialize ListView
		listItems = new ListView<String>();
		
		// Initialize begin button
		this.beginBtn = new Button("Start");
		this.beginBtn.setOnAction(e->{ 
			// Convert textfields
			int transEnterPort = Integer.parseInt(enterPort.getText());
			primaryStage.setScene(sceneMap.get("server"));
			primaryStage.setTitle("This is the Server");
			serverConnection = new Server(data -> {
				Platform.runLater(()->{
					listItems.getItems().add(data.toString());
				});
			});
			serverConnection.inputPort = transEnterPort;
		});		
		
		// Initialize the pane layout
		textfieldBox = new VBox(10,enterPort, beginBtn);
		startPane = new BorderPane();
		startPane.setCenter(textfieldBox);
		
		// Initialize the scene
		startScene = new Scene(startPane, 300,200);
		
		// Initialize HashMap for different scenes
		sceneMap = new HashMap<String, Scene>();
		
		// Adds a scene to the hashmap with a key called server
		sceneMap.put("server",  createServerGui());
		
		
		
		// Close command?
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
		
	}
	
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 400, 400);
		
		
	}

}
