package com.example.mvvmbanglapart2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmbanglapart2.R;
import com.example.mvvmbanglapart2.model.ContactUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactUser> userList;

    public void getContactList(List<ContactUser> userList){
        this.userList= userList;
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.single_contact,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(userList.get(position).getContactImage()).centerCrop()
                .placeholder(R.drawable.profile).into(holder.circleImageView);
        holder.contactId.setText(userList.get(position).getContactId());
        holder.contactName.setText(userList.get(position).getContactName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private TextView contactId;
        private TextView contactName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.singleImageId);
            contactId= itemView.findViewById(R.id.singleContactId);
            contactName= itemView.findViewById(R.id.singleNameId);

        }
    }
}