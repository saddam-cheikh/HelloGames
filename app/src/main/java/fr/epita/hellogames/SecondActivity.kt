package fr.epita.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    var game = Games()
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
    val service: WebService = retrofit.create(WebService::class.java)
    val callback = object : retrofit2.Callback<Games> {
        override fun onFailure(call: Call<Games>?, t: Throwable?) {
            Log.d("TAG", "Call to the Webservice failed")
        }
        override fun onResponse(call: Call<Games>?,
                                response: Response<Games>?) {
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    val data = response.body()
                    if (data != null) {
                        game = data
                        findViewById<ImageView>(R.id.imageView).setImageResource(getImageGame(game.id!!))
                        findViewById<TextView>(R.id.textName).text = "Name: " + game.name
                        findViewById<TextView>(R.id.textType).text = "Type: " + game.type
                        findViewById<TextView>(R.id.textNbPlayers).text = "Nb Player: " + game.players.toString()
                        findViewById<TextView>(R.id.textYear).text = "Year: " + game.year.toString()
                        findViewById<TextView>(R.id.textDescription).text = "Description: " + game.description_en

                        knowMore.setOnClickListener{
                            val url = game.url
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }
                    }
                    else
                        Log.d("TAG", "No Data On the Webservice")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val  selectGame = intent.getIntExtra(" selectGame", -1)
        service.DescGame( selectGame).enqueue(callback)
    }

    fun getImageGame(id: Int): Int {
        var selectGame = 0
        when(id) {
            1 ->  selectGame = R.drawable.tictactoe
            2 ->  selectGame = R.drawable.hangman
            3 ->  selectGame = R.drawable.sudoku
            4 ->  selectGame = R.drawable.battleship
            5 ->  selectGame = R.drawable.mineswepper
            6 ->  selectGame = R.drawable.gamelife
            7 ->  selectGame = R.drawable.memory
            8 ->  selectGame = R.drawable.simon
            9 ->  selectGame = R.drawable.slidingpuzzle
            10 -> selectGame = R.drawable.mastermind
        }
        return  selectGame
    }
}