package com.codetron.imscodingtest.shirtstore.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.shirtstore.R
import com.codetron.imscodingtest.shirtstore.data.DataSources
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.databinding.FragmentCartSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class CartBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCartSheetBinding? = null
    private val binding get() = _binding!!

    private val accountManager: AccountManager by inject()

    private val dataSources: DataSources by inject()

    private val cartAdapter by lazy { CartAdapter() }

    private var clickListener: (() -> Unit)? = null

    fun setClickListener(clickListener: (() -> Unit)? = null) {
        this.clickListener = clickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        getData()
    }

    private fun setupView() = with(binding) {
        val name = accountManager.getName().orEmpty()
        textTitle.text = getString(R.string.account_choose, name)

        listCart.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listCart.adapter = cartAdapter

        cartAdapter.setDeleteOnClickListener { id ->
            accountManager.addProductToCart(id)
            getData()
            clickListener?.invoke()
        }
    }

    private fun getData() = with(binding) {
        val cartIds = accountManager.getProductsCart()
        textError.isVisible = cartIds.isEmpty()

        if (cartIds.isEmpty()) {
            cartAdapter.submitData(emptyList())
            return
        }

        val products = mutableListOf<Product>()
        cartIds.forEach {
            dataSources.getProductById(it)?.let(products::add)
        }

        textError.isVisible = products.isEmpty()
        cartAdapter.submitData(products)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}