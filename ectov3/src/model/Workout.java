package model;

import java.util.LinkedList;
import java.util.Queue;

public class Workout {
	private String date;
	private Queue<Exercise> exercises;
	
	public Workout() {
	}
	
	

	public Workout(String date) {
		super();
		this.setDate(date);
		this.exercises = new LinkedList<>();
		
	}
	
	/**
	 * @param - add exercise e to queue
	 */
	public void addExercise(Exercise e) {
		exercises.offer(e);
	}
	
	/**
	 * @return first exercise in queue
	 */
	public Exercise removeExercise() {
		return exercises.poll();
	}
	
	/**
	 * @return first exercise in queue
	 */
	public Exercise peekExercise() {
		return exercises.peek();
	}
	
	/**
	 * @return boolean
	 */
	public boolean hasExercise() {
		return exercises.isEmpty();
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "Workout [date=" + date + ", exercises=" + exercises + "]";
	}
	
	
	
	
}
