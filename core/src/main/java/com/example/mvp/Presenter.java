package com.example.mvp;

public abstract class Presenter<T extends View> {
    protected T view;

    public void attach(T view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
    }


}
