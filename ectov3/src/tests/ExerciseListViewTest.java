package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ui.ExerciseListView;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseListViewTest {

    private ExerciseListView exerciseListView;

    @BeforeEach
    public void setUp() {
        exerciseListView = new ExerciseListView(new TextField(), new ChoiceBox<>());
    }

    @Test
    public void testLoadExercisesFromCSV() {
        String csvFilePath = "exercises.csv";

        exerciseListView.loadExercisesFromCSV(csvFilePath);

        ListView<String> exerciseList = exerciseListView.getExerciseList();
        ObservableList<String> items = exerciseList.getItems();

        assertFalse(items.isEmpty());

    }
}

