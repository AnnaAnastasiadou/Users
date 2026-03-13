package com.example.userlist.features.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.userlist.R
import com.example.userlist.features.users.UserAdapter
import com.example.userlist.databinding.ProfileFragmentBinding
import com.example.userlist.features.User
import com.example.userlist.features.renderState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = ProfileFragmentBinding.bind(view)

        // Set a static title immediately
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = "Profile"

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.viewStates.renderState(
                        isLoading = state.isLoading,
                        error = state.error,
                        contentView = binding.userDetailsView
                    )

                    state.data?.let { user: User ->
                        binding.userDetailsView.setModel(user)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}