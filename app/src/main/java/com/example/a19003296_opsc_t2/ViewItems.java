package com.example.a19003296_opsc_t2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class ViewItems extends AppCompatActivity {
    //Declarations for camera and image:
    private Button fab;
    private ImageView imgCameraImage;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private String catAr[];
    //Spinner object for dropdown
    Spinner UniDDL;
    ArrayList<CategoryClass> basket = new ArrayList<CategoryClass>();
    //declaration for saving to database
    ItemType itemType = new ItemType();
    TextView itemName, description, date, categories;
    String userName;
    DatabaseReference ref;
    //
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    //to be used later to give image unique searchable id

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email = user.getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        //camera
        fab = findViewById(R.id.btnEditAddImage);
        imgCameraImage = findViewById(R.id.img_CameraImage);

        //------------------------------------------------------------
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
                String cat = "";
                int arCount = 0;
                while(count < basket.size())
                {
                    CategoryClass holder = new CategoryClass();
                    holder = basket.get(count);
                    if(email.equals(holder.getUser()))
                    {
                        arCount++;
                    }
                    count++;
                }
                count = 0;
                int countSoft = 0;
                String[] catAR = new String[arCount];
                while(count < basket.size())
                {
                    CategoryClass holder = new CategoryClass();
                    holder = basket.get(count);
                    String a = "Category: " + holder.getCategoryName() + "\nGoal: " + holder.getGoal() + "\n \n \n";
                    if(email.equals(holder.getUser()))
                    {
                        catAR[countSoft] = holder.getCategoryName();
                        countSoft++;
                    }
                    count++;
                }
                //Prevents crashing
                if(catAR[0] == "")
                {
                    catAR[0] = "Create category before proceeding";
                }

                count = 0;
                //Pushes data to drop down list
                //outs data into dropdown

                Spinner DDL = (Spinner) findViewById(R.id.categories);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewItems.this, android.R.layout.simple_spinner_dropdown_item, catAR);
                DDL.setAdapter(adapter);
                UniDDL = DDL;
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }

        });//----------------------------------------
        }

    public void btnEditAddImage(View view)
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCameraImage.setImageBitmap(bitmap);
        }
        //To be replaced by proper ID system for referencing it to an item.
        Date unq;
        Calendar cal = Calendar.getInstance();
        unq = cal.getTime ();
        StorageReference mountainsRef = storageRef.child(String.valueOf(unq));
        imgCameraImage.setDrawingCacheEnabled(true);
        imgCameraImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgCameraImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(datas);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

    public void btnItemBack(View view)
    {
        IntentHelper.openIntent(ViewItems.this,"testing", ItemList.class);
    }

    public void btnItemSave(View view)
    {
        //saving to database
        itemName = (TextView) findViewById(R.id.tbEditItemName);
        description = (TextView) findViewById(R.id.tbEditItemDescription);
        date = (TextView) findViewById(R.id.tbEditItemDate);
        ref = FirebaseDatabase.getInstance().getReference().child("ItemClass");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            String email = user.getEmail();
            userName = email;
        }
        //toString: done in manner to attempt to avoid referencing (pointer) error
        String sItemName;
        String sDescription;
        String sDate;
        String sCategories;
        sItemName = itemName.getText().toString();
        sDescription = description.getText().toString();
        sDate = date.getText().toString();
        //drop down list item
        String text = UniDDL.getSelectedItem().toString();
        sCategories = text;//--------------------------------------------------------------------------------------------------------------------------------------------
        //input not stopped if left blank if the user chooses to do so with intention as it won't crash the app and allows for more possible user use cases. Furthermore the username is automatically added ans that is what will be used to filter, items without categories wont be searcheble as well, which could be used as a feature.
        //Adding information to object
        itemType.setName(sItemName);
        itemType.setDescription(sDescription);
        itemType.setDate(sDate);
        itemType.setCategoryId(sCategories);
        itemType.setUser(userName);

        //Add to database
        ref.push().setValue(itemType);

        //Redirect
        Toast.makeText(ViewItems.this, "Item added", Toast.LENGTH_SHORT).show(); //----------------------------------------------
        IntentHelper.openIntent(ViewItems.this,"testing", ItemList.class);
    }

    //use where to find selected item ID
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
