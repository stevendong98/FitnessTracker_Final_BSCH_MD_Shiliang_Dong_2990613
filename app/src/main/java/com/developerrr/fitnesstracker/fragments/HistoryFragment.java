package com.developerrr.fitnesstracker.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.adapters.QuotesAdapter;
import com.developerrr.fitnesstracker.utils.Constants;
import com.developerrr.fitnesstracker.utils.DataSource;
import com.developerrr.fitnesstracker.utils.Qoute;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {


    RecyclerView recyclerView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<Qoute> qouteList;
    int lastQuotePosition;
    float stepsToday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=view.findViewById(R.id.recyclerHistory);


        preferences = getContext().getSharedPreferences(Constants.prefFileName, MODE_PRIVATE);
        editor = preferences.edit();

        stepsToday= Constants.totalSteps;
        
        qouteList = new ArrayList<>();
        lastQuotePosition = preferences.getInt(Constants.lastMorningCardPosition, 0);


        qouteList.addAll(DataSource.getHomeQoutes());
        qouteList.addAll(DataSource.getMorningQoutes());
        qouteList.addAll(DataSource.getWorkQoutes());


        setupRecyclerView();

        return view;
    }


    private void setupRecyclerView() {

        if(stepsToday>=100){
            if(lastQuotePosition>=qouteList.size()-1){
                lastQuotePosition=0;
                editor.putInt(Constants.lastMorningCardPosition,lastQuotePosition);
                editor.commit();
                editor.apply();
            }

            List<Qoute> list=new ArrayList<>();
            list.add(qouteList.get(lastQuotePosition));
            lastQuotePosition++;
            editor.putInt(Constants.lastMorningCardPosition,lastQuotePosition);
            editor.commit();
            editor.apply();
            QuotesAdapter adapter=new QuotesAdapter(getContext(),list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            Constants.totalSteps=0;
        }



    }


}