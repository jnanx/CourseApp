package com.example.courseapp.Room

import androidx.room.TypeConverter
import com.example.courseapp.Models.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlayerListConverter {
        @TypeConverter
        fun fromPlayerList(value: List<Player>): String {
            val gson = Gson()
            val type = object : TypeToken<List<Player>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        fun toPlayerList(value: String): List<Player> {
            val gson = Gson()
            val type = object : TypeToken<List<Player>>() {}.type
            return gson.fromJson(value, type)
        }
}