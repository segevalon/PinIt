package com.sa.pinit.screens.base;

public interface BasePresenter<T> {
    public void onAttach(T view);
    public void onDetach();
}