package com.example.userlist.data.mapper

import com.example.userlist.data.remote.AddressDto
import com.example.userlist.features.Address

fun AddressDto.toAddress() : Address {
    return Address(
        street = street,
        suite = suite,
        city = city,
        zipcode = zipcode
    )
}