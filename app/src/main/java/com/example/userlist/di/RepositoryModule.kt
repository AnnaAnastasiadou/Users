package com.example.userlist.di

import com.example.userlist.data.repository.UsersRepository
import com.example.userlist.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}