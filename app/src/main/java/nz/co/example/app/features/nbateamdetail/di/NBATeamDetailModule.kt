package nz.co.example.app.features.nbateamdetail.di

import nz.co.example.app.features.nbateamdetail.NBATeamDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val nbaTeamDetailModule = module {
    viewModel { params -> NBATeamDetailViewModel(params.get(), get()) }
}