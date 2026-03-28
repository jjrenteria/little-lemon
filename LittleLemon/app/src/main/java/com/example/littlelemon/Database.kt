package com.example.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// Crear la base de datos
@Database(entities = [Menu::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun menuDao(): MenuDao
}

// Crear tabla o entidad para guardar el menu
@Entity(tableName = "Menu")
data class Menu(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

// crear la interfaz dao
@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(menu: Menu)

    @Insert
    fun insertAll(vararg menuItems: Menu)

    @Query( "SELECT * FROM Menu")
    fun getAll() : LiveData<MutableList<Menu>>

    @Query(value = "SELECT DISTINCT category FROM Menu")
    fun getCategories() : LiveData<MutableList<String>>

    @Query(value = "SELECT (SELECT COUNT(*) FROM Menu) == 0")
    fun isEmpty(): Boolean

}


