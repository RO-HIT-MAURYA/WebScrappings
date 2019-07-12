package com.example.webscrapping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

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
                String string = document.toString();
                if (string.contains("Reliance Inds."))
                    Log.e("conditionIs",true+"");
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
}
