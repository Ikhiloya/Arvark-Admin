package com.loya.android.arvark_admin.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loya.android.arvark_admin.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Post extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 22;
    private Button submitBtn;
    private ImageView equipmentImage;
    private Spinner equipmentCategorySpinner;
    private EditText equipmentNameField, equipmentOriginalPriceField, equipmentDiscountPriceField, equipmentSizeField, equipmentDescField;
    private String equipmentName, equipmentOriginalPrice, equipmentDiscountPrice, equipmentSize, equipmentDesc, equipmentCategory;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseCategory;
    private Uri mImageUri;

    private StorageReference mStorageRef;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //instance of firebase auth
        mAuth = FirebaseAuth.getInstance();
        mDatabaseCategory = FirebaseDatabase.getInstance().getReference().child("Category");
        //a directory is created to store all equipment images to the Firebase Storage
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Equipment_images");

        //init views
        submitBtn = (Button) findViewById(R.id.submitBtn);
        equipmentCategorySpinner = (Spinner) findViewById(R.id.spinner);
        equipmentImage = (ImageView) findViewById(R.id.equipment_image);
        equipmentNameField = (EditText) findViewById(R.id.equipment_name);
        equipmentOriginalPriceField = (EditText) findViewById(R.id.equipment_original_price);
        equipmentDiscountPriceField = (EditText) findViewById(R.id.equipment_discount_price);
        equipmentSizeField = (EditText) findViewById(R.id.equipment_size);
        equipmentDescField = (EditText) findViewById(R.id.equipment_description);
        mProgressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);


        setUpCategorySpinner();

        equipmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //image picker intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Post.this, equipmentCategory, Toast.LENGTH_LONG).show();

                postNewEquipment();

//                String user_id = mAuth.getCurrentUser().getUid();
//                // Write a message to the database
//                DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
//                mDatabaseUsers.child(user_id).child("name").setValue("Ikhiloya");
//                mDatabaseUsers.child(user_id).child("company_name").setValue("Arvark");
//                mDatabaseUsers.child(user_id).child("address").setValue("29 Kotie STreet");
//                mDatabaseUsers.child(user_id).child("phone").setValue("070660044003");
//                mDatabaseUsers.child(user_id).child("business_type").setValue("Rental");
//                mDatabaseUsers.child(user_id).child("lga").setValue("Warri South");
//                mDatabaseUsers.child(user_id).child("state").setValue("Delta");
//                finish();
            }
        });


    }

    private void postNewEquipment() {
        equipmentName = equipmentNameField.getText().toString().trim();
        equipmentOriginalPrice = equipmentOriginalPriceField.getText().toString();
        equipmentDiscountPrice = equipmentDiscountPriceField.getText().toString().trim();
        equipmentSize = equipmentSizeField.getText().toString().trim();
        equipmentDesc = equipmentDescField.getText().toString().trim();

        final DatabaseReference newEquipment = mDatabaseCategory.child(equipmentCategory).push();
        if (!validateDetails()) {
            return;
        }

        showProgress();

        //upload the image to the Firebase Storage so that the download url can be retrieved
        //and saved to the database
        StorageReference filePath = mStorageRef.child(mImageUri.getLastPathSegment());
        filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                //creates a new child inside the User directory
                //stores the name and profile image of the current user to the database using the user id
                newEquipment.child(equipmentName + " image").setValue(downloadUrl);
                newEquipment.child("equipment_name").setValue(equipmentName);
                newEquipment.child("equipment_original_price").setValue(equipmentOriginalPrice);
                newEquipment.child("equipment_discount_price").setValue(equipmentDiscountPrice);
                newEquipment.child("equipment_size").setValue(equipmentSize);
                newEquipment.child("equipment_description").setValue(equipmentDesc);

                Toast.makeText(Post.this, "Post successful!", Toast.LENGTH_LONG).show();
                dismissProgress();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to post","msg:" + e.toString());
                Toast.makeText(Post.this, "Failed to post", Toast.LENGTH_LONG).show();
                dismissProgress();
            }
        });

    }

    private void setUpCategorySpinner() {
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.category,
                android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        equipmentCategorySpinner.setAdapter(categoryAdapter);

        equipmentCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equipmentCategory = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setAllowRotation(true)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();

                equipmentImage.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private boolean validateDetails() {
        boolean valid = true;

        if (mImageUri == null) {
            Toast.makeText(Post.this, "Please select an image", Toast.LENGTH_LONG).show();
            valid = false;
        }
        String equipmentName = equipmentNameField.getText().toString();
        if (TextUtils.isEmpty(equipmentName)) {
            equipmentNameField.setError("Required.");
            valid = false;
        } else {
            equipmentNameField.setError(null);
        }

        String equipmentOriginalPrice = equipmentOriginalPriceField.getText().toString();
        if (TextUtils.isEmpty(equipmentOriginalPrice)) {
            equipmentOriginalPriceField.setError("Required.");
            valid = false;
        } else {
            equipmentOriginalPriceField.setError(null);
        }


        String equipmentDiscountPrice = equipmentDiscountPriceField.getText().toString();
        if (TextUtils.isEmpty(equipmentDiscountPrice)) {
            equipmentDiscountPriceField.setError("Required.");
            valid = false;
        } else {
            equipmentDiscountPriceField.setError(null);
        }

        String equipmentSize = equipmentSizeField.getText().toString();
        if (TextUtils.isEmpty(equipmentSize)) {
            equipmentSizeField.setError("Required.");
            valid = false;
        } else {
            equipmentSizeField.setError(null);
        }

        String equipmentDesc = equipmentDescField.getText().toString();
        if (TextUtils.isEmpty(equipmentDesc)) {
            equipmentDescField.setError("Required.");
            valid = false;
        } else {
            equipmentDescField.setError(null);
        }

        return valid;
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
