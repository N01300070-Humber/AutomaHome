package ca.humbermail.n01300070.automahome.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ca.humbermail.n01300070.automahome.R;

public class ManageHome extends AppCompatActivity {

    //UI Elements -- to be referenced in code!
    ImageButton inviteUserButton;
    ImageButton addNetworkButton;

    public void inviteUserBtn_Clicked(View view){
        Toast.makeText(this, "InviteUser Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, InviteUser.class);
        startActivity(intent0);
    }

    public void addNetworkBtn_Clicked(View view){
        Toast.makeText(this, "AddNetwork Button Clicked", Toast.LENGTH_SHORT).show();
        Intent intent0 = new Intent(this, InviteUser.class);
        startActivity(intent0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_home);

        //Referencing the invite user button by finding its iD
        inviteUserButton = findViewById(R.id.inviteUser);
        //Referencing the add network button by finding its iD
        addNetworkButton = findViewById(R.id.addNetwork);
    }
}