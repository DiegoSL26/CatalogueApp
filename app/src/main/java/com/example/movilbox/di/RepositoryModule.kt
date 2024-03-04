package com.example.movilbox.di

import com.example.movilbox.repository.ProductRepository
import com.example.movilbox.repository.ProductRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun productRepository(repo: ProductRepositoryImp) : ProductRepository

}