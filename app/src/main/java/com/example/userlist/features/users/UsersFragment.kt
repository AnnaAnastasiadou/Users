package com.example.userlist.features.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.userlist.R
import com.example.userlist.adapter.UserAdapter
import com.example.userlist.data.repository.UsersRepository
import com.example.userlist.databinding.UsersFragmentBinding
import com.example.userlist.features.details.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.users_fragment) {

    private val viewModel: UsersViewModel by viewModels()

    // This allows us to access the RecyclerView from the XML
    private var _binding: UsersFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = UsersFragmentBinding.bind(view)

        val adapter = UserAdapter(emptyList()) { clickedUser ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("USER_ID", clickedUser.id)
            }
            // navigate to DetailActivity
            startActivity(intent)
        }
        binding.userList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Handle Loading Spinner
                    binding.viewStates.progressBar.isVisible = state.isLoading
                    binding.userList.isVisible = state.data != null
                    binding.viewStates.errorLayout.isVisible = state.error != null

                    state.error?.let { binding.viewStates.tvErrorMessage.text = it }

                    state.data?.let { users ->
                        adapter.updateData(users)
                    }
                }
            }
        }
    }

    // Fragments live longer than their views,
    // so we null out the binding to save memory.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}