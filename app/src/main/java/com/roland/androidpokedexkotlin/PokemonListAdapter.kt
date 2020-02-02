package com.roland.androidpokedexkotlin

import android.content.Context
import android.widget.TextView
import android.view.ViewGroup
import com.roland.androidpokedexkotlin.model.NamePair
import android.view.LayoutInflater
import android.view.View
import android.widget.BaseAdapter

class PokemonListAdapter(context: Context) : BaseAdapter() {
    private val pokemonNamePairs: MutableList<NamePair> = ArrayList()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun addPokemonNamePairs(pokemon: List<NamePair>) {
        pokemonNamePairs.addAll(pokemon)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return pokemonNamePairs.size
    }

    override fun getItem(i: Int): Any {
        return pokemonNamePairs[i]
    }

    override fun getItemId(i: Int): Long {
        return 0 // ignore this method
    }

    override fun getView(i: Int, v: View?, viewGroup: ViewGroup): View {
        val view = v ?: inflater.inflate(R.layout.view_pokemon_list_item, viewGroup, false)
        val name = view.findViewById(R.id.tv_lv_name) as TextView
        name.text = Utils.toUpper(pokemonNamePairs[i].name)
        return view
    }
}