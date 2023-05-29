package rs.raf.vezbe11.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.vezbe11.data.database.Database
import rs.raf.vezbe11.data.datasources.remote.NutritionService
import rs.raf.vezbe11.data.repositories.FoodRepository
import rs.raf.vezbe11.data.repositories.NutritionRepositoryImplementation
import rs.raf.vezbe11.presentation.viewmodel.NutritionViewModel

val nutritionModule=module{

    viewModel {
        NutritionViewModel(repository = get())
    }
    single<FoodRepository> {
        NutritionRepositoryImplementation(localDataSource = get(), remoteDataSource = get())
    }
    single {
        get<Database>().getNutritionDao()
    }
    single<NutritionService> {
        create(retrofit = get())
    }



}