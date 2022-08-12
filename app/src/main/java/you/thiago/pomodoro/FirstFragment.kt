package you.thiago.pomodoro

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import you.thiago.pomodoro.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var countdownTimer: CountDownTimer? = null
    private var timeInMilliseconds = 60000L
    private var pauseOffSet = 0L

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfig.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.btnStartTimer.setOnClickListener {
            starTimer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView().also {
            _binding = null
        }
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        resumeTimer()
    }

    private fun starTimer(){
        countdownTimer = object : CountDownTimer(timeInMilliseconds - 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = timeInMilliseconds - millisUntilFinished
                binding.tvTimer.text= (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {}
        }.start()
    }

    private fun resumeTimer() {
        countdownTimer?.start()
    }

    private fun pauseTimer() {
        countdownTimer?.cancel()
    }

    @SuppressLint("SetTextI18n")
    private fun resetTimer() {
        countdownTimer?.cancel()

        binding.tvTimer.text = (timeInMilliseconds/1000).toString()

        countdownTimer = null
        pauseOffSet = 0
    }
}