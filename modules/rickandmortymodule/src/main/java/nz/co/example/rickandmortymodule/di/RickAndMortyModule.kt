package nz.co.example.rickandmortymodule.di

import nz.co.example.rickandmortymodule.features.api.di.apiModule
import nz.co.example.rickandmortymodule.features.characters.di.charactersModule
import nz.co.example.rickandmortymodule.features.database.di.databaseModule
import org.koin.dsl.module

val rickAndMortyModule = module {
    includes(apiModule, databaseModule, charactersModule)
}