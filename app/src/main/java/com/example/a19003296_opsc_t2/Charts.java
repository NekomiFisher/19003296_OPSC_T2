package com.example.a19003296_opsc_t2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Charts extends AppCompatActivity {
    //Declaration
    private int catCount = 0;
    String order = "a"; //getIntent().getStringExtra("order"); //causes crash
    ArrayList<CategoryClass> basket = new ArrayList<CategoryClass>();
    ArrayList<ItemType> basketB = new ArrayList<ItemType>();
    //Add send back if not signed in
    String con = "";
    String cat = "";
    String[][] info;
    int items = 0;
    //uses dependency to declare pie chart
    PieChart pieChart;
    BarChart barChart;

    //To retrieve username
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        //Putting data from list into list
        final ArrayList<CategoryClass> item = new ArrayList<CategoryClass>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        //Saves categories into array and counts
        databaseReference.child("CategoryClass").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children)
                {
                    CategoryClass value = child.getValue(CategoryClass.class);
                    item.add(value);
                }
                //passes Array list to bigger context
                basket = item;
                //Loops to display data
                //A filter by user will have to be used for the actual app
                int count = 0;
                con = "";
                cat = "";
                catCount = 0;
                while(count < basket.size())
                {
                    CategoryClass holder = new CategoryClass();
                    holder = basket.get(count);
                    if(email.equals(holder.getUser()))
                    {
                        catCount++;
                    }
                    count++;
                }
                count = 0;

                //Puts items into array
                String[][] infoTemp = new String[catCount][3];
                String a;
                String b;
                int tally = 0;
                while(count < basket.size())
                {
                    CategoryClass holder = new CategoryClass();
                    holder = basket.get(count);
                    a = holder.getCategoryName();
                    b = holder.getGoal();
                    if(email.equals(holder.getUser()))
                    {
                        infoTemp[tally][0] = a;
                        infoTemp[tally][1] = b;
                        infoTemp[tally][2] = "";
                        tally++;
                    }
                    count++;
                }
                tally = 0;
                //Puts items into array
                info = infoTemp;
                }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        //Filling in the rest of the data from the items
        final ArrayList<ItemType> itemB = new ArrayList<ItemType>();
        FirebaseDatabase databaseB = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceB = databaseB.getReference();
        databaseReferenceB.child("ItemClass").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children)
                {
                    ItemType value = child.getValue(ItemType.class);
                    itemB.add(value);
                }
                //passes Array list to bigger context
                basketB = itemB;
                //Loops to display data
                int count = 0;
                int c = 0;
                int catCH = 0;
                ItemType holderB = new ItemType();

                while(catCount != catCH)//---------------------------------------------------------------------catCount is one more than array
                {
                    while(count != basketB.size())
                    {
                        holderB = basketB.get(count);
                        if(email.equals(holderB.getUser()) && holderB.getCategoryId().equals(info[catCH][0]))
                        {
                            c++;
                        }
                        count++;
                    }
                    info[catCH][2] = String.valueOf(c);
                    c = 0;
                    count = 0;
                    catCH++;
                }
                catCH = 0;

                //Creates Percentage taken graph ----------------------
                pieChart = (PieChart) findViewById(R.id.percentC);

                //Adds data into graph
                List<PieEntry> entries = new ArrayList<>();
                int holder = 0;
                while(holder != catCount)
                {
                    items = items + Integer.parseInt(info[holder][2]);
                    holder++;
                }
                holder = 0;
                float fInfo;
                float floater;
                while(holder != catCount)
                {
                    fInfo = Integer.valueOf(info[holder][2]);
                    floater = fInfo / items * 100;
                    entries.add(new PieEntry(floater, info[holder][0]));
                    holder++;
                }
                holder = 0;
                PieDataSet set = new PieDataSet(entries, "Percentage %");
                set.setSliceSpace(2);
                Description description = pieChart.getDescription();
                description.setEnabled(false);
                PieData data = new PieData(set);
                pieChart.setData(data);
                pieChart.invalidate(); // refresh


                //Creates goal graph ---------------------------------
                barChart = findViewById(R.id.goalC);
                //Adds data to graph
                List<BarEntry> barEntries = new ArrayList<>();

                holder = 0;
                float fInfoC;
                float fInfoCB;
                float floaterC;
                String[] bNames = new String[catCount];
                while(holder != catCount)
                {
                    bNames[holder] = info[holder][0];
                    fInfoC = Integer.valueOf(info[holder][2]);
                    fInfoCB = Integer.valueOf(info[holder][1]);
                    floaterC = fInfoC / fInfoCB * 100;
                    if(fInfoC > fInfoCB)
                    {
                        floaterC = 100;
                    }
                    barEntries.add(new BarEntry(holder, floaterC));
                    holder++;
                }
                holder = 0;
                BarDataSet setb = new BarDataSet(barEntries, "Percentage %");
                //Sets the x axis names
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(bNames));
                xAxis.setLabelCount(catCount);//--------------------------------------------------------------------------------------



                //Pushes data to chart
                BarData datab = new BarData(setb);
                Description descriptionB = barChart.getDescription();
                descriptionB.setEnabled(false);
                barChart.setData(datab);
                barChart.invalidate(); // refresh

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        });
    }

    public void btnGoalBack(View view)
    {
        IntentHelper.openIntent(Charts.this,"testing", Collections.class);
    }
}
//Bibliography:
//Stackoverflow. n/a. stackowerflow, n/a. [Online].
//Available at: https://stackoverflow.com/ [Accessed 1 June 2021]
//Study tonight. n/a. Get Value from the EditText and Set value to the TextView, n/a. [Online].
//Available at: https://www.studytonight.com/android/get-edittext-set-textview [Accessed 1 June //2021]
//Shahrokni, M. 2015. Why I Cant Compare My String From Edittext To My String?, 8 May 2015. //[Online].
//Available at: https://www.codeproject.com/Questions/989291/Why-I-Cant-Compare-My-String-//From-Edittext-To-My-S [Accessed 1 June 2021]
//Firebase . n/a. Firebase Documentation, n/a. [Online].
//Available at: https://firebase.google.com/docs [Accessed 1 June 2021]
//javaTpont. n/a. Find unique elements in array Java,. [Online].
//Available at: https://www.javatpoint.com/find-unique-elements-in-array-//java#:~:text=In%20Java%2C%20the%20simplest%20way,element%20from%20the%20hashmap%2//0keySet [Accessed 1 June 2021]
//Anchit. 2016. Displaying Error if no item from a spinner is selected, 19November 2016. [Online].
//Available at: https://mobikul.com/displaying-error-no-item-spinner-selected/ [Accessed 13 July 2021]
//htmlcolorcodes. n/a. HTML COLOR CODES, n/a. [Online].
//Available at: https://htmlcolorcodes.com/ [Accessed 1 June 2021]
//MV. T. 2020. Java String to Int – How to Convert a String to an Integer, 23 November 2020. [Online].
//Available at: https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/ [Accessed 13 July 2021]
//MPAndroidChart.n/a. MPAndroidChart is a powerful & easy to use chart library for Android,. [Online].
//Available at: https://weeklycoding.com/mpandroidchart/  [Accessed 13 July 2021]
//How to Change the Background Color of a Button in Android using ColorStateList. 2020.YouTube video, added by Rahul Pandey.[Online]. Available at: https://www.youtube.com/watch?v=xWWnrh_Gks0  [Accessed 13 July 2021]
//011 Grouped Bar Chart : MP Android Chart Tutorial. 2018. Youtube video, added by
//Sarthi Technology. [Online]. Available at: https://www.youtube.com/watch?v=Bd76zMHdrDE [Accessed 13 July 2021]
//Android Beginner Tutorial Series. 2017. Youtube video by CodingWithMitch. [Online]. Available at: https://www.youtube.com/c/CodingWithMitch/videos [Accessed 13 July 2021]