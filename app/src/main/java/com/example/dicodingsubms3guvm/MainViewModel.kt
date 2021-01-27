package com.example.dicodingsubms3guvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<GithubUser>>()
    val listString = MutableLiveData<List<String>>()

    fun setAPISearchUser(username: String) {
        var listItem = arrayListOf<GithubUser>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token 287d0da9dd42fd519db7f3a60a39aa88735970f5")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                // Jika koneksi berhasil
                val result = String(responseBody)
                Log.d("Data berhasil", result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val jsonObject = items.getJSONObject(i)
                        val name = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val idUser = jsonObject.getString("id")
                        val li =GithubUser(
                            idUser,
                            name,
                            avatar
                        )
                        listItem.add(li)
                    }
                    listUsers.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d(errorMessage, error.message.toString())
            }
        })
    }

    fun getAPISearchUser(): LiveData<ArrayList<GithubUser>> {
        return listUsers
    }


    fun setAPIFollowingUser(username: String) {
        var listItem = arrayListOf<GithubUser>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token 287d0da9dd42fd519db7f3a60a39aa88735970f5")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                // Jika koneksi berhasil
                val result = String(responseBody)
                Log.d("Data_Following", result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val idUser = jsonObject.getString("id")
                        val li = GithubUser(
                            idUser,
                            username,
                            avatar
                        )
                        listItem.add(li)
                    }
                    listUsers.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d(errorMessage, error.message.toString())
            }
        })
    }

    fun getAPIFollowingUser(): LiveData<ArrayList<GithubUser>> {
        return listUsers
    }


    fun setAPIFollowersUser(username: String) {
        var listItem = arrayListOf<GithubUser>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token 287d0da9dd42fd519db7f3a60a39aa88735970f5")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                // Jika koneksi berhasil
                val result = String(responseBody)
                Log.d("Data_Followers", result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val avatar = jsonObject.getString("avatar_url")
                        val idUser = jsonObject.getString("id")
                        val li = GithubUser(
                            idUser,
                            username,
                            avatar
                        )
                        listItem.add(li)
                    }
                    listUsers.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d(errorMessage, error.message.toString())
            }
        })
    }

    fun getAPIFollowersUser(): LiveData<ArrayList<GithubUser>> {
        return listUsers
    }


    fun setAPIDetailUser(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token 287d0da9dd42fd519db7f3a60a39aa88735970f5")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                // Jika koneksi berhasil
                val result = String(responseBody)
                Log.d("Berhasil", result)
                try {

                    val responseObject = JSONObject(result)
                    val name = responseObject.getString("name")
                    val bio = responseObject.getString("bio")
                    val loc = responseObject.getString("location")

                    listString.value = arrayListOf(name, bio, loc)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d(errorMessage, error.message.toString())
            }
        })
    }

    fun getAPIDetailUser(): LiveData<List<String>> {
        return listString
    }
}