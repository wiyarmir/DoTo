package pl.orellana.doto;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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
						if (position == 0) { // as we set the first element, we
												// know is ours
							showAddDialog();
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		// Get the SearchView and set the searchable configuration
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		// Assumes current activity is the searchable activity
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				Cursor c;
				if (query.equals("")) {
					c = dbh.getTasksCursor();
				} else {
					c = dbh.getTasksSearchCursor(query);
				}
				((SimpleCursorAdapter) getListAdapter()).changeCursor(c);
				return false;
			}
		});
		return true;
	}

	private void showAddDialog() {
		AddOptionDialog addOptionDialog = new AddOptionDialog();
		addOptionDialog.show(fm, "fragment_add_item");
	}

	public void doPositiveClick(String task, String taskgroup) {
		dbh.addTask(new Task(0, task, taskgroup));
		((SimpleCursorAdapter) getListAdapter()).changeCursor(dbh
				.getTasksCursor());
	}

}
