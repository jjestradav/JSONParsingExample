package abhiandroid.com.jsonparsingexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    // ArrayList for person names, email Id's and mobile numbers
    ArrayList<String> personNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAsyncTasks myAsyncTasks = new MyAsyncTasks("http://192.168.0.14:8080/BackEnd/ServeletEmployee");
        myAsyncTasks.execute();
        // get the reference of RecyclerView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        // set a LinearLayoutManager with default vertical orientation
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        try {
//            // get JSONObject from JSON file
//            JSONObject obj = new JSONObject(loadJSONFromAsset());
//            // fetch JSONArray named users
//            JSONArray userArray = obj.getJSONArray("users");
//            // implement for loop for getting users list data
//            for (int i = 0; i < userArray.length(); i++) {
//                // create a JSONObject for fetching single user data
//                JSONObject userDetail = userArray.getJSONObject(i);
//                // fetch email and name and store it in arraylist
//                personNames.add(userDetail.getString("name"));
//                emailIds.add(userDetail.getString("email"));
//                // create a object for getting contact data from JSONObject
//                JSONObject contact = userDetail.getJSONObject("contact");
//                // fetch mobile number and store it in arraylist
//                mobileNumbers.add(contact.getString("mobile"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames, emailIds, mobileNumbers);
//        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("users_list.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }



    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        String apiUrl;
        public MyAsyncTasks(String api){
            this.apiUrl=api;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(this.apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            Log.d("Current", current);
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                    Log.v("API",s);
                ArrayList<Persona> result=new ArrayList<>();

                //JSONObject obj = new JSONObject(s);
                JSONArray arr=new JSONArray(s);
                for(int i=0; i<arr.length(); i++){
                    Persona p= new Persona();
                    p.setId(Integer.parseInt(arr.getJSONObject(i).getString("id")));
                    p.setName(arr.getJSONObject(i).getString("name"));
                    p.setDepartment(arr.getJSONObject(i).getString("department"));
                    p.setSalary(arr.getJSONObject(i).getInt("salary"));
                    result.add(p);
                }



                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                // set a LinearLayoutManager with default vertical orientation
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,result);
                recyclerView.setAdapter(customAdapter);
                progressDialog.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
            }





        }

    }
}
