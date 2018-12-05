package fr.epita.hellogames

import retrofit2.http.GET
import retrofit2.http.Query

interface Interface {

    @GET("game/list")
    fun listGames(): retrofit2.Call<List<Games>>

    @GET("game/details")
    fun descriptionGame(@Query("game_id") id: Int): retrofit2.Call<Games>
}

