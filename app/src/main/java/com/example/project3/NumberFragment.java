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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class NumberFragment extends Fragment {
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
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0); //position at the begining of the audio file

            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_listview, container, false);
        final ArrayList<EnglishToBangla> wordArray = new ArrayList<EnglishToBangla>();

        //EnglishToBangla word = new EnglishToBangla("One","Ek");
        wordArray.add(new EnglishToBangla("One","Ek",R.drawable.number_one,R.raw.number_one));
        wordArray.add(new EnglishToBangla("Two","Doi",R.drawable.number_two, R.raw.number_two));
        wordArray.add(new EnglishToBangla("Three","Tin",R.drawable.number_three,R.raw.number_three));
        wordArray.add(new EnglishToBangla("Four","Char",R.drawable.number_four,R.raw.number_four));
        wordArray.add(new EnglishToBangla("Five","Pach",R.drawable.number_five,R.raw.number_five));
        wordArray.add(new EnglishToBangla("Six","Soi",R.drawable.number_six,R.raw.number_six));
        wordArray.add(new EnglishToBangla("Seven","Shat",R.drawable.number_seven,R.raw.number_seven));
        wordArray.add(new EnglishToBangla("Eight","Aat",R.drawable.number_eight,R.raw.number_eight));
        wordArray.add(new EnglishToBangla("Nine","Noy",R.drawable.number_nine,R.raw.number_nine));
        wordArray.add(new EnglishToBangla("Ten","Dosh",R.drawable.number_ten,R.raw.number_ten));


        ListView rootListView = rootView.findViewById(R.id.rootListView);
        WordCustomAdapter itemAdapter = new WordCustomAdapter(getActivity(),0, wordArray, R.color.catagory_numbers);
        rootListView.setAdapter(itemAdapter);


        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);//initialize

        rootListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnglishToBangla currentItem = wordArray.get(position);
                Toast.makeText(getActivity(),currentItem.getBanglaWord()+" pronouncing", Toast.LENGTH_SHORT).show();

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