package ca.humbermail.n01300070.automahome.ui.manageHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageHomeViewModel extends ViewModel {
	
	private final MutableLiveData<String> mText;
	
	public ManageHomeViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is manage home fragment");
	}


	public LiveData<String> getText() {
		return mText;
	}
}