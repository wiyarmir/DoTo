package pl.orellana.doto;

import java.io.Serializable;

public class Task implements Serializable {
	private static final long serialVersionUID = 8024427794224889724L;
	private int id;
	private String task;
	private String category;
	private boolean haslocation;
	private double latitude, longitude;
	private float accuracy;

	public Task() {
	}

	public Task(int id, String task, String category) {
		this.id = id;
		this.task = task;
		this.category = category;
	}

	public Task(int id, String task, String category, double latitude,
			double longitude, float accuracy) {
		this.id = id;
		this.task = task;
		this.category = category;
		this.latitude = latitude;
		this.longitude = longitude;
		this.accuracy = accuracy;
		this.haslocation = true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", task=" + task + ", category=" + category
				+ ", haslocation=" + haslocation + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", accuracy=" + accuracy + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category_id) {
		this.category = category_id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public boolean hasLocation() {
		return haslocation;
	}

	public void setHaslocation(boolean haslocation) {
		this.haslocation = haslocation;
	}
}
