package com.example.project3;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ColorFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;


    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
            }
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0); //will start from 0 when resume
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        final ArrayList<EnglishToBangla> colorsArray = new ArrayList<EnglishToBangla>();
        colorsArray.add(new EnglishToBangla("Red","Lal",R.drawable.color_red,R.raw.color_red));
        colorsArray.add(new EnglishToBangla("Green","Shoboj",R.drawable.color_green,R.raw.color_green));
        colorsArray.add(new EnglishToBangla("Brown", "Begoni",R.drawable.color_brown,R.raw.color_brown));
        colorsArray.add(new EnglishToBangla("Gray","Dhosor",R.drawable.color_gray,R.raw.color_gray));
        colorsArray.add(new EnglishToBangla("Black","Kalo",R.drawable.color_black,R.raw.color_black));
        colorsArray.add(new EnglishToBangla("White","Shada",R.drawable.color_white,R.raw.color_white));
        colorsArray.add(new EnglishToBangla("Yellow","Holod",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colorsArray.add(new EnglishToBangla("Pink","Golapi",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));


        ListView rootListView = rootView.findViewById(R.id.rootListView);
        WordCustomAdapter itemAdapter = new WordCustomAdapter(getActivity(), 0, colorsArray,R.color.catagory_colors);
        rootListView.setAdapter(itemAdapter);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE); //initialize

        rootListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnglishToBangla currentItem = colorsArray.get(position);
                Toast.makeText(getContext(),currentItem.getBanglaWord()+" pronouncing", Toast.LENGTH_SHORT).show();

                releaseMediaPlayer();//if other songs clicked

                int requestResult = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(requestResult==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mMediaPlayer = MediaPlayer.create(getContext(),currentItem.getAudioResourceId() );
                    mMediaPlayer.start();
               /* mediaPlayerObj.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaPlayer();
                    }
                });*/
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);//release
                }

            }
        });
        return rootView;

    }
    public void releaseMediaPlayer()
    {
        if(mMediaPlayer!=null)
        {
            mMediaPlayer.release();
            mMediaPlayer=null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}