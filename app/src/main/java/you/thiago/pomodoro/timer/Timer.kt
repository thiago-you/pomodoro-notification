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

    private var status = TimerStatus.PAUSED

    fun isResumed(): Boolean {
        return status == TimerStatus.RESUMED
    }

    fun startTimer(action: (time: String) -> Unit) {
        status = TimerStatus.RESUMED

        progress = 0
        progressAction = action
        startedTime = System.currentTimeMillis()
        futureTime = startedTime + timeInMilliseconds

        setupCountdown()
    }

    fun resumeTimer() {
        startedTime = System.currentTimeMillis()
        futureTime += startedTime

        status = TimerStatus.RESUMED
        setupCountdown()
    }

    fun pauseTimer() {
        futureTime -= System.currentTimeMillis() + interval

        status = TimerStatus.PAUSED
        countdownTimer?.cancel()
    }

    fun resetTimer() {
        countdownTimer?.cancel()

        countdownTimer = null
        pauseOffSet = 0

        status = TimerStatus.PAUSED
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
                    progress += interval.toInt() / 1000
                    pauseOffSet = timeInMilliseconds - millisUntilFinished
                    progressAction(formatTime(millisUntilFinished / 1000))
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

    private fun formatTime(time: Long): String {
        var minutes = 0L
        var seconds = time

        if (seconds > 3600) {
            minutes = (seconds / 3600 * 60  + ((seconds % 3600) / 60))
        }

        seconds %= 60

        return String.format("%02d:%02d", minutes, seconds)
    }

    private enum class TimerStatus {
        RESUMED,
        PAUSED
    }
}