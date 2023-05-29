package rs.raf.vezbe11.data.models


//Sealed klasa je nacin da podatke spakujemo u neku strukturu
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>() //uspesno proslo fetch sa neta
    data class Loading<out T>(val message: String = "") : Resource<T>() //jos uvek traje fetch
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): Resource<T>() //puklo
}

//when(resource){
//    is Resource.Success -> {
//        //resource.data
//    }
//    is Resource.Loading -> {
//        //resource.message
//    }
//    is Resource.Error -> {
//        //resource.error
//        //resource.data
//    }
//
//}
