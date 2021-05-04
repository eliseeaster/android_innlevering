package no.kristiania.android_innlevering.data

import UserDatabase
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

//Makes database start as soon as teh application runs
class UserApplication : Application() {

    val appScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserDatabase.getInstance(this, appScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}