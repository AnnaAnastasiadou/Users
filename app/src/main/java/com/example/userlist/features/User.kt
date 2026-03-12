package com.example.userlist.features

import android.os.Parcelable
import com.example.userlist.data.remote.CompanyDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
) : Parcelable