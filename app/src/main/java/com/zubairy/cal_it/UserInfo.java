package com.zubairy.cal_it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity {

    private static final String TAG = UserInfo.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail , inputPhone , inputAge , inputWeight , inputHeight;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Displaying toolbar icon
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.contact_form_title);
        inputName = (EditText) findViewById(R.id.edit1);
        inputEmail = (EditText) findViewById(R.id.edit2);
        inputPhone = (EditText) findViewById(R.id.edit3);
        inputAge = (EditText) findViewById(R.id.edit4);
        inputWeight = (EditText) findViewById(R.id.edit5);
        inputHeight = (EditText) findViewById(R.id.edit6);
        btnSave = (Button) findViewById(R.id.button1);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
//        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "App title updated");
//
//                String appTitle = dataSnapshot.getValue(String.class);
//
//                // update toolbar title
//               // getSupportActionBar().setTitle(appTitle);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read app title value.", error.toException());
//                Toast.makeText(UserInfo.this, "Authentication failed." ,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();
                String age = inputAge.getText().toString();
                String weight = inputWeight.getText().toString();
                String height = inputHeight.getText().toString();
                createUser(name, email , phone , age , weight , height);
                // Check for already existed userId
//                if (TextUtils.isEmpty(userId)) {
//                    createUser(name, email , phone , age , weight , height);
//                } else {
//                   // updateUser(name, email);
//                }
            }
        });

       // toggleButton();
    }

    // Changing button text
   /* private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    } */

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email , String phone , String age , String weight , String height) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        Users users = new Users(name, email , phone , age , weight , height);

        mFirebaseDatabase.child(userId).setValue(users);

        //addUserChangeListener();
    }

    /**
     * User data change listener
     */
  /*  private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);

                // Display newly updated name and email
                txtDetails.setText(user.name + ", " + user.email);

                // clear edit text
                inputEmail.setText("");
                inputName.setText("");

                //toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    } */

   /* private void updateUser(String name, String email) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);
    } */
}