package com.example.userlist.data.mapper

import com.example.userlist.data.remote.UserDto
import com.example.userlist.features.User

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        username = username,
        email = email,
        address = address.toAddress(),
        phone = phone,
        website = website,
        company = company.toCompany()
    )
}