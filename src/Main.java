package assignment5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import static javafx.scene.layout.AnchorPane.*;


public class Main extends Application{
    static AnchorPane controller = new AnchorPane();
    static Canvas canvas = new Canvas();
    static ArrayList<String> listOfJavaFiles = new ArrayList<>();
    static ArrayList<String> listOfPossibleClasses = new ArrayList<>();
    static String packageName = Critter.class.getPackage().toString().split(" ")[1];
	public static void main(String[] args) {
		launch(args);
	}

    public void findClasses() throws InvalidCritterException {
        File folder = new File("src/assignment5");
        File[] listOfFiles = folder.listFiles();
        for (int i =0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().endsWith(".java")) {
                listOfJavaFiles.add(listOfFiles[i].getName());
            }
        }
        for (String s: listOfJavaFiles) {
            listOfPossibleClasses.add(s);
        }

        for (int i=0; i < listOfPossibleClasses.size(); i++) {
            try {
                Class myClass = Class.forName(packageName + "." + listOfPossibleClasses.get(i));
                Critter c = (Critter) myClass.newInstance();
            }
            catch(Exception e){
                listOfPossibleClasses.remove(listOfPossibleClasses.get(i));
                printStackTrace();
                //i--;
            }

        }
        System.out.println(Main.listOfPossibleClasses);
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            createController(primaryStage);

        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void createController(Stage primaryStage) {
        primaryStage.setTitle("Controller");
        try {
            findClasses();
        }
        catch (InvalidCritterException e) {

        }
        Label seed = new Label("Set seed to: ");
        TextField getSeed = new TextField();
        Button setSeed = new Button("Set Seed");
        Label seedWarning = new Label("Please enter number");
        seedWarning.setVisible(false);
        seedWarning.setTextFill(Color.RED);
        HBox seedStuff = new HBox();
        seedStuff.getChildren().addAll(seed, getSeed, setSeed, seedWarning);
        seedStuff.setSpacing(5.0);
        setLeftAnchor(seedStuff, 8.0);
        setTopAnchor(seedStuff, 8.0);
        controller.getChildren().add(seedStuff);
        setSeed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = getSeed.getText();
                if (text.equals("")) {
                seedWarning.setVisible(true);
                }
                else {
                    long num = (long) Integer.parseInt(text);
                    Critter.setSeed(num);
                }
            }
        });



        Label critterT = new Label("Select Critter type: ");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                      "Option 1", "Option 2", "Option 3"
                );
        ComboBox comboBox = new ComboBox(options);
        Label critterTypeWarning = new Label("Please select critter");
        critterTypeWarning.setVisible(false);
        critterTypeWarning.setTextFill(Color.RED);
        controller.getChildren().addAll(critterT, comboBox, critterTypeWarning);
        setLeftAnchor(critterT, 8.0);
        setTopAnchor(critterT, 50.0);
        setLeftAnchor(comboBox, 140.0);
        setTopAnchor(comboBox, 50.0);
        setTopAnchor(critterTypeWarning, 50.0);
        setLeftAnchor(critterTypeWarning, 290.0);


        Label critterN = new Label("Enter number of critters: ");
        TextField enterCritterN = new TextField();
        Label critterNWarning = new Label("Please enter a number");
        critterNWarning.setVisible(false);
        critterNWarning.setTextFill(Color.RED);
        HBox critterNum = new HBox();
        critterNum.getChildren().addAll(critterN, enterCritterN, critterNWarning);
        controller.getChildren().add(critterNum);
        setLeftAnchor(critterNum, 8.0);
        setTopAnchor(critterNum, 85.0);

        Button make = new Button("Make Critters");
        controller.getChildren().add(make);
        setLeftAnchor(make, 8.0);
        setTopAnchor(make, 120.0);
        make.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*First check if input is valid */
                boolean valid = true;
                String numText = enterCritterN.getText();
                Object type = comboBox.getValue();
                boolean isNumSet=!numText.equals("");
                if (!isNumSet){
                    valid = false;
                    critterNWarning.setVisible(true);
                }
                boolean isTypeSet = !(type==null);
                if (!isTypeSet) {
                    valid = false;
                    critterTypeWarning.setVisible(true);
                }

                if (valid) {
                    try {
                        int num = Integer.parseInt(numText);
                        for (int i = 0; i < num; i++) {
                            Critter.makeCritter(type.toString());
                        }
                    }
                    catch (InvalidCritterException e) {
                        //TODO: What should we do here?
                    }
                }


            }
        });

        Button oneTimeStep = new Button("Perform one time step");
        controller.getChildren().add(oneTimeStep);
        setLeftAnchor(oneTimeStep, 8.0);
        setTopAnchor(oneTimeStep, 160.0);
        oneTimeStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Critter.worldTimeStep();
            }
        });

        Label steps = new Label("Enter number of time steps: ");
        TextField enterSteps = new TextField();
        Label timeStepWarning = new Label("Please enter a number");
        timeStepWarning.setVisible(false);
        timeStepWarning.setTextFill(Color.RED);
        HBox manySteps = new HBox();
        manySteps.setSpacing(5.0);
        manySteps.getChildren().addAll(steps, enterSteps, timeStepWarning);
        controller.getChildren().add(manySteps);
        setLeftAnchor(manySteps, 8.0);
        setTopAnchor(manySteps, 200.0);

        Button processSteps = new Button("Perform time steps");
        controller.getChildren().add(processSteps);
        setLeftAnchor(processSteps, 8.0);
        setTopAnchor(processSteps, 230.0);
        processSteps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String numText = enterSteps.getText();
                boolean valid = true;
                boolean isSet = !numText.equals("");
                if (!isSet) {
                    valid = false;
                    timeStepWarning.setVisible(true);
                }
                if (valid) {
                    int num = Integer.parseInt(numText);
                    for (int i = 0; i < num; i++) {
                        Critter.worldTimeStep();
                    }
                }
            }
        });

        Label chooseStats = new Label("Invoke runStats for: ");
        ObservableList<String> statsOptions =
                FXCollections.observableArrayList(
                        "Option 1", "Option 2", "Option 3"
                );
        ComboBox statsChoice = new ComboBox(statsOptions);
        Label statsWarning = new Label("Please select critter");
        statsWarning.setVisible(false);
        statsWarning.setTextFill(Color.RED);
        HBox stats = new HBox();
        stats.setSpacing(5.0);
        stats.getChildren().addAll(chooseStats, statsChoice, statsWarning);
        controller.getChildren().add(stats);
        setLeftAnchor(stats, 8.0);
        setTopAnchor(stats, 295.0);

        Button processStats = new Button("Display");
        controller.getChildren().add(processStats);
        setLeftAnchor(processStats, 8.0);
        setTopAnchor(processStats, 320.0);
        processStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Object s =statsChoice.getValue();
                if (s==null) {
                    statsWarning.setVisible(true);
                }
                else {
                    //TODO: actually call runstats function
                }
            }
        });

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



        Scene scene = new Scene(controller, 550, 380);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
