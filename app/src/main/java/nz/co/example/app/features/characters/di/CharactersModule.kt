package nz.co.example.app.features.characters.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import nz.co.example.app.features.characters.CharactersViewModel

val charactersModule = module {
    viewModelOf(::CharactersViewModel)
}