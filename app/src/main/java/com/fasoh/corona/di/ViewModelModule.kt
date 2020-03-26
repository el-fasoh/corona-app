package com.ungagroup.mwananchi.di.modules

import com.fasoh.corona.ui.dashboard.DashboardViewModel
import com.fasoh.corona.ui.home.HomeViewModel
import com.fasoh.corona.ui.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }

    viewModel { HomeViewModel(get(), get()) }

    viewModel { NotificationsViewModel() }
}