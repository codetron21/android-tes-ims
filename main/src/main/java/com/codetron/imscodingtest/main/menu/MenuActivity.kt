package com.codetron.imscodingtest.main.menu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.abstraction.ActivityRouter
import com.codetron.imscodingtest.main.R
import com.codetron.imscodingtest.main.databinding.ActivityMenuBinding
import org.koin.android.ext.android.inject

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private val accountManager by inject<AccountManager>()

    private val router by inject<ActivityRouter>()

    private val menuAdapter by lazy {
        MenuAdapter(MenuModel.getData()) { id ->
            navigateTo(id)
        }
    }

    private fun navigateTo(id: Int) {
        val intent = when (id) {
            0 -> router.navigateToFeetCalculator(this)
            1 -> router.navigateToStore(this)
            else -> throw  IllegalStateException("destination not found")
        }

        startActivity(intent)
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_out
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        actionListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            com.codetron.imscodingtest.resources.R.anim.anim_slide_left_in,
            com.codetron.imscodingtest.resources.R.anim.anim_slide_right_out
        )
        accountManager.reset()
    }

    private fun actionListeners() = with(binding) {
        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView() = with(binding) {
        // setup text
        textName.text = getString(R.string.greetings, accountManager.getName())

        // setup recyclerview
        listButton.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 2)
            addItemDecoration(MenuDecorator())
            setHasFixedSize(true)
            adapter = menuAdapter
        }
    }

}