package com.shsy.libbase.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 * @ClassName BindingImageView
 * @Description ImageView DataBinding
 * @Author 嘟嘟侠
 * @Date 2021/11/18 9:36 上午
 * @Version 1.0
 */
@BindingAdapter("imgResource")
fun ImageView.bindImgResource(imgResource: Int) {
    this.setImageResource(imgResource)
}

@BindingAdapter("imgUrl")
fun ImageView.bindImgUrl(imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {

    } else {
        this.load(imgUrl)
    }
}
