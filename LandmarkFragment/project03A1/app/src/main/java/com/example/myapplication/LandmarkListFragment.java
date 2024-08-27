package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.ListFragment;


public class LandmarkListFragment extends ListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }
    //define listener interface for selecting a landmark
    interface OnLandmarkSelectedListener {
        void onLandmarkSelected(String url);
    }

    //listener to handle landmark selection events
    OnLandmarkSelectedListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //attempt to attach listener from context
            callback = (OnLandmarkSelectedListener) context;
        } catch (ClassCastException e) {
            //if context does not implement the required listener
            throw new ClassCastException(context.toString() + "implement OnLandmarkSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        return inflater.inflate(R.layout.fragment_landmark_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get landmarks array from resources
        String[] landmarks = getResources().getStringArray(R.array.chicago_landmarks);
        //create and set list adapter for displaying landmarks
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, landmarks);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //mark the clicked list item as checked
        getListView().setItemChecked(position, true);
        //get urls array from resources
        String[] urls = getResources().getStringArray(R.array.landmark_urls);
        //trigger callback with the selected landmark's url
        callback.onLandmarkSelected(urls[position]);
    }

}
