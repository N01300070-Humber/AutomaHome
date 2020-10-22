package ca.humbermail.n01300070.automahome.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {
	
	private MutableLiveData<String> mText;
	
	public SlideshowViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is slideshow fragment");
	}
	
	public LiveData<String> getText() {
		return mText;
	}
}