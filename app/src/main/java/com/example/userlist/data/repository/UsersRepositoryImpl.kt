package com.example.userlist.data.repository

import com.example.userlist.data.remote.NetworkResult
import com.example.userlist.data.remote.UserApi
import com.example.userlist.data.remote.UserDto
import com.example.userlist.features.Address
import com.example.userlist.features.Company
import com.example.userlist.features.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UsersRepository {

    private suspend fun <T> safeCall(
        call: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(body)
            } else {
                var message: String
                if (response.errorBody() != null) {
                    message = JSONObject(
                        response.errorBody()!!.charStream().readText()
                    ).getString("message")
                } else {
                    message = "An Error Occurred"
                }
                NetworkResult.Error(message)
            }
        } catch (e: ConnectException) {
            NetworkResult.Error(message = "Unable to reach the server")
        } catch (e: UnknownHostException) {
            NetworkResult.Error(message = "No internet connection")
        } catch (e: HttpException) {
            NetworkResult.Error(message = "HTTP ${e.code()}: ${e.message}")
        }
    }

    private val _cachedUsers = MutableStateFlow<List<UserDto>?>(null)
//    private var cachedUsers: List<UserDto>? = null

    override fun getCachedUsers(): List<UserDto>? {
        return _cachedUsers.value
    }

    private val dummyList: List<User> by lazy {
        (1..10).map { i ->
            User(
                id = i,
                name = "User Number $i",
                username = "user_$i",
                email = "user$i@example.com",
                address = Address(
                    street = "Street $i", suite = "Apt. $i", city = "City", zipcode = "12345-$i"
                ),
                phone = "555-010$i",
                website = "www.user$i.com",
                company = Company(
                    name = "Company $i LLC",
                    catchPhrase = "Innovating the future of $i",
                    bs = "synergize scalable $i"
                )
            )
        }
    }


    override fun getDummyUsers(): List<User> = dummyList

    override suspend fun getAllUsers(): NetworkResult<List<UserDto>> {
        val response = safeCall { userApi.getAllUsers() }
        if (response is NetworkResult.Success) {
            _cachedUsers.value = response.data
        }
        return response
    }

    override fun getUserById(id: Int): UserDto? {
        return _cachedUsers.value?.find { it.id == id }
    }

    override fun observeUserById(id: Int): Flow<UserDto?> {
        return _cachedUsers.map{list -> list?.find { it.id == id }}
    }
}