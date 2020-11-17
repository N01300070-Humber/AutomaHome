package ca.humbermail.n01300070.automahome.ui.manageHome;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;

public class InviteUserActivity extends AppCompatActivity {
    
    EditText emailEditText;
    Button inviteButton;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        emailEditText = findViewById(R.id.editText_inviteUser_email);
        inviteButton = findViewById(R.id.button_inviteUser);
        
        emailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                inviteUser();
                return true;
            }
        });
    }
    
    /**
     * Handles back button onClick event
     * @param item non-null MenuItem
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
    
    /**
     * onClick event handler for InviteUserButton
     * @param view Source view
     */
    public void inviteButton_Clicked(View view) {
        inviteUser();
    }
    
    public void inviteUser() {
        Toast.makeText(getApplicationContext(), "User invited", Toast.LENGTH_SHORT).show();
        finish();
    }
}