package com.fasoh.corona.extentions

import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.fadeIn(durationValue: Long = 500L, alphaValue: Float = 1F) {
    this.animate().apply {
        interpolator = LinearInterpolator()
        duration = durationValue
        alpha(alphaValue)
        start()
    }
}

fun View.fadeOut(durationValue: Long = 500L, alphaValue: Float = 0F) {
    this.animate().apply {
        interpolator = LinearInterpolator()
        duration = durationValue
        alpha(alphaValue)
        start()
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.toggleVisibility() {
    when (this.visibility) {
        View.GONE -> this.visibility = View.VISIBLE
        View.INVISIBLE -> this.visibility = View.VISIBLE
        View.VISIBLE -> this.visibility = View.GONE
    }
}

fun View.slideVertically(distance: Float, duration: Long = 500L) {
    this.animate().translationY(distance).duration = duration
}

fun View.animateWithDelay(distance: Float, duration: Long = 500L) {
    this.animate()
        .translationY(0f)
        .setStartDelay(0)
        .withEndAction {
            this.animate().setStartDelay(duration).translationY(distance)
        }
}

fun View.disableView() {
    this.isEnabled = false
}

fun View.enableView() {
    this.isEnabled = true
}

fun View.addLayoutMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val params: LinearLayout.LayoutParams =
        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    params.setMargins(left, top, right, bottom)
    this.layoutParams = params
}