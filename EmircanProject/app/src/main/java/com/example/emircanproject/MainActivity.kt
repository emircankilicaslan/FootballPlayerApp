package com.example.emircanproject


import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

import utils.PlayerUtils

class MainActivity : AppCompatActivity() {

    private lateinit var editText: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerDao: PlayerDao
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Veritabanı ve Dao'nun oluşturulması
        val playerDatabase = PlayerDatabase.getDatabase(this)
        playerDao = playerDatabase.playerDao()

        // Material Design kullanarak iki görünüm oluşturma
        editText = findViewById(R.id.editText)
        submitButton = findViewById(R.id.submitButton)

        // RecyclerView tanımlama ve ayarlama
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Oyuncuları veritabanından al
        lifecycleScope.launch(Dispatchers.IO) {
            val playerList = playerDao.getAllPlayers()
            runOnUiThread {
                // RecyclerView için adaptörü oluşturma ve bağlama
                playerAdapter = PlayerAdapter(playerList)
                recyclerView.adapter = playerAdapter
            }
        }

        // EditText görünümü için özellikler ayarlama
        editText.hint = "Enter your name"

        // Button görünümü için özellikler ayarlama
        submitButton.setOnClickListener {
            val playerName = editText.text.toString()
            if (playerName.isNotEmpty()) {
                // Veritabanına oyuncu ekleme
                val player = Player(playerName, "")
                insertPlayer(player)
                // Arka planda bir işlem başlatma
                startBackgroundOperation()
                // Ses dosyasını çalma
                playSound()
            }
        }

        // MediaPlayer oluşturma
      //  mediaPlayer = MediaPlayer.create(this, R.raw.bell_sound)

        // Örnek oyuncu oluşturma
        val player = Player("Lionel Messi", "Forward")
        // Oyuncuyu biçimlendirme
        val formattedPlayer = PlayerUtils.formatPlayer(player)
        println(formattedPlayer)
    }

    private fun insertPlayer(player: Player) {
        lifecycleScope.launch(Dispatchers.IO) {
            playerDao.insert(player)
            // Oyuncuları güncelle
            val updatedPlayers = playerDao.getAllPlayers()
            runOnUiThread {
                playerAdapter.submitList(updatedPlayers)
            }
        }
    }

    private fun startBackgroundOperation() {
        val request = OneTimeWorkRequestBuilder<MyWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS) // İşlemi 5 saniye sonra başlat
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun playSound() {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // MediaPlayer'ı serbest bırak
    }
}
