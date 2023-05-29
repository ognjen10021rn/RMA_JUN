package rs.raf.vezbe11.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import rs.raf.vezbe11.data.database.Database
import rs.raf.vezbe11.data.datasources.remote.FoodService
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.data.repositories.FoodRepositoryImplementation
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

val mealModule=module{

    viewModel {
        FoodViewModel(repository = get())
    }
    single<FoodRepository> {
        FoodRepositoryImplementation(localDataSource = get(), remoteDataSource = get())
    }
    single {
        get<Database>().getFoodDao()
    }
    single<FoodService> {
        get<Retrofit>(named("mealDbRetrofit")).create(FoodService::class.java)
    }



}