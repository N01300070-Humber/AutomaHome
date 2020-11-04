package ca.humbermail.n01300070.automahome.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.humbermail.n01300070.automahome.R;

public class TasksFragment extends Fragment {
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		TasksViewModel tasksViewModel = new ViewModelProvider(this).get(TasksViewModel.class);
		View root = inflater.inflate(R.layout.fragment_tasks, container, false);
		final TextView textView = root.findViewById(R.id.text_tasks);
		tasksViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});
		return root;
	}
}