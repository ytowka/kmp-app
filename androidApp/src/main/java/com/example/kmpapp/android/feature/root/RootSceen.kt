package com.example.kmpapp.android.feature.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature.root.RootSideEffect
import com.example.feature.root.RootState
import com.example.feature.root.RootViewModel
import com.example.feature.users.domain.dto.RoleDto
import com.example.feature.users.ui.info.UserInfoViewModel
import com.example.kmpapp.android.coreui.NavigationBar
import com.example.kmpapp.android.coreui.injectViewModel
import com.example.kmpapp.android.coreui.onSideEffect
import com.example.kmpapp.android.feature.auth.ui.AuthScreen
import com.example.kmpapp.android.feature.content.ui.ContentListScreen
import com.example.kmpapp.android.feature.review.ui.edit.ReviewEditorScreen
import com.example.kmpapp.android.feature.review.ui.list.ReviewListScreen
import com.example.kmpapp.android.feature.topics.ui.TopicScreen
import com.example.kmpapp.android.feature.users.ui.edit.EditUserScreen
import com.example.kmpapp.android.feature.users.ui.info.UserInfoScreen
import com.example.kmpapp.android.feature.users.ui.list.UserListScreen
import org.koin.core.parameter.parametersOf
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun RootScreen(
    viewModel: RootViewModel= injectViewModel()
){
    val navController = rememberNavController()
    val state by viewModel.state.collectAsState()

    viewModel.onSideEffect {
        when(it) {
            RootSideEffect.Auth -> {
                try {
                    navController.navigate(
                        route = NavDestinations.AUTH,
                        navOptions = NavOptions
                            .Builder()
                            .setPopUpTo(NavDestinations.AUTH, inclusive = false, saveState = false)
                            .build(),
                        navigatorExtras = null,
                    )
                }catch (_: Exception){}
            }
        }
    }

    (state as? RootState.Success)?.let { successState ->
        Column(modifier = Modifier.fillMaxSize()) {
            NavContainer(
                navController = navController,
                startDestination = if (successState.isLoggedIn) NavDestinations.TOPIC_LIST else NavDestinations.AUTH
            )
            var currentNavItem by remember { mutableIntStateOf(0) }

            LaunchedEffect(navController.currentDestination){
                when(navController.currentDestination?.route){
                    NavDestinations.MY_ACCOUNT -> currentNavItem = 2
                    NavDestinations.USER_SEARCH -> currentNavItem = 1
                    NavDestinations.TOPIC_LIST -> currentNavItem = 0
                    NavDestinations.USER_ADMIN_LIST -> currentNavItem = 3
                }
            }
            if(successState.isLoggedIn){
                NavigationBar(
                    items = {
                        navigationItem(label = "Главная", icon = Icons.Default.Menu, onClick = {
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(0, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.TOPIC_LIST, navOptions = navOptions)
                        })
                        navigationItem(label = "Пользователи", icon = Icons.Default.Groups, onClick = {
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(0, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.USER_SEARCH, navOptions = navOptions)
                        })
                        navigationItem(label = "Я", icon = Icons.Default.AccountCircle, onClick = {
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(0, false).build()
                            currentNavItem = it
                            navController.navigate(NavDestinations.MY_ACCOUNT, navOptions = navOptions)
                        })
                        if(successState.currentUser?.role == RoleDto.ADMIN){
                            navigationItem(label = "Админ", icon = Icons.Default.AccountCircle, onClick = {
                                val navOptions = NavOptions.Builder()
                                    .setPopUpTo(0, false).build()
                                currentNavItem = it
                                navController.navigate(NavDestinations.USER_ADMIN_LIST, navOptions = navOptions)
                            })
                        }
                    },
                    selectedItemIndex = currentNavItem
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
private fun ColumnScope.NavContainer(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .weight(1f),
        startDestination = startDestination,
        navController = navController,
    ){
        composable(NavDestinations.AUTH) {
            AuthScreen{
                navController.navigate(
                    route = NavDestinations.TOPIC_LIST,
                    navOptions = NavOptions
                        .Builder()
                        .setPopUpTo(NavDestinations.AUTH, inclusive = true, saveState = false)
                        .build(),
                    navigatorExtras = null,
                )
            }
        }
        composable(NavDestinations.TOPIC_LIST) {
            TopicScreen (
                onTopicClick = {
                    navController.navigate(NavDestinations.TopicContent(it.id, it.name))
                },
            )
        }
        composable(
            route = NavDestinations.TopicContent.destination,
            arguments = listOf(
                navArgument(NavDestinations.TopicContent.topicIdArg) { type = NavType.LongType },
                navArgument(NavDestinations.TopicContent.topicNameArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(NavDestinations.TopicContent.topicIdArg)!!
            val name = backStackEntry.arguments?.getString(NavDestinations.TopicContent.topicNameArg)!!
            ContentListScreen(
                viewModel= injectViewModel { parametersOf(id, name) },
                onContentClick = { navController.navigate(NavDestinations.ReviewList(it.id, it.name)) },
                onBack = { navController.navigateUp() }
            )
        }
        composable(NavDestinations.USER_ADMIN_LIST){
            UserListScreen(
                onUserSelected = {
                    navController.navigate(NavDestinations.UserDetails(it.id.toString()))
                }
            )
        }
        composable(NavDestinations.USER_SEARCH){
            UserListScreen(
                onUserSelected = {
                    navController.navigate(NavDestinations.UserProfile(it.id.toString()))
                }
            )
        }
        composable(
            route = NavDestinations.UserDetails.destination,
            arguments = listOf(navArgument(NavDestinations.UserDetails.userIdArg) { type = NavType.StringType })
        ){ backStackEntry ->
            EditUserScreen(
                viewModel= injectViewModel { parametersOf(backStackEntry.arguments?.getString(NavDestinations.UserDetails.userIdArg)!!) },
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = NavDestinations.UserProfile.destination,
            arguments = listOf(navArgument(NavDestinations.UserProfile.userIdArg) { type = NavType.StringType })
        ){ backStackEntry ->
            UserInfoScreen(
                viewModel= injectViewModel { parametersOf(backStackEntry.arguments?.getString(NavDestinations.UserDetails.userIdArg)!!) },
                onBack = {
                    navController.navigateUp()
                },
                onContentClick = { navController.navigate(
                    NavDestinations.ReviewList(
                        it.reviewModel.contentId,
                        it.reviewModel.contentName
                    )
                ) },
                onReviewClick = {}
            )
        }

        composable(
            route = NavDestinations.MY_ACCOUNT,
        ){ backStackEntry ->
            UserInfoScreen(
                viewModel= injectViewModel { parametersOf(UserInfoViewModel.MY_USER_ID) },
                onBack = {
                    navController.navigateUp()
                },
                onContentClick = { navController.navigate(
                    NavDestinations.ReviewList(
                        it.reviewModel.contentId,
                        it.reviewModel.contentName
                    )
                ) },
                onReviewClick = {
                    navController.navigate(NavDestinations.ReviewEditor(
                        it.reviewModel.contentId, backStackEntry.destination.id)
                    )
                }
            )
        }

        composable(
            route = NavDestinations.ReviewList.destination,
            arguments = listOf(
                navArgument(NavDestinations.ReviewList.contentIdArg) { type = NavType.LongType },
                navArgument(NavDestinations.ReviewList.contentNameArg) { type = NavType.StringType },
            )
        ){ backStackEntry ->
            val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewList.contentIdArg)!!
            val contentName = backStackEntry.arguments?.getString(NavDestinations.ReviewList.contentNameArg)!!
            ReviewListScreen(
                viewModel= injectViewModel { parametersOf(contentId) },
                contentName = contentName,
                onBack = {
                    navController.navigateUp()
                },
                onWriteReview = {
                    navController.navigate(NavDestinations.ReviewEditor(contentId, backStackEntry.destination.id))
                },
                onCardClick = {
                    navController.navigate(NavDestinations.UserProfile(it.reviewUserInfo.userId))
                }
            )
        }

        composable(
            route = NavDestinations.ReviewEditor.destination,
            arguments = listOf(
                navArgument(NavDestinations.ReviewEditor.contentIdArg) { type = NavType.LongType },
                navArgument(NavDestinations.ReviewEditor.parentDestinationArg) { type = NavType.IntType }
            )
        ){ backStackEntry ->
            val contentId = backStackEntry.arguments?.getLong(NavDestinations.ReviewEditor.contentIdArg)!!
            val parentDestination = backStackEntry.arguments?.getInt(NavDestinations.ReviewEditor.parentDestinationArg)!!
            ReviewEditorScreen(
                viewModel= injectViewModel { parametersOf(contentId) },
                onBack = { navController.navigateUp() },
                onSave = {
                    navController.navigate(NavDestinations.MY_ACCOUNT) {
                        popUpTo(parentDestination)
                        launchSingleTop = true
                    }
                }
            )
        }

    }
}