/*
CSE535: Mobile Computing (Spring 2018) Assignment #1
Group Number: 9
@TeamMember1: Akshay Sonawane, 1213371677, apsonawa@asu.edu
@TeamMember2: Jad Aboul Hosn, 1214075576, jadhosn@asu.edu
@TeamMember3: Salil Vartikar, 1213373523, svartika@asu.edu
@TeamMember4: Vartika Sharma, 1213413043, vsharm50@asu.edu

#Added Dependency:
compile 'com.jjoe64:graphview:4.2.1'
#Reference code from the online documentation for the library:
http://www.android-graphview.org/realtime-chart/

*/

package com.test.graphview.group9;

//Import all the necessary modules
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import static android.graphics.Color.BLUE;


public class MainActivity extends AppCompatActivity {

    private final Handler hndlr = new Handler();
    private Runnable runn;

    Button AddBtn;
    Button Stop;
    // We use GraphView library to create and visualize our graph
    GraphView graph;

    private static final Random rnd = new Random();
    public LineGraphSeries<DataPoint> input;

    private double lastX = 5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_name = (EditText) findViewById(R.id.editText_name); //EditText is defined as edittext in xml
        final TextView tv_name = (TextView) findViewById(R.id.textView_name); //TextView is defined as textview in xml
        final EditText et_id = (EditText) findViewById(R.id.editText_id); //EditText is defined as edittext in xml
        final TextView tv_id = (TextView) findViewById(R.id.textView_id); //TextView is defined as textview in xml
        final EditText et_age = (EditText) findViewById(R.id.editText_age); //EditText is defined as edittext in xml
        final TextView tv_age = (TextView) findViewById(R.id.textView_age); //TextView is defined as textview in xml
        Button Enter = (Button) findViewById(R.id.button1); //Button is defined as button in xml
        Button Clear = (Button) findViewById(R.id.button2); //Button is defined as button in xml

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString(); //Get txt from et when button is clicked
                tv_name.append("\n");
                tv_name.append(name);
                et_name.setText("");//String age = et_age.getText().toString(); //Get txt from et when button is clicked
                String id = et_id.getText().toString(); //Get txt from et when button is clicked
                tv_id.append("\n");
                tv_id.append(id);
                et_id.setText("");//String age = et_age.getText().toString(); //Get txt from et when button is clicked
                String age = et_age.getText().toString(); //Get txt from et when button is clicked
                tv_age.append("\n");
                tv_age.append(age);
                et_age.setText("");//String age = et_age.getText().toString(); //Get txt from et when button is clicked
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = et_name.getText().toString(); //Get txt from et when button is clicked
                tv_name.setText("     NAME"); //Set text extracted from et in tv

                //String id = et_id.getText().toString(); //Get txt from et when button is clicked
                tv_id.setText("       ID"); //Set text extracted from et in tv
                et_id.setText("");//String age = et_age.getText().toString(); //Get txt from et when button is clicked
                tv_age.setText("        AGE"); //Set text extracted from et in tv

            }
        });

        AddBtn = (Button)findViewById(R.id.AddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        Stop = (Button)findViewById(R.id.stop);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        graph = (GraphView) findViewById(R.id.graph);
        //Set Graph Title and X-Axis and Y-Axis Labels
        graph.setTitle("Group 9: Health Monitoring UI");
        graph.setTitleTextSize((float) 60.0);
        graph.setTitleColor(BLUE);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Patient Data");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(BLUE);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time/s");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(BLUE);

        Viewport viewport = graph.getViewport();
        viewport.setScrollable(false);

        //input = new LineGraphSeries<>(generateData());
        input = new LineGraphSeries<>();
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.getViewport().setXAxisBoundsManual(true);
    }

    /*
    @Override
    public void onResume()
    {
        super.onResume();
    }
    */

    public void start() {
        graph.addSeries(input);
        super.onStart();
        runn = new Runnable() {
            @Override
            public void run() {
                //input.resetData(generateData());
                lastX+=1d;
                input.appendData(new DataPoint(lastX, rndGen()), true, 100);
                hndlr.postDelayed(this, 270);
            }
        };
        hndlr.postDelayed(runn, 270);
    }
    public void stop()
    {
        hndlr.removeCallbacks(runn);
        super.onPause();
        graph.removeAllSeries();
    }

    double m =2;
    private double rndGen() {
        return m += rnd.nextDouble()*0.75;
    }
}