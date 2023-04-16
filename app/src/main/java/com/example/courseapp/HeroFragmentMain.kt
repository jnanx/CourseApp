package com.example.courseapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.courseapp.Adapter.HeroAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HeroFragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeroFragmentMain : Fragment(){
    private val viewModel: HeroFragmentViewModel by activityViewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var backToHome : CardView

    private lateinit var heroListStrength : RecyclerView

    private lateinit var heroListAgility : RecyclerView

    private lateinit var heroListIntelligence : RecyclerView


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hero_main, container, false)
        backToHome = view.findViewById(R.id.backToHome)

        backToHome.setOnClickListener(){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        heroListStrength = view.findViewById(R.id.hero_list_strength)
        heroListStrength.layoutManager = GridLayoutManager(context, 3)

        heroListAgility = view.findViewById(R.id.hero_list_agility)
        heroListAgility.layoutManager = GridLayoutManager(context, 3)

        heroListIntelligence = view.findViewById(R.id.hero_list_intelligence)
        heroListIntelligence.layoutManager = GridLayoutManager(context, 3)

        heroListStrength.setHasFixedSize(false)
        heroListAgility.setHasFixedSize(false)
        heroListIntelligence.setHasFixedSize(false)


        val strengthHeroesAdapter = HeroAdapter(viewModel.heroesStrength)
            .setOnItemClickListener{
                    position -> openHeroInfoFragment(viewModel.heroesStrength.value[position].id)
            }
        val agilityHeroesAdapter = HeroAdapter(viewModel.heroesAgility)
            .setOnItemClickListener{
                    position -> openHeroInfoFragment(viewModel.heroesAgility.value[position].id)
            }
        val intelligenceHeroesAdapter = HeroAdapter(viewModel.heroesIntelligence)
            .setOnItemClickListener{
                position -> openHeroInfoFragment(viewModel.heroesIntelligence.value[position].id)
            }

        heroListStrength.adapter = strengthHeroesAdapter
        heroListAgility.adapter = agilityHeroesAdapter
        heroListIntelligence.adapter = intelligenceHeroesAdapter
        lifecycleScope.launch{
            viewModel.heroesStrength.collect{
                strengthHeroesAdapter.notifyDataSetChanged()
                agilityHeroesAdapter.notifyDataSetChanged()
                intelligenceHeroesAdapter.notifyDataSetChanged()
            }
        }

        viewModel.loadHeroes()
        return view
    }

    private fun openHeroInfoFragment(heroId: Int?){
        val newFragment = heroId?.let { HeroPageFragment.newInstance(it) }
        if (newFragment != null)
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainContainer, newFragment)
                addToBackStack(null)
                commit()
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HeroFragmentMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HeroFragmentMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}