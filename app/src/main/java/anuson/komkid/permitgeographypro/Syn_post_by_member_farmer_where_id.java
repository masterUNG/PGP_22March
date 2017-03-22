package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;



public class Syn_post_by_member_farmer_where_id extends AsyncTask<Void,Void,String>{

    private Context context;
    private static final String urlJSON = "http://swiftcodingthai.com/gam/get_post_to_member_farmer_where_id.php";
    private String post_idString;


    public Syn_post_by_member_farmer_where_id(Context context,
                                              String post_idString) {
        this.context = context;
        this.post_idString = post_idString;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", post_idString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlJSON).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("31JanV1", "e doIn ==> " + e.toString());
            return null;
        }
    }
}
