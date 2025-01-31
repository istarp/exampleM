package nz.co.example.app.features.favouritecharacters.di

import nz.co.example.app.features.favouritecharacters.FavouriteCharactersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favouriteCharactersModule = module {
    viewModelOf(::FavouriteCharactersViewModel)
}