package anuson.komkid.permitgeographypro;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Add_Blank_Lis extends AsyncTask<Void ,Void ,String>{

    private final Context context;
    private String strPostID, userLoginString, strDateEnd;
    private static final String urlPHP = "http://swiftcodingthai.com/gam/add_black_lis.php";

    public Add_Blank_Lis(Context context,
                         String strPostID,
                         String userLoginString,
                         String strDateEnd){
        this.context = context;
        this.strPostID = strPostID;
        this.userLoginString = userLoginString;
        this.strDateEnd = strDateEnd;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("post_id", strPostID)
                    .add("mem_u_id", userLoginString)
                    .add("date_black_lis", strDateEnd)
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
