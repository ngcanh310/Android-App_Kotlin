package com.example.mylife.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mylife.R
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.exercise.ActivityDestination
import com.example.mylife.ui.exercise.ActivityScreen
import com.example.mylife.ui.exercise.AddExerDestination
import com.example.mylife.ui.exercise.AddExerciseScreen
import com.example.mylife.ui.food.DetailInforDestination
import com.example.mylife.ui.food.FoodListDestination
import com.example.mylife.ui.food.FullInforFood
import com.example.mylife.ui.food.rowItemFood
import com.example.mylife.ui.home.HomeDestination
import com.example.mylife.ui.home.HomeScreen
import com.example.mylife.ui.information.EntryInfoViewModel
import com.example.mylife.ui.information.EntryInforDestination
import com.example.mylife.ui.information.USerDestination
import com.example.mylife.ui.information.UpProfile
import com.example.mylife.ui.information.UserInformationScreen
import com.example.mylife.ui.meal.AddFoodDestination
import com.example.mylife.ui.meal.AddFoodScreen
import com.example.mylife.ui.meal.AddMealDestination
import com.example.mylife.ui.meal.AddMealScreen
import com.example.mylife.ui.meal.EachMealDestination
import com.example.mylife.ui.meal.MealListDestination
import com.example.mylife.ui.meal.rowItemEachMeal
import com.example.mylife.ui.meal.rowItemListMeal
import com.example.mylife.ui.welcome.WelcomeDestination
import com.example.mylife.ui.welcome.WelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: EntryInfoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.navUiState
    val startDestination = if (uiState.firstTime == 0) {
        WelcomeDestination.route
    } else {
        HomeDestination.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = WelcomeDestination.route) {
            WelcomeScreen(navigateToEntryInfor={ navController.navigate(EntryInforDestination.route) },
            )
        }//
        composable(route = EntryInforDestination.route) {
            UpProfile( { navController.navigate(HomeDestination.route) } ,
            )
        }//
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToListExer = { navController.navigate(ActivityDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToListMeal = { navController.navigate(MealListDestination.route) })
        }//
        composable(route = FoodListDestination.route) {
            rowItemFood(
                navigateToDetailFood = { navController.navigate(DetailInforDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
            )
        }
        composable(route = ActivityDestination.route) {
            ActivityScreen(
                navigateToAddExer = { navController.navigate(AddExerDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
        composable(
            route = DetailInforDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailInforDestination.servingIdArg) {
                type = NavType.IntType
            })
        ) {
            FullInforFood(
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToHome = { navController.navigate("${EachMealDestination.route}/$it") },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
        composable(
            route = AddFoodDestination.routeWithArg,
            arguments = listOf(navArgument(AddFoodDestination.mealIdArg) {
                type = NavType.IntType
            })

        ) {
            AddFoodScreen(
                navigateToEachMeal = { navController.navigate("${EachMealDestination.route}/$it") },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToHome = { navController.navigate("${EachMealDestination.route}/$it") },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }
        composable(route = AddExerDestination.route) {
            AddExerciseScreen(navigateToHome = { navController.navigate(ActivityDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToActivity =  { navController.navigate(ActivityDestination.route) },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
        composable(route = USerDestination.route) {
            UserInformationScreen(navigateToHome = { navController.navigate(HomeDestination.route) },

            )
        }//
        composable(route = MealListDestination.route) {
            rowItemListMeal(navigateToEachMeal = { navController.navigate("${EachMealDestination.route}/$it") },
                navigateToAddMeal = { navController.navigate(AddMealDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
        composable(
            route = EachMealDestination.routeWithArg,
            arguments = listOf(navArgument(EachMealDestination.mealIdArg){
                type = NavType.IntType
            })
        ) {
            rowItemEachMeal(
                navigateToHome = { navController.navigate(MealListDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToAddFood = { navController.navigate("${AddFoodDestination.route}/$it") },
                navigateToDetailFood = { navController.navigate("${DetailInforDestination.route}/$it") },
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
        composable(
            route = AddMealDestination.route,
        ) {
            AddMealScreen(navigateToHome = { navController.navigate(MealListDestination.route) },
                navigateToUser = { navController.navigate(USerDestination.route) },
                navigateToListMeal = { navController.navigate(MealListDestination.route) },
                navigateToEachMeal = {navController.navigate("${EachMealDestination.route}/$it")},
                navigateToMain = { navController.navigate(HomeDestination.route) }
            )
        }//
    }
}

// navigateToMain = { navController.navigate(HomeDestination.route) }

enum class AppScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    ListFood(title = R.string.FOODLIST),
    ListExer(title = R.string.EXERLIST),
    AddFood(title = R.string.ADDFOOD),
    AddExer(title = R.string.ADDEXER),
    Detail(title = R.string.DETAILINFOR),
    User(title = R.string.USERINFOR),
}


