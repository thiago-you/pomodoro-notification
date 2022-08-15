package you.thiago.pomodoro.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import you.thiago.pomodoro.R
import you.thiago.pomodoro.databinding.FragmentHomeBinding
import you.thiago.pomodoro.timer.Timer

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var defaultTimer = "30:00"

    private var timer: Timer? = null

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTimer.text = defaultTimer

        binding.progressBar.max = 100
        binding.progressBar.progress = 100
        binding.progressBar.isIndeterminate = false

        binding.btnConfig.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_ConfigFragment)
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
        timer?.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        timer?.resumeTimer()
    }

    private fun starTimer() {
        timer = Timer()

        timer?.also { timer ->
            binding.progressBar.progress = 0
            binding.progressBar.max = timer.timeInMilliseconds.toInt()

            timer.startTimer { time ->
                binding.tvTimer.text = time
                binding.progressBar.progress = timer.progress
            }
        }
    }
}