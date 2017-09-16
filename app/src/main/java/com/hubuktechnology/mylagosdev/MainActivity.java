package com.hubuktechnology.mylagosdev;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    ArrayAdapter<responseModel> mAdapater;
    MyAdapter myAdapter;
   public responseModel[] developers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String lagsoDevURI = "https://api.github.com/search/users?q=language:java+location:lagos";
        new GetLagosDevs().execute(lagsoDevURI);
    }
    class GetLagosDevs extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            ///TODO: Network call
            GetRequest getRequest = new GetRequest();
            String response = null;
            try {
                response = getRequest.run(strings[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject object = new JSONObject(response);
               JSONArray jsonArray = object.getJSONArray("items");
                developers = new responseModel[jsonArray.length()];
                for (int i= 0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    responseModel newdev = new responseModel();
                    newdev.setLogin(jsonObject.getString("login"));
                    newdev.setAvatar_url(jsonObject.getString("avatar_url"));
                    developers[i] = newdev;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListView =  (ListView) findViewById(R.id.list_view);
            myAdapter = new MyAdapter() {
                @Override
                public int getCount() {
                    return developers.length;
                }

                @Override
                public Object getItem(int i) {
                    return developers[i];
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }
            };
            mListView.setAdapter(myAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, DeveloperDetail.class);
                    intent.putExtra("dev", developers[i].login);
                    intent.putExtra("display",developers[i].avatar_url);
                    startActivity(intent);
                }
            });
        }
    }

    private abstract class MyAdapter extends BaseAdapter {
        // override other abstract methods here
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_model, container, false);
            }
            responseModel dev = (responseModel) getItem(position);
            ((TextView) convertView.findViewById(R.id.devName))
                    .setText(dev.getLogin());

            ImageView img = ((ImageView) convertView.findViewById(R.id.devImage));
            Picasso.with(getApplicationContext()).load(dev.getAvatar_url()).into(img);
            return convertView;
        }
    }
}
