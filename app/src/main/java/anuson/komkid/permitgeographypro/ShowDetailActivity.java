package anuson.komkid.permitgeographypro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowDetailActivity extends AppCompatActivity {

    //Explicit
    private String idStrings,titleString, textString, startString, endString,
            statusString, urlPic1String, urlPic2String;

    private TextView titleTextView, textView, startTextView,
            endTextView, statusTextView;

    private ImageView pic1ImageView, pic2ImageView;

    private Button button,button_customer,button_stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        //Bind Widget
        bindWidget();

        //Get Value From Intent
        idStrings =getIntent().getStringExtra("post_id");
        titleString = getIntent().getStringExtra("post_tiltle");
        textString = getIntent().getStringExtra("post_text");
        startString = getIntent().getStringExtra("post_data_ster");
        endString = getIntent().getStringExtra("post_data_end");
        statusString = getIntent().getStringExtra("status_reserv_id");
        urlPic1String = getIntent().getStringExtra("post_pic");
        urlPic2String = getIntent().getStringExtra("post_pic_two");

        Log.d("16MerV1", "JSON_post_1 ==> " + idStrings);

        //Show View
        showView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(ShowDetailActivity.this, Manu_customer_by_farmer.class);
                newActivity.putExtra("post_id_to_user", idStrings);
                startActivity(newActivity);
            }
        });


    }   // Main Method

    private void showView() {

        titleTextView.setText(titleString);
        textView.setText(textString);

        startTextView.setText(dateThai(startString));
        endTextView.setText(dateThai(endString));

        statusTextView.setText(showStatus(statusString));

        Picasso.with(ShowDetailActivity.this)
                .load(urlPic1String).into(pic1ImageView);
        Picasso.with(ShowDetailActivity.this)
                .load(urlPic2String).into(pic2ImageView);


        if (statusString.equals("0")) {
            button_customer.setText("-");
            button_customer.setEnabled(false);
        }else if (statusString.equals("1")) {
            button_stop.setEnabled(false);
        }else if (statusString.equals("2")){
            button_customer.setEnabled(false);
            button_stop.setEnabled(false);
        }

    }

    private String showStatus(String statusString) {

        String[] strings = new String[]{"กำลังขาย", "จอง", "สิ้นสุด"};
        int i = Integer.parseInt(statusString);

        return strings[i];
    }

    private void bindWidget() {

        titleTextView = (TextView) findViewById(R.id.textView12);
        textView = (TextView) findViewById(R.id.textView15);
        startTextView = (TextView) findViewById(R.id.textView19);
        endTextView = (TextView) findViewById(R.id.textView21);
        statusTextView = (TextView) findViewById(R.id.textView22);
        pic1ImageView = (ImageView) findViewById(R.id.imageView5);
        pic2ImageView = (ImageView) findViewById(R.id.imageView7);
        button = (Button) findViewById(R.id.button5);
        button_customer = (Button) findViewById(R.id.button11);
        button_stop = (Button) findViewById(R.id.button9);
    }
    public static String dateThai(String strDate)
    {
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.format("%s %s %s", day,Months[month],year+543);
    }

}   // Main Class
