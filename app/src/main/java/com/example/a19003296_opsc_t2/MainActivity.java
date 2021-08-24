package com.example.a19003296_opsc_t2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button reg;
    TextView userName, Password;
    //Firebase, objectlist, view recycler, cardview
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();

        btn = (Button) findViewById(R.id.btnLogIn);
        reg = (Button) findViewById(R.id.btnCreateAccount);

        //Log In button:
        btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Get text
                    userName  = (TextView) findViewById(R.id.tbUsername);
                    Password = (TextView) findViewById(R.id.tbPassword);
                    String name;
                    name = userName.getText().toString();
                    String pw;
                    pw = Password.getText().toString();

                    if (!name.isEmpty() && !pw.isEmpty())
                    {
                        mFirebaseAuth.signInWithEmailAndPassword(name, pw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {

                                    Toast.makeText(MainActivity.this, "Logged on", Toast.LENGTH_SHORT).show();
                                    IntentHelper.openIntent(MainActivity.this,"testing", Collections.class);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        );

        //CREATE ACCOUNT button
        reg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //getContext
                userName = (TextView) findViewById(R.id.tbUsername);
                Password = (TextView) findViewById(R.id.tbPassword);
                //toString
                String name;
                name = userName.getText().toString();
                String pw;
                pw = Password.getText().toString();
                boolean x = false;
                if(pw.length() < 8)
                {
                    Toast.makeText(MainActivity.this, "Please enter password with at least 8 characters", Toast.LENGTH_SHORT).show();
                    x = true;
                }

                //modified from stackoverflow
                String Pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern password = java.util.regex.Pattern.compile(Pattern);
                java.util.regex.Matcher match = password.matcher(name);
                if(match.matches() == false)
                {
                    Toast.makeText(MainActivity.this, "Please enter valid e-mail", Toast.LENGTH_SHORT).show();
                    x = true;
                }

                if ((!name.isEmpty() && !pw.isEmpty()) && x == false)
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(name, pw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Successfully created account", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Please log in", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Failed, or account is in use or e-mail doesn't exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT);
                }
            }
        });
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
