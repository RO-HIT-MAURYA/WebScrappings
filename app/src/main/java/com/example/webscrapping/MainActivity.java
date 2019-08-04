package com.example.webscrapping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> companyNameList = new ArrayList<>();
    private ArrayList<String> currentPriceList = new ArrayList<>();
    private ArrayList<String> percentChangeList = new ArrayList<>();
    private ArrayList<String> volumeList = new ArrayList<>();
    private ArrayList<String> equityList = new ArrayList<>();
    private ArrayList<String> faceValueList = new ArrayList<>();
    private ArrayList<String> marketCapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LongTask().execute();
        loadData();
    }

    Handler handler = new Handler();
    Runnable runnable;
    private void loadData()
    {
        runnable = new Runnable() {
            @Override
            public void run() {


                handler.postDelayed(this,20000);// on every 20sec data will change if data is changed on website.
            }
        };
    }

    private class LongTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://money.rediff.com/money/jsp/market_cap.jsp").get();
                //Log.e("stringIs",document.toString());
                //String string = document.toString();
                Elements elements = document.select("table[class=dataTable]");
                elements = elements.select("tbody");

                extractData(elements);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void extractData(Elements elements) {
        // for company name
        Elements subElements = elements.select("tr").select("td").select("a");
        String string;
        companyNameList = new ArrayList<>();
        for (Element element : subElements) {
            string = element + "";
            string = string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"));
            companyNameList.add(string);
        }
        Log.e("companyNameListIs", companyNameList + "");

        //for rest
        currentPriceList = new ArrayList<>();
        percentChangeList = new ArrayList<>();
        volumeList = new ArrayList<>();
        equityList = new ArrayList<>();
        faceValueList = new ArrayList<>();
        marketCapList = new ArrayList<>();
        subElements = elements.select("tr");
        Elements innerElements;
        for (int i = 0; i < subElements.size(); i++) {
            innerElements = subElements.eq(i).select("td");
            //Log.e("innerElementIs",innerElements+"");
            for (int j = 1; j < innerElements.size(); j++) {
                string = innerElements.get(j) + "";
                string = string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"));

                if (j == 1)
                    currentPriceList.add(string);
                else if (j == 2)
                    percentChangeList.add(string);
                else if (j == 3)
                    volumeList.add(string);
                else if (j == 4)
                    equityList.add(string);
                else if (j == 5)
                    faceValueList.add(string);
                else if (j == 6)
                    marketCapList.add(string);
            }
        }

        Log.e("currentPriceListIs", currentPriceList + "");
        Log.e("changeListIs", percentChangeList + "");
        Log.e("volumeListIs", volumeList + "");
        Log.e("equityListIs", equityList + "");
        Log.e("faceValueIs", faceValueList + "");
        Log.e("marketCapListIs", marketCapList + "");

        final ListView listView = findViewById(R.id.listView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new CustomListAdapter());
            }
        });
    }



    private class CustomListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return marketCapList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.inflatable_view, viewGroup, false);

            ((TextView) view.findViewById(R.id.companyName)).setText(companyNameList.get(i));
            ((TextView) view.findViewById(R.id.currentPrice)).setText(currentPriceList.get(i));
            ((TextView) view.findViewById(R.id.percentChange)).setText(percentChangeList.get(i));
            ((TextView) view.findViewById(R.id.volume)).setText(volumeList.get(i));
            ((TextView) view.findViewById(R.id.equity)).setText(equityList.get(i));
            ((TextView) view.findViewById(R.id.faceValue)).setText(faceValueList.get(i));
            ((TextView) view.findViewById(R.id.marketCap)).setText(marketCapList.get(i));

            return view;
        }
    }
}
