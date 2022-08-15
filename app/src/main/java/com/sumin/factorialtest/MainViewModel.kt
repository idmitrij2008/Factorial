package com.sumin.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val myCoroutineScope =
        CoroutineScope(Dispatchers.Main + CoroutineName("myCoroutineScope"))

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    override fun onCleared() {
        super.onCleared()
        myCoroutineScope.cancel()
    }

    fun calculate(value: String?) {
        _state.value = Progress

        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        myCoroutineScope.launch {
            val number = value.toLong()
            val res = factorial(number)
            _state.value = Factorial(res.toString())
        }
    }

    private suspend fun factorial(number: Long) = withContext(Dispatchers.Default) {
        var res = BigInteger.ONE

        for (i in 1..number) {
            res = res.multiply(BigInteger.valueOf(i))
        }

        res.toString()
    }

//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            thread {
//                var res = BigInteger.ONE
//
//                for (i in 1..number) {
//                    res = res.multiply(BigInteger.valueOf(i))
//                }
//
//                it.resumeWith(Result.success(res.toString()))
//            }
//        }
//    }
}