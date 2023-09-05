package com.example.testcat.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testcat.R
import com.example.testcat.adapter.AdapterCat
import com.example.testcat.viewmodel.CatViewModel
import kotlinx.coroutines.launch
import com.example.testcat.databinding.ActivityShowCatBinding

class CatShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowCatBinding
    private lateinit var adapterCat: AdapterCat
    private var dialog: Dialog? = null
    private lateinit var viewModel: CatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowCatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolBarCat.root
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this)
            .get(CatViewModel::class.java)
        viewModel.viewModelScope.launch {
            viewModel.getImageCat()

            catImageObserver()

            setupRecyclerView()
        }

    }

    private fun catImageObserver() {
        viewModel.resultCat.observe(this) { resultCat ->
            resultCat?.let {
                adapterCat.differ.submitList(it.data)
            }
        }

        viewModel.loadingResultCat.observe(this) { load ->
            load.let {
                if (load) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        }

        viewModel.errorResultCat.observe(this) { errorCode ->
            errorCode.let {
                Toast.makeText(this, "erro $errorCode", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupRecyclerView() {
        adapterCat = AdapterCat()
        binding.recyclerCat.apply {
            adapter = adapterCat
            layoutManager =
                GridLayoutManager(this@CatShowActivity, 4, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showProgressDialog() {
        dialog = Dialog(this)
        dialog?.let {
            it.setContentView(R.layout.dialog)
            it.show()
        }
    }

    private fun hideProgressDialog() = dialog?.dismiss()
}