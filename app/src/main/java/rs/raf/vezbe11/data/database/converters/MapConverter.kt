package rs.raf.vezbe11.data.database.converters


import com.squareup.moshi.JsonAdapter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.koin.core.KoinComponent
import org.koin.core.get
import java.lang.reflect.Type

//Da bismo mapirali mapu : Sastojak:kolicina
class MapConverter : KoinComponent{

    private val jsonAdapter: JsonAdapter<Map<String,Long>>

    init {
        val type: Type = Types.newParameterizedType(MutableList::class.java, String::class.java)
        val moshi: Moshi = get ()
        jsonAdapter = moshi.adapter(type)
    }


    @TypeConverter
    fun fromMap(map: Map<String,Long>?): String? {

        return map?.let {
            return jsonAdapter.toJson(map)
        }
    }

    @TypeConverter
    fun toMap(json: String?): Map<String, Long>? {
       return json?.let {
            return jsonAdapter.fromJson(json)
       }
    }
}