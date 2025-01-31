package nz.co.example.rickandmortymodule.features.characters.di

import nz.co.example.rickandmortymodule.features.characters.CharactersFeature
import nz.co.example.rickandmortymodule.features.characters.business.CharactersRepository
import nz.co.example.rickandmortymodule.features.characters.business.CharactersUseCase
import nz.co.example.rickandmortymodule.features.characters.data.CharactersRepositoryImpl
import nz.co.example.rickandmortymodule.features.characters.data.remote.CharactersApiService
import org.koin.dsl.module
import retrofit2.Retrofit

internal val charactersModule = module {
    factory<CharactersFeature> { CharactersUseCase(get()) }
    single {
        val retrofit = get<Retrofit>()
        retrofit.create(CharactersApiService::class.java)
    }
    single<CharactersRepository> { CharactersRepositoryImpl(get(), get(), get()) }
}