package rs.raf.vezbe11.data.database.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    //Imena metoda su nebitni
    //jedan converter prevodi podatak iz baze(long) u podatak u kotlinu,a drugi obrnuto
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}