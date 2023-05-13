package com.barkdev.network.gateway

import com.barkdev.netwrok.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit instance
 */
class RetrofitInstance<out S> private constructor(clazz: Class<S>) : CallResult<S> {
    val service: S = Api.service(clazz)

    /**
     * Asynchronously send the request and notify callback of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     */
    override fun <T> enqueue(
        call: Call<T>,
        apiListener: ApiListener<T>
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val data = response.body()

                if (response.isSuccessful) {
                    if (data != null) {
                        println("SUCCESS")
                        //listener.onDataLoaded(ResultData.Success(data))
                        apiListener.onSuccess(data)
                    }
                } else {
                    apiListener.onError(Throwable("FAIL: Response Unsuccessful"))
                    // error response, no access to resource?
                    println("FAIL: Response Unsuccessful")
                }
            }

            override fun onFailure(call: Call<T>, error: Throwable) {
                // something went completely south (like no internet connection)
                // handle failure
                apiListener.onError(Throwable(error.message))
                println("FAIL: Call Failed")
                println(error.message)
            }
        })
    }

    /**
     * Synchronously send the request and return its response.
     */
    override fun <T> execute(call: Call<T>): T? {
        var data: T? = null

        try {
            val result = call.execute()
            println("SUCCESS")
            if (result.body() != null)
                data = result.body()!!
        } catch (e: Exception) {
            println("FAIL: Call Failed")
            println(e.message)
        }

        return data
    }

    object Api {
        fun <T> service(clazz: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(clazz)
        }
    }

    class Builder {
        fun <S> build(clazz: Class<S>) = RetrofitInstance(clazz)
    }
}