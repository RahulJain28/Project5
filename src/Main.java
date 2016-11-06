package assignment5;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application{
    static GridPane controller = new GridPane();
    static Canvas canvas = new Canvas();
	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {
        try {
            createController(primaryStage);

        }
        catch (Exception e) {
            System.out.println("Oops");
        }

    }

    public void createController(Stage primaryStage) {
        primaryStage.setTitle("Controller");
        controller.setAlignment(Pos.CENTER);


        /*Label critterT = new Label("Select Critter type: ");
        TextField enterCritterT = new TextField();
        HBox critterChoice = new HBox();
        critterChoice.getChildren().addAll(critterT, enterCritterT);
        controller.getChildren().add(critterChoice);
        setLeftAnchor(critterChoice, 8.0);
        setTopAnchor(critterChoice, 8.0);

        Label critterN = new Label("Enter number of critters: ");
        TextField enterCritterN = new TextField();
        Label critterNWarning = new Label("Please enter a number");
        critterNWarning.setVisible(false);
        critterNWarning.setTextFill(Color.RED);
        HBox critterNum = new HBox();
        critterNum.getChildren().addAll(critterN, enterCritterN, critterNWarning);
        controller.getChildren().add(critterNum);
        setLeftAnchor(critterNum, 8.0);
        setTopAnchor(critterNum, 50.0);

        Button make = new Button("Make Critters");
        controller.getChildren().add(make);
        setLeftAnchor(make, 8.0);
        setTopAnchor(make, 80.0);

        Button oneTimeStep = new Button("Perform one time step");
        controller.getChildren().add(oneTimeStep);
        setLeftAnchor(oneTimeStep, 8.0);
        setTopAnchor(oneTimeStep, 130.0);

        Label numTimeSteps = new Label("Enter number of time steps: ");
        TextField enterNumTimeSteps = new TextField();
        Button multipleTimeSteps = new Button("Perform multiple time steps");
        HBox timeSteps = new HBox();
        timeSteps.getChildren().addAll(numTimeSteps, enterNumTimeSteps, multipleTimeSteps);
        controller.getChildren().add(timeSteps);
        setLeftAnchor(timeSteps, 8.0);
        setTopAnchor(timeSteps, 170.0);

        Button quit = new Button("Quit");
        quit.setPrefWidth(80);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        controller.getChildren().add(quit);
        setTopAnchor(quit, 8.0);
        setRightAnchor(quit, 8.0);

        Scene scene = new Scene(controller, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }


}
