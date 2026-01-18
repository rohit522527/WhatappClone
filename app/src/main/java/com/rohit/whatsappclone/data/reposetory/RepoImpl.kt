package com.rohit.whatsappclone.data.reposetory

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.rohit.whatsappclone.data.model.MessageDTO
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
            val rer = firebaseDatabase.reference.child("users")
            trySend(FirebaseResult.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userDTO.email, userDTO.password)
                .addOnSuccessListener { it ->
                    rer.child(firebaseAuth.uid!!).setValue(userDTO.copy(uId = firebaseAuth.uid!!))
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

    override suspend fun updateUser(userName: String,profilePic: String): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        val rer = firebaseDatabase.reference.child("users").child(firebaseAuth.uid!!)
        val updates= mapOf(
            "userName" to userName,
            "profilePic" to profilePic
        )
        rer.updateChildren(updates)
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

    override suspend fun getMessage(receiverId: String): Flow<FirebaseResult<List<MessageDTO>>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        val chatId= getChatId(firebaseAuth.currentUser!!.uid,receiverId)
        val ref = firebaseDatabase.reference
            .child("chats")
            .child(chatId)
            .child("messages")
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              val messages=mutableListOf<MessageDTO>()
                snapshot.children.forEach {
                    it.getValue(MessageDTO::class.java)?.let{msg->
                        messages.add(msg)
                    }
                }
                trySend(FirebaseResult.Success(messages))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(FirebaseResult.Error(error.message))
            }
        }
        ref.addValueEventListener(listener)
        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    override suspend fun sendMessage(messageDTO: MessageDTO,receiverId: String): Flow<FirebaseResult<String>> = callbackFlow {
        trySend(FirebaseResult.Loading)
        val currentUserId = firebaseAuth.currentUser!!.uid
        val chatId=getChatId(currentUserId,receiverId)
        val ref=firebaseDatabase.reference.child("chats")
            .child(chatId)
            .child("messages")

        val messageId= ref.push().key?:run {
            trySend(FirebaseResult.Error("Message Id not generated"))
            close()
            return@callbackFlow
        }
        val finalMessage=messageDTO.copy(messageId = messageId)
        ref.child(messageId).setValue(messageDTO).addOnSuccessListener {
            trySend(FirebaseResult.Success("message send successfully"))
            close()
        }.addOnFailureListener {
            trySend(FirebaseResult.Error(it.message.toString()))
            close()
        }
        awaitClose {  }
    }
    private  fun getChatId(senderId:String,receiverId:String):String{
        return if(senderId>receiverId){
            "${senderId}_$receiverId"
        }else{
            "${receiverId}_$senderId"
        }
    }
}