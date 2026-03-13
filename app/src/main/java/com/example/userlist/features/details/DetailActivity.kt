package com.example.userlist.features.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.userlist.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    // We use 'lateinit' because an Activity and its View are created and
    // destroyed at the exact same time. There is no risk of the View
    // being gone while the Activity is still alive.
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout: This converts the XML file into a live Kotlin object.
        // Activity binding is straightforward: inflate once and use it until
        // the Activity is finished.
        binding = ActivityDetailsBinding.inflate((layoutInflater))

        // Attach the UI: This puts the 'root' view of our layout on the screen.
        setContentView(binding.root)

        // Toolbar Setup: We tell Android to treat our XML toolbar as the official Action Bar.
        // This allows us to use helper methods like 'supportActionBar'.
        setSupportActionBar(binding.toolbar)
        // Back Button: Setting to true makes the arrow appear on the tool bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // We define what happens when the back arrow is clicked
        binding.toolbar.setNavigationOnClickListener {
            // We use 'onBackPressedDispatcher' to trigger a "Clean Exit."
            // This is better than calling 'finish()' because:
            //   - It plays the standard Android slide-out animation.
            //   - It manages the "Back Stack" correctly so we return to the List screen smoothly.
            //   - It allows any 'back-press' listeners (like "Discard changes?" popups) to run first.
            onBackPressedDispatcher.onBackPressed()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.userDetails.isVisible = state.data != null
                    binding.viewStates.errorLayout.isVisible = state.error != null
                    binding.viewStates.progressBar.isVisible = state.isLoading
                    state.error?.let {
                        binding.viewStates.tvErrorMessage.text = it
                    }
                    state.data?.let {
                        supportActionBar?.title = it.name
                        binding.userDetailsView.setModel(it)
                    }
                }
            }
        }
    }
}