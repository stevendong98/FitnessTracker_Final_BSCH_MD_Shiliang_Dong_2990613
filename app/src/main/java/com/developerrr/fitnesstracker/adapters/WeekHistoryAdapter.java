package com.developerrr.fitnesstracker.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developerrr.fitnesstracker.R;
import com.developerrr.fitnesstracker.models.MyDate;
import com.developerrr.fitnesstracker.models.Week;
import com.developerrr.fitnesstracker.roomdb.MyDatabase;
import com.developerrr.fitnesstracker.roomdb.StepDao;
import com.developerrr.fitnesstracker.roomdb.StepTable;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeekHistoryAdapter extends RecyclerView.Adapter<WeekHistoryAdapter.WeekViewHolder> {

    private static final int SUCCESS_STEPS_COUNT = 9999;
    List<Week> weeksList;
    Context context;

    public WeekHistoryAdapter(List<Week> weeksList, Context context) {
        this.weeksList = weeksList;
        this.context = context;
    }



    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item_week,parent,false);

        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder holder, int position) {

        List<StepTable> steps=weeksList.get(position).getDays();

        BarData data = createChartData(steps);
        configureChartAppearance(holder.chart);
        prepareChartData(data, holder.chart);


        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                StepTable selectedBar = null;
                for(StepTable s :steps){
                    if(s.steps==h.getY()){
                        selectedBar=s;
                    }
                }
                String val="GetY "+h.getY()+"\n GetX"+h.getX()+"\n Entry: "+e+"\nString "+h;
                Toast.makeText(context, String.valueOf(selectedBar.steps)+"\n"+selectedBar.dayName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }


    private void prepareChartData(BarData data,BarChart chart) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    private void configureChartAppearance(BarChart chart) {
        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }

    private BarData createChartData(List<StepTable> stepTables) {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();


        for (int i = 0; i < stepTables.size(); i++) {
            float x = i;
            if(stepTables.get(i).steps > SUCCESS_STEPS_COUNT) {
                values.add(new BarEntry(x, stepTables.get(i).steps));
            }else {
                values2.add(new BarEntry(x, stepTables.get(i).steps));
            }
        }


        BarDataSet set1 = new BarDataSet(values, "Success");
        BarDataSet set2 = new BarDataSet(values2, "Failure");


        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[1]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);
        return data;
    }

    class WeekViewHolder extends RecyclerView.ViewHolder{

        BarChart chart;

        public WeekViewHolder(@NonNull View itemView) {
            super(itemView);

            chart=itemView.findViewById(R.id.item_barchart);
        }
    }
}
