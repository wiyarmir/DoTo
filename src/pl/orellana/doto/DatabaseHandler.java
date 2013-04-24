package pl.orellana.doto;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	static final public String DATABASE_NAME = "TODO_DB";
	static final public int DATABASE_VERSION = 3;

	private static final String TABLE_TASKS = "tasks";
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_GROUP = "_group";
	public static final String KEY_HAS_GEO = "hasgeo";
	public static final String KEY_GEO_LAT = "geo_lat";
	public static final String KEY_GEO_LON = "geo_lon";
	public static final String KEY_GEO_ACC = "geo_acc";

	public DatabaseHandler(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DATABASE", "Creating database... (onCreate)");
		String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT,"
				+ KEY_GROUP + " TEXT" + "," + KEY_HAS_GEO + " INTEGER,"
				+ KEY_GEO_LAT + " REAL," + KEY_GEO_LON + " REAL," + KEY_GEO_ACC
				+ "REAL)";

		db.execSQL(CREATE_TASKS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		onCreate(db);
	}

	public void addTask(Task t) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TASK, t.getTask());
		values.put(KEY_GROUP, t.getCategory());

		db.insert(TABLE_TASKS, null, values);
		db.close();
	}

	public Task getTask(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_ID, KEY_TASK,
				KEY_GROUP }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Task t = new Task(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		return t;
	}

	public List<Task> getAllTasks() {
		List<Task> l = new ArrayList<Task>();

		Cursor cursor = getTasksCursor();

		if (cursor.moveToFirst()) {
			do {
				Task t = new Task();
				t.setId(Integer.parseInt(cursor.getString(0)));
				t.setTask(cursor.getString(1));
				t.setCategory(cursor.getString(2));
				l.add(t);
			} while (cursor.moveToNext());
		}

		return l;
	}

	public int getTasksCount() {
		String query = "SELECT * FROM " + TABLE_TASKS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		cursor.close();

		return cursor.getCount();
	}

	public int updateTask(Task t) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TASK, t.getTask());
		values.put(KEY_GROUP, t.getCategory());

		return db.update(TABLE_TASKS, values, KEY_ID + "=?",
				new String[] { String.valueOf(t.getId()) });
	}

	public void deleteTask(Task t) {
		deleteTask(t.getId());
	}

	public void deleteTask(int taskid) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TASKS, KEY_ID + "=?",
				new String[] { String.valueOf(taskid) });
		db.close();
	}

	public Cursor getTasksCursor() {
		String query = "SELECT * FROM " + TABLE_TASKS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		return cursor;
	}

	public Cursor getTasksSearchCursor(String search) {

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE_TASKS, new String[] { KEY_ID, KEY_TASK,
				KEY_GROUP }, KEY_GROUP + " LIKE ?", new String[] { search },
				null, null, null, null);
		return cursor;
	}

	public Cursor getSuggestionsCursor() {
		SQLiteDatabase db = this.getWritableDatabase();
		// String query = "SELECT DISTINCT (RANDOMBLOB(4), " + KEY_GROUP
		// + ") FROM " + TABLE_TASKS + " AS (_ID, SUGGEST_COLUMN_TEXT_1)";
		String query = "SELECT DISTINCT " + KEY_GROUP + " FROM " + TABLE_TASKS
				+ " AS SUGGEST_COLUMN_TEXT_1";
		Log.d("getSuggestionsCursor()", "Query: " + query);
		Cursor c = db.rawQuery(query, null);
		return c;
	}

}
