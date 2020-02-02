package com.roland.androidpokedexkotlin.service

import com.roland.androidpokedexkotlin.model.*
import io.reactivex.Observable

class MockPokemonService: PokemonApiService {
    val allPokemon: List<Pokemon> = listOf(
        Pokemon(1,
            "Bulbasaur",
            SpriteList("sprite"),
            listOf(Stat(NamePair("stat1", ""), 0)),
            listOf(),
            listOf()),
        Pokemon(2,
            "Ivysaur",
            SpriteList("sprite"),
            listOf(Stat(NamePair("other stat", ""), 2)),
            listOf(),
            listOf())
    )


    override fun getPokemon(name: String): Observable<Pokemon?> {
        return Observable.just(allPokemon.find { p -> p.name == name })
    }

    override fun getPokemon(): Observable<PokemonList> {
        return Observable.just(PokemonList(
            allPokemon.map { pokemon -> NamePair(pokemon.name, "")}
        ))
    }
}

// Singleton Pokemon API Service
object PokemonApi {
    val service: PokemonApiService by lazy {
        PokemonApiService.create()
//        MockPokemonService()
    }
}