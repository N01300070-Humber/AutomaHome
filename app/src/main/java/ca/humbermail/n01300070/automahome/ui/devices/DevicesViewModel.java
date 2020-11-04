package ca.humbermail.n01300070.automahome.ui.devices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevicesViewModel extends ViewModel {
	
	private final MutableLiveData<String> mText;
	
	public DevicesViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is devices fragment");
	}
	
	public LiveData<String> getText() {
		return mText;
	}
}