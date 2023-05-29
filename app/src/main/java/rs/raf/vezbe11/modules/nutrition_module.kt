package rs.raf.vezbe11.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import rs.raf.vezbe11.data.database.Database
import rs.raf.vezbe11.data.datasources.remote.NutritionService
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.data.repositories.NutritionRepository
import rs.raf.vezbe11.data.repositories.NutritionRepositoryImplementation
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel

val nutritionModule=module{

    viewModel {
        NutritionViewModel(repository = get())
    }
    single<NutritionRepository> {
        NutritionRepositoryImplementation(localDataSource = get(), remoteDataSource = get())
    }
    single {
        get<Database>().getNutritionDao()
    }
    single<NutritionService> {
        get<Retrofit>(named("nutritionRetrofit")).create(NutritionService::class.java)
    }




}