package com.example.a19003296_opsc_t2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class IntentHelper {

    public static void openIntent(Context context, String order, Class passTo){
        Intent i = new Intent(context, passTo);
        i.putExtra("order", order);
        context.startActivity(i);
    }

    public static void shareIntent(Context context, String order){
        Intent sendIntent = new Intent();
        //
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, order);
        //
        sendIntent.setType("set/plain");
        //
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
    }

    public static void shareIntent(Context context, String productName, String customerName, String customerCell){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        //
        Bundle shareOrderDetails = new Bundle();
        shareOrderDetails.putString("productName", productName);
        shareOrderDetails.putString("customerName", customerName);
        shareOrderDetails.putString("customerCell", customerCell);
        //
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareOrderDetails);
        sendIntent.setType("text/plain");
        //
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        context.startActivity(shareIntent);
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


