package ui;

import java.util.Queue;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Exercise;

public class DisplayWorkout {

	public static void displayWorkout(Queue<Exercise> exerciseQueue) {
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		
		int row = 0;
		for(Exercise e : exerciseQueue) {
			Label name = new Label(e.getName());
			Label type = new Label(e.getType());
			Label reps = new Label(Integer.toString(e.getReps())); //int to string
			
			grid.addRow(row++, name, type, reps);
		}
		
		Scene scene = new Scene(grid, 600, 400);
		
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("ECTO Workout Planner");
		stage.show();
	}
}
