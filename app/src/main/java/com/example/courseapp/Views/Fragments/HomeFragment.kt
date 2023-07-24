package com.example.courseapp.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.courseapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var toHeroCardView: CardView

    private lateinit var toItemCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val i = inflater.inflate(R.layout.fragment_home, container, false)
        toHeroCardView = i.findViewById(R.id.heroCard)
        toItemCardView = i.findViewById(R.id.itemCard)

        toHeroCardView.setOnClickListener(){
            //findNavController().navigate(R.id.heroFragmentMain)
            val newFragment = HeroFragmentMain()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainContainer, newFragment)
                addToBackStack(null)
                commit()
            }
        }

        toItemCardView.setOnClickListener(){
            val newFragment = ItemMainFragment()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.mainContainer, newFragment)
                addToBackStack(null)
                commit()
            }

        }

        return i
    }

    companion object {

    }
}