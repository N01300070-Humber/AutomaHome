package ca.humbermail.n01300070.automahome.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
	
	private final FirebaseAuth authentication = FirebaseAuth.getInstance();
	
	private FirebaseUser currentUser;
	
	
	public interface LoginStateListener {
		void onLoginStateChanged(@NonNull FirebaseAuth auth, boolean loggedIn);
	}
	
	
	public LoginDataSource(final LoginStateListener loginStateListener) {
		authentication.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				currentUser = firebaseAuth.getCurrentUser();
				loginStateListener.onLoginStateChanged(firebaseAuth, currentUser != null);
			}
		});
	}
	
//	public boolean isLoggedIn() {
//		return currentUser != null;
//	}
	
//	public void updateCurrentUser() {
//		currentUser = authentication.getCurrentUser();
//	}
	
	public void login(Executor executor, String emailAddress, String password,
					  OnCompleteListener<AuthResult> onCompleteListener) {
		
		authentication.signInWithEmailAndPassword(emailAddress, password)
				.addOnCompleteListener(executor, onCompleteListener);
	}
	
	public void register(Executor executor, String emailAddress, String password,
						 OnCompleteListener<AuthResult> onCompleteListener) {
		
		authentication.createUserWithEmailAndPassword(emailAddress, password)
				.addOnCompleteListener(executor, onCompleteListener);
	}
	
	public void setDisplayName(String displayName) {
		currentUser.updateProfile(
				new UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
		);
	}
	
	public void logout() {
		authentication.signOut();
		currentUser = null;
	}
	
	public void deleteAccount() {
		// TODO: Remove all user data from the database
		currentUser.delete();
		currentUser = null;
	}
	
	public String getUserID() {
		return currentUser.getUid();
	}
	
	public String getDisplayName() {
		return currentUser.getDisplayName();
	}
}