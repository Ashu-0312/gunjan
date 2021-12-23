package app.gunjan.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.gunjan.fragments.AboutOtherFragment;
import app.gunjan.fragments.DonationFragment;
import app.gunjan.fragments.DonationReceivedFragment;
import app.gunjan.fragments.OthersPostFragment;

public class OthersTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public OthersTabAdapter(FragmentManager fm, int NoofTabs) {
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AboutOtherFragment about = new AboutOtherFragment();
                return about;
            case 1:
                OthersPostFragment post = new OthersPostFragment();
                return post;
            default:
                return null;
        }
    }
}
