package com.example.courseapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import com.example.courseapp.Models.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ITEM_ID_PARAM = "itemId"

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
        item = viewModelItemPage.getItemById(itemId!!)!!
    }

    private val viewModelItemPage: ItemFragmentViewModel by activityViewModels()

    lateinit var backToHomeBtn: CardView

    private var item: Item? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_page, container, false)
        backToHomeBtn = view.findViewById(R.id.backToHome)

        backToHomeBtn.setOnClickListener(){
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemPageFragment.
         */
        // TODO: Rename and change types and number of parameters
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