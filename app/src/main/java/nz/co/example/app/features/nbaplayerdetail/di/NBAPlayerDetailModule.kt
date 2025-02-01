package nz.co.example.app.features.nbaplayerdetail.di

import nz.co.example.app.features.nbaplayerdetail.NBAPlayerDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val nbaPlayerDetailModule = module {
    viewModel { params -> NBAPlayerDetailViewModel(params.get(), get()) }
}