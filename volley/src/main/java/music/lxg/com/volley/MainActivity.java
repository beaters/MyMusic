package music.lxg.com.volley;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import music.lxg.com.volley.bean.ResponseMusicsListentity;

public class MainActivity extends AppCompatActivity {
    private Button music;
    private TextView tv;
    private JSONObject JSO;
    public static String url;
    private String uri="https://www.douban.com/j/app/radio/people?channel=0&app_name=radio_android&version=100&type=&sid=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.response);
        music=(Button)findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson(uri);
            }
        });
    }

    public void getJson(String uri){
        RequestQueue RQ= Volley.newRequestQueue(this);
        JsonObjectRequest js=new JsonObjectRequest(uri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Json", response.toString());
                Gson gson=new Gson();
                ResponseMusicsListentity r=gson.fromJson(response.toString(), ResponseMusicsListentity.class);
                MainActivity.url=r.getSong().get(0).getUrl();
                paly();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RQ.add(js);
    }

    public void paly()
    {
        MediaPlayer mediaPlayer=new MediaPlayer();
        try{
            mediaPlayer.setDataSource(this, Uri.parse(MainActivity.url));
            mediaPlayer.prepare();
        }catch (java.io.IOException ex){

        }
        mediaPlayer.start();
    }


}
