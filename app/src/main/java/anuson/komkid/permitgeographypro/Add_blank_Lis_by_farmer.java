package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class Add_blank_Lis_by_farmer extends AsyncTask<Void, Void, String> {

    private static final String urlPHP = "http://swiftcodingthai.com/gam/add_Black_where_id_by_farmer.php";
    private final Context context;
    private String srt;

    public Add_blank_Lis_by_farmer(Context context,
                                   String srt) {
        this.context = context;
        this.srt = srt;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("mem_u_name", srt)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            return null;
        }

    }
}
