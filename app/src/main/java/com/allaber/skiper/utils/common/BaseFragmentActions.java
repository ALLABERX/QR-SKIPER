package com.allaber.skiper.utils.common;

import android.view.View;

public interface BaseFragmentActions extends View.OnClickListener {
    void initiationViewElements(View view);
    void setOnClickListener();
}
