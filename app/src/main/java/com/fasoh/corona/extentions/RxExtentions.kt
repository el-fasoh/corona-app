package com.fasoh.corona.extentions

import com.fasoh.corona.AutoDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(autoDisposable: AutoDisposable) { autoDisposable.add(this) }