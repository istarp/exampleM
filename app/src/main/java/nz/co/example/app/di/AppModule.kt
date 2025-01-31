package nz.co.example.app.di

import nz.co.example.app.features.characterdetail.di.characterDetailModule
import nz.co.example.app.features.characters.di.charactersModule
import org.koin.dsl.module

val appModule = module {
    includes(charactersModule, characterDetailModule)
}