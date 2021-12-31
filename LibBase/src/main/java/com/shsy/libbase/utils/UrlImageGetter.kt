package com.shsy.libbase.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.text.Html
import android.util.Base64
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @ClassName UrlImageGetter
 * @Description imgGetter
 * @Author 嘟嘟侠
 * @Date 2021/12/1 10:59 上午
 * @Version 1.0
 */
class UrlImageGetter(
    private val context: Context,
    private val scope: CoroutineScope,
    private val textView: TextView
) : Html.ImageGetter {
    override fun getDrawable(src: String): Drawable {

        val drawable = LevelListDrawable()
        scope.launch {

            val resultDrawable: Drawable?

            if (src.startsWith("http")) {
                val request = ImageRequest.Builder(context)
                    .data(src)
                    .build()
                resultDrawable = context.imageLoader.execute(request).drawable
            } else {
                val bytes = Base64.decode(src.split(",")[1], Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                resultDrawable = bitmap.toDrawable(context.resources)
            }

            drawable.addLevel(1, 1, resultDrawable)
            drawable.setBounds(
                0,
                0,
                resultDrawable?.intrinsicWidth ?: 0,
                resultDrawable?.intrinsicHeight ?: 0
            )
            drawable.level = 1
            textView.invalidate()
            textView.text = textView.text
        }
        return drawable
    }
}