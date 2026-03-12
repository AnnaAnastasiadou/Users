package com.example.userlist.data.mapper

import com.example.userlist.data.remote.CompanyDto
import com.example.userlist.features.Company

fun CompanyDto.toCompany(): Company {
    return Company(
        name = name,
        catchPhrase = catchPhrase,
        bs = bs
    )
}