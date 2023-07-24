package com.example.courseapp.Views.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.Adapter.ItemAdapter
import com.example.courseapp.VeiwModels.ItemFragmentViewModel
import com.example.courseapp.R
import com.example.courseapp.SearchMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ItemMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemMainFragment : Fragment() {
    private val viewModel: ItemFragmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var backToHome: CardView

    private lateinit var itemList: RecyclerView

    private lateinit var layoutItems: ConstraintLayout

    private lateinit var progressItems: ProgressBar

    private lateinit var itemSearchEditText: EditText

    private lateinit var itemListSearched: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_main, container, false)

        itemSearchEditText = view.findViewById(R.id.itemSearchText)
        itemListSearched = view.findViewById(R.id.item_list_searched)

        layoutItems = view.findViewById(R.id.layoutItems)
        progressItems = view.findViewById(R.id.progressBarItems)

        backToHome = view.findViewById(R.id.backToHome)
        backToHome.setOnClickListener(){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        itemList = view.findViewById(R.id.item_list)

        val itemListAdapter = ItemAdapter(viewModel.itemList)
            .setOnItemClickListener{id ->
                openItemInfoFragment(id)


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

        val searchedItemsAdapter = ItemAdapter(viewModel.itemsSearched)
            .setOnItemClickListener { id ->
                openItemInfoFragment(id)
            }

        itemSearchEditText.addTextChangedListener(
            afterTextChanged = {
                if (itemSearchEditText.text.toString().isNotEmpty()) {
                    viewModel.searchItems(itemSearchEditText.text.toString())
                    itemList.visibility = View.GONE
                    itemListSearched.visibility = View.VISIBLE
                } else {
                    itemList.visibility = View.VISIBLE
                    itemListSearched.visibility = View.GONE
                }
            })

        itemListSearched.adapter = searchedItemsAdapter
        itemListSearched.layoutManager = GridLayoutManager(context, 4)
        itemList.setHasFixedSize(false)



        lifecycleScope.launch() {
            viewModel.itemsSearched.collect {
                searchedItemsAdapter.notifyDataSetChanged()
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
            ItemMainFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}