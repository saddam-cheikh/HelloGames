package fr.epita.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val gameList = arrayListOf<Games>()
    private val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    private val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    private val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
    private val service: Interface = retrofit.create(Interface::class.java)
    private val callback = object : Callback<List<Games>> {
        override fun onFailure(call: Call<List<Games>>?, t: Throwable?) {
            Log.d("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<List<Games>>?, response: Response<List<Games>>?) {
            if (response != null) {
                if (response.code() == 200) {
                    val value = response.body()
                    if (value != null) {
                        gameList.addAll(value)
                        Log.d("TAG", "WebService success : " + gameList.size)
                        val selctRandomGames = ArrayList<Int>()
                        for (i in 0..4) {
                            val randomInt = (0 until gameList.size).shuffled().first()
                            if (!selctRandomGames.contains(randomInt)) {
                                selctRandomGames.add(randomInt)
                            }
                        }
                        imageTopLeft.setImageResource(getImageGame(gameList[selctRandomGames[0]].id!!))
                        imageTopLeft.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra(" selectGame", gameList[selctRandomGames[0]].id)
                            startActivity(explicitIntent)
                        }

                        imageTopRight.setImageResource(getImageGame(gameList[selctRandomGames[1]].id!!))
                        imageTopRight.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra(" selectGame", gameList[selctRandomGames[1]].id)
                            startActivity(explicitIntent)
                        }

                        imageBottomLeft.setImageResource(getImageGame(gameList[selctRandomGames[2]].id!!))
                        imageBottomLeft.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra(" selectGame", gameList[selctRandomGames[2]].id)
                            startActivity(explicitIntent)
                        }

                        imageBottomRight.setImageResource(getImageGame(gameList[selctRandomGames[3]].id!!))
                        imageBottomRight.setOnClickListener{
                            val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                            explicitIntent.putExtra(" selectGame", gameList[selctRandomGames[3]].id)
                            startActivity(explicitIntent)
                        }

                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service.listGames().enqueue(callback)
    }

    fun getImageGame(id: Int): Int {
        var selectGame = 0
        when(id) {
            1 -> selectGame= R.drawable.tictactoe
            2 -> selectGame = R.drawable.hangman
            3 -> selectGame = R.drawable.sudoku
            4 -> selectGame = R.drawable.battleship
            5 -> selectGame = R.drawable.mineswepper
            6 -> selectGame = R.drawable.gamelife
            7 -> selectGame = R.drawable.memory
            8 -> selectGame = R.drawable.simon
            9 -> selectGame = R.drawable.slidingpuzzle
            10 ->selectGame = R.drawable.mastermind
        }
        return selectGame
    }
}