package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class Fragment_1 extends Fragment {

    private static final String urlJSON ="http://swiftcodingthai.com/gam/php_get_advice.php";



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);

        return rootView;

    }

    private static class Syn_advice extends AsyncTask<Void, Void, String> {

        private Context context;
        private boolean aBoolean = true;

        private String[] column_advice = new String[]{
                "advice_fruit_id",
                "Fragment",
                "advice_fruit_text",
                "advice_fruit_pictures",
                "web"};
        private String[] advice_Strings;
        private String Fragment_1;





        public Syn_advice(Context context) {this.context = context; }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("15JanV1", "e doInBack ==>" + e.toString());
                return null;
            }

        }//doInBackround

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONArray jsonArray = new JSONArray(s);

                advice_Strings = new String[column_advice.length];

                for (int i = 0; i < jsonArray.length();i+=1){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (Fragment_1.equals(jsonObject.getString(column_advice[1]))){

                        for (int j=0;j<column_advice.length;j+=1){
                            advice_Strings[j] = jsonObject.getString(column_advice[j]);
                            Log.d("15febV1", "logStrings(" + j + ") = " + advice_Strings[j]);
                        }

                    }//if


                }//for


            }catch (Exception e){
                e.printStackTrace();//แสดงข้อมูลของ error
            }


        }//onPost
    }//AsyncTask
}