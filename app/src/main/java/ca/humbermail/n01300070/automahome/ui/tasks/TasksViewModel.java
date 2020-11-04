package ca.humbermail.n01300070.automahome.ui.tasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TasksViewModel extends ViewModel {
	
	private final MutableLiveData<String> mText;
	
	public TasksViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is tasks fragment");
	}
	
	public LiveData<String> getText() {
		return mText;
	}
}