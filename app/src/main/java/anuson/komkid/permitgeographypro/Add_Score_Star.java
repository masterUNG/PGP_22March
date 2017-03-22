package anuson.komkid.permitgeographypro;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Add_Score_Star extends AsyncTask<Void ,Void ,String> {

    private final Context context;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/add_scorec_s.php";
    private String ratingString,post_idStrings,mem_idStrings,mem_u_idStrings;

    public Add_Score_Star(Context context,
                          String ratingString,
                          String post_idStrings,
                          String mem_idStrings,
                          String mem_u_idStrings) {

        this.context = context;
        this.ratingString = ratingString;
        this.post_idStrings = post_idStrings;
        this.mem_idStrings = mem_idStrings;
        this.mem_u_idStrings = mem_u_idStrings;
    }


    @Override
    protected String doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("scoerv_s", ratingString)
                    .add("post_id", post_idStrings)
                    .add("mem_id", mem_idStrings)
                    .add("mem_u_id", mem_u_idStrings)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            Log.d("9MarV1", "e doIn ==> " + e.toString());
            return null;
        }
    }
}
