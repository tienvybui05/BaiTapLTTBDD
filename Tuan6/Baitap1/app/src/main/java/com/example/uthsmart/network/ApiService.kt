package com.example.uthsmart.network

import com.example.uthsmart.model.Task
import com.example.uthsmart.model.TaskDetail
import com.example.uthsmart.model.TaskResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

//    API: https://amock.io/api/researchUTH/tasks
    @GET("researchUTH/tasks")
    suspend fun getTasks(): Response<TaskResponse>  // ✅ trả về object có "data": []

    @GET("task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Response<TaskDetail>

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
