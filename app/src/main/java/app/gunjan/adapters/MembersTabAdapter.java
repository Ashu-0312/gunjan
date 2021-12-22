package app.gunjan.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import app.gunjan.fragments.ActiveMembersFragment;
import app.gunjan.fragments.AdminMembersFragment;

public class MembersTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public MembersTabAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                ActiveMembersFragment active = new ActiveMembersFragment();
                return active;
            case 1:
                AdminMembersFragment admin = new AdminMembersFragment();
                return admin;
            default:
                return null;
        }
    }
}
