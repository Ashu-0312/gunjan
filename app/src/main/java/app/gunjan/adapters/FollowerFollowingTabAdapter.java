package app.gunjan.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import app.gunjan.fragments.ActiveMembersFragment;
import app.gunjan.fragments.AdminMembersFragment;
import app.gunjan.fragments.FollowersListFragment;
import app.gunjan.fragments.FollowingListFragment;

public class FollowerFollowingTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FollowerFollowingTabAdapter(FragmentManager fm, int NoofTabs) {
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
                FollowersListFragment follower = new FollowersListFragment();
                return follower;
            case 1:
                FollowingListFragment following = new FollowingListFragment();
                return following;
            default:
                return null;
        }
    }
}
