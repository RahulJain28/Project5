package assignment5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.scene.layout.AnchorPane.*;

public class Main extends Application{
    static AnchorPane controller = new AnchorPane();
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

        Label critterT = new Label("Select Critter type: ");
        TextField enterCritterT = new TextField();
        controller.getChildren().addAll(critterT, enterCritterT);
        setLeftAnchor(critterT, 8.0);
        setTopAnchor(critterT, 8.0);
        setLeftAnchor(enterCritterT, 140.0);
        setTopAnchor(critterT, 8.0);

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
        setTopAnchor(make, 85.0);

        Button oneTimeStep = new Button("Perform one time step");
        controller.getChildren().add(oneTimeStep);
        setLeftAnchor(oneTimeStep, 8.0);
        setTopAnchor(oneTimeStep, 130.0);

        Label steps = new Label("Enter number of time steps: ");
        TextField enterSteps = new TextField();
        Button processSteps = new Button("Perform time steps");
        HBox manySteps = new HBox();
        manySteps.setSpacing(5.0);
        manySteps.getChildren().addAll(steps, enterSteps, processSteps);
        controller.getChildren().add(manySteps);
        setLeftAnchor(manySteps, 8.0);
        setTopAnchor(manySteps, 170.0);

        Label chooseStats = new Label("Invoke runStats for: ");
        TextField enterCritterStats = new TextField();
        Button processStats = new Button("Display");
        HBox stats = new HBox();
        stats.setSpacing(5.0);
        stats.getChildren().addAll(chooseStats, enterCritterStats, processStats);
        controller.getChildren().add(stats);
        setLeftAnchor(stats, 8.0);
        setTopAnchor(stats, 210.0);

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
        primaryStage.show();
    }


}
