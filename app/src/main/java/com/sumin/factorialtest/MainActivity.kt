package com.sumin.factorialtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.sumin.factorialtest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.progressBarLoading.isVisible = it is Progress
            binding.buttonCalculate.isEnabled = it !is Progress

            when (it) {
                is Error -> {
                    Toast.makeText(this, "You did not enter value", Toast.LENGTH_SHORT).show()
                }
                is Progress -> Unit
                is Factorial -> {
                    binding.textViewFactorial.text = it.value
                }
            }
        }
    }
}
