package com.example.mvvmbanglapart2.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmbanglapart2.model.ContactUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactRepository {
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();// data save


   public MutableLiveData<String> insertContacFireStore(final ContactUser user, Uri uri)
   {

       final MutableLiveData<String>insertResultLiveData=new MutableLiveData<>();
       final String currentUser=firebaseAuth.getCurrentUser().getUid();

       final StorageReference image_path=storageReference.child("profile_image").child(currentUser).child(user.contactId+".jpg");//image save on storage
       image_path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {//dwonload image storage link
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String,String> contactMap= new HashMap<>(); //data save map
                        contactMap.put("contact_Id",user.contactId);
                        contactMap.put("contact_Name",user.contactName);
                        contactMap.put("contact_Image",uri.toString());
                        contactMap.put("contact_Phone",user.contactPhone);
                        contactMap.put("contact_Email",user.contactEmail);
                        contactMap.put("contact_Search",user.contactId);

                        //put the value on fireStore
                        firebaseFirestore.collection("ContactList").document(currentUser).collection("User").document(user.contactId)
                                .set(contactMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        insertResultLiveData.setValue("Upload Successfully");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                insertResultLiveData.setValue(e.toString());
                            }
                        });

                    }
                });
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
                insertResultLiveData.setValue(e.toString());
           }
       });

       return insertResultLiveData;
   }

   public MutableLiveData<List<ContactUser>> getDatFromFireStore()
   {
       final MutableLiveData<List<ContactUser>> getFireStoreMutableLiveData=new MutableLiveData<>();
       String currentUser=firebaseAuth.getCurrentUser().getUid();
       final List<ContactUser> contactUserList=new ArrayList<>();
       firebaseFirestore.collection("ContactList").document(currentUser).collection("User")
               .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               contactUserList.clear();
               for(DocumentSnapshot documentSnapshot : task.getResult())
               {
                   String id= documentSnapshot.getString("contact_Id");
                   String name= documentSnapshot.getString("contact_Name");
                   String image= documentSnapshot.getString("contact_Image");
                   String phone= documentSnapshot.getString("contact_Phone");
                   String email= documentSnapshot.getString("contact_Email");
                   ContactUser user= new ContactUser(id,name,image,phone,email);
                   contactUserList.add(user);
               }
               getFireStoreMutableLiveData.setValue(contactUserList);
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

           }
       });


       return getFireStoreMutableLiveData;
   }

   public void deleteDataFirebase(final String id)
   {
       final String currentUser=firebaseAuth.getCurrentUser().getUid();
       StorageReference deleteImage=storageReference.child("profile_image").child(currentUser).child(id+".jpg");
       deleteImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               firebaseFirestore.collection("ContactList").document(currentUser).collection("User")
                       .document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {

                   }
               });
           }
       });

   }
}
