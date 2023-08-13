package com.indev.suntuk.api

import com.indev.suntuk.service.api.IUserAPI
import com.indev.suntuk.service.api.UserAPI
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class UserAPITest {
    private lateinit var httpClient: HttpClient

    @Before
    fun setUp() {
        httpClient = HttpClient {
            install(ContentNegotiation){
                gson()
            }
            defaultRequest {
                url("http://localhost:8080")
                headers {
                    // bearer token
                    append("Authorization", "Bearer eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJlbWFpbCI6ImxpbmNvbG4uaHVlbEB5YWhvby5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImF1dGhfdGltZSI6MTY5MDk5MDg4NywidXNlcl9pZCI6Iml4dVV5bmFyVlVidTh3eVM2Q1NvVlMxYzc4cjEiLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImxpbmNvbG4uaHVlbEB5YWhvby5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9LCJpYXQiOjE2OTA5OTA4ODcsImV4cCI6MTY5MDk5NDQ4NywiYXVkIjoicHJvamVjdC1pZC1lbXVsYXRlIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3Byb2plY3QtaWQtZW11bGF0ZSIsInN1YiI6Iml4dVV5bmFyVlVidTh3eVM2Q1NvVlMxYzc4cjEifQ.")
                }
            }
        }
    }

    @Test
    fun testGetUser() = runTest {
        val userAPI: UserAPI = IUserAPI(httpClient)
        val res =  userAPI.getUser()
        assertNotEquals(res.errorMessage, "")
    }
}