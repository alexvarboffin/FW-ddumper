package com.walhalla.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.support.v4.app.ControlPanel;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.R;
import com.walhalla.ui.adapter.SectionsPagerAdapter;
import com.walhalla.ui.fragment.tab.BlockListFragment;
import com.walhalla.ui.fragment.tab.OutputFragment;
import com.walhalla.ui.fragment.tab.SettingsPreferenceFragment;

import java.util.ArrayList;
import java.util.List;

public class TabHolderFragment extends Fragment implements ViewPager.OnPageChangeListener {

    //Gui
    private TabLayout tabLayout;
    private ViewPager mViewpager;
    private SectionsPagerAdapter mPagerAdapter;

    private int[] icons = {
            R.drawable.ic_computer,
            R.drawable.ic_format_list_numbered,
            R.drawable.ic_bug_report,
            R.drawable.ic_settings
    };
    private int mSelected;

    public TabHolderFragment() {
        // Required empty public constructor
    }

    public static TabHolderFragment newInstance(String param1, String param2) {
        return new TabHolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_holder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = getActivity().findViewById(R.id.tabs);
        mViewpager = view.findViewById(R.id.viewpager);


        setupViewPager(mViewpager);
        tabLayout.setupWithViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(tabLayout.getTabCount());
        mViewpager.addOnPageChangeListener(this);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
        invalidateFragmentMenus(mViewpager.getCurrentItem());
    }

    private void setupViewPager(ViewPager o) {
        mPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mPagerAdapter.addFragment(new OutputFragment(), "Shell");//getString(R.string.tab_active)
        mPagerAdapter.addFragment(new BlockListFragment(), "/dev/");
        mPagerAdapter.addFragment(ControlPanel.newInstance(), getString(R.string.action_cp));
        mPagerAdapter.addFragment(new SettingsPreferenceFragment(), getString(R.string.action_settings));
        o.setAdapter(mPagerAdapter);
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    /**
     * Output data to console
     *
     * @param progress
     */
    public void println(Object progress) {
        Fragment cc = getFr();
        if (cc != null) {
            ((OutputFragment) cc).println(progress);
        }
    }

    /**
     * Out to recycler
     *
     * @param data
     */
    public void priWntList(ArrayList<Obj> data) {
        BlockListFragment bl = (BlockListFragment) getChildFragmentManager().getFragments().get(1);
        if (bl != null) {
            bl.showData(data);
        }
    }

    public void printList(Obj object) {
        ArrayList<Obj> list = new ArrayList<>();
        list.add(object);
        priWntList(list);
    }

    private Fragment getFr() {
        return getChildFragmentManager().getFragments().get(0);
    }

    public void clear() {
        Fragment cc = getFr();
        if (cc != null) {
            ((OutputFragment) cc).clear();
        }
    }

    public void println(List<String> object) {
//        ArrayList<Obj> list = new ArrayList<Obj>(object);
//        priWntList(list);
    }


    /**
     * OnPageChangeListener
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        invalidateFragmentMenus(position);
        mSelected = position;
    }

    private void invalidateFragmentMenus(int position) {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            mPagerAdapter.getItem(i).setHasOptionsMenu(i == position);
        }
        getActivity().invalidateOptionsMenu(); //or respectively its support method.
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public boolean onBackPressed() {
        Fragment baseFragment = getChildFragmentManager()
                .getFragments().get(mSelected);
        return false;
    }
}
