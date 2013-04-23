package pl.orellana.doto;

import android.content.SearchRecentSuggestionsProvider;

public class Suggestions extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "pl.orellana.doto.Suggestions";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public Suggestions() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
