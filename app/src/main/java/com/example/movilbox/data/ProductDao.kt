package com.example.movilbox.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM ProductEntity ORDER BY rating DESC")
    fun getProductsRating() : List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY price DESC")
    fun getProductsPrice() : List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY discountPercentage DESC")
    fun getProductsDiscount() : List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY category DESC")
    fun getProductsCategory() : List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY stock DESC")
    fun getProductsStock() : List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY brand DESC")
    fun getProductsBrand() : List<ProductEntity>
}