package com.example.userlist.features.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userlist.databinding.ActivityDetailsBinding
import com.example.userlist.features.Address
import com.example.userlist.features.Company
import com.example.userlist.features.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout: This converts the XML file into a live Kotlin object.
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

        // Data Retrieval: We extract the information sent via the Intent.
        // Intents are like "envelopes" passed between activities.
        val userId = intent.getIntExtra("USER_ID", -1)

        val user = User(
            id = userId,
            name = "User Number $userId",
            username = "user_$userId",
            email = "user$userId@example.com",
            address = Address(
                street = "Street $userId",
                suite = "Apt. $userId",
                city = "City",
                zipcode = "12345-$userId"
            ),
            phone = "555-010$userId",
            website = "www.user$userId.com",
            company = Company(
                name = "Company $userId LLC",
                catchPhrase = "Innovating the future of $userId",
                bs = "synergize scalable $userId"
            )
        )

        user?.let {
            // update the action bar
            supportActionBar?.title = it.name
            // trigger the ui updates
            binding.userDetailsView.setModel(it)
        }
    }
}