package com.example.courseapp.Room


import androidx.room.*
import com.example.courseapp.Models.MatchData
import com.example.courseapp.Models.Player
import com.example.courseapp.Models.ProMatchData
import com.example.courseapp.Retrofit.MatchDataRepository
import com.example.courseapp.Room.PlayerListConverter

@Entity(tableName = "query_matches")
data class QueryMatchData(
    @ColumnInfo val match_id: Long,
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}

data class MatchDataRoomDTO(
    val duration: Int,
    val radiant_win: Boolean,
    val radiant_score: Int,
    val dire_score: Int,
    val players: List<Player>,
)

@Entity(tableName = "match")
data class MatchDataEntity(
    @ColumnInfo(name="duration") val duration: Int,
    @ColumnInfo(name="radiant_win") val radiant_win: Boolean,
    @ColumnInfo(name="radiant_score") val radiant_score: Int,
    @ColumnInfo(name="dire_score") val dire_score: Int,
    @ColumnInfo(name="players") val players: List<Player>,
    @ColumnInfo(name="id_query") val idQuery: Long
){
    @PrimaryKey(autoGenerate = true) var id = 0
}

@Dao
interface MatchDataDao{
    @Insert
    suspend fun insertQuery(queryMatches: QueryMatchData): Long

    @Insert
    suspend fun insertMatch(match: MatchDataEntity)

    @Query("SELECT duration, radiant_win, radiant_score, dire_score, players FROM `match` INNER JOIN query_matches ON (query_matches.id = id_query) WHERE match_id=:id" )
    suspend fun getMatchData(id: Long): MatchDataRoomDTO?
}

@Database(entities = [QueryMatchData::class, MatchDataEntity::class], version = 1)
@TypeConverters(PlayerListConverter::class)
abstract class MatchDataDatabase: RoomDatabase(){
    abstract fun matchDao(): MatchDataDao
}

class RoomDecoratorMatchDataRepository(
    private val decoratedRepository: MatchDataRepository,
    matchesDatabase: MatchDataDatabase
): MatchDataRepository {

    private val matchDao = matchesDatabase.matchDao()
    override suspend fun getMatchData(matchId: Long): MatchData {
        val matchData = matchDao.getMatchData(matchId)
        return if(matchData == null){
            val matchDataFromDecorated = decoratedRepository.getMatchData(matchId)
            val queryMatchData = QueryMatchData(matchId)
            val id = matchDao.insertQuery(queryMatchData)
            matchDao.insertMatch(
                matchDataFromDecorated.let {
                    MatchDataEntity(
                        duration = it.duration!!,
                        radiant_win = it.radiant_win!!,
                        radiant_score = it.radiant_score!!,
                        dire_score = it.dire_score!!,
                        players = it.players!!,
                        idQuery = id
                    )
                }
            )
            matchDataFromDecorated
        }
        else matchData.let {
            MatchData(
                match_id = matchId,
                duration = it.duration,
                radiant_win = it.radiant_win,
                radiant_score = it.radiant_score,
                dire_score = it.dire_score,
                players = it.players
            )
        }

    }

    override suspend fun getProMatchData(heroId: Int): List<ProMatchData> {
        TODO("Not yet implemented")
    }
}