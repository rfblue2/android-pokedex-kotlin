package com.roland.androidpokedexkotlin.model

data class NamePair(val name: String, val url: String)

data class SpriteList(val front_default: String)

data class Stat(val stat: NamePair, val base_stat: Int)

data class Type(val type: NamePair)

data class Move(val move: NamePair)

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: SpriteList,
    val stats: List<Stat>,
    val types: List<Type>,
    val moves: List<Move>
) {
    fun getSpriteUrl(): String {
        return sprites.front_default
    }
}

data class PokemonList(
    val results: List<NamePair>
)