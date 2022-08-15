package you.thiago.pomodoro.timer

import android.annotation.SuppressLint
import android.os.CountDownTimer

class Timer {

    var progress = 0
    var interval = 1000L
    var timeInMilliseconds = 60000L

    private var countdownTimer: CountDownTimer? = null
    private var pauseOffSet = 0L

    fun startTimer(action: (time: String) -> Unit) {
        progress = 0

        countdownTimer = object : CountDownTimer(timeInMilliseconds - interval, interval) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = timeInMilliseconds - millisUntilFinished
                progress += interval.toInt()
                action((millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {}
        }.start()
    }

    fun resumeTimer() {
        countdownTimer?.start()
    }

    fun pauseTimer() {
        countdownTimer?.cancel()
    }

    @SuppressLint("SetTextI18n")
    fun resetTimer() {
        countdownTimer?.cancel()

        countdownTimer = null
        pauseOffSet = 0
    }
}