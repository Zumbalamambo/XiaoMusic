package com.yzx.xiaomusic.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.DensityUtils;
import com.yzx.xiaomusic.utils.icimplement.TextWatchImp;
import com.yzx.xiaomusic.widget.StateView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author yzx
 * @date 2018/2/9
 * Description
 */

public class SearchFragment extends BaseFragment implements StateView.OnRetryClickListener {

    private static final String TAG = "yglSearchFragment";
    public static SearchFragment searchFragment;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.layout_state_container)
    RelativeLayout layoutStateContainer;
    private SearchPresenter mPresenter;
    public CommonMusicAdapter adapter;
    public StateView stateView;

    @SuppressLint("ValidFragment")
    private SearchFragment() {

    }

    public static SearchFragment getInstance() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }

        return searchFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter = new SearchPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        stateView = StateView.inject(layoutStateContainer);
        stateView.setOnRetryClickListener(this);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolBar);
            activity.getSupportActionBar().setTitle(null);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonMusicAdapter();
        recyclerView.setAdapter(adapter);
        etSearch.addTextChangedListener(new TextWatchImp() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    showPopWindow(s);
                }
            }
        });
    }

    /**
     * 弹popWindow
     *
     * @param s
     */
    private void showPopWindow(final Editable s) {
        ArrayList<String> info = new ArrayList<>();
        info.add(String.valueOf(s));
        final ListPopupWindow listPopupWindow = new ListPopupWindow(context);
        listPopupWindow.setAnchorView(toolBar);
        listPopupWindow.setHorizontalOffset((int) DensityUtils.dip2px(16));
        listPopupWindow.setAdapter(new ArrayAdapter<String>(context, R.layout.item_search, info));
        listPopupWindow.setContentWidth((int) (DensityUtils.getScreenWidth() - DensityUtils.dip2px(30)));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i(TAG, "onItemClick: 搜索");
                mPresenter.search(String.valueOf(s));
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

    @Override
    public void onRetryClick() {

    }
}
