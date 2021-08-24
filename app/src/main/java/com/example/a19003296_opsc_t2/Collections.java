package com.example.a19003296_opsc_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//Notes:
//Initiated Categories will later be used toto filter by category;
//The ide is to have a user link a bunch of categories to their items but to only initiate those that they want searchable or want to have a goal attached
//The Collections will later be clickable and will redirect to a filtered list of items
public class Collections extends AppCompatActivity {
    //Declaration
    String order = "a"; //getIntent().getStringExtra("order"); //causes crash
    ArrayList<CategoryClass> basket = new ArrayList<CategoryClass>();
    //Add send back if not signed in

    //To retrieve username
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        //Putting data from list into list
        final ArrayList<CategoryClass> item = new ArrayList<CategoryClass>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
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
                String cat = "";
                while(count < basket.size())
                {
                    CategoryClass holder = new CategoryClass();
                    holder = basket.get(count);
                    String a = "Category: " + holder.getCategoryName() + "\nGoal: " + holder.getGoal() + "\n \n \n";
                    if(email.equals(holder.getUser()))
                    {
                        cat = cat + a;
                    }
                    count++;
                }
                count = 0;
                //Prints the data
                TextView multiTB = (TextView)findViewById(R.id.tbCategoriesOutput);
                multiTB.setText(cat);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        });
    }

    public void btnEditCategory(View view)
    {
        Toast.makeText(Collections.this, "Future feature", Toast.LENGTH_SHORT).show();
    }


    public void btnAddCategory(View view)
    {
        IntentHelper.openIntent(Collections.this,order, AddCategory.class);
    }

    public void btnBack(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Collections.this, "Logged out", Toast.LENGTH_SHORT).show();
        IntentHelper.openIntent(Collections.this,order, MainActivity.class);
    }

    public void btnGoals(View view)
    {
        IntentHelper.openIntent(Collections.this,order, Charts.class);
    }

    public void btnAllItems(View view)
    {
        IntentHelper.openIntent(Collections.this,order, ItemList.class);
    }
    ////use where to find selected item ID under specific user
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
