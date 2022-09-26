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

    private var _btnLabelStatus = MutableStateFlow(ButtonStatus.BTN_START)
    val btnLabelStatus: StateFlow<ButtonStatus> = _btnLabelStatus

    init {
        setStartAnimation(true)
    }

    fun isTimerRunning(): Boolean {
        return timer != null && timer?.isResumed() == true
    }

    fun setStartAnimation(animation: Boolean) {
        _startAnimation.value = animation
    }

    fun toggleTimer() {
        if (timer == null) {
            starTimer()
        } else {
            if (timer?.isResumed() == true) {
                _btnLabelStatus.value = ButtonStatus.BTN_START
                pauseTimer()
            } else {
                _btnLabelStatus.value = ButtonStatus.BTN_STOP
                resumeTimer()
            }
        }
    }

    private fun starTimer() {
        timer?.resetTimer()
        _timerProgress.value = 0

        timer = Timer()

        _btnLabelStatus.value = ButtonStatus.BTN_STOP

        timer?.also { timer ->
            _timerMaxProgress.value = timer.getMaxProgress()

            timer.startTimer { time ->
                _timerProgress.value = timer.progress
                _timerTime.value = time

                if (timer.progress >= 100) {
                    resetTimer()
                }
            }
        }
    }

    fun resetTimer() {
        timer?.resetTimer()
        _timerProgress.value = 100
        _timerTime.value = defaultTimer

        timer = null

        _btnLabelStatus.value = ButtonStatus.BTN_START
    }

    fun pauseTimer() {
        timer?.pauseTimer()
    }

    fun resumeTimer() {
        timer?.resumeTimer()
    }

    enum class ButtonStatus {
        BTN_START,
        BTN_STOP
    }
}