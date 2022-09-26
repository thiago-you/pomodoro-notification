package you.thiago.pomodoro.presenter.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import you.thiago.pomodoro.R
import you.thiago.pomodoro.databinding.FragmentHomeBinding
import you.thiago.pomodoro.presenter.home.HomeViewModel.ButtonStatus.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInterface()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView().also {
            _binding = null
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeTimer()
    }

    private fun setupInterface() {
        binding.tvTimer.text = viewModel.defaultTimer

        binding.progressBar.max = 100
        binding.progressBar.progress = viewModel.timerProgress.value
        binding.progressBar.isIndeterminate = false

        binding.btnConfig.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_ConfigFragment)
        }

        binding.btnToggleTimer.setOnClickListener {
            viewModel.toggleTimer()
        }

        binding.btnResetTimer.setOnClickListener {
            viewModel.resetTimer()
            binding.btnResetTimer.isVisible = false
        }
    }

    private fun setupListeners() {
        lifecycleScope.launchWhenResumed {
            viewModel.timerMaxProgress.collect { value ->
                binding.progressBar.max = value
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.timerProgress.collect { value ->
                binding.progressBar.progress = value
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.timerTime.collect { value ->
                binding.tvTimer.text = value
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.startAnimation.collect { animation ->
                if (animation) {
                    viewModel.setStartAnimation(false)
                    startingAnimation()
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.btnLabelStatus.collect { status ->
                when (status) {
                    BTN_START -> {
                        binding.btnToggleTimer.setText(R.string.start)

                        if (viewModel.isTimerRunning()) {
                            binding.btnResetTimer.isVisible = true
                        }
                    }
                    BTN_STOP -> {
                        binding.btnToggleTimer.setText(R.string.stop)
                        binding.btnResetTimer.isVisible = false
                    }
                }
            }
        }
    }

    private fun startingAnimation() {
        ObjectAnimator.ofInt(binding.progressBar, "progress", 100)
            .setDuration(1500)
            .start()
    }
}