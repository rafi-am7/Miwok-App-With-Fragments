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


public class FamilyFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

        final ArrayList<EnglishToBangla> familyArray = new ArrayList<EnglishToBangla>();
        familyArray.add(new EnglishToBangla("Father", "Baba", R.drawable.family_father, R.raw.family_father));
        familyArray.add(new EnglishToBangla("Mother", "Ma", R.drawable.family_mother, R.raw.family_mother));
        familyArray.add(new EnglishToBangla("Son", "Potro", R.drawable.family_son, R.raw.family_son));
        familyArray.add(new EnglishToBangla("Daughter", "Konna", R.drawable.family_daughter, R.raw.family_daughter));
        familyArray.add(new EnglishToBangla("Older Brother", "Boro Vai", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyArray.add(new EnglishToBangla("Younger Brother", "Choto Vai", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyArray.add(new EnglishToBangla("Older Sister", "Boro Bon", R.drawable.family_older_sister, R.raw.family_older_sister));
        familyArray.add(new EnglishToBangla("Younger Sister", "Choto Bon", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyArray.add(new EnglishToBangla("Grandfather", "Dado", R.drawable.family_grandfather, R.raw.family_grandfather));
        familyArray.add(new EnglishToBangla("Grandmother", "Dadoma", R.drawable.family_grandmother, R.raw.family_grandmother));

        WordCustomAdapter itemAdapter = new WordCustomAdapter(getActivity(), 0, familyArray, R.color.catagory_family);
        ListView rootListView = rootView.findViewById(R.id.rootListView);
        rootListView.setAdapter(itemAdapter);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);//initialize
        rootListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnglishToBangla currentItem = familyArray.get(position);
                Toast.makeText(getContext(),currentItem.getBanglaWord()+" pronouncing", Toast.LENGTH_SHORT).show();

                releaseMediaPlayer();//if other songs clicked

                int requestResult = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(requestResult==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mMediaPlayer = MediaPlayer.create(getActivity(),currentItem.getAudioResourceId() );
                    mMediaPlayer.start();

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