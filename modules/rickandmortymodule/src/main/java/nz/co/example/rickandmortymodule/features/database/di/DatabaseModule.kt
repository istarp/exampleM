package nz.co.example.rickandmortymodule.features.database.di

import androidx.room.Room
import nz.co.example.rickandmortymodule.features.characters.data.models.LocationConverter
import nz.co.example.rickandmortymodule.features.characters.data.models.OriginConverter
import nz.co.example.rickandmortymodule.features.characters.di.charactersModule
import nz.co.example.rickandmortymodule.features.database.Database
import org.koin.dsl.module

val databaseModule = module {
    includes(charactersModule)
    single {
        Room.databaseBuilder(
            get(),
            Database::class.java, "rick-and-morty-database"
        ).fallbackToDestructiveMigration()
            .addTypeConverter(OriginConverter())
            .addTypeConverter(LocationConverter())
            .build()
    }
}