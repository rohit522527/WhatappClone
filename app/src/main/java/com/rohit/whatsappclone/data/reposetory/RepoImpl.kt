package com.rohit.whatsappclone.data.reposetory

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rohit.whatsappclone.data.model.UserDTO
import com.rohit.whatsappclone.domain.reposetory.Repo
import com.rohit.whatsappclone.utils.FirebaseResult
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : Repo {
    override suspend fun createUser(userDTO: UserDTO): Flow<FirebaseResult<String>> =
        callbackFlow {
            val rer = firebaseDatabase.reference.child("users").child(firebaseAuth.uid!!)
            trySend(FirebaseResult.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userDTO.email, userDTO.password)
                .addOnSuccessListener { it ->
                    rer.setValue(userDTO)
                        .addOnSuccessListener {
                            trySend(FirebaseResult.Success("Data saved Successfully"))
                            close()
                        }.addOnFailureListener {
                            trySend(FirebaseResult.Error(it.message.toString()))
                            close()
                        }
                }.addOnFailureListener {
                trySend(FirebaseResult.Error(it.localizedMessage.toString()))
                    close()
            }
            awaitClose {}
        }

    override suspend fun updateUser(userDTO: UserDTO): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        val rer = firebaseDatabase.reference.child("users").child(firebaseAuth.uid!!)
        rer.setValue(userDTO)
            .addOnSuccessListener {
                trySend(FirebaseResult.Success("Data saved Successfully"))
                close()
            }.addOnFailureListener {
                trySend(FirebaseResult.Error(it.message.toString()))
                close()
            }
        awaitClose { }
    }

    override suspend fun getAllUsers(): Flow<FirebaseResult<List<UserDTO>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { child ->
                    child.getValue(UserDTO::class.java)
                }
                trySend(FirebaseResult.Success(users))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(FirebaseResult.Error(error.message))
                close(error.toException())
            }
        }
        firebaseDatabase.reference.child("users").addValueEventListener(listener)
        awaitClose {
            firebaseDatabase.reference.child("users").removeEventListener(listener)
        }
    }

    override suspend fun logInUser(email:String,password:String): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { it ->
                trySend(FirebaseResult.Success("Account LognIned Successfully"))
                close()
            }.addOnFailureListener {
                trySend(FirebaseResult.Error(it.localizedMessage.toString()))
                close()
            }
        awaitClose {}
    }
}