package com.fasoh.corona.ui

import androidx.lifecycle.ViewModel
import com.fasoh.corona.AutoDisposable

open class BaseViewModel : ViewModel(){
    protected val autoDisposable = AutoDisposable()
}