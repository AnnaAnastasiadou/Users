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
import com.example.userlist.features.users.UserAdapter
import com.example.userlist.data.repository.UsersRepository
import com.example.userlist.databinding.UsersFragmentBinding
import com.example.userlist.features.details.DetailActivity
import com.example.userlist.features.renderState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.users_fragment) {
    private val viewModel: UsersViewModel by viewModels()

    // Fragments can outlive their Views (e.g., when a Fragment is moved to the backstack).
    // If we kept a permanent reference to the View, we would cause a Memory Leak.
    // '_binding' is the real storage. It is nullable because the View can be null
    // while the Fragment is still "alive" in the background.
    private var _binding: UsersFragmentBinding? = null
    // 'binding' is only valid between 'onViewCreated' and 'onDestroyView'.
    // The '!!' is safe because we only access it when the UI is actually visible.
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 'bind' connects our Kotlin object to the already existing View.
        // It maps the XML IDs so we can use binding.userList instead of findViewById.
        _binding = UsersFragmentBinding.bind(view)

        // Initialize the adapter used for the RecyclerView (list) in the fragment
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
                    binding.viewStates.renderState(
                        isLoading = state.isLoading,
                        error = state.error,
                        contentView = binding.userList
                    )

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