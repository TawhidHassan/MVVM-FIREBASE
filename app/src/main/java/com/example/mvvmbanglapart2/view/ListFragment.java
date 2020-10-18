package com.example.mvvmbanglapart2.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmbanglapart2.R;
import com.example.mvvmbanglapart2.adapter.ContactAdapter;
import com.example.mvvmbanglapart2.dialogue.DetailsDialogue;
import com.example.mvvmbanglapart2.model.ContactUser;
import com.example.mvvmbanglapart2.model.UpdateUser;
import com.example.mvvmbanglapart2.viewmodel.ContactViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ListFragment extends Fragment implements ContactAdapter.ClickiInterFace{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<ContactUser> userList= new ArrayList<>();
    private ContactAdapter adapter;
    //view Model
    private ContactViewModel contactViewModel;

    private Button updateImageButton,updateInfoButton;
    private TextView idTextView;
    private EditText nameEditText,phoneEditText,emailEditText;

    private Uri updateUri= null;
    int userPosition;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        setupRecycle();
        //find Section...
        searchView= (SearchView) view.findViewById(R.id.searchViewId);
        recyclerView= view.findViewById(R.id.recycleViewId);
        adapter= new ContactAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupRecycle() {
        //show spots dialogue here.....
        final AlertDialog dialogue= new SpotsDialog.Builder().setContext(getActivity()).setTheme(R.style.Custom).setCancelable(true).build();
        dialogue.show();
        contactViewModel.show();
        contactViewModel.getContactLiveData.observe(getViewLifecycleOwner(), new Observer<List<ContactUser>>() {
            @Override
            public void onChanged(List<ContactUser> contactUsers) {
                dialogue.dismiss();
                userList= contactUsers;
                //userList.clear();
                adapter.getContactList(userList);
                recyclerView.setAdapter(adapter);
//                Toast.makeText(getActivity(), contactUsers.get(0).getContactName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViewModel() {
        contactViewModel= new ViewModelProvider(getActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ContactViewModel.class);
    }

    @Override
    public void onItemClick(int position) {
       openDetailsDialogue(position);
    }

    private void openDetailsDialogue(int position) {
        DetailsDialogue detailsDialogue=new DetailsDialogue(userList,position);
        detailsDialogue.show(getChildFragmentManager(),"Details Dialogue");
    }

    @Override
    public void onLongItemClick(final int position) {

        final String id= userList.get(position).contactId;
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        String[] option= {"Update","Delete"};
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    update(position);
                }
                if(which==1){
                    contactViewModel.delete(id);
                    Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                    userList.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        }).create().show();
    }

    private void update(final int position) {
        userPosition= position;

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        final View view= inflater.inflate(R.layout.update_dialogue,null);
        builder.setView(view).setTitle("Update Contact").setIcon(R.drawable.ic_update).setCancelable(true)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        updateImageButton= view.findViewById(R.id.updateImageButtonId);
        updateInfoButton= view.findViewById(R.id.updateInfoButtonId);
        idTextView= view.findViewById(R.id.updateId);
        nameEditText= view.findViewById(R.id.updateNameId);
        phoneEditText= view.findViewById(R.id.updatePhoneId);
        emailEditText= view.findViewById(R.id.updateEmailId);


        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= userList.get(position).getContactId();
                String name= nameEditText.getText().toString();
                String phone= phoneEditText.getText().toString();
                String email= emailEditText.getText().toString();

                UpdateUser user= new UpdateUser(id,name,phone,email);
                contactViewModel.updateInfo(user);
                Toast.makeText(getActivity(), "Info Update", Toast.LENGTH_SHORT).show();
            }
        });

        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        idTextView.setText("ID: "+userList.get(position).getContactId());
        nameEditText.setText(userList.get(position).getContactName());
        phoneEditText.setText(userList.get(position).getContactPhone());
        emailEditText.setText(userList.get(position).getContactEmail());
        builder.create().show();





    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(getContext(),this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==getActivity().RESULT_OK){
                updateUri= result.getUri();
                String id= userList.get(userPosition).getContactId();
                contactViewModel.updateImage(id,updateUri);
                Toast.makeText(getActivity(), "Image Update", Toast.LENGTH_SHORT).show();
                //circleImageView.setImageURI(updateUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}