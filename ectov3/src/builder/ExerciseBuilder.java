package builder;

import model.Exercise;

public class ExerciseBuilder {


	public ExerciseBuilder() {
		
	}
	
	public Exercise build(String name, int sets, int reps, int time, double weight, String type) {
		//this.validate(name, sets, reps, time, weight, type);
		Exercise e = new Exercise(name, sets, reps, time, weight, type);
		return e;
	}
	
	//input validation - not longer used
	public void validate(String name, int sets, int reps, int time, double weight, String type) {
		if(name.isEmpty() || type.isEmpty()) {
			throw new IllegalArgumentException("Exercise must have a name and a type");
		}
		if(sets <= 0) {
			throw new IllegalArgumentException("Sets cannot be less than 1");
		}
		if((reps <= 0 && time <=0) || (reps < 0 || time < 0)){
			throw new IllegalArgumentException("Reps or Duration must be greater than 0");
		}
		if(weight < 0) {
			throw new IllegalArgumentException("Weight cannot be negative");
		}
	}
	
	

}
