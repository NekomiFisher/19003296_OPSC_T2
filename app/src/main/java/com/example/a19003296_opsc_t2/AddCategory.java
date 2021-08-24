package com.example.a19003296_opsc_t2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//Notes:
//Goal is string for database purposes but only allows for int input and can thus be changed to int for processing later
public class AddCategory extends AppCompatActivity {
    String order = "a";
    TextView name, goal;
    DatabaseReference ref;
    String userName;
    CategoryClass categoryClass = new CategoryClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
    }

    //get data
    public void btnSaveCategory(View view) {
        //get tb text and create object to pass
        name = (TextView) findViewById(R.id.tbCategoryName);
        goal = (TextView) findViewById(R.id.tbCategoryGoal);
        ref = FirebaseDatabase.getInstance().getReference().child("CategoryClass");

        //Copied from firebase repository (n/a)
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            String email = user.getEmail();
            userName = email;
        }

        //set class data
        String sName;
        String sGoal;
        String suserName;
        sName = name.getText().toString();
        sGoal = goal.getText().toString();
        suserName = userName;
        if(sGoal.isEmpty())
        {
            sGoal = "0";
        }
        if(sName == "" || sName.isEmpty())
        {
            Toast.makeText(AddCategory.this, "Please enter Category Name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            categoryClass.setCategoryName(sName);
            categoryClass.setGoal(sGoal);
            categoryClass.setUser(suserName);

            //Add to database
            ref.push().setValue(categoryClass);

            //Redirect
            Toast.makeText(AddCategory.this, "Category added", Toast.LENGTH_SHORT).show(); //----------------------------------------------
            IntentHelper.openIntent(AddCategory.this, "testing", Collections.class);
        }
    }

    public void btnCancelCategory(View view) {
        IntentHelper.openIntent(AddCategory.this,order, Collections.class);
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