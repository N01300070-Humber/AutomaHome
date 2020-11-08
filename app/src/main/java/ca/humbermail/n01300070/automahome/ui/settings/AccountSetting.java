package ca.humbermail.n01300070.automahome.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.WelcomeActivity;

public class AccountSetting extends AppCompatActivity {

    ImageButton generalButton;
    ImageButton notificationButton;
    ImageButton delAcctButton;
    ImageButton signOutButton;

    public void delAcctBtn_Clicked(View view){
        Toast.makeText(this, "Delete Account Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, WelcomeActivity.class);
        startActivity(intent0);
    }

    public void signOutBtn_Clicked(View view){
        Toast.makeText(this, "Sign Out Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, WelcomeActivity.class);
        startActivity(intent0);
    }

    public void generalBtn_Clicked(View view){
        Toast.makeText(this, "Account Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, GeneralSetting.class);
        startActivity(intent0);
    }

    public void notificationBtn_Clicked(View view){
        Toast.makeText(this, "Notification Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, NotificationaSetting.class);
        startActivity(intent0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        /* Referencing the general button by finding its iD */
        generalButton = findViewById(R.id.general2btn);
        /* Referencing the notification button by finding its iD */
        notificationButton = findViewById(R.id.notification2btn);
        /* Referencing the delete account button by finding its iD */
        delAcctButton = findViewById(R.id.delAcctBtn);
        /* Referencing the sign out button by finding its iD */
        signOutButton = findViewById(R.id.signOutBtn);
    }
}