package com.roland.androidpokedexkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity(), PokemonListFragment.PokemonListListener, PokemonFragment.PokemonListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // initially display the list of pokemon
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, PokemonListFragment.newInstance(), "pokemonList")
                .commit()
        }


    }

    override fun onPokemonSelected(name: String) {
        // when user selects a pokemon to view
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, PokemonFragment.newInstance(name), "pokemon")
            .addToBackStack(null)
            .commit()
    }

    override fun onBack() {
        // When user hits back from the pokemon activity, go back to list
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, PokemonListFragment.newInstance(), "pokemonList")
            .addToBackStack(null)
            .commit()
    }
}
