package anuson.komkid.permitgeographypro;


import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Syn_reserv_where_id_By_farmer extends AsyncTask<Void, Void, String> {

    private final Context context;
    private String idStrings;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/get_reserv_where_id_By_farmer.php";

    public Syn_reserv_where_id_By_farmer(Context context,
                                         String idStrings) {

        this.context = context;
        this.idStrings = idStrings;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", idStrings)
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
