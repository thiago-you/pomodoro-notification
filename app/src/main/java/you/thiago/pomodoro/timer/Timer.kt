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

    private val padTime = 1000L

    fun startTimer(action: (time: String) -> Unit) {
        progress = 0
        progressAction = action
        startedTime = System.currentTimeMillis() + padTime
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

    fun getMaxProgress(): Int {
        return (timeInMilliseconds / 1000).toInt()
    }

    private fun setupCountdown() {
        val currentTime = System.currentTimeMillis()

        if (currentTime >= futureTime) {
            setFinishedTimer()
            return
        }

        countdownTimer = object : CountDownTimer(futureTime - currentTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished < 1000) {
                    setFinishedTimer()
                } else {
                    pauseOffSet = timeInMilliseconds - millisUntilFinished
                    progress += interval.toInt() / 1000

                    progressAction((millisUntilFinished / 1000).toString())
                }
            }

            override fun onFinish() {
                setFinishedTimer()
            }
        }.start()
    }

    private fun setFinishedTimer() {
        progress = 100
        progressAction("0:00")
    }
}