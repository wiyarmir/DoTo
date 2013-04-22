package pl.orellana.doto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditOptionDialog extends DialogFragment {
	private EditText tTask;
	private EditText tGroup;
	private Task t;

	public EditOptionDialog() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_add_item, null);

		t = (Task) getArguments().getSerializable("task");

		tTask = (EditText) v.findViewById(R.id.txt_task);
		tGroup = (EditText) v.findViewById(R.id.txt_group);

		tTask.setText(t.getTask());
		tGroup.setText(t.getCategory());

		return new AlertDialog.Builder(getActivity())
				// .setIcon(R.drawable.alert_dialog_icon)
				.setView(v)
				.setTitle("Edit item")
				.setPositiveButton("Commit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String task = tTask.getText().toString();
								String taskgroup = tGroup.getText().toString();
								t.setTask(task);
								t.setCategory(taskgroup);
								((MainActivity) getActivity()).doEdit(t);

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
