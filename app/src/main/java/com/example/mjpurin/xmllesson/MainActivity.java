package com.example.mjpurin.xmllesson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> list;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTask task=new MyTask();
        task.execute("http://10.0.2.2/addressbook.json");
    }

    public void jsonParser(String data){
        list=new ArrayList<Person>();

        try {
            JSONObject root=new JSONObject(data);
            JSONArray arr=root.getJSONArray("address");
            for(int i=0;i<arr.length();i++){
                Person p=new Person();
                JSONObject obj=arr.getJSONObject(i);
                if(obj.has("name")){
                    p.name=obj.getString("name");
                }
                if(obj.has("email")){
                    p.email=obj.getString("email");
                }
                list.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            HttpManager hm=new HttpManager();

            return hm.reqText(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jsonParser(s);
            lv=(ListView)findViewById(R.id.listView);
            ArrayAdapter<String> adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list);
            lv.setAdapter(adapter);

        }
    }
}
