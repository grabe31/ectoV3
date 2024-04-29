package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExerciseListView extends VBox {

    private ListView<String> exerciseList;
    private TextField exerciseNameField;
    private ChoiceBox workoutTypeChoiceBox;
    private Map<String, String> exerciseMap; // Exercise name as key, exercise type as value

    public ExerciseListView(TextField exerciseNameField, ChoiceBox<String> workoutTypeChoiceBox) {
        this.exerciseNameField = exerciseNameField;
        this.workoutTypeChoiceBox = workoutTypeChoiceBox;
        exerciseList = new ListView<>();
        exerciseList.setPrefHeight(400);
        
        exerciseMap = new HashMap<>();

        exerciseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update the exercise name text field in the main program with the selected exercise
                exerciseNameField.setText(newValue);
                workoutTypeChoiceBox.setValue(getExerciseType(newValue));
            }
        });

        this.getChildren().add(exerciseList);
    }

    public void loadExercisesFromCSV(String csvFilePath) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into tokens using comma as the delimiter
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String exerciseName = tokens[0].trim();
                    String exerciseType = tokens[1].trim();
                    items.add(exerciseName);
                    exerciseMap.put(exerciseName, exerciseType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sortAlphabetically(items);
        exerciseList.setItems(items);
    }
    
   
        private void sortAlphabetically(List<String> exerciseNames) {
            int n = exerciseNames.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (exerciseNames.get(j).compareToIgnoreCase(exerciseNames.get(j + 1)) > 0) {
                        // Swap exercise names
                        String temp = exerciseNames.get(j);
                        exerciseNames.set(j, exerciseNames.get(j + 1));
                        exerciseNames.set(j + 1, temp);
                    }
                }
            }
        }
    


    public String getExerciseType(String exerciseName) {
        return exerciseMap.get(exerciseName);
    }


    public ListView<String> getExerciseList() {
        return exerciseList;
    }
}
