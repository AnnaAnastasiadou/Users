package com.example.userlist.features

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.userlist.databinding.ViewUserDetailsBinding
import com.google.android.material.card.MaterialCardView

// @JvmOverloads tells Kotlin: "Automatically create all 3 constructor versions that Android needs"
class UserDetailsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    // ViewUserDetailsBinding is a generated class that "knows" all the IDs in your XML.
    // .inflate() turns the XML file into live objects.
    // 'this' tells the binding: "Put the XML content directly inside THIS CardView."
    private val binding: ViewUserDetailsBinding =
        ViewUserDetailsBinding.inflate(LayoutInflater.from(context), this)

    init {
        radius = 12f
        cardElevation = 4f
    }

    // this function distributes that data to the text fields.
    fun setModel(user: User) {
        binding.apply {
            tvPhone.text = user.phone
            tvEmail.text = user.email
            tvWebsite.text = user.website

            tvCity.text = user.address.city
            tvSuiteStreet.text = "${user.address.suite}, ${user.address.street}"
            tvZip.text = user.address.zipcode

            tvCompanyName.text = user.company.name
            tvCatchPhrase.text = user.company.catchPhrase
            tvBs.text = user.company.bs
        }
    }
}