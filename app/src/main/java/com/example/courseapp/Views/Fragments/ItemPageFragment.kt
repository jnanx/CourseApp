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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.courseapp.Adapter.ItemAdapter
import com.example.courseapp.VeiwModels.ItemFragmentViewModel
import com.example.courseapp.R
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ItemPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var itemId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemId = it.getInt(ITEM_ID_PARAM)
        }
        viewModelItemPage.setCurrentItem(itemId!!)
    }

    private val viewModelItemPage: ItemFragmentViewModel by activityViewModels()

    lateinit var backToHomeBtn: CardView

    lateinit var itemName: TextView

    lateinit var itemIcon: ImageView

    lateinit var itemCost: TextView

    lateinit var itemType: TextView

    lateinit var itemLore: TextView

    lateinit var itemDescription: TextView

    lateinit var recipeRecycler: RecyclerView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_page, container, false)
        backToHomeBtn = view.findViewById(R.id.backToHome)

        backToHomeBtn.setOnClickListener(){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        val adapter = ItemAdapter(viewModelItemPage.itemsRecipe).setOnItemClickListener {

        }
        itemName = view.findViewById(R.id.itemNameFragment)
        itemIcon = view.findViewById(R.id.itemIconFragment)
        itemCost = view.findViewById(R.id.itemCostFragment)
        itemType = view.findViewById(R.id.itemTypeFragment)
        itemLore = view.findViewById(R.id.itemLoreFragment)
        itemDescription = view.findViewById(R.id.itemDescriptionFragment)
        recipeRecycler = view.findViewById(R.id.recipeRecycler)
        recipeRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recipeRecycler.adapter = adapter

        lifecycleScope.launch(){
            viewModelItemPage.itemsRecipe.collect {
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModelItemPage.recipeVisibility.collect {
                if (it) recipeRecycler.visibility = View.VISIBLE
                else recipeRecycler.visibility = View.GONE
            }
        }

        itemName.text = viewModelItemPage.currentItem?.localized_name
        viewModelItemPage.currentItem?.icon?.let { Glide.with(itemIcon).load(BASE_URL+it).into(itemIcon) }
        itemCost.text = viewModelItemPage.currentItem?.cost.toString()

        if(viewModelItemPage.currentItem?.neutral_tier.isNullOrEmpty()){
            itemType.text = "buyable"
        } else itemType.text = "neutral"

        itemLore.text = viewModelItemPage.currentItem?.lore
        //itemLore.text = item?.id.toString()
        itemDescription.text = viewModelItemPage.currentItem?.description
        itemDescription.text = viewModelItemPage.currentItem?.description?.replace("**", "")


        return view
    }



    companion object {
        private const val ITEM_ID_PARAM = "itemId"
        @JvmStatic
        fun newInstance(itemId: Int) =
            ItemPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ITEM_ID_PARAM, itemId)
                }
            }

        const val BASE_URL = "https://dotabase.dillerm.io/dota-vpk"
    }
}