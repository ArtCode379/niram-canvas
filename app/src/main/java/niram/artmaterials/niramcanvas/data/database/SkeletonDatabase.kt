package niram.artmaterials.niramcanvas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import niram.artmaterials.niramcanvas.data.dao.CartItemDao
import niram.artmaterials.niramcanvas.data.dao.OrderDao
import niram.artmaterials.niramcanvas.data.database.converter.Converters
import niram.artmaterials.niramcanvas.data.entity.CartItemEntity
import niram.artmaterials.niramcanvas.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class IWURVDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}