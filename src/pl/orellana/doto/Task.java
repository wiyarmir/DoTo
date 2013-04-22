package pl.orellana.doto;

import java.io.Serializable;

public class Task implements Serializable {
	private static final long serialVersionUID = 8024427794224889724L;
	private int id;
	private String task;
	private String category;

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

	public Task(int id, String task, String category) {
		this.id = id;
		this.task = task;
		this.category = category;
	}

	public Task() {
	}
}
