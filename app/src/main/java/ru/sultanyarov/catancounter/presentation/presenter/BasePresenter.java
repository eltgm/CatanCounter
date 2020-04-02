package ru.sultanyarov.catancounter.presentation.presenter;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;


public abstract class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disconnect();
    }

    protected abstract void disconnect();
}
