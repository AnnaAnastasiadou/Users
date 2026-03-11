package com.example.userlist.features

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.userlist.R
import com.example.userlist.adapter.UserAdapter
import com.example.userlist.databinding.UsersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment: Fragment(R.layout.users_fragment) {

    // This allows us to access the RecyclerView from the XML
    private var _binding: UsersFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = UsersFragmentBinding.bind(view)

        val adapter = UserAdapter(getDummyUsers())

        binding.userList.adapter = adapter
    }

    // Fragments live longer than their views,
    // so we null out the binding to save memory.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

fun getDummyUsers(): List<User> {
    val users = mutableListOf<User>()

    for (i in 1..10) {
        val dummyAddress = Address(
            street = "Street $i",
            suite = "Apt. $i",
            city = "City",
            zipcode = "12345-$i"
        )

        val dummyCompany = Company(
            name = "Company $i LLC",
            catchPhrase = "Innovating the future of $i",
            bs = "synergize scalable $i"
        )

        // 3. Assemble the User object
        users.add(
            User(
                id = i,
                name = "User Number $i",
                username = "user_$i",
                email = "user$i@example.com",
                address = dummyAddress,
                phone = "555-010$i",
                website = "www.user$i.com",
                company = dummyCompany
            )
        )
    }
    return users
}