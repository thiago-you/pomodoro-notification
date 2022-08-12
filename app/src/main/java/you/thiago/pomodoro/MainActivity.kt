package you.thiago.pomodoro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import you.thiago.pomodoro.databinding.ActivityMainBinding
import you.thiago.pomodoro.notification.NotificationBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        NotificationBuilder()
            .build(this@MainActivity)
            .send()
    }
}