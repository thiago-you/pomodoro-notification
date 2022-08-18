package you.thiago.pomodoro.presenter.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import you.thiago.pomodoro.timer.Timer

class HomeViewModel : ViewModel() {

    var defaultTimer = "30:00"

    private var timer: Timer? = null

    private var _timerProgress = MutableStateFlow(100)
    val timerProgress: StateFlow<Int> = _timerProgress

    private var _timerMaxProgress = MutableStateFlow(100)
    val timerMaxProgress: StateFlow<Int> = _timerMaxProgress

    private var _timerTime = MutableStateFlow(defaultTimer)
    val timerTime: StateFlow<String> = _timerTime

    private var _startAnimation = MutableStateFlow(true)
    val startAnimation: StateFlow<Boolean> = _startAnimation

    init {
        setStartAnimation(true)
    }

    fun setStartAnimation(animation: Boolean) {
        _startAnimation.value = animation
    }

    fun starTimer() {
        timer?.resetTimer()
        _timerProgress.value = 0

        timer = Timer()

        timer?.also { timer ->
            _timerMaxProgress.value = timer.timeInMilliseconds.toInt()

            timer.startTimer { time ->
                _timerProgress.value = timer.progress
                _timerTime.value = time
            }
        }
    }

    fun pauseTimer() {
        timer?.pauseTimer()
    }

    fun resumeTimer() {
        timer?.resumeTimer()
    }
}