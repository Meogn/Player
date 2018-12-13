package com.example.player;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer  mediaPlayer = new MediaPlayer();

    ListView mylist;
    List<Song> list;
    boolean seekbarchange = false;
    int currIndex1;
    SeekBar seekbar;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pre = (Button) findViewById(R.id.pre);
        Button pause = (Button) findViewById(R.id.pause);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        Button next = (Button) findViewById(R.id.next);

        pre.setOnClickListener(this);
        pause.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new MySeekBar());

        mylist = (ListView) findViewById(R.id.mylist);

        list = new ArrayList<>();

        list = Utils.getmusic(this);

        MyAdapter myAdapter = new MyAdapter(this, list);
        mylist.setAdapter(myAdapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p = list.get(i).path;//获得歌曲的地址
                play(p);
                currIndex1=i;
//                if((currIndex1+1)!=(list.size())) {
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            play(list.get(currIndex1 + 1).path);
//                        }
//                    });
//                    currIndex1 = currIndex1 + 1;
//                }
//                else if((currIndex1+1)==list.size())
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
////                            mediaPlayer.stop();
//                            play(list.get(0).path);
//                        }
//                    });
//                currIndex1 = 0;
            }
        });
//        ItemOnLongClick();
    }
//    private void ItemOnLongClick() {
//
//        mylist.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//
//                contextMenu.add(0, 0, 0, "单曲循环");
//                contextMenu.add(0, 1, 0, "列表循环");
//                contextMenu.add(0, 2, 0, "随机循环");
//            }
//        });
//    }
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
//                .getMenuInfo();
//        currIndex1 = (int) info.id;
//        Random random = new Random();
//        int num = random.nextInt(6) + 0;
//        switch (item.getItemId()){
//            case 0:
//                final String p0 = list.get(currIndex1).path;
////                while(!mediaPlayer.isPlaying()){
////                    play(p0);
////                }
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        play(p0);
//                    }
//                });
//                break;
//            case 1:
//                if((currIndex1+1)!=(list.size())){
//                    currIndex1++;
//                    final String p1 = list.get(currIndex1).path;
////                    while(!mediaPlayer.isPlaying()) {
////                        play(p1);
////                    }
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            play(p1);
//                        }
//                    });
//                }else{
//                    Toast.makeText(this, "当前已经是最后一首歌曲了", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 2:
//                final String p2 = list.get(num).path;
////                while(!mediaPlayer.isPlaying()) {
////                    play(p3);
////                }
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        play(p2);
//                    }
//                });
//                currIndex1 = num;
//                break;
//        }
//        return true;
//    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                }
                break;
            case R.id.pre:
                if((currIndex1-1)>=0){
                    currIndex1--;
                    String p = list.get(currIndex1).path;
                    play(p);
                }else{
                    Toast.makeText(this, "当前已经是第一首歌曲了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next:
                if(currIndex1+1<list.size()){
                    currIndex1++;
                    String p = list.get(currIndex1).path;
                    play(p);
                }else{
                    Toast.makeText(this, "当前已经是最后一首歌曲了", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
//    protected void onDestroy() {
//        super.onDestroy();
//        if(mediaPlayer!=null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//    }


    class MyAdapter extends BaseAdapter {

        Context context;
        List<Song> list;



        public MyAdapter(MainActivity mainActivity, List<Song> list) {
            this.context = mainActivity;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Myholder myholder;

            if (view == null) {
                myholder = new Myholder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text, null);
                myholder.t_position = view.findViewById(R.id.t_postion);
                myholder.t_song = view.findViewById(R.id.t_song);
                myholder.t_singer = view.findViewById(R.id.t_singer);
                myholder.t_duration = view.findViewById(R.id.t_duration);
                view.setTag(myholder);

            } else {
                myholder = (Myholder) view.getTag();
            }

            myholder.t_song.setText(list.get(i).song.toString());
            myholder.t_singer.setText(list.get(i).singer.toString());
//            String time = Utils.formatTime(list.get(i).duration);
//            myholder.t_duration.setText(time);
            myholder.t_position.setText(i + 1 + "");


            return view;
        }


        class Myholder {
            TextView t_position, t_song, t_singer, t_duration;
        }


    }

    public void play(String path) {

        try {

            mediaPlayer.reset();//        调用方法传进播放地址
            mediaPlayer.setDataSource(path);//            异步准备资源，防止卡顿
            mediaPlayer.prepareAsync();//            调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.seekTo(mediaPlayer.getCurrentPosition());
                    seekbar.setMax(mediaPlayer.getDuration());
                }
            });
            //监听播放时回调函数
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!seekbarchange){
                        seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            },0,50);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void onDestroy() {
        mediaPlayer.release();
        timer.cancel();
        timer = null;
        mediaPlayer = null;
        super.onDestroy();
    }

    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            seekbarchange = true;
        }
        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            seekbarchange = false;
            mediaPlayer.seekTo(seekBar.getProgress());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Random random = new Random();
        int num = random.nextInt(6) + 0;
        switch (item.getItemId()) {
            case R.id.oneloop://监听菜单按钮
                final String p0 = list.get(currIndex1).path;
//                while(!mediaPlayer.isPlaying()){
//                    play(p0);
//                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play(p0);
                    }
                });
                break;
            case R.id.listloop:
                if ((currIndex1 + 1) != (list.size())) {
                    currIndex1++;
                    final String p1 = list.get(currIndex1).path;
//                    while(!mediaPlayer.isPlaying()) {
//                        play(p1);
//                    }
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play(p1);
                        }
                    });
                }
                break;
            case R.id.loop:
                final String p2 = list.get(num).path;
//              while(!mediaPlayer.isPlaying()) {
//                    play(p3);
//              }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play(p2);
                    }
                });
                currIndex1 = num;
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
