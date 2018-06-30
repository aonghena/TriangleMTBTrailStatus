package com.example.a.trianglemtbtrailstatus;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.nio.file.Files;
import java.util.*;
import android.widget.TextView;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.content.*;
import android.support.v7.widget.Toolbar;

import android.view.*;

import java.io.IOException;
import android.support.v7.app.ActionBar;


public class MainActivity extends AppCompatActivity {


    private  GridView gv;
    private Hashtable numbers;
    private SwipeRefreshLayout s;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numbers = new Hashtable();
        numbers.put("Beaver Dam", "tel:9196761027");
        numbers.put("Crabtree", "tel:9194603390");
        numbers.put("Forest Ridge", "tel:9195566781");
        numbers.put("Harris", "tel:9193874342");
        numbers.put("Little River", "tel:9197325505");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("TMTB Trail Status");
        toolbar.setBackgroundColor(Color.parseColor("#9E9E9E"));




        gv = findViewById(R.id.gv);




        new DataGrabber().execute();

        s = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DataGrabber().execute();
                s.setRefreshing(false);
            }
        });



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v,
                int position, long id) {
            String str = gv.getItemAtPosition(position).toString();
            if(numbers.containsKey(str)){
                Intent i=new Intent(Intent.ACTION_DIAL,android.net.Uri.parse(numbers.get(str).toString()));
                startActivity(i);
            }
        }
    });



    }



    private class DataGrabber extends AsyncTask<Void, Void, Void> {
        private ArrayList<String> all = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {

            try{
                Document doc = Jsoup.connect("http://www.trianglemtb.com/mobiletrailstatus.php").get();
                Element table = doc.select("table").get(0); //select the first table.
                Elements rows = table.select("tr");


                for (int i = 1; i < rows.size()-1; i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");
                    for(int y=0; y < 3; y++){
                        all.add(cols.get(y).text().trim());
                    }
                }


            }catch (Exception e) {
                android.util.Log.e("MYAPP", "exception", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            GridView gv = (GridView) findViewById(R.id.gv);
            CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, R.layout.row1,all);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }
}
