package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 2/2/2017 AD.
 */

public class AddNewComment extends AsyncTask<Void, Void, String>{

    //Explicit
    private Context context;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/add_comment.php";
    private String namePostString, dateTimeString, commentString;

    public AddNewComment(Context context,
                         String namePostString,
                         String dateTimeString,
                         String commentString) {
        this.context = context;
        this.namePostString = namePostString;
        this.dateTimeString = dateTimeString;
        this.commentString = commentString;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("NamePost", namePostString)
                    .add("DateTime", dateTimeString)
                    .add("Comment", commentString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("2febV1", "e doIn ==> " + e.toString());
            return null;
        }


    }
}   // Main Class
