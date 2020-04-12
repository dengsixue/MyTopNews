package com.example.mytopnews.model.data



@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status
) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        val HIDDEN = NetworkState(Status.HIDDEN)
        val FAILED = NetworkState(Status.FAILED)
    }
}
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    HIDDEN
}