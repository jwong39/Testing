
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientFx extends Application{
	HashMap<String, Scene> sceneMap;
	BorderPane startPane;
	Scene startScene;
	TextField enterPort, enterIp, enterFingers, enterTotal;
	VBox textfieldBox, clientBox;
	Button beginBtn, sendBtn,
	noFing, oneFing, twoFing, threFing, fouFing, fivFing;
	Client clientConnection;
	ListView<String> listItems;
	ImageView nof, onef, twof, threef, fourf,fivef;
	int numFingers;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		// Sets the title
		primaryStage.setTitle("Client GUI");
		int h = 80;
		int w = 80;
		
		// Initialize TextFields
		enterPort = new TextField("Port # (i.e 5555)");
		enterIp = new TextField("IP Address (127.0.0.1)");
		enterFingers = new TextField("0-5");
		enterTotal = new TextField("0-10");
		
		// Initialize ListView
		listItems = new ListView<String>();
		
		// Initialize begin button
		this.beginBtn = new Button("Start");
		this.beginBtn.setOnAction(e-> {
			// Convert textfields
			int transEnterPort = Integer.parseInt(enterPort.getText());
			String transEnterIp = enterIp.getText();
			primaryStage.setScene(sceneMap.get("client"));
			primaryStage.setTitle("Please enter in your guesses!");
			clientConnection = new Client(data->{
				Platform.runLater(()->{listItems.getItems().add(data.toString());
				});
			});
				clientConnection.inputAddress = transEnterIp;
				clientConnection.inputPort = transEnterPort;
				clientConnection.start();		
			
		});
		
		// Initialize send button
		sendBtn = new Button("Send");
		sendBtn.setOnAction(e->{
			// Check if integer first before sending
			if(isInteger(enterTotal.getText())
				&& Integer.parseInt(enterTotal.getText()) <= 10
				&& Integer.parseInt(enterTotal.getText()) >= 0) {
				clientConnection.send(numFingers, enterTotal.getText());
				enterFingers.clear();
				enterTotal.clear();	
			}
			else {
				primaryStage.setTitle("Oops! You entered in the wrong input!");
			}
		});
		
		// Create Finger Buttons
		Image pic = new Image("noFinger.jpg"); // Initialize image
		nof = new ImageView(pic); // Initialize imagview nof with image
		nof.setFitHeight(h); // Set width and height
		nof.setFitWidth(w);
		nof.setPreserveRatio(true); // IDK
		noFing = new Button(); // Initialize button
		noFing.setGraphic(nof); // Make button into an image
		noFing.setOnAction(e->{numFingers = 0;}); // Add action to button
				// Rinse and repeat
		Image pic1 = new Image("oneFinger.jpg");
		onef = new ImageView(pic1);
		onef.setFitHeight(h);
		onef.setFitWidth(w);
		onef.setPreserveRatio(true);
		oneFing = new Button();
		oneFing.setGraphic(onef);
		oneFing.setOnAction(e->{numFingers = 1;});
		
		Image pic2 = new Image("twoFinger.jpg");
		twof = new ImageView(pic2);
		twof.setFitHeight(h);
		twof.setFitWidth(w);
		twof.setPreserveRatio(true);
		twoFing = new Button();
		twoFing.setGraphic(twof);
		twoFing.setOnAction(e->{numFingers = 2;});
		
		Image pic3 = new Image("threeFinger.jpg");
		threef = new ImageView(pic3);
		threef.setFitHeight(h);
		threef.setFitWidth(w);
		threef.setPreserveRatio(true);
		threFing = new Button();
		threFing.setGraphic(threef);
		threFing.setOnAction(e->{numFingers = 3;});
		
		Image pic4 = new Image("fourFinger.jpg");
		fourf = new ImageView(pic4);
		fourf.setFitHeight(h);
		fourf.setFitWidth(w);
		fourf.setPreserveRatio(true);
		fouFing = new Button();
		fouFing.setGraphic(fourf);
		fouFing.setOnAction(e->{numFingers = 4;});
		
		Image pic5 = new Image("fiveFinger.jpg");
		fivef = new ImageView(pic5);
		fivef.setFitHeight(h);
		fivef.setFitWidth(w);
		fivef.setPreserveRatio(true);
		fivFing = new Button();
		fivFing.setGraphic(fivef);
		fivFing.setOnAction(e->{numFingers = 5;});
		
		// Initialize the pane layout
		textfieldBox = new VBox(10,enterPort, enterIp, beginBtn);
		startPane = new BorderPane();
		startPane.setCenter(textfieldBox);
		
		// Initialize the scene
		startScene = new Scene(startPane, 300,200);
		
		// Initialize HashMap for different scenes
		sceneMap = new HashMap<String, Scene>();
		
		// Adds a scene to the hashmap with a key called server
		sceneMap.put("client",  createClientGui());
		
		// Close command?
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		// Show stage
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	
	// This is how I add new scenes
	public Scene createClientGui() {	
		HBox fingerBox = new HBox(noFing,oneFing, twoFing, threFing, fouFing, fivFing);
		clientBox = new VBox(10,fingerBox,enterTotal,sendBtn,listItems);
		clientBox.setStyle("-fx-background-color: blue");
		return new Scene(clientBox, 400, 400);
		
	}
	
	// Check if string is integer
	static boolean isInteger(String s) {
		
		boolean isValidInteger = false;
		try
		{
			Integer.parseInt(s);
			isValidInteger = true;
		}
		catch (NumberFormatException ex)
		{
			
		}
		return isValidInteger;	
	}
}
