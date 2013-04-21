package pl.orellana.doto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddOptionDialog extends DialogFragment {
	private EditText tTask;
	private EditText tGroup;

	public AddOptionDialog() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_add_item, null);

		tTask = (EditText) v.findViewById(R.id.txt_task);
		tGroup = (EditText) v.findViewById(R.id.txt_group);

		return new AlertDialog.Builder(getActivity())
				// .setIcon(R.drawable.alert_dialog_icon)
				.setView(v)
				.setTitle("Add new item")
				.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String task = tTask.getText().toString();
								String taskgroup = tGroup.getText().toString();

								((MainActivity) getActivity()).doPositiveClick(
										task, taskgroup);

							}
						})
				.setNegativeButton("Dismiss",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						}).create();
	}

}
