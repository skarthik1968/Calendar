package test.calendar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);


    }

    public  void Facilities(View view)
    {
        Intent intent = new Intent(this,Facilities.class);
        startActivity(intent);

    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, About.class));
    }

}
