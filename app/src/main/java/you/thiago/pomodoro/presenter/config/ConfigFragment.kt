package you.thiago.pomodoro.presenter.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import you.thiago.pomodoro.R
import you.thiago.pomodoro.databinding.FragmentConfigBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_ConfigFragment_to_HomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView().also {
            _binding = null
        }
    }
}