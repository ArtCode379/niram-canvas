package niram.artmaterials.niramcanvas.di

import androidx.room.Room
import niram.artmaterials.niramcanvas.data.database.IWURVDatabase
import org.koin.dsl.module

private const val DB_NAME = "iwurv_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = IWURVDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<IWURVDatabase>().cartItemDao() }

    single { get<IWURVDatabase>().orderDao() }
}