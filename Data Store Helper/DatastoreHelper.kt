package com.findandhire.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.findandhire.data.model.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreHelper(val context: Context) {
    companion object {
        val Context.datastore: DataStore<Preferences> by preferencesDataStore("userpref")

        private val ISUSERLOGGEDIN = booleanPreferencesKey("isLogin")
        private val USERID = stringPreferencesKey("userID")
        private val USERNAME = stringPreferencesKey("userName")
        private val USEREMAIL = stringPreferencesKey("userEmail")
        private val USERPROFILE = stringPreferencesKey("userProfile")
        private val USERPHONE = stringPreferencesKey("userPhone")
    }

    suspend fun updateLogin(boolean: Boolean) {
        context.datastore.edit {
            it[ISUSERLOGGEDIN] = boolean
        }
    }

    suspend fun updateUserID(userID: String) {
        context.datastore.edit {
            it[USERID] = userID
        }
    }

    val userID: Flow<String> = context.datastore.data.map {
        it[USERID] ?: ""
    }

    suspend fun updateUserName(userName: String) {
        context.datastore.edit {
            it[USERNAME] = userName
        }
    }

    val userName: Flow<String?>
        get() = context.datastore.data.map {
            it[USERNAME]
        }

    suspend fun updateUserPhone(userPhone: String) {
        context.datastore.edit {
            it[USERPHONE] = userPhone
        }
    }

    val userPhone = context.datastore.data.map {
        it[USERPHONE]
    }

    suspend fun updateUserEmail(userEmail: String) {
        context.datastore.edit {
            it[USEREMAIL] = userEmail
        }
    }

    val userEmail = context.datastore.data.map {
        it[USEREMAIL]
    }

    suspend fun updateUserProfile(userProfile: String) {
        context.datastore.edit {
            it[USERPROFILE] = userProfile
        }
    }

    val userProfile = context.datastore.data.map {
        it[USERPROFILE] ?: ""
    }


    val isUserLoggedIn = context.datastore.data.map {
        it[ISUSERLOGGEDIN] ?: false
    }

    suspend fun logout() = context.datastore.edit {
        it.clear()
    }
}