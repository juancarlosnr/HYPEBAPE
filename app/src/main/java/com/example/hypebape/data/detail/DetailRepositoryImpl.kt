package com.example.hypebape.data.detail

import android.util.Log
import com.example.hypebape.data.home.HomeRepository
import com.example.hypebape.data.home.Sneaker
import com.example.hypebape.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DetailRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : DetailRepository {
    override fun getDetailSneaker(idSneaker: Int): Flow<Resource<Sneaker>> {
            return flow {
                emit(Resource.Loading())
                val sneakers = getDetailSneakers(idSneaker)
                emit(Resource.Success(sneakers))
            }.catch {
                emit(Resource.Error(it.message.toString()))
            }
    }

    private suspend fun getDetailSneakers(id:Int): Sneaker {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("sneakers").whereEqualTo("id", id).get()
                .addOnSuccessListener { documents ->
                    val sneakersList = mutableListOf<Sneaker>()
                    for (document in documents) {
                        val sneaker = document.toObject<Sneaker>()
                        sneakersList.add(sneaker)
                    }
                    continuation.resume(sneakersList.get(0))
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }

    }
}