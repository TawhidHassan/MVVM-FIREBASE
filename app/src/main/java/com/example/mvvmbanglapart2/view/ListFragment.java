package com.example.mvvmbanglapart2.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mvvmbanglapart2.R;
import com.example.mvvmbanglapart2.model.ContactUser;
import com.example.mvvmbanglapart2.viewmodel.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<ContactUser> userList= new ArrayList<>();
    //view Model
    private ContactViewModel contactViewModel;

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
        searchView= view.findViewById(R.id.searchViewId);
        recyclerView= view.findViewById(R.id.recycleViewId);
    }

    private void setupRecycle() {
        contactViewModel.show();
        contactViewModel.getContactLiveData.observe(getViewLifecycleOwner(), new Observer<List<ContactUser>>() {
            @Override
            public void onChanged(List<ContactUser> contactUsers) {
                Toast.makeText(getActivity(), contactUsers.get(0).getContactName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViewModel() {
        contactViewModel= new ViewModelProvider(getActivity(),ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ContactViewModel.class);
    }
}