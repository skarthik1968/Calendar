package test.calendar;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;
    TextView dateDisplay;

    //local variables
    int tempDay; //stores date retrieved from db when user asks if that day is free for reservation

    //receive option chosen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        // Write a message to the database
        FirebaseDatabase dbDay = FirebaseDatabase.getInstance();
        final DatabaseReference day = dbDay.getReference("Day");

        FirebaseDatabase dbMonth = FirebaseDatabase.getInstance();
        final DatabaseReference month = dbMonth.getReference("Month");

        FirebaseDatabase dbYear = FirebaseDatabase.getInstance();
        final DatabaseReference year = dbYear.getReference("Year");


        Bundle extras = getIntent().getExtras();
        String chosen = null;

        if(extras!= null)
        {
            chosen = extras.getString("optionChosen");
        }

        calendarView = (CalendarView)findViewById(R.id.calendarView);
        final String Timestamp = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());



        final String finalChosen = chosen;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, final int YEAR, final int MONTH, final int DAY) {

                final AlertDialog.Builder builder1 = new AlertDialog.Builder(Calendar.this);
                builder1.setCancelable(true);

                //CHANGES DIALOG BASED ON WHETHER CHOSEN IS "RESERVED"/"CANCEL"
                if(Objects.equals(finalChosen, "reserved")) {
                    //RESERVATION CONFIRMATION
                    builder1.setMessage("Would you like to make a reservation on this date");
                }
                else
                {
                    builder1.setMessage("Are you sure you would like to cancel your reservation");
                }



                //RESERVATION CLASHING
                Toast.makeText(getApplicationContext(), "Date right now right now right now?" + Timestamp + " " + finalChosen, Toast.LENGTH_SHORT).show();


                //read from database
                day.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int value = dataSnapshot.getValue(int.class);
                        tempDay = value;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                //If user clicks the reserve button

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                if(DAY == tempDay)
                                {

                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Calendar.this);
                                    builder2.setMessage("There is already a reservation here, reschedule?");
                                    builder2.setCancelable(true);

                                    builder2.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });


                                    AlertDialog alert11 = builder2.create();
                                    alert11.show();



                                }

                                //Checks if previous reservation exists
                                //Sets date user asked to reserve
                                day.setValue(DAY);
                                month.setValue(MONTH);
                                year.setValue(YEAR);


                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }

        });

    }
}
