package com.example.courseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var txtUser: TextView
    private lateinit var btn : Button
    private lateinit var match_idView : TextView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        setupWithNavController(bottomNavView, navController)
//
//
//
//        val currentFragment = supportFragmentManager.fragments.last()
//
//        if (currentFragment!= null && currentFragment is SearchFragment){
//            txtUser = findViewById(R.id.txtUser)
//            btn = findViewById<Button>(R.id.findByIdButton)
//            match_idView = findViewById<TextView>(R.id.gameIdText)
//            btn.setOnClickListener{
//
//                val retrofit=Retrofit.Builder()
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .baseUrl("https://api.opendota.com/api/matches/${match_idView.text}/")
//                    .build()
//                val MacthesRouter = retrofit.create(MacthesRouter::class.java)
//
//                val mcall: Call<MatchInfo> = MacthesRouter.getData()
//                mcall.enqueue(object : Callback<MatchInfo>
//                {
//                    override fun onResponse(call: Call<MatchInfo>, response: Response<MatchInfo>) {
//                        val mmodel = response.body()!!
//
//                        txtUser.movementMethod= ScrollingMovementMethod()
//                        txtUser.text="${mmodel.duration/60}:${mmodel.duration%60}"
//                    }
//
//                    override fun onFailure(call: Call<MatchInfo>, t: Throwable) {
//                        Log.e("Error", t.message.toString())
//                    }
//                })
//
//            }
//        }
    }



}