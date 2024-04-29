package builder;

import java.util.Queue;
import java.util.Stack;

import model.Exercise;
import model.Workout;

import java.util.LinkedList;

public class WorkoutBuilder {

		private String date;
		private Queue<Exercise> exercises;
		private Stack<Exercise> prevExercises;
		
		public WorkoutBuilder() {
			this.date = "";
			this.exercises = new LinkedList<>();
			this.prevExercises = new Stack<>();
		}
		
		/**
		 * @param add exercise e to queue
		 */
		public void addExercise(Exercise e) {
			this.exercises.offer(e);
		}
		
		/**
		 * @param add exercise e to stack
		 */
		public void addPrevExecise(Exercise e) {
			this.prevExercises.add(e);
		}
		
		/**
		 * @return first exercise in queue
		 */
		public Exercise getNext() {
			if(!exercises.isEmpty()) {
				Exercise e = exercises.poll();
				//prevExercises.push(current);
				return e;
			}
			return null;
		}
		
		/**
		 * @return first exercise in stack
		 */
		public Exercise getPrev() {
			if(!prevExercises.isEmpty()) {
				Exercise e = prevExercises.pop();
				//exercises.add(current);
				return e;
			}
			return null;
		}
		
		/**
		 * @return workout
		 */
		public Workout build() {
			if(this.date == null) {
				throw new IllegalArgumentException("Workout date cannot be empty.");
			}
			if(this.exercises.isEmpty()) {
				throw new IllegalStateException("Cannot build workout with no exercises.");
			}
			
			Workout w = new Workout(this.date);
			while(!exercises.isEmpty()) {
				w.addExercise(exercises.poll());
			}
			return w;
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

		/**
		 * @return the exercises
		 */
		public Queue<Exercise> getExercises() {
			return exercises;
		}

		/**
		 * @param exercises the exercises to set
		 */
		public void setExercises(Queue<Exercise> exercises) {
			this.exercises = exercises;
		}

		
		
		/**
		 * @return the prevExercises
		 */
		public Stack<Exercise> getPrevExercises() {
			return prevExercises;
		}

		/**
		 * @param prevExercises the prevExercises to set
		 */
		public void setPrevExercises(Stack<Exercise> prevExercises) {
			this.prevExercises = prevExercises;
		}

		@Override
		public String toString() {
			return "WorkoutBuilder [date=" + date + ", exercises=" + exercises + "]";
		}
		
		
		
		
}
