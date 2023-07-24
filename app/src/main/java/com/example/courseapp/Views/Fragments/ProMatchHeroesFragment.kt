package com.example.courseapp.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.Adapter.ProHeroAdapter
import com.example.courseapp.VeiwModels.HeroFragmentViewModel
import com.example.courseapp.Models.Hero
import com.example.courseapp.R
import com.example.courseapp.SearchMode
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ProMatchHeroesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProMatchHeroesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var heroId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroId = it.getInt(HERO_ID_PARAM)
        }
    }

    private val viewModelProHeroPage: HeroFragmentViewModel by activityViewModels()


    private lateinit var backToPage: CardView

    private lateinit var recyclerProHero: RecyclerView

    private lateinit var progressProHeroes: ContentLoadingProgressBar



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pro_match_heroes, container, false)


        backToPage = view.findViewById(R.id.backToHome)

        backToPage.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        recyclerProHero = view.findViewById(R.id.recyclerProHero)

        progressProHeroes = view.findViewById(R.id.progressBarHeroes)


        val proMatchesAdapter = ProHeroAdapter(viewModelProHeroPage.proMatches)
        recyclerProHero.adapter = proMatchesAdapter
        recyclerProHero.layoutManager = GridLayoutManager(context, 1)
        recyclerProHero.setHasFixedSize(false)


        lifecycleScope.launch {
            viewModelProHeroPage.proMatches.collect {
                proMatchesAdapter.notifyDataSetChanged()
            }
        }


        lifecycleScope.launch{
            viewModelProHeroPage.loadHeroesStateFlow.collect{
                when(it){
                    SearchMode.NONE -> {
                        progressProHeroes.hide()
                        progressProHeroes.visibility = View.GONE
                        recyclerProHero.visibility = View.GONE
                    }

                    SearchMode.ERROR -> {
                        progressProHeroes.hide()
                        progressProHeroes.visibility = View.GONE
                        recyclerProHero.visibility = View.GONE
                    }

                    SearchMode.LOADING -> {
                        progressProHeroes.show()
                        progressProHeroes.visibility = View.VISIBLE
                        recyclerProHero.visibility = View.GONE
                    }

                    SearchMode.LOADED -> {
                        progressProHeroes.hide()
                        progressProHeroes.visibility = View.GONE
                        recyclerProHero.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModelProHeroPage.loadProMatches(heroId!!)

        return view
    }


    companion object {
        private const val HERO_ID_PARAM = "hero_id"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProMatchHeroesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(heroId: Int) =
            ProMatchHeroesFragment().apply {
                arguments = Bundle().apply {
                    putInt(HERO_ID_PARAM, heroId)
                }
            }
    }
}