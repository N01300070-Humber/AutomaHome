package ca.humbermail.n01300070.automahome.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritesViewModel extends ViewModel {
	
	private final MutableLiveData<String> mText;
	
	public FavoritesViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is favorites fragment");
	}
	
	public LiveData<String> getText() {
		return mText;
	}
}