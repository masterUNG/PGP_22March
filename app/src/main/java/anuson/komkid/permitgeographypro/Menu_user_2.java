package anuson.komkid.permitgeographypro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Menu_user_2 extends Activity {

    private String[] userLoginStrings, idReservStrings, mem_idStrings;
    private ListView listView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_user_2);

        listView = (ListView) findViewById(R.id.livReserv_by_user);

        userLoginStrings = getIntent().getStringArrayExtra("Login");
        for (int i = 0; i < userLoginStrings.length; i++) {
            Log.d("18JanV1", "userLogin" + i + "]=" + userLoginStrings[i]);
        }//for

        createListView();

    }// Main Method

    private void createListView() {
        try {
            SysReserv sysReserv = new SysReserv(Menu_user_2.this, userLoginStrings[0]);
            sysReserv.execute();
            String stringreserv = sysReserv.get();
            Log.d("18JanV5", "JSON==> " + stringreserv);

            JSONArray jsonArray = new JSONArray(stringreserv);

            final String[] titleStrings = new String[jsonArray.length()];
            final String[] endStrings = new String[jsonArray.length()];
            final String[] data_endStrings = new String[jsonArray.length()];
            final String[] statusShowStrings = new String[jsonArray.length()];
            final String[] statusStrings = new String[jsonArray.length()];
            final String[] textStrings = new String[jsonArray.length()];
            final String[] startStrings = new String[jsonArray.length()];
            final String[] pic1Strings = new String[jsonArray.length()];
            final String[] pic2Strings = new String[jsonArray.length()];
            String[] date_reserv = new String[jsonArray.length()];
            String[] expDate_reserv = new String[jsonArray.length()];

            final String[] idStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                titleStrings[i] = jsonObject1.getString("post_tiltle");
                endStrings[i] = jsonObject1.getString("post_data_end");
                data_endStrings[i] = dateThai(endStrings[i]);
                statusStrings[i] = jsonObject1.getString("status_reserv_id");
                statusShowStrings[i] = showStatus(jsonObject1.getString("status_reserv_id"));
                textStrings[i] = jsonObject1.getString("post_text");
                startStrings[i] = jsonObject1.getString("post_data_ster");
                pic1Strings[i] = jsonObject1.getString("post_pic");
                pic2Strings[i] = jsonObject1.getString("post_pic_two");
                idStrings[i] = jsonObject1.getString("post_id");
                date_reserv[i] = jsonObject1.getString("date_reserv");
                expDate_reserv[i] = dateThai(addOneDay(date_reserv[i]));

                checkExpDate(addOneDay(date_reserv[i]),
                        jsonObject1.getString("post_id"),
                        jsonObject1.getString("status_reserv_id"),
                        jsonObject1.getString("post_data_end"));

                Log.d("18JanV5", "date_reserv(" + i + ") ==> " + date_reserv[i]);


            }//for
            MyReservListview myReservListview = new MyReservListview(Menu_user_2.this,
                    titleStrings, expDate_reserv, statusShowStrings);
            listView.setAdapter(myReservListview);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(Menu_user_2.this, ShowDetailByUser.class);
                    intent.putExtra("post_tiltle", titleStrings[i]);
                    intent.putExtra("post_data_end", endStrings[i]);
                    intent.putExtra("status_reserv_id", statusStrings[i]);
                    intent.putExtra("post_text", textStrings[i]);
                    intent.putExtra("post_data_ster", startStrings[i]);
                    intent.putExtra("post_pic", pic1Strings[i]);
                    intent.putExtra("post_pic_two", pic2Strings[i]);

                    intent.putExtra("Login", userLoginStrings);
                    intent.putExtra("idPost", idStrings[i]);

                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.d("27novV3", "e menu3 ==> " + e.toString());
        }
    }

    private void checkExpDate(String strExpDate,
                              String strPostID,
                              String strStatus,
                              String strDateEnd) {

        String tag = "1MarchV3";
        Log.d(tag, "สิ่งที่ได้รับมา ==>" + strExpDate);



        String[] strings = strExpDate.split("-");
        Calendar currentCalendar = Calendar.getInstance();
        Calendar expCalendar = Calendar.getInstance();
        expCalendar.set(Calendar.YEAR, Integer.parseInt(strings[0]));
        expCalendar.set(Calendar.MONTH, Integer.parseInt(strings[1])-1);
        expCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strings[2]));

        Log.d(tag, "currentCalendar ==> " + currentCalendar.getTime().toString());
        Log.d(tag, "expCalendar ==> " + expCalendar.getTime().toString());

        if (currentCalendar.after(expCalendar)) {
            Log.d(tag, "Show ExpDate ==> " + expCalendar.getTime().toString());
            Log.d(tag, "post_id ==> " + strPostID);

            try {

                if (Integer.parseInt(strStatus) == 1) {

                    EditStatusWhenExip editStatusWhenExip = new EditStatusWhenExip(Menu_user_2.this);
                    editStatusWhenExip.execute(strPostID);

                    Add_Blank_Lis add_blank_lis = new Add_Blank_Lis(Menu_user_2.this,strPostID,userLoginStrings[0],strDateEnd);
                    add_blank_lis.execute();

                    if (Boolean.parseBoolean(editStatusWhenExip.get())) {
                        createListView();
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }   // checkExpDate

    private String addOneDay(String strDateTime) {

        String result = null;
        String tag = "1MarchV2";

        String[] myDate = strDateTime.split(" ");
        Log.d(tag, "strDateTime ==> " + strDateTime);
        Log.d(tag, "วันที่เริ่มจอง ==> " + myDate[0]);

        String[] myDate2 = myDate[0].split("-");
        int intYear = Integer.parseInt(myDate2[0]);
        int intMonth = Integer.parseInt(myDate2[1]) - 1;
        int intDay = Integer.parseInt(myDate2[2]);

        String[] myDate3 = myDate[1].split(":");
        int intHr = Integer.parseInt(myDate3[0]);
        int intMinus = Integer.parseInt(myDate3[1]);
        int intSecond = Integer.parseInt(myDate3[2]);

        Log.d(tag, "Year ==> " + intYear);
        Log.d(tag, "Month ==> " + intMonth);
        Log.d(tag, "Day ==> " + intDay);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, intYear);
        calendar.set(Calendar.MONTH, intMonth);
        calendar.set(Calendar.DAY_OF_MONTH, intDay);
        calendar.set(Calendar.HOUR_OF_DAY, intHr);
        calendar.set(Calendar.MINUTE, intMinus);
        calendar.set(Calendar.SECOND, intSecond);

        Log.d(tag, "เวลาในรูปแบบ Calendar ==> " + calendar.getTime().toString());

        int intDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        Log.d(tag, "วันในรูปแบบปี ==> " + intDayOfYear);

        intDayOfYear += 1;
        calendar.set(Calendar.DAY_OF_YEAR, intDayOfYear);
        Log.d(tag, "expDate ==> " + calendar.getTime().toString());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result = dateFormat.format(calendar.getTime());
        Log.d(tag, "result ==> " + result);

        return result;
    }

    private String showStatus(String statusString) {
        String[] strings = new String[]{"กำลังขาย", "จอง", "สิ้นสุด"};
        return strings[Integer.parseInt(statusString)];
    }

    public static String dateThai(String strDate) {
        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year = 0, month = 0, day = 0;
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
        return String.format("%s %s %s", day, Months[month], year + 543);
    }
}
