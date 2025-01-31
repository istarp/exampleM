package nz.co.example.app.features.characterdetail.di

import nz.co.example.app.features.characterdetail.CharacterDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val characterDetailModule = module {
    viewModel { params -> CharacterDetailViewModel(params.get(), get()) }
}