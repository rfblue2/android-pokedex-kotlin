package com.roland.androidpokedexkotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.roland.androidpokedexkotlin.model.NamePair
import com.roland.androidpokedexkotlin.service.PokemonApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Displays a list of pokemon
 */
class PokemonListFragment : Fragment() {
    private var listener: PokemonListListener? = null

    private lateinit var pokemonList: ListView
    private lateinit var pokemonAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        // Create the listview and bind it to an adapter
        pokemonList = view.findViewById(R.id.lv_pokemon)
        pokemonAdapter = PokemonListAdapter(view.context)
        pokemonList.adapter = pokemonAdapter

        // Handle item click events
        pokemonList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                // Get the pokemon clicked (the name and url)
                val (name, _) = adapterView.getItemAtPosition(i) as NamePair
                listener!!.onPokemonSelected(name)
            }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PokemonListListener) listener = context
        else throw RuntimeException("$context must implement PokemonListListener")
    }

    override fun onResume() {
        super.onResume()
        // when user views this fragment, fetch the pokemon info if we haven't already
        if (pokemonList.adapter.isEmpty) {
            PokemonApi.service.getPokemon()
                .subscribeOn(Schedulers.io())              // run in the background
                .observeOn(AndroidSchedulers.mainThread()) // switch to main thread when result arrives
                .subscribe(
                    { pokemonAdapter.addPokemonNamePairs(it.results) }, // populate the list
                    { err -> err.printStackTrace() }       // otherwise print the error
                )

        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface PokemonListListener {
        fun onPokemonSelected(name: String)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PokemonListFragment.
         */
        @JvmStatic
        fun newInstance() = PokemonListFragment()
    }
}
