package application;

import java.util.Queue;
import java.util.Stack;

import builder.ExerciseBuilder;
import builder.WorkoutBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Exercise;
import model.Workout;
import ui.ExerciseListView;

public class Main extends Application {
	private ExerciseBuilder eb = new ExerciseBuilder();
	private WorkoutBuilder wb = new WorkoutBuilder();
	private VBox workoutDisplay;
	private int wHeight = 650;
	private final int WWIDTH = 600;
	private final int padding = 10;
	private final int inputWidth = 75;
	private final int exBoxWidth = 200;
	private final int exBoxHeight = 50;
	Label timerLabel = new Label();
	TextField nameField = new TextField();
	ChoiceBox<String> typeBox = new ChoiceBox<>();

	@Override
	public void start(Stage primaryStage) {

		VBox exerciseInputs = createExerciseInputs(wb, eb);
		workoutDisplay = createWorkoutDisplay();

		// begin workout button functionality
		Button beginWorkoutButton = new Button("Begin Workout");
		beginWorkoutButton.setPrefWidth(100);
		beginWorkoutButton.setPrefHeight(50);
		beginWorkoutButton.setAlignment(Pos.CENTER);
		beginWorkoutButton.setOnAction(event -> {
			if (wb.getExercises().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please add at least one exercise to the workout.");
				alert.showAndWait();
			} else {
				showCurrentExerciseWindow(primaryStage);
			}
		});

		// display exercise rectangles/workout
		Label space1 = new Label("               ");
		Label space2 = new Label("               ");
		HBox beginHBox = new HBox(padding);
		beginHBox.getChildren().addAll(beginWorkoutButton, space1, space2);
		VBox beginVBox = new VBox(padding);
		beginVBox.getChildren().addAll(beginHBox, workoutDisplay);

		// display list of exercises to choose from
		ExerciseListView exerciseListView = new ExerciseListView(nameField, typeBox);
		exerciseListView.loadExercisesFromCSV("C:\\Users\\ericg\\git\\ectoV3\\ectov3\\src\\resources\\exercises.csv");

		VBox leftSide = new VBox(padding);
		leftSide.getChildren().addAll(exerciseInputs, exerciseListView);
		BorderPane root = new BorderPane();
		root.setLeft(leftSide);
		root.setRight(beginVBox);

		Scene scene = new Scene(root, WWIDTH, wHeight);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ECTO");
		primaryStage.show();
	}

	/**
	 * @param - javaFX stage Opens new window to cycle through exercises Includes
	 *          button functionality for next, start, previous buttons
	 */
	private void showCurrentExerciseWindow(Stage primaryStage) {
		Stage exerciseStage = new Stage();
		exerciseStage.initModality(Modality.WINDOW_MODAL);
		exerciseStage.initOwner(primaryStage);

		VBox exerciseBox = new VBox(padding);
		exerciseBox.setPadding(new Insets(padding * 2));

		Stack<Exercise> repeatedExercises = new Stack<>();
		Exercise[] current = new Exercise[1];
		current[0] = wb.getNext();
		StackPane currentExercisePane = createExerciseRectangle(current[0]);

		Button startButton = new Button("Start");
		Button prevButton = new Button("Prev Exercise");
		Button nextButton = new Button("Next Exercise");
		HBox buttons = new HBox(padding);
		buttons.getChildren().addAll(prevButton, startButton, nextButton);

		nextButton.setOnAction(event -> {

			wb.addPrevExecise(current[0]);
			if (!repeatedExercises.isEmpty()) {
				current[0] = repeatedExercises.pop();
			} else if (wb.getExercises().size() > -1) {
				current[0] = wb.getNext();
			}

			if (current[0] != null && wb.getExercises().size() > -1) {
				StackPane nextExercisePane = createExerciseRectangle(current[0]);
				VBox content = new VBox(padding);
				content.getChildren().addAll(nextExercisePane, buttons, timerLabel);
				content.setAlignment(Pos.BOTTOM_CENTER);
				startTimer(current[0].getTime(), timerLabel);

				exerciseBox.getChildren().clear();
				exerciseBox.getChildren().add(content);

			} else {
				exerciseStage.close();
				workoutDisplay.getChildren().clear();
			}
		});

		prevButton.setOnAction(event -> {

			repeatedExercises.push(current[0]);
			current[0] = wb.getPrev();

			if (current[0] != null) {
				StackPane prevExercisePane = createExerciseRectangle(current[0]);
				VBox content = new VBox(padding);
				content.getChildren().addAll(prevExercisePane, buttons, timerLabel);
				content.setAlignment(Pos.BOTTOM_CENTER);
				startTimer(current[0].getTime(), timerLabel);

				exerciseBox.getChildren().clear();
				exerciseBox.getChildren().add(content);

			} else {
				// do nothing
			}

		});

		startButton.setOnAction(event -> {
			if (current[0].getTime() > 0) {
				startTimer(current[0].getTime(), timerLabel);
			}
		});

		exerciseBox.getChildren().addAll(currentExercisePane, buttons, timerLabel);

		Scene exerciseScene = new Scene(exerciseBox);
		exerciseStage.setScene(exerciseScene);
		exerciseStage.setTitle("Current Exercise");
		exerciseStage.show();
	}

	/**
	 * @param - exercise time
	 * @param - Timer label Count down for timed exercises
	 */
	private void startTimer(int seconds, Label timerLabel) {
		final int[] remainingSeconds = { seconds };

		Timeline time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			if (remainingSeconds[0] > 0) {
				remainingSeconds[0]--;
				timerLabel.setText("Time Remaining: " + remainingSeconds[0] + " seconds");

			} else {
				timerLabel.setText("Press Next to continue");
			}
		}));
		time.setCycleCount(seconds);
		time.play();
	}

	/**
	 * @param workout  builder wb
	 * @param exercise builder eb
	 * @return VBox with exercise inputs Functionality for all exercise inputs and
	 *         add to workout button
	 * 
	 */
	private VBox createExerciseInputs(WorkoutBuilder wb, ExerciseBuilder eb) {
		VBox exerciseInputs = new VBox(padding);
		exerciseInputs.setPadding(new Insets(padding));
		exerciseInputs.setAlignment(Pos.TOP_LEFT);

		Label nameLabel = new Label("Exercise:");
		nameField.setPromptText("Name");

		HBox typeAndSetsBox = new HBox(padding);
		typeAndSetsBox.setAlignment(Pos.CENTER_LEFT);

		Label typeLabel = new Label("Type:");
		typeBox.getItems().addAll("Cardio", "Strength", "Flexibility", "Balance", "Warm-Ups", "Cool Down", "Other");
		typeBox.setValue("Cardio");

		Label setsLabel = new Label("            Sets");
		TextField setsField = new TextField();
		setsField.setMaxWidth(inputWidth);
		setsField.setText("1");
		typeAndSetsBox.getChildren().addAll(typeLabel, typeBox, setsLabel, setsField);

		HBox repsAndWeightBox = new HBox(padding);
		repsAndWeightBox.setAlignment(Pos.CENTER_LEFT);
		Label repsLabel = new Label("      Reps");
		TextField repsField = new TextField();
		repsField.setMaxWidth(inputWidth);
		repsField.setText("0");
		Label weightLabel = new Label("and Weight");
		TextField weightField = new TextField();
		weightField.setMaxWidth(inputWidth);
		weightField.setText("20");
		repsAndWeightBox.getChildren().addAll(repsLabel, repsField, weightLabel, weightField);

		Label or = new Label("or");
		or.setAlignment(Pos.CENTER);

		HBox timeAndSubmitBox = new HBox(padding);
		Label timeLabel = new Label("      Time");
		timeLabel.setAlignment(Pos.CENTER);
		TextField timeField = new TextField();
		timeField.setMaxWidth(inputWidth);
		timeField.setPromptText("Seconds");
		timeField.setAlignment(Pos.CENTER);
		timeField.setText("5");
		Button submitButton = new Button("Add to Workout");
		submitButton.setAlignment(Pos.CENTER_RIGHT);
		Label space = new Label("     ");
		timeAndSubmitBox.getChildren().addAll(timeLabel, timeField, space, submitButton);

		// submit exercise data

		submitButton.setOnAction(event -> {
			// Retrieve exercise data from input fields
			String name = nameField.getText();
			String type = typeBox.getValue();
			String sets = setsField.getText();
			String reps = repsField.getText();
			String time = timeField.getText();
			String weight = weightField.getText();

			if (time.equals("")) {
				time = "0";
			}
			if (reps.equals("")) {
				reps = "0";
			}
			if (sets.equals("")) {
				sets = "0";
			}
			if (weight.equals("")) {
				weight = "0";
			}
			if (time.equals("0") && reps.equals("0")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a value for time or sets (not both)");
				alert.showAndWait();
			} else if (!time.equals("0") && !reps.equals("0")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a value for time or sets (not both)");
				alert.showAndWait();
			} else {
				Exercise e = eb.build(name, Integer.parseInt(sets), Integer.parseInt(reps), Integer.parseInt(time),
						Double.parseDouble(weight), type);
				for (int i = 0; i < Integer.parseInt(sets); i++) {
					wb.addExercise(e);
					updateWorkoutDisplay();
				}
			}

			// Clear input fields
			nameField.clear();
			typeBox.getSelectionModel().clearSelection();
			setsField.clear();
			repsField.clear();
			timeField.clear();
			weightField.clear();
			typeBox.setValue("Cardio");
			setsField.setText("0");
			repsField.setText("0");
			timeField.setText("0");
			weightField.setText("0");

		});

		exerciseInputs.getChildren().addAll(nameLabel, nameField, typeAndSetsBox, repsAndWeightBox, or,
				timeAndSubmitBox);

		return exerciseInputs;
	}

	/**
	 * @return VBox with exercise rectangles
	 */
	private VBox createWorkoutDisplay() {
		VBox workoutDisplay = new VBox(padding);
		workoutDisplay.setPadding(new Insets(padding));
		ScrollPane scrollPane = new ScrollPane(workoutDisplay);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefHeight(wHeight);
		updateWorkoutDisplay();
		return workoutDisplay;
	}

	/**
	 * updates exercise display when exercise is added to workout
	 */
	private void updateWorkoutDisplay() {
		if (workoutDisplay != null) {
			workoutDisplay.getChildren().clear();
		}
		// Queue<Exercise> workoutQueue = wb.getExercises();

		for (Exercise e : wb.getExercises()) {
			StackPane exercisePane = createExerciseRectangle(e);
			workoutDisplay.getChildren().add(exercisePane);
		}

	}

	/**
	 * @param Exercise e
	 * @return StackPane layer with exercise rectangle Build exercise rectangle to
	 *         add to the workout display
	 */
	private StackPane createExerciseRectangle(Exercise e) {
		Rectangle rect = new Rectangle(exBoxWidth, exBoxHeight);
		rect.setFill(getExerciseColor(e.getType()));
		Text t = new Text(e.buildLabel());
		t.setFill(Color.WHITE);
		t.setStroke(Color.BLACK);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(rect, t);

		return stackPane;
	}

	/**
	 * @param exercise type
	 * @return Color
	 */
	private Color getExerciseColor(String type) {

		switch (type) {
		case "Strength":
			return Color.ORANGE;
		case "Warm-Ups":
			return Color.GREEN;
		case "Cardio":
			return Color.RED;
		case "Balance":
			return Color.PURPLE;
		case "Flexibility":
			return Color.YELLOW;
		case "Cool Down":
			return Color.BLUE;
		default:
			return Color.GRAY;
		}
	}

	public static void main(String[] args) {
		// Launch the JavaFX application
		launch(args);
	}

}
