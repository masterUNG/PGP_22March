package anuson.komkid.permitgeographypro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Manu_customer_by_farmer extends AppCompatActivity {

    private String idStrings;
    private TextView nameTextView,addTextView,emailTextView,telTextView,date_TextView;
    private ImageView imageView;
    private String[] columnuser_by_user = new String[]{
            "reserv_id",
            "post_id",
            "mem_u_id",
            "date_reserv",
            "mem_u_user",
            "mem_u_pass",
            "mem_u_name",
            "mem_u_add",
            "mem_u_mail",
            "mem_u_tel",
            "mem_u_key",
            "mem_u_pic"};
    private String[] mem_user_re_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_customer_by_farmer);

        idStrings = getIntent().getStringExtra("post_id_to_user");
        Log.d("16MerV1", "JSON_post ==> " + idStrings);

        try {
            Syn_reserv_where_id_By_farmer syn_reserv_where_id_by_farmer = new Syn_reserv_where_id_By_farmer(this,idStrings);
            syn_reserv_where_id_by_farmer.execute();
            String s = syn_reserv_where_id_by_farmer.get();

            Log.d("16MerV1", "JSON_reserv ==> " + s);

            nameTextView = (TextView) findViewById(R.id.textView102);
            addTextView = (TextView) findViewById(R.id.textView103);
            emailTextView = (TextView) findViewById(R.id.textView104);
            telTextView = (TextView) findViewById(R.id.textView105);
            date_TextView = (TextView) findViewById(R.id.textView106);

            imageView = (ImageView) findViewById(R.id.imageView_user_by_farmer);

            JSONArray jsonArray = new JSONArray(s);

            mem_user_re_String = new String[columnuser_by_user.length];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                    for (int j = 0; j < columnuser_by_user.length; j += 1) {
                        mem_user_re_String[j] = jsonObject.getString(columnuser_by_user[j]);
                        Log.d("16Mer", "jsonObject : " + j + " : " + mem_user_re_String[j]);

                        nameTextView.setText("ชื่อ-นามสกุล :" + mem_user_re_String[6]);
                        addTextView.setText("ที่อยู่ :" + mem_user_re_String[7]);
                        emailTextView.setText("e-mail :" + mem_user_re_String[8]);
                        telTextView.setText("โทร :" + mem_user_re_String[9]);
                        date_TextView.setText("จองเมื่อ : " + mem_user_re_String[3]);

                        Picasso.with(Manu_customer_by_farmer.this).load(mem_user_re_String[11]).into(imageView);

                    }


            }//for





        }catch (Exception e){

        }




    }//onCreate

}//Mianclass
