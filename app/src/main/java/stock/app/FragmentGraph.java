package stock.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.patriques.output.digitalcurrencies.IntraDay;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FragmentGraph extends AppCompatActivity {

    private String TAG = "FragmentGraph";
    private IntraDay data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_graph);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.d(TAG, "Started.");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        Intent intent = getIntent();
        double[] priceData = intent.getExtras().getDoubleArray("priceData");
        String[] timeData = intent.getExtras().getStringArray("timeData");
        System.out.println(timeData[0]);
        GraphView graphView = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i = priceData.length - 1; i >= 0; i--) {
            Date date = new Date();
            try{
                date = parser.parse(timeData[i]);
                System.out.println(date);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            series.appendData(new DataPoint(date.getTime(), priceData[i]),false,priceData.length);
        }
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(priceData.length);
        graphView.addSeries(series);
    }
}