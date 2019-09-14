package ru.visdom.raiffeisenbusinessad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.visdom.raiffeisenbusinessad.preferences.UserPreferences

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialization user preferences
        UserPreferences.init(applicationContext)
    }
}
