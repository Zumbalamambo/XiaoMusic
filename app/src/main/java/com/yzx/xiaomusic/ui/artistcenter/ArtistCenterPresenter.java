package com.yzx.xiaomusic.ui.artistcenter;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.utils.GlideUtils;

/**
 * Created by yzx on 2018/2/2.
 * Description
 */

public class ArtistCenterPresenter implements ArtistCenterContract.Presenter {

    private final ArtistCenterFragment mFragment;
    private final ArtistCenterModel mModel;

    public ArtistCenterPresenter(ArtistCenterFragment fragment) {
        mFragment = fragment;
        mModel = new ArtistCenterModel();
    }

    @Override
    public void getArtistCenterInfo(String artistId) {

        mModel.getArtistInfo(mFragment,artistId, new MvpObserver<ArtistCenterInfo>() {
            @Override
            protected void onSuccess(ArtistCenterInfo artistCenterInfo) {
                GlideUtils.loadImg(mFragment.getContext(),artistCenterInfo.getArtist().getPicUrl(),-1,mFragment.ivArtistPoster);
                mFragment.setToolBar(mFragment.toolBar,artistCenterInfo.getArtist().getName());
                mFragment.adapter.setDatas(artistCenterInfo);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
            }
        });
    }
}
