package com.rohit.whatsappclone.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rohit.whatsappclone.data.reposetory.RepoImpl
import com.rohit.whatsappclone.domain.reposetory.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  HiltModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }
   @Provides
   fun provideRepoImpl(firebaseDatabase: FirebaseDatabase,firebaseAuth: FirebaseAuth): Repo{
       return RepoImpl(firebaseDatabase=firebaseDatabase,firebaseAuth=firebaseAuth)
   }
}