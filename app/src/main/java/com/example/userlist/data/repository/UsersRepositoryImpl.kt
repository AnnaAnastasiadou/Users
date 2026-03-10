package com.example.userlist.data.repository

import com.example.userlist.data.remote.NetworkResult
import com.example.userlist.data.remote.UserApi
import com.example.userlist.data.remote.UserDto
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

    override suspend fun getAllUsers(): NetworkResult<List<UserDto>> =
        safeCall { userApi.getAllUsers() }


    override suspend fun getUserById(id: Int): NetworkResult<UserDto> =
        safeCall { userApi.getUserById(id) }

}