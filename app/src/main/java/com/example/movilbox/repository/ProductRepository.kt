package com.example.movilbox.repository

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import com.example.movilbox.data.ProductDao
import com.example.movilbox.data.ProductEntity
import com.example.movilbox.datasource.RestDataSource
import com.example.movilbox.model.Product
import javax.inject.Inject

interface ProductRepository {
    suspend fun getNewProducts(): List<Product>
    suspend fun getNewCategories(): List<String>
    fun getAllProducts(filter: String): List<ProductEntity>
}

class ProductRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getNewProducts(): List<Product> {
        val products = dataSource.getProducts().products

        products.forEach {product ->
            productDao.insertProduct(
                ProductEntity(
                    product.id,
                    product.title,
                    product.description,
                    product.price,
                    product.discountPercentage,
                    product.rating,
                    product.stock,
                    product.brand,
                    product.category,
                    product.thumbnail,
                    product.images
                )
            )
        }
        return products
    }

    override suspend fun getNewCategories(): List<String> {
        return dataSource.getCategories()
    }

    override fun getAllProducts(filter: String): List<ProductEntity>
    = when(filter) {
        "Precio" -> productDao.getProductsPrice()
        "Descuento" -> productDao.getProductsDiscount()
        "Categoria" -> productDao.getProductsCategory()
        "Stock" -> productDao.getProductsStock()
        "Marca" -> productDao.getProductsBrand()
        else -> productDao.getProductsRating()
    }
}