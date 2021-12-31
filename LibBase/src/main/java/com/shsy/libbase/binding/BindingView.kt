package com.shsy.libbase.binding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.shsy.libbase.exts.goneAlphaAnimation
import com.shsy.libbase.exts.logV
import com.shsy.libbase.exts.visibleAlphaAnimation

@BindingAdapter("isVisible")
fun View.bindingIsVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isVisibleAnim")
fun View.bindingIsVisibleAnim(isVisible: Boolean) {
    if (isVisible) {
        visibleAlphaAnimation()
    } else {
        goneAlphaAnimation()
    }
}