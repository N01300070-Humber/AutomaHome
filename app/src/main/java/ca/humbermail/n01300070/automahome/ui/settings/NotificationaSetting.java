package ca.humbermail.n01300070.automahome.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;

public class NotificationaSetting extends AppCompatActivity {

    ImageButton accountButton;
    ImageButton generalButton;

    public void accountBtn_Clicked(View view){
        Toast.makeText(this, "Account Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, AccountSetting.class);
        startActivity(intent0);
    }

    public void generalBtn_Clicked(View view){
        Toast.makeText(this, "General Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, GeneralSetting.class);
        startActivity(intent0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationa_setting);

        /* Referencing the account button by finding its iD */
        accountButton = findViewById(R.id.account2btn);
        /* Referencing the general button by finding its iD */
        generalButton = findViewById(R.id.general3btn);
    }
}