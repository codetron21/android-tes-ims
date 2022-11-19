package com.codetron.imscodingtest.shirtstore.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetron.imscodingtest.shirtstore.data.StoreState
import com.codetron.imscodingtest.shirtstore.databinding.ActivityStoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding

    private val viewModel: StoreViewModel by viewModel()

    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            setOnClickListener { id ->
                viewModel.selectCategory(id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.getCategories()
        }

        observeViewModel()
        setupView()
    }

    private fun observeViewModel() = with(binding) {
        viewModel.getStateCategories().observe(this@StoreActivity) { state ->
            when (state) {
                is StoreState.Error -> {
                    progressCategories.isVisible = false
                    textErrorCategories.isVisible = true
                    categoriesAdapter.updateData(emptyList())
                }
                StoreState.Loading -> {
                    progressCategories.isVisible = true
                    textErrorCategories.isVisible = false
                }
                is StoreState.Success -> {
                    progressCategories.isVisible = false
                    textErrorCategories.isVisible = false
                    categoriesAdapter.updateData(state.data)
                }
            }
        }
    }

    private fun setupView() = with(binding) {
        buttonCart.setOnClickListener {

        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        listCategory.layoutManager =
            LinearLayoutManager(this@StoreActivity, RecyclerView.HORIZONTAL, false)
        listCategory.adapter = categoriesAdapter
        listCategory.addItemDecoration(CategoryItemDecoration())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_out
        )
    }

}