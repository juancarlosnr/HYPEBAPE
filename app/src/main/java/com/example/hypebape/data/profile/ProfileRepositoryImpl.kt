package com.example.hypebape.data.profile

import android.util.Log
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ProfileRepository {

    override fun getData(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            val user = getUserFromFirestore()
            emit(Resource.Success(user))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getFavorites(): Flow<Resource<List<Sneaker>>> {
        return flow {
            emit(Resource.Loading())
            val sneakers = getSneakersFromFirestore()
            emit(Resource.Success(sneakers))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    private suspend fun getSneakersFromFirestore(): List<Sneaker> {
        val favoritesString = getUserFromFirestore().favorites

        val listSneakers: MutableList<Sneaker> = mutableListOf()

        for (favorito in favoritesString) {
            val documentReference =
                firebaseFirestore.collection("sneakers").document(favorito.toString())
            val documentSnapshot = documentReference.get().await()

            if (documentSnapshot.exists()) {
                val data = documentSnapshot.toObject<Sneaker>()
                data?.let {
                    listSneakers.add(it)
                }
            }
        }

        return listSneakers
    }


    override fun deleteFavorite(idSneaker: Int) {
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser!!.uid)
            .update("favorites", FieldValue.arrayRemove(idSneaker))
    }

    private suspend fun getUserFromFirestore(): User {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("users")
                .document(firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { document ->
                    val id = document.getString("id") ?: ""
                    val name = document.getString("name") ?: ""
                    val urlphoto = document.getString("urlPhoto") ?: ""
                    val favorites = document.get("favorites") as? List<Int>
                    var favoritesUser = emptyList<Int>()
                    if (favorites != null) {
                        favoritesUser = favorites
                    }
                    val user = User(id, name, urlphoto, favoritesUser)
                    continuation.resume(user)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}

