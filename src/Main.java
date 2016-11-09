package assignment5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import assignment5.Critter.CritterShape;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import static javafx.scene.layout.AnchorPane.*;


public class Main extends Application{
	static Stage displayStage = new Stage();
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
        System.out.println(Main.listOfJavaFiles);
        for (int i=0; i < listOfPossibleClasses.size(); i++) {
            try {
            	String file = listOfPossibleClasses.get(i);
            	file = file.substring(0, file.length()-5);
            	System.out.println(file);
                Class myClass = Class.forName(packageName + "." + file);
                Critter c = (Critter) myClass.newInstance();
            }
            catch(Exception e){
               listOfPossibleClasses.remove(i);
               i--;
               e.printStackTrace();
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
                    seedWarning.setVisible(false);
                }
            }
        });



        Label critterT = new Label("Select Critter type: ");
        ObservableList<String> options = FXCollections.observableArrayList(listOfPossibleClasses);
        ComboBox<String> comboBox = new ComboBox<>(options);
        Label critterTypeWarning = new Label("Please select critter");
        critterTypeWarning.setVisible(false);
        critterTypeWarning.setTextFill(Color.RED);
        controller.getChildren().addAll(critterT, comboBox, critterTypeWarning);
        setLeftAnchor(critterT, 8.0);
        setTopAnchor(critterT, 50.0);
        setLeftAnchor(comboBox, 140.0);
        setTopAnchor(comboBox, 50.0);
        setTopAnchor(critterTypeWarning, 50.0);
        setLeftAnchor(critterTypeWarning, 370.0);


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
                            Critter.makeCritter(type.toString().substring(0, type.toString().length()-5));
                        }
                        critterNWarning.setVisible(false);
                        critterTypeWarning.setVisible(false);
                        Critter.displayWorld();
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
                Critter.displayWorld();
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
                    timeStepWarning.setVisible(false);
                }
                Critter.displayWorld();
            }
        });

        Label chooseStats = new Label("Invoke runStats for: ");
        ObservableList<String> statsOptions = FXCollections.observableArrayList(listOfPossibleClasses);
        ComboBox<String> statsChoice = new ComboBox<>(statsOptions);
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



        Scene scene = new Scene(controller, 600, 380);
        primaryStage.setScene(scene);
        primaryStage.show();
//        try{
//        	Critter.makeCritter("Critter1");
//        	 Critter.displayWorld();
//            Critter.worldTimeStep();
//            Critter.worldTimeStep();
//            Critter.makeCritter("Algae");
//        	Critter.makeCritter("AlgaephobicCritter");
//        	 Critter.worldTimeStep();
//        	 Critter.worldTimeStep();
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//        Critter.displayWorld();
    }
    
    public static void View(List<Critter> population, List<Integer> x_coord, List<Integer> y_coord){
    	Group display = new Group();
    	int rows = Params.world_height;
		int columns = Params.world_width;
		int height = rows*20;
		int width = columns*20;
		List<Line> lines = new ArrayList<Line>();
		for(int i = 1; i<= columns; i++){
			int x = i*(width/columns);
			lines.add(new Line(x,0,x ,height));
		}
		for(int i = 1; i<= rows; i++){
			int y = i*(height/rows);
			lines.add(new Line(0,y,width ,y));
		}
		for(int i =0; i<population.size(); i++){
			CritterShape shape = population.get(i).viewShape();
			int x = x_coord.get(i)*(width/columns);
			int y = y_coord.get(i)*(height/rows); 
			if(shape == CritterShape.CIRCLE){
				Circle cir = new Circle(x+((width/columns)/2), y+((height/rows)/2), Math.min(width/columns, (height/rows)/2));
				cir.setFill(population.get(i).viewFillColor());
				cir.setStroke(population.get(i).viewOutlineColor());
				cir.setStrokeWidth(1.5);
				display.getChildren().addAll(cir);
			}
			if(shape == CritterShape.SQUARE){
				Rectangle rec = new Rectangle(x,y,width/columns,height/rows);
				rec.setFill(population.get(i).viewFillColor());
				rec.setStroke(population.get(i).viewOutlineColor());
				rec.setStrokeWidth(1.5);
				display.getChildren().addAll(rec);
			}
			if(shape == CritterShape.TRIANGLE){
				Polygon triangle = new Polygon();
				triangle.getPoints().addAll(new Double[]{
						(double)x, (double)y+(height/rows),
						(double)(x + (width/columns)), (double)y+(height/rows),
						(double)x + ((width/columns)/2), (double)y
				});
				triangle.setFill(population.get(i).viewFillColor());
				triangle.setStroke(population.get(i).viewOutlineColor());
				triangle.setStrokeWidth(1.5);
				display.getChildren().addAll(triangle);
			}
			if(shape == CritterShape.DIAMOND){
				Polygon diamond = new Polygon();
				diamond.getPoints().addAll(new Double[]{
						(double)x, (double)y+((height/rows)/2),
						(double)x + ((width/columns)/2), (double)y,
						(double)x + (width/columns), (double)y+((height/rows)/2),
						(double)x + ((width/columns)/2), (double)y+(height/rows)
				});
				diamond.setFill(population.get(i).viewFillColor());
				diamond.setStroke(population.get(i).viewOutlineColor());
				diamond.setStrokeWidth(1.5);
				display.getChildren().addAll(diamond);
			}
			if(shape == CritterShape.STAR){
				Polygon triangle = new Polygon();
				triangle.getPoints().addAll(new Double[]{
						(double)x, (double)y+(height/rows)- (double)((height/rows)/4),
						(double)(x + (width/columns)), (double)y+(height/rows) - (double)((height/rows)/4),
						(double)x + ((width/columns)/2), (double)y
				});
				Polygon triangle2 = new Polygon();
				triangle2.getPoints().addAll(new Double[]{
						(double)x, (double)y+(double)((height/rows)/4),
						(double)(x + (width/columns)), (double)y + (double)((height/rows)/4),
						(double)x + ((width/columns)/2), (double)y +(height/rows)
				});
				triangle.setFill(population.get(i).viewFillColor());
				triangle.setStroke(population.get(i).viewOutlineColor());
				triangle2.setFill(population.get(i).viewFillColor());
				triangle2.setStroke(population.get(i).viewOutlineColor());
				display.getChildren().addAll(triangle,triangle2);
			}
				
		}
//		int x = 5*(width/columns);
//		int y = 6*(height/rows); 
//		Polygon triangle = new Polygon();
//		triangle.getPoints().addAll(new Double[]{
//				(double)x, (double)y+(height/rows)- (double)((height/rows)/4),
//				(double)(x + (width/columns)), (double)y+(height/rows) - (double)((height/rows)/4),
//				(double)x + ((width/columns)/2), (double)y
//		});
//		Polygon triangle2 = new Polygon();
//		triangle2.getPoints().addAll(new Double[]{
//				(double)x, (double)y+(double)((height/rows)/4),
//				(double)(x + (width/columns)), (double)y + (double)((height/rows)/4),
//				(double)x + ((width/columns)/2), (double)y +(height/rows)
//		});
//		triangle.setFill(Color.WHITE);
//		triangle.setStroke(Color.GREEN);
//		triangle.setStrokeWidth(1.5);
//		display.getChildren().addAll(triangle);
		display.getChildren().addAll(lines);
		Scene scene = new Scene(display, width, height);
		displayStage.setTitle("Display");
		displayStage.setScene(scene);
        displayStage.show();
    }
}
