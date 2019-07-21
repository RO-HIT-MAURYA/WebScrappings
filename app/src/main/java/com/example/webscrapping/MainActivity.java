package com.example.webscrapping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.service.autofill.FieldClassification;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<String> companyNameList = new ArrayList<>();
    private ArrayList<String> currentPriceList = new ArrayList<>();
    private ArrayList<String> changeList = new ArrayList<>();
    private ArrayList<String> volumeList = new ArrayList<>();
    private ArrayList<String> equityList = new ArrayList<>();
    private ArrayList<String> faceValueList = new ArrayList<>();
    private ArrayList<String> marketCapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new InnerClass().execute();
    }

    private class InnerClass extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
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

    private void extractData(Elements elements)
    {
        // for company name
        Elements subElements = elements.select("tr").select("td").select("a");
        String string;
        companyNameList = new ArrayList<>();
        for (Element element : subElements)
        {
            string = element+"";
            string = string.substring(string.indexOf(">")+1,string.lastIndexOf("<"));
            companyNameList.add(string);
        }
        Log.e("companyNameListIs",companyNameList+"");

        //for rest
        currentPriceList = new ArrayList<>();
        changeList = new ArrayList<>();
        volumeList = new ArrayList<>();
        faceValueList = new ArrayList<>();
        marketCapList = new ArrayList<>();
        /*subElements = elements.select("tr");
        Elements innerElements;
        for (int i=0; i<subElements.size(); i++)
        {
            innerElements  = subElements.eq(i).select("td");
            //Log.e("innerElementIs",innerElements+"");
            for (int j=1; j<innerElements.size(); j++ )
            {
                string = innerElements.get(j) + "";
                string = string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"));

                if (j==1)
                    currentPriceList.add(string);
            }
        }

        Log.e("listIs",currentPriceList+"");*/

    }
}
