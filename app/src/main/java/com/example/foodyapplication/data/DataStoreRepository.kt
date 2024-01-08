package com.example.foodyapplication.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodyapplication.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodyapplication.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foodyapplication.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    suspend fun saveMealAndDietType(mealType: String,mealTypeId: Int, dietType: String, dietTypeId: Int){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.selectedMealType] = mealType
            preferences[PreferencesKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferencesKeys.selectedDietType] = dietType
            preferences[PreferencesKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        context.dataStore.edit {  preferences ->
            preferences[PreferencesKeys.backOnline] = backOnline
        }
    }


    val readMealAndDietType: kotlinx.coroutines.flow.Flow<MealAndDietType> = context.dataStore.data
        .catch {exception->
            if (exception is IOException) {
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferencesKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferencesKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = context.dataStore.data
        .catch {exception->
            if (exception is IOException) {
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map {preferences ->
        val backOnline = preferences[PreferencesKeys.backOnline] ?: false
            backOnline

        }
}
data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)
