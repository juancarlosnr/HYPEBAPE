package com.example.hypebape.data.home

import android.util.Log
import com.example.hypebape.data.profile.User
import com.example.hypebape.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firestore.v1.ArrayValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : HomeRepository {
    override fun getSneakers(): Flow<Resource<List<Sneaker>>> {
        return flow {
            emit(Resource.Loading())
            val sneakers = getSneakersFromFirestore()
            emit(Resource.Success(sneakers))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getBrands(): Flow<Resource<List<Brand>>> {
        return flow {
            emit(Resource.Loading())
            val sneakers = getBrandsFromFirestore()
            Log.d("brands", sneakers.toString())
            emit(Resource.Success(sneakers))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun setOrDeleteFavorite(idSneaker: Int) {
        val favorites = getFavoritesFromFirebase()
        val sneaker = getSneakerById(idSneaker)
        val favoritesString = favorites.map { it.toString().trim() }
        if(favoritesString.contains(sneaker.id.toString())){
            deleteFavorite(sneaker.id)
        }else{
            setFavorites(sneaker.id)
        }


    }

    override fun setFavorites(idSneaker: Int) {
        Log.d("datosUsuaro", firebaseAuth.currentUser!!.uid + firebaseAuth.currentUser!!.email)
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser!!.uid)
            .update("favorites", FieldValue.arrayUnion(idSneaker))


    }
    override fun deleteFavorite(idSneaker: Int) {
        firebaseFirestore.collection("users")
            .document(firebaseAuth.currentUser!!.uid)
            .update("favorites", FieldValue.arrayRemove(idSneaker))
    }

    override fun getFavorites(): Flow<Resource<List<Int>>> {
        return flow {
            emit(Resource.Loading())
            val sneakers = getFavoritesFromFirebase()
            emit(Resource.Success(sneakers))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }



    private suspend fun getFavoritesFromFirebase(): List<Int> {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("users")
                .document(firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { document ->
                    val favorites = document.get("favorites") as? List<Int>
                    var favoritesUser = emptyList<Int>()
                    if (favorites != null) {
                        favoritesUser = favorites
                    }
                    continuation.resume(favoritesUser)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override fun getSneakersByBrands(brand: String): Flow<Resource<List<Sneaker>>> {
        return flow {
            emit(Resource.Loading())
            val sneakers = getSneakersByBrandsFromFirestore(brand)
            emit(Resource.Success(sneakers))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun getAdvertising(): Flow<Resource<List<Advertising>>> {
        return flow {
            emit(Resource.Loading())
            val advertising = getAdvertisingFromFirestore()
            emit(Resource.Success(advertising))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    private suspend fun getAdvertisingFromFirestore(): List<Advertising> {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("advertisings").get()
                .addOnSuccessListener { documents ->
                    val advertisingList = mutableListOf<Advertising>()
                    for (document in documents) {
                        // Obtener los datos del documento y crear un objeto Sneaker
                        val advertising = document.toObject<Advertising>()
                        advertisingList.add(advertising)
                    }
                    continuation.resume(advertisingList)
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }

    private suspend fun getSneakersByBrandsFromFirestore(brand: String): List<Sneaker> {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("sneakers").whereEqualTo("brand", brand).get()
                .addOnSuccessListener { documents ->
                    val sneakersList = mutableListOf<Sneaker>()
                    for (document in documents) {
                        // Obtener los datos del documento y crear un objeto Sneaker
                        val sneaker = document.toObject<Sneaker>()
                        sneakersList.add(sneaker)
                    }
                    continuation.resume(sneakersList)
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }

    private suspend fun getSneakerById(id: Int): Sneaker {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("sneakers").whereEqualTo("id", id).get()
                .addOnSuccessListener { documents ->
                    var sneaker:Sneaker = Sneaker("", 0,"","", emptyList())
                    for (document in documents) {
                        // Obtener los datos del documento y crear un objeto Sneaker
                        sneaker = document.toObject<Sneaker>()
                    }
                    continuation.resume(sneaker)
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }

    private suspend fun getSneakersFromFirestore(): List<Sneaker> {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("sneakers").get()
                .addOnSuccessListener { documents ->
                    val sneakersList = mutableListOf<Sneaker>()
                    for (document in documents) {
                        // Obtener los datos del documento y crear un objeto Sneaker
                        val sneaker = document.toObject<Sneaker>()
                        // Agregar el objeto a la lista
                        sneakersList.add(sneaker)
                    }
                    continuation.resume(sneakersList)
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }

    private suspend fun getBrandsFromFirestore(): List<Brand> {
        return suspendCoroutine { continuation ->
            firebaseFirestore.collection("brands").get()
                .addOnSuccessListener { documents ->
                    val brandsList = mutableListOf<Brand>()
                    for (document in documents) {
                        // Obtener los datos del documento y crear un objeto Sneaker
                        val brand = document.toObject<Brand>()
                        // Agregar el objeto a la lista
                        brandsList.add(brand)
                    }
                    continuation.resume(brandsList)
                }
                .addOnFailureListener { exception ->
                    Log.e("ERROR", "Error al obtener la lista de sneakers", exception)
                    continuation.resumeWithException(exception)
                }
        }
    }
}
