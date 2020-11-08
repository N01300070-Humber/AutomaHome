package ca.humbermail.n01300070.automahome.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;
import ca.humbermail.n01300070.automahome.ui.home.InviteUser;

public class GeneralSetting extends AppCompatActivity {

    ImageButton accountButton;
    ImageButton notificationButton;

    public void accountBtn_Clicked(View view){
        Toast.makeText(this, "Account Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, AccountSetting.class);
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
        setContentView(R.layout.activity_general_setting);

        /* Referencing the account button by finding its iD */
        accountButton = findViewById(R.id.accountbtn);
        /* Referencing the notification button by finding its iD */
        notificationButton = findViewById(R.id.notificationbtn);
    }
}