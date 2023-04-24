package com.example.hypebape.di

import com.example.hypebape.data.auth.AuthRepository
import com.example.hypebape.data.auth.AuthRepositoryImpl
import com.example.hypebape.data.detail.DetailRepository
import com.example.hypebape.data.detail.DetailRepositoryImpl
import com.example.hypebape.data.home.HomeRepository
import com.example.hypebape.data.home.HomeRepositoryImpl
import com.example.hypebape.data.profile.ProfileRepository
import com.example.hypebape.data.profile.ProfileRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesProfileRepositoryImpl(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth):ProfileRepository{
        return ProfileRepositoryImpl(firebaseFirestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesHomeRepositoryImpl(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth):HomeRepository{
        return HomeRepositoryImpl(firebaseFirestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesDetailRepositoryImpl(firebaseFirestore: FirebaseFirestore): DetailRepository{
        return DetailRepositoryImpl(firebaseFirestore)
    }
}