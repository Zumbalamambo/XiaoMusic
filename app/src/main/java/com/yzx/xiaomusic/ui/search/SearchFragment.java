package com.yzx.xiaomusic.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author yzx
 * @date 2018/2/9
 * Description
 */

public class SearchFragment extends BaseFragment {

    public static SearchFragment searchFragment;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    Unbinder unbinder;
    private SearchPresenter mPresenter;
    public CommonMusicAdapter adapter;

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
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolBar);
            activity.getSupportActionBar().setTitle(null);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonMusicAdapter();
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        String keyWord = etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(keyWord)){
            mPresenter.search(keyWord);
        }else {
            ToastUtils.showToast(R.string.note_cant_be_null);
        }

    }
}
