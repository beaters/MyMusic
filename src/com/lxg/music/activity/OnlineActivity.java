package com.lxg.music.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lxg.music.R;
import com.lxg.music.model.ResponseMusicsListentity;
import com.lxg.music.utils.FastBlurUtil;

import org.json.JSONObject;


public class OnlineActivity extends Activity implements IConstants, MediaPlayer.OnPreparedListener,View.OnClickListener,MediaPlayer.OnCompletionListener{
    private static String url;
    private ImageView disc_view,background;
    private TextView player_name,song_name;
    private ImageButton pre,play_ctr,next;
    private static ResponseMusicsListentity response;
    private static int times=0;
    private static int Status=0; //0代表暂停，1代表播放
    MediaPlayer mediaPlayer=new MediaPlayer();
    private  Handler mHandler;
    private ProgressDialog  mProgress;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        disc_view=(ImageView)findViewById(R.id.musics_player_disc_view);
        background=(ImageView)findViewById(R.id.musics_player_background);
        player_name=(TextView)findViewById(R.id.musics_player_name);
        song_name=(TextView)findViewById(R.id.musics_player_songer_name);
        pre=(ImageButton)findViewById(R.id.musics_player_play_prev_btn);
        play_ctr=(ImageButton)findViewById(R.id.musics_player_play_ctrl_btn);
        next=(ImageButton)findViewById(R.id.musics_player_play_next_btn);

        play_ctr.setOnClickListener(this);
        next.setOnClickListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        gson=new Gson();
        init();
        getJsonFromWeb();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0x123) {
                    super.handleMessage(msg);
                    mProgress.dismiss();
                }
            }
        };
    }

    public void init(){
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

    }
    public void getJsonFromWeb(){
        JSONObject jsonObject=new JSONObject();
        RequestQueue RQ= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,uri,jsonObject,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        response=gson.fromJson(jsonObject.toString(), ResponseMusicsListentity.class);
                        OnlineActivity.url=response.getSong().get(0).getUrl();
                        setup();
                        play();
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RQ.add(jsonObjectRequest);
    }

    public void play(){
        //MediaPlayer mediaPlayer=new MediaPlayer();
        if(times==0){
            try{
                mediaPlayer.setDataSource(this, Uri.parse(OnlineActivity.url));
                mediaPlayer.prepare();
                mediaPlayer.start();
                play_ctr.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_play));
            }catch (java.io.IOException ex){

            }
        }else {
            mediaPlayer.reset();
            try{
                mediaPlayer.setDataSource(this, Uri.parse(OnlineActivity.url));
                mediaPlayer.prepare();
                mediaPlayer.start();
                play_ctr.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_play));
            }catch (java.io.IOException ex){

            }

        }

    }

    @Override
    public void onClick(View v){
        int id=v.getId();
        switch (id){
            case R.id.musics_player_play_ctrl_btn:
                if(Status==1){
                    mediaPlayer.pause();
                    play_ctr.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pause));
                    Status=0;
                }else {
                    mediaPlayer.start();
                    play_ctr.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_play));
                    Status=1;
                }
                break;
            case R.id.musics_player_play_next_btn:
                times++;
                getJsonFromWeb();
                break;
        }


    }

    public void setup(){
        ImageRequest imageRequest=new ImageRequest(this.response.getSong().get(0).getPicture(),new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap res){
                disc_view.setImageBitmap(res);
                Bitmap scale=Bitmap.createScaledBitmap(res,res.getWidth()/SCALE,res.getHeight()/SCALE,false);
                Bitmap blurBitmap= FastBlurUtil.doBlur(scale,BLURRADIUS,true);
                background.setImageBitmap(blurBitmap);
                player_name.setText(response.getSong().get(0).getArtist());
                song_name.setText(response.getSong().get(0).getTitle());

            }
        },253,253,Bitmap.Config.ARGB_8888,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });

        Volley.newRequestQueue(this).add(imageRequest);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        getJsonFromWeb();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mHandler.sendEmptyMessage(0x123);
    }
}
