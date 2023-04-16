package com.example.courseapp

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.Adapter.ItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragmentMain : Fragment() {
    private val viewModel: ItemFragmentViewModel by activityViewModels()

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

    private lateinit var backToHome: CardView

    private lateinit var itemList: RecyclerView

    private lateinit var layoutItems: ConstraintLayout

    private lateinit var progressItems: ProgressBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_main, container, false)

        layoutItems = view.findViewById(R.id.layoutItems)
        progressItems = view.findViewById(R.id.progressBarItems)

        backToHome = view.findViewById(R.id.backToHome)
        backToHome.setOnClickListener(){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        itemList = view.findViewById(R.id.item_list)

        val itemListAdapter = ItemAdapter(viewModel.itemList)
            .setOnItemClickListener{
                position -> openItemInfoFragment(viewModel.itemList.value[position].id)
            }
        itemList.adapter = itemListAdapter
        itemList.layoutManager = GridLayoutManager(context, 4)
        itemList.setHasFixedSize(false)

        lifecycleScope.launch(Dispatchers.Main){
            viewModel.itemList.collect{
                itemListAdapter.notifyDataSetChanged()
            }
        }


        lifecycleScope.launch {
            viewModel.loadItemsStateFlow.collect{
                when(it){
                    SearchMode.NONE -> progressItems.visibility = View.VISIBLE
                    SearchMode.ERROR -> progressItems.visibility = View.GONE
                    SearchMode.LOADING -> progressItems.visibility = View.VISIBLE
                    SearchMode.LOADED -> {
                        layoutItems.visibility = View.VISIBLE
                        progressItems.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.loadItems()

        return view
    }


    private fun openItemInfoFragment(itemId: Int?){
        val newFragment = itemId?.let { ItemPageFragment.newInstance(it) }
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
         * @return A new instance of fragment ItemFragmentMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemFragmentMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}