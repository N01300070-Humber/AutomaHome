package ca.humbermail.n01300070.automahome.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.humbermail.n01300070.automahome.ui.settings.AccountSetting;

public class HomeViewModel extends ViewModel {
	
	private final MutableLiveData<String> mText;
	
	public HomeViewModel() {
		mText = new MutableLiveData<>();
		mText.setValue("This is manage home fragment");
	}


	public LiveData<String> getText() {
		return mText;
	}
}