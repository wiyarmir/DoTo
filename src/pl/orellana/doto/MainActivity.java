package pl.orellana.doto;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

	private SimpleCursorAdapter ad;
	private DatabaseHandler dbh;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		fm = getFragmentManager();

		getListView().addHeaderView(
				getLayoutInflater().inflate(R.layout.first_list_item, null),
				null, true);

		dbh = new DatabaseHandler(this);
		Cursor c = dbh.getTasksCursor();

		ad = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
				c, new String[] { DatabaseHandler.KEY_TASK,
						DatabaseHandler.KEY_GROUP }, new int[] {
						android.R.id.text1, android.R.id.text2 },
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(ad);

		getListView().setOnItemClickListener(
				new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Log.d("onItemClick", "position:" + position);
						if (position == 0) { // as we set the first element, we
												// know is ours
							AddOptionDialog a = new AddOptionDialog();
							a.show(fm, "fragment_add_item");
						} else {
							LongPressDialog l = new LongPressDialog();
							Bundle b = new Bundle();
							b.putInt("position", position);
							l.show(fm, "fragment_options_dialog");
						}
					}
				});

		getListView().setOnItemLongClickListener(
				new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (position != 0) {

							return true;
						} else {
							return false;
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchView searchView;

		getMenuInflater().inflate(R.menu.activity_main, menu);
		searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				if (query.equals("")) {
					updateCursor();
				} else {
					((SimpleCursorAdapter) getListAdapter()).changeCursor(dbh
							.getTasksSearchCursor(query));
				}
				return true;
			}
		});

		Cursor suggestionCursor = dbh.getSuggestionsCursor();

		searchView.setSearchableInfo(null);
		return true;
	}

	public void doPositiveClick(String task, String taskgroup) {
		dbh.addTask(new Task(0, task, taskgroup));
		updateCursor();
	}

	public void deleteOne(int p) {
		Cursor c = (Cursor) getListAdapter().getItem(p - 1);
		dbh.deleteTask(c.getInt(0));
		updateCursor();
	}

	public void updateCursor() {
		((SimpleCursorAdapter) getListAdapter()).changeCursor(dbh
				.getTasksCursor());
	}

	public void doEdit(Task t) {
		dbh.updateTask(t);
		updateCursor();
	}

	public void editOne(int p) {
		Cursor c = (Cursor) getListAdapter().getItem(p - 1);
		EditOptionDialog l = new EditOptionDialog();
		Bundle b = new Bundle();
		b.putSerializable("task", dbh.getTask(c.getInt(0)));
		l.setArguments(b);
		l.show(fm, "fragment_edit_option");
	}
}
