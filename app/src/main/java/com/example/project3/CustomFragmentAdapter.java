package com.example.project3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustomFragmentAdapter extends FragmentPagerAdapter {
    public CustomFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new NumberFragment();

        }
        else if(position==1)
        {
            return new ColorFragment();

        }
        else if(position==2)
        {
            return new FamilyFragment();
        }
        else if(position==3)
        {
            return new PhrasesFragment();
        }


        return null;
    }



    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
        {
            return "Numbers";

        }
        else if(position==1)
        {
            return  "Colors";

        }
        else if(position==2)
        {
            return  "Family";
        }
        else if(position==3)
        {
            return  "Phrases";
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return 4;
    }
}
