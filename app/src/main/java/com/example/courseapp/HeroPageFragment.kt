package com.example.courseapp

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.courseapp.Adapter.HeroAdapter
import com.example.courseapp.Adapter.PlayerAdapter
import com.example.courseapp.Models.Hero

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val HERO_ID_PARAM = "heroId"

/**
 * A simple [Fragment] subclass.
 * Use the [HeroPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeroPageFragment private constructor(): Fragment() {
    // TODO: Rename and change types of parameters
    private var heroId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroId = it.getInt(HERO_ID_PARAM)
        }
        hero = viewModelHeroPage.getHeroById(heroId!!)!!

    }

    private val viewModelHeroPage: HeroFragmentViewModel by activityViewModels()

    private var hero: Hero? = null

    private lateinit var backToHome : CardView
    private lateinit var heroName: TextView
    private lateinit var heroIcon: ImageView
    private lateinit var heroPortrait: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hero_page, container, false)

        backToHome = view.findViewById(R.id.backToHome)
        heroName = view.findViewById(R.id.heroNameFragment)
        heroIcon = view.findViewById(R.id.heroIconFragment)
        heroPortrait = view.findViewById(R.id.heroPortraitFragment)

        backToHome.setOnClickListener{
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        heroName.text= hero?.localized_name
        hero?.icon?.let {Glide.with(heroIcon).load(BASE_URL+it).into(heroIcon)  }
        hero?.image.let { Glide.with(heroPortrait).load(BASE_URL+it).into(heroPortrait) }

        when(hero?.attr_primary){
            "strength"->view.findViewById<ImageView>(R.id.isStrenght).visibility=View.VISIBLE
            "agility"->view.findViewById<ImageView>(R.id.isAgility).visibility=View.VISIBLE
            "intelligence"->view.findViewById<ImageView>(R.id.isIntelligence).visibility=View.VISIBLE
        }

        return view
    }
//приложение вылетает когда захожу сюда в этот фрагмент с героем
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param heroId id of a hero.
         * @return A new instance of fragment hero_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(heroId: Int) =
            HeroPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(HERO_ID_PARAM, heroId)
                }
            }

        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk"
    }



}