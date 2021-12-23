package app.gunjan.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.gunjan.fragments.DonationFragment;
import app.gunjan.fragments.DonationReceivedFragment;
import app.gunjan.fragments.FollowersListFragment;
import app.gunjan.fragments.FollowingListFragment;

public class DonationHelpTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public DonationHelpTabAdapter(FragmentManager fm, int NoofTabs) {
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
                DonationFragment donation = new DonationFragment();
                return donation;
            case 1:
                DonationReceivedFragment receive = new DonationReceivedFragment();
                return receive;
            default:
                return null;
        }
    }
}
