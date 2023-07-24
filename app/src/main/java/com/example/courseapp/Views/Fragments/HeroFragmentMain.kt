package com.example.courseapp.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.Adapter.HeroAdapter
import com.example.courseapp.VeiwModels.HeroFragmentViewModel
import com.example.courseapp.R
import com.example.courseapp.SearchMode
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HeroFragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeroFragmentMain : Fragment() {
    private val viewModel: HeroFragmentViewModel by activityViewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private lateinit var backToHome: CardView

    private lateinit var heroListStrength: RecyclerView

    private lateinit var heroListAgility: RecyclerView

    private lateinit var heroListIntelligence: RecyclerView

    private lateinit var heroListUniversal: RecyclerView

    private lateinit var isStrengthBtn: CardView

    private lateinit var isAgilityBtn: CardView

    private lateinit var isIntelligenceBtn: CardView

    private lateinit var isUniversalBtn: CardView

    private lateinit var heroesScrollView: NestedScrollView

    private lateinit var heroSearchEditText: EditText

    private lateinit var heroListSearched: RecyclerView

    private lateinit var progressHeroes: ContentLoadingProgressBar

    private lateinit var layoutHeroes: ConstraintLayout


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hero_main, container, false)
        backToHome = view.findViewById(R.id.backToHome)

        backToHome.setOnClickListener() {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        viewModel.loadAbilities()

        heroListStrength = view.findViewById(R.id.hero_list_strength)
        heroListStrength.layoutManager = GridLayoutManager(context, 3)

        heroListAgility = view.findViewById(R.id.hero_list_agility)
        heroListAgility.layoutManager = GridLayoutManager(context, 3)

        heroListIntelligence = view.findViewById(R.id.hero_list_intelligence)
        heroListIntelligence.layoutManager = GridLayoutManager(context, 3)

        heroListUniversal = view.findViewById(R.id.hero_list_universal)
        heroListUniversal.layoutManager = GridLayoutManager(context, 3)
        heroListSearched = view.findViewById(R.id.hero_list_searched)
        heroListSearched.layoutManager = GridLayoutManager(context, 3)

        heroListStrength.setHasFixedSize(false)
        heroListAgility.setHasFixedSize(false)
        heroListIntelligence.setHasFixedSize(false)
        heroListUniversal.setHasFixedSize(false)
        heroListSearched.setHasFixedSize(false)

        isStrengthBtn = view.findViewById(R.id.heroIsStrength)
        isAgilityBtn = view.findViewById(R.id.heroIsAgility)
        isIntelligenceBtn = view.findViewById(R.id.heroIsIntelligence)
        isUniversalBtn = view.findViewById(R.id.heroIsUniversal)

        heroesScrollView = view.findViewById(R.id.heroesScrollView)

        heroSearchEditText = view.findViewById(R.id.heroSearchEditText)


        progressHeroes = view.findViewById(R.id.progressBarHeroes)
        layoutHeroes = view.findViewById(R.id.layoutHeroes)


        val strengthHeroesAdapter = HeroAdapter(viewModel.heroesStrength)
            .setOnItemClickListener { id ->
                openHeroInfoFragment(id)
            }
        val agilityHeroesAdapter = HeroAdapter(viewModel.heroesAgility)
            .setOnItemClickListener { id ->
                openHeroInfoFragment(id)
            }
        val intelligenceHeroesAdapter = HeroAdapter(viewModel.heroesIntelligence)
            .setOnItemClickListener { id ->
                openHeroInfoFragment(id)
            }
        val universalHeroesAdapter = HeroAdapter(viewModel.heroesUniversal)
            .setOnItemClickListener { id ->
                openHeroInfoFragment(id)
            }
        val searchedHeroesAdapter = HeroAdapter(viewModel.heroesSearched)
            .setOnItemClickListener { id ->
                openHeroInfoFragment(id)
            }

        isStrengthBtn.setOnClickListener {
            heroesScrollView.post { heroesScrollView.smoothScrollTo(0, heroListStrength.top) }
        }

        isAgilityBtn.setOnClickListener {
            heroesScrollView.post { heroesScrollView.smoothScrollTo(0, heroListAgility.top) }
        }

        isIntelligenceBtn.setOnClickListener {
            heroListIntelligence.post { heroesScrollView.smoothScrollTo(0, heroListIntelligence.top) }
        }

        isUniversalBtn.setOnClickListener {
            heroesScrollView.post { heroesScrollView.smoothScrollTo(0, heroListUniversal.top) }
        }

        heroSearchEditText.addTextChangedListener(
            afterTextChanged = {
                if (heroSearchEditText.text.toString().isNotEmpty()) {
                    viewModel.searchHeroes(heroSearchEditText.text.toString())
                    heroListStrength.visibility = View.GONE
                    heroListAgility.visibility = View.GONE
                    heroListIntelligence.visibility = View.GONE
                    heroListUniversal.visibility = View.GONE
                    heroListSearched.visibility = View.VISIBLE
                } else {
                    heroListStrength.visibility = View.VISIBLE
                    heroListAgility.visibility = View.VISIBLE
                    heroListIntelligence.visibility = View.VISIBLE
                    heroListUniversal.visibility = View.VISIBLE
                    heroListSearched.visibility = View.GONE
                }
            })

        heroListStrength.adapter = strengthHeroesAdapter
        heroListAgility.adapter = agilityHeroesAdapter
        heroListIntelligence.adapter = intelligenceHeroesAdapter
        heroListUniversal.adapter = universalHeroesAdapter
        heroListSearched.adapter = searchedHeroesAdapter

        lifecycleScope.launch {
            viewModel.heroesStrength.collect {
                strengthHeroesAdapter.notifyDataSetChanged()
                agilityHeroesAdapter.notifyDataSetChanged()
                intelligenceHeroesAdapter.notifyDataSetChanged()
                universalHeroesAdapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch() {
            viewModel.heroesSearched.collect {
                searchedHeroesAdapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.loadHeroesStateFlow.collect {
                when (it) {
                    SearchMode.NONE -> {
                        progressHeroes.hide()
                        progressHeroes.visibility = View.GONE
                        layoutHeroes.visibility = View.GONE
                    }

                    SearchMode.ERROR -> {
                        progressHeroes.hide()
                        progressHeroes.visibility = View.GONE
                        layoutHeroes.visibility = View.GONE
                    }

                    SearchMode.LOADING -> {
                        progressHeroes.show()
                        progressHeroes.visibility = View.VISIBLE
                        layoutHeroes.visibility = View.GONE
                    }

                    SearchMode.LOADED -> {
                        progressHeroes.hide()
                        layoutHeroes.visibility = View.VISIBLE
                        progressHeroes.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.loadHeroes()

        return view


    }

    private fun openHeroInfoFragment(heroId: Int?) {
        val newFragment = heroId?.let { HeroPageFragment.newInstance(it) }
        if (newFragment != null)
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainContainer, newFragment)
                addToBackStack(null)
                commit()
            }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HeroFragmentMain().apply {
                arguments = Bundle().apply {

                }
            }
    }

}