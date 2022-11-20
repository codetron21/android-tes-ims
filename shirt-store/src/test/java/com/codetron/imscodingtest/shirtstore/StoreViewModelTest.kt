package com.codetron.imscodingtest.shirtstore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.codetron.imscodingtest.shirtstore.data.Category
import com.codetron.imscodingtest.shirtstore.data.DataSources
import com.codetron.imscodingtest.shirtstore.data.Product
import com.codetron.imscodingtest.shirtstore.data.StoreState
import com.codetron.imscodingtest.shirtstore.store.StoreViewModel
import com.codetron.imscodingtest.shirtstore.util.getData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class StoreViewModelTest {

    @get:Rule
    val executor = InstantTaskExecutorRule()

    @MockK
    private lateinit var dataSources: DataSources

    @MockK
    private lateinit var categoriesObs: Observer<StoreState<List<Category>>>

    @MockK
    private lateinit var productsObs: Observer<StoreState<List<Product>>>

    private lateinit var viewModel: StoreViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = StoreViewModel(dataSources)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun test_get_categories_error() = runTest {
        val capturesStateCat = mutableListOf<StoreState<List<Category>>>()

        every {
            categoriesObs.onChanged(capture(capturesStateCat))
        } just runs

        every {
            dataSources.getAllCategories()
        } returns emptyList()

        viewModel.getStateCategories().observeForever(categoriesObs)

        viewModel.getCategories()

        verifyOrder {
            categoriesObs.onChanged(capturesStateCat[0]) // loading
            dataSources.getAllCategories()
            categoriesObs.onChanged(capturesStateCat[1]) // error
        }

        Assert.assertTrue(capturesStateCat[0] is StoreState.Loading)
        Assert.assertTrue(capturesStateCat[1] is StoreState.Error)
    }

    @Test
    fun test_get_categories_success() = runTest {
        val categoryId = 1
        val capturesStateCat = mutableListOf<StoreState<List<Category>>>()
        val result = listOf(Category(id = categoryId))

        every {
            categoriesObs.onChanged(capture(capturesStateCat))
        } just runs

        every {
            dataSources.getAllCategories()
        } returns result

        viewModel.getStateCategories().observeForever(categoriesObs)

        viewModel.getCategories()

        verifyOrder {
            categoriesObs.onChanged(capturesStateCat[0]) // loading
            dataSources.getAllCategories()
            categoriesObs.onChanged(capturesStateCat[1]) // success
        }

        val resultData = viewModel.getStateCategories().value?.getData()
        Assert.assertTrue(capturesStateCat[0] is StoreState.Loading)
        Assert.assertTrue(capturesStateCat[1] is StoreState.Success)
        Assert.assertTrue(resultData?.isNotEmpty() ?: false)
        Assert.assertTrue(resultData?.size == 2)
        Assert.assertTrue(resultData?.last()?.id == categoryId)
    }

    @Test
    fun test_get_products_error() = runTest {
        val capturesStateProducts = mutableListOf<StoreState<List<Product>>>()

        every {
            productsObs.onChanged(capture(capturesStateProducts))
        } just runs

        viewModel.getStateCategories().observeForever(categoriesObs)
        viewModel.getStateProducts().observeForever(productsObs)

        viewModel.getProducts()

        verifyOrder {
            productsObs.onChanged(capturesStateProducts[0]) // loading
            productsObs.onChanged(capturesStateProducts[1]) // error
        }

        Assert.assertTrue(capturesStateProducts[0] is StoreState.Loading)
        Assert.assertTrue(capturesStateProducts[1] is StoreState.Error)
    }

    @Test
    fun test_get_products_success() = runTest {
        val categoryId = 1
        val listCategories = listOf(Category(id = categoryId, "name", "#000000", isSelected = true))
        val slotCatId = slot<Int>()

        val listProducts = listOf(Product(id = 1, "name", 1L, "desc", categoryId = categoryId))
        val capturesStateProducts = mutableListOf<StoreState<List<Product>>>()

        every {
            productsObs.onChanged(capture(capturesStateProducts))
        } just runs

        every {
            dataSources.filterProductsByCategory(capture(slotCatId))
        } returns listProducts

        viewModel.getStateProducts().observeForever(productsObs)

        viewModel.getProducts(listCategories)

        verifyOrder {
            productsObs.onChanged(capturesStateProducts[0])
            productsObs.onChanged(capturesStateProducts[1])
        }

        val result = viewModel.getStateProducts().value?.getData() ?: emptyList()
        Assert.assertEquals(categoryId, slotCatId.captured)
        Assert.assertTrue(capturesStateProducts[0] is StoreState.Loading)
        Assert.assertTrue(capturesStateProducts[1] is StoreState.Success)
        Assert.assertTrue(result.isNotEmpty())
        Assert.assertEquals(categoryId, result[0].categoryId)
    }

    @Test
    fun test_select_categories() {
        val state = StoreState.Success<List<Category>>(emptyList())
        val slotState = slot<StoreState<List<Category>>>()

        every { categoriesObs.onChanged(capture(slotState)) } just runs

        categoriesObs.onChanged(state)

        viewModel.getStateCategories().observeForever(categoriesObs)

        viewModel.selectCategory(1)

        verify { categoriesObs.onChanged(slotState.captured) }
        Assert.assertTrue(slotState.captured.getData()?.isEmpty() ?: false)
        Assert.assertTrue(viewModel.getStateCategories().value?.getData()?.isEmpty() ?: false)
    }

    @After
    fun tierDown() {
        Dispatchers.resetMain()
    }

}