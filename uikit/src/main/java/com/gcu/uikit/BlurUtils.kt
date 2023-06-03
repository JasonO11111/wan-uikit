package com.gcu.uikit

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 *
 * @author: zoulongsheng
 * @date: 2023/6/1
 * 高斯模糊工具类
 */
object BlurUtils {

    const val TAG = "BlurUtils"

    /**
     * radiusX 沿 X 轴的模糊半径
     * radiusY 沿 Y 轴的模糊半径
     * inputEffect 模糊一次(传入 RenderEffect)
     * edgeTreatment 用于如何模糊模糊内核边缘附近的内容
     * isGoodEffect 高斯模糊效果好 -> 性能差 反之
     */
    fun blurView(
        view: ImageView?,
        rx: Float = 0f,
        ry: Float = 0f,
        mode: Shader.TileMode = Shader.TileMode.CLAMP,
        isGoodEffect: Boolean = false,
        url: String = ""
    ) {
        if (view == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //官方通用方案
            Log.d(TAG, "blur to render effect")
            view.setRenderEffect(RenderEffect.createBlurEffect(rx, ry, mode))
        } else {
            Log.d(TAG, "your version is smaller that Android 12")
            if (isGoodEffect) {
                //效果好，选择Glide，性能差
                Glide.with(view.context).load(url)
                    .apply(
                        RequestOptions.bitmapTransform(
                            BlurTransformation(
                                (rx + rx / 2).toInt(),
                                8
                            )
                        )
                    ).into(view)
            } else {
                //效果不好, RenderScript，性能好 sdk > 17

            }
        }
    }
}