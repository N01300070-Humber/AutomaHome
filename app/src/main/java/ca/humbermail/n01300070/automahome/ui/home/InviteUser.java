package ca.humbermail.n01300070.automahome.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;

public class InviteUser extends AppCompatActivity {

    ImageButton inviteButton;

    public void inviteBtn_Clicked(View view){
        Toast.makeText(this, "Invite Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, ManageHome.class);
        startActivity(intent0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);

        //Referencing the invite user button by finding its iD
        inviteButton = findViewById(R.id.invitebtn);
    }
}