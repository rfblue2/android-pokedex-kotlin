package com.roland.androidpokedexkotlin.service

import com.roland.androidpokedexkotlin.model.Pokemon
import com.roland.androidpokedexkotlin.model.PokemonList
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") name: String): Observable<Pokemon?>

    @GET("pokemon/")
    fun getPokemon(): Observable<PokemonList>

    companion object {
        fun create(): PokemonApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()

            return retrofit.create(PokemonApiService::class.java)
        }
    }

}