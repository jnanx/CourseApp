package com.example.courseapp.Views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Adapter.AbilityAdapter
import com.example.courseapp.VeiwModels.HeroFragmentViewModel
import com.example.courseapp.Models.Hero
import com.example.courseapp.R

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

    private lateinit var heroBaseStr: TextView
    private lateinit var heroBaseStrReg: TextView

    private lateinit var heroBaseAgl: TextView
    private lateinit var heroBaseAglReg: TextView

    private lateinit var heroBaseInt: TextView
    private lateinit var heroBaseIntReg: TextView

    private lateinit var abilityRecyclerView: RecyclerView

    private lateinit var heroHype: TextView

    private lateinit var heroBio: TextView

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


        heroBaseStr = view.findViewById(R.id.baseStrength)
        heroBaseStrReg = view.findViewById(R.id.baseStrengthRegen)

        heroBaseAgl = view.findViewById(R.id.baseAgility)
        heroBaseAglReg = view.findViewById(R.id.baseAgilityRegen)

        heroBaseInt = view.findViewById(R.id.baseIntelligence)
        heroBaseIntReg = view.findViewById(R.id.baseIntelligenceRegen)

        abilityRecyclerView = view.findViewById(R.id.abilityRecyclerView)

        heroHype = view.findViewById(R.id.hypeTextBox)
        heroBio = view.findViewById(R.id.bioTextBox)

        backToHome.setOnClickListener{
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        heroName.text= hero?.localized_name
        hero?.icon?.let { Glide.with(heroIcon).load(BASE_URL+it).into(heroIcon)  }
        hero?.image.let { Glide.with(heroPortrait).load(BASE_URL+it).into(heroPortrait) }

        heroBaseStr.text = hero?.attr_strength_base.toString()
        heroBaseStrReg.text = hero?.attr_strength_gain.toString()

        heroBaseAgl.text = hero?.attr_agility_base.toString()
        heroBaseAglReg.text = hero?.attr_agility_gain.toString()

        heroBaseInt.text = hero?.attr_intelligence_base.toString()
        heroBaseIntReg.text = hero?.attr_intelligence_gain.toString()

        heroHype.text = hero?.hype?.replace("**", "")
        heroBio.text = hero?.bio?.replace("**", "")

        when(hero?.attr_primary){
            "strength"->view.findViewById<ImageView>(R.id.isStrenght).visibility= View.VISIBLE
            "agility"->view.findViewById<ImageView>(R.id.isAgility).visibility= View.VISIBLE
            "intelligence"->view.findViewById<ImageView>(R.id.isIntelligence).visibility=
                View.VISIBLE
        }

        abilityRecyclerView.adapter = AbilityAdapter(viewModelHeroPage.getHeroAbilities(heroId!!))
        abilityRecyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    companion object {
        private const val HERO_ID_PARAM = "heroId"
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