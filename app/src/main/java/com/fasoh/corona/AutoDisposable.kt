package com.fasoh.corona

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposable : LifecycleObserver {
    lateinit var compositeDisposable: CompositeDisposable
    fun bindTo(lifecycle: Lifecycle): AutoDisposable {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
        return this
    }

    fun add(disposable: Disposable) {
        when {
            ::compositeDisposable.isInitialized -> compositeDisposable.add(disposable)
            else -> throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun Disposable.addTo(autoDisposable: AutoDisposable) {
        autoDisposable.add(this)
    }
} 