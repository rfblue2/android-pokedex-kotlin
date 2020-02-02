package com.roland.androidpokedexkotlin

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.roland.androidpokedexkotlin.model.Pokemon
import com.roland.androidpokedexkotlin.service.PokemonApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

private const val ARG_NAME = "name"

/**
 * Displays information about a pokemon
 */
class PokemonFragment : Fragment() {
    private var listener: PokemonListener? = null

    private var name: String? = null
    private var pokemon: Pokemon? = null

    private lateinit var nameView: TextView
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon, container, false)

        nameView   = view.findViewById(R.id.tv_name)
        backBtn    = view.findViewById(R.id.btn_back)

        backBtn.setOnClickListener { listener!!.onBack() }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PokemonListener) listener = context
        else throw RuntimeException("$context must implement PokemonListener")
    }

    override fun onResume() {
        super.onResume()
        // when user views this fragment, fetch the pokemon's info
        name.let { pokemonName -> // since name is var, we use a safe call
            if (pokemon == null && pokemonName != null) {
                PokemonApi.service.getPokemon(pokemonName)
                    .subscribeOn(Schedulers.io())              // run in the background
                    .observeOn(AndroidSchedulers.mainThread()) // switch to main thread when result arrives
                    .subscribe(
                        {
                            // set all the information
                            nameView.text = it!!.name
                            // etc.
                        },
                        { err -> err.printStackTrace() }       // otherwise print the error
                    )

            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface PokemonListener {
        fun onBack()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name The name of the pokemon to display
         * @return A new instance of fragment PokemonFragment.
         */
        @JvmStatic
        fun newInstance(name: String) =
            PokemonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
    }
}
