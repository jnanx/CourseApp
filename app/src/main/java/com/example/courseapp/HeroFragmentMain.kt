package com.example.courseapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
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
class HeroFragmentMain : Fragment() {
    private val viewModel: HeroFragmentViewModel by viewModels()

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

    private lateinit var heroList : RecyclerView
    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hero_main, container, false)
        backToHome = view.findViewById(R.id.backToHome)

        backToHome.setOnClickListener(){
            //findNavController().navigate(R.id.heroFragmentMain)

            activity?.supportFragmentManager?.popBackStackImmediate()

        }

        heroList= view.findViewById(R.id.hero_list)
        heroList.layoutManager = GridLayoutManager(context, 3)

        heroList.setHasFixedSize(false)
        val heroAdapter = HeroAdapter(viewModel.heroesStateFlow)
        heroList.adapter = heroAdapter

        lifecycleScope.launch{
            viewModel.heroesStateFlow.collect{
                heroAdapter.notifyDataSetChanged()
            }
        }

        viewModel.loadHeroes()

        return view


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