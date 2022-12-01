package com.example.courseapp

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseapp.Adapter.PlayerAdapter
import com.example.courseapp.Models.MatchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private  lateinit var playerRecyclerView: RecyclerView
lateinit var adapter: PlayerAdapter

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: SearchFragmentViewModel by viewModels()



//    private lateinit var binding: FragmentSearchBinding


//    private val client = OkHttpClient.Builder()
//        .addInterceptor {
//            val request = it
//                .request()
//                .newBuilder()
//                .addHeader("Authorization", "Bearer ")
//                .build()
//            return@addInterceptor it.proceed(request)
//        }
//        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val txtUser = view.findViewById<TextView>(R.id.txtUser)
        val allinfocard = view.findViewById<CardView>(R.id.allinfo)
        val gameId = view.findViewById<EditText>(R.id.gameIdText)
        val direText = view.findViewById<TextView>(R.id.dire)
        val radiantText = view.findViewById<TextView>(R.id.radiant)
        val direWLText = view.findViewById<TextView>(R.id.direWL)
        val radiantWLText = view.findViewById<TextView>(R.id.radiantWL)
        val durationText = view.findViewById<TextView>(R.id.durationText)
        val direScoreText = view.findViewById<TextView>(R.id.direScore)
        val radiantScoreText = view.findViewById<TextView>(R.id.radiantScore)

        view.findViewById<Button>(R.id.findByIdButton).setOnClickListener {

            if (gameId.text.isNullOrBlank()) {
                Toast.makeText(activity, "Введите ID игры", Toast.LENGTH_SHORT).show()
            } else {
                allinfocard.visibility = View.VISIBLE
                Log.d("DOTAAPI", "Click called")

                val matchId = gameId.text.toString().toLongOrNull() ?: 0

                viewModel.getMatchData(matchId)

            }
        }

        lifecycleScope.launch{

            viewModel.matchDataStateFlow.collect{
                Log.d("COLLECT", "$it")
                launch(Dispatchers.Main){
                    if (it != null){

                        if (it.duration != null) {
                            durationText.text=
                                "${resources.getString(R.string.duration)}: " +
                                        if (it.duration / 60 < 10) "0${it.duration / 60}" else "${it.duration / 60}" + ":" + if (it.duration % 60 < 10) "0${it.duration % 60}" else "${it.duration / 60}"
                            direScoreText.text="${it.dire_score}"
                            radiantScoreText.text="${it.radiant_score}"
                        }
                        if(it.radiant_win==false){
                            direText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantText.setTextColor(Color.parseColor("#8C3131"))
                            radiantWLText.text = resources.getString(R.string.lose)
                            direWLText.text = resources.getString(R.string.win)
                            direScoreText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantScoreText.setTextColor(Color.parseColor("#8C3131"))
                        } else{
                            direText.setTextColor(Color.parseColor("#8C3131"))
                            radiantText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantWLText.text = resources.getString(R.string.win)
                            direWLText.text = resources.getString(R.string.lose)
                            direScoreText.setTextColor(Color.parseColor("#8C3131"))
                            radiantScoreText.setTextColor(Color.parseColor("#4D8B31"))
                        }

                        if(it.duration != null && it.radiant_win != null){


                        txtUser.movementMethod = ScrollingMovementMethod()
                        txtUser.text =
                            "${resources.getString(R.string.duration)}: ${it.duration / 60}:${it.duration % 60}" +
                                    "\n${resources.getString(R.string.radiant_win)}: ${it.radiant_win}\n${
                                        resources.getString(
                                                R.string.radiant_score
                                            )
                                    }: ${it.radiant_score}" +
                                    "\n${resources.getString(R.string.dire_win)}: ${!it.radiant_win}\n${
                                        resources.getString(
                                                R.string.dire_score
                                            )
                                    }: ${it.dire_score}"
                        }

                        if(it.players != null) {
                            playerRecyclerView = view.findViewById(R.id.radiantHeroList)
                            playerRecyclerView.layoutManager = LinearLayoutManager(context)
                            playerRecyclerView.setHasFixedSize(false)
                            adapter = PlayerAdapter(it.players)
                            playerRecyclerView.adapter = adapter

                            lifecycleScope.launch{
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment SearchFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SearchFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
