package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
//    @BindView(R.id.rb_music)
//    RadioButton rbMusic;
//    @BindView(R.id.rb_cloud)
//    RadioButton rbCloud;
//    @BindView(R.id.rb_friend)
//    RadioButton rbFriend;
//    @BindView(R.id.viewPager)
//    ViewPager viewPager;
//    @BindView(R.id.navigationView)
//    NavigationView navigationView;
//    @BindView(R.id.drawerLayout)
//    DrawerLayout drawerLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        viewPager.setOffscreenPageLimit(3);
//        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
//        ArrayList<BaseFragment> fragments = new ArrayList<>();
//        fragments.add(new MusicFragment());
//        fragments.add(new CloudFragment());
//        fragments.add(new FriendFragment());
//        adapter.setFragmentDatas(fragments);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(this);
        MainFragment fragment = findFragment(MainFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.layout_container, new MainFragment());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }

//    @OnClick({R.id.rb_music, R.id.rb_cloud, R.id.rb_friend})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.rb_music:
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.rb_cloud:
//                viewPager.setCurrentItem(1);
//                break;
//            case R.id.rb_friend:
//                viewPager.setCurrentItem(2);
//                break;
//        }
//    }
//
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        switch (position) {
//            case 0:
//                rbMusic.setChecked(true);
//                break;
//            case 1:
//                rbCloud.setChecked(true);
//                break;
//            case 2:
//                rbFriend.setChecked(true);
//                break;
//        }
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
}
