package no.kristiania.android_innlevering.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao){

    val readAllData: Flow<List<User>> = userDao.getAll()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}