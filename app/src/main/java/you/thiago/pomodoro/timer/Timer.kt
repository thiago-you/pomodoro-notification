package you.thiago.pomodoro.timer

import android.os.CountDownTimer

class Timer {

    var progress = 0
    var interval = 1000L
    var timeInMilliseconds = 10000L

    private var countdownTimer: CountDownTimer? = null
    private var pauseOffSet = 0L

    private var futureTime = 0L
    private var startedTime = 0L
    private var progressAction: (time: String) -> Unit = {}

    fun startTimer(action: (time: String) -> Unit) {
        progress = 0

        startedTime = System.currentTimeMillis()
        progressAction = action

        futureTime = startedTime + timeInMilliseconds

        setupCountdown()
    }

    fun resumeTimer() {
        setupCountdown()
    }

    fun pauseTimer() {
        countdownTimer?.cancel()
    }

    fun resetTimer() {
        countdownTimer?.cancel()

        countdownTimer = null
        pauseOffSet = 0
    }

    private fun setupCountdown() {
        val currentTime = System.currentTimeMillis()

        if (currentTime >= futureTime) {
            progress = 100
            progressAction("0:00")
            return
        }

        countdownTimer = object : CountDownTimer(futureTime - currentTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = timeInMilliseconds - millisUntilFinished
                progress += interval.toInt()

                progressAction((millisUntilFinished / 1000).toString())
            }

            override fun onFinish() {}
        }.start()
    }
}