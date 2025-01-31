package nz.co.example.rickandmortymodule.features.characters.di

import nz.co.example.rickandmortymodule.features.characters.CharactersFeature
import nz.co.example.rickandmortymodule.features.characters.business.CharactersRepository
import nz.co.example.rickandmortymodule.features.characters.business.CharactersUseCase
import nz.co.example.rickandmortymodule.features.characters.data.CharactersRepositoryImpl
import nz.co.example.rickandmortymodule.features.characters.data.CharactersService
import nz.co.example.rickandmortymodule.features.characters.data.CharactersServiceImpl
import org.koin.dsl.module

internal val charactersModule = module {
    factory<CharactersFeature> { CharactersUseCase(get()) }
    factory<CharactersService> { CharactersServiceImpl(get()) }
    single<CharactersRepository> { CharactersRepositoryImpl(get(), get(), get()) }
}