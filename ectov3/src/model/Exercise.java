package model;

public class Exercise {

	private String name;
	private int reps;
	private int sets;
	private int time;
	private String type;
	private double weight;
	
	public Exercise() {
		
	}
	
	
	public Exercise(String name, int sets, int reps, int time, double weight, String type) {
		super();
		this.name = name;
		this.reps = reps;
		this.sets = sets;
		this.time = time;
		this.type = type;
		this.weight = weight;
	}

	/**
	 * @return Text for exercise rectangle as String
	 */
	public String buildLabel() {
		String s = "";

		if(this.getReps() == 0) {
			s = this.getName() + " - " + this.getTime() + " seconds";
		}
		else if(this.getTime() == 0) {
			s = this.getName() + " - " + this.getWeight() + " lbs - " + this.getReps() + " reps" ;
		}
		
		return s;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}

	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}

	/**
	 * @return the sets
	 */
	public int getSets() {
		return sets;
	}

	/**
	 * @param sets the sets to set
	 */
	public void setSets(int sets) {
		this.sets = sets;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}




	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}




	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Exercise [name=" + name + ", reps=" + reps + ", sets=" + sets + ", time=" + time + ", type=" + type
				+ ", weight=" + weight + "]";
	}
	
	

}
