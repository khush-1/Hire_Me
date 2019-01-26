package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<String> branch,stats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("curstatistics");
        branch = new ArrayList<String>();
        stats = new ArrayList<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                    branch.add(userDetails.getKey().toString());
                    stats.add(userDetails.getValue().toString());
                }
                solve2();
                solve();
                //databaseReference.removeEventListener();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void solve() {
        BarChart lineChart = (BarChart) findViewById(R.id.chart);
        lineChart.setDrawBarShadow(false);
        lineChart.setDrawValueAboveBar(true);
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i< stats.size();i++) {
            String valspi = stats.get(i);
            if (valspi.equals("n/a"))
                valspi = "0";
            entries.add(new BarEntry(Float.parseFloat(valspi), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "Year");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0;i < stats.size();i++) {
            String tmp = Integer.toString(i+1);
            labels.add(tmp);
        }
        BarData data = new BarData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
        lineChart.setData(data);
    }
    public void solve2()
    {
        LineChart lineChart = (LineChart) findViewById(R.id.chart2);
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(int i = 0; i< stats.size();i++) {
            String valspi = stats.get(i);
            if (valspi.equals("n/a"))
                valspi = "0";
            else if(valspi.equals(""))
                valspi = "0";
            else
                entries.add(new Entry(Float.parseFloat(valspi), i));
        }

        LineDataSet dataset = new LineDataSet(entries, "Year");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0;i < stats.size();i++)
        {
            labels.add(branch.get(i));
        }

        LineData data;
        data = new LineData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
        //dataset.setDrawCubic(true);
        dataset.setDrawCircles(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(2000);
    }

}



