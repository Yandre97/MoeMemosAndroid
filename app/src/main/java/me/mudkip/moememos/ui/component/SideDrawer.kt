package me.mudkip.moememos.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import me.mudkip.moememos.R
import me.mudkip.moememos.data.model.Account
import me.mudkip.moememos.ext.string
import me.mudkip.moememos.ui.page.common.LocalRootNavController
import me.mudkip.moememos.ui.page.common.RouteName
import me.mudkip.moememos.viewmodel.LocalMemos
import me.mudkip.moememos.viewmodel.LocalUserState
import java.net.URLEncoder

@Composable
private fun drawerItemColors() = NavigationDrawerItemDefaults.colors(
    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
    unselectedContainerColor = Color.Transparent,
    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
)

@Composable
private fun NavigationIcon(
    route: String,
    currentDestination: androidx.navigation.NavDestination?
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == route } == true
    Icon(
        imageVector = when (route) {
            RouteName.MEMOS -> if (isSelected) Icons.Filled.GridView else Icons.Outlined.GridView
            RouteName.EXPLORE -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
            RouteName.RESOURCE -> if (isSelected) Icons.Filled.PhotoLibrary else Icons.Outlined.PhotoLibrary
            RouteName.ARCHIVED -> if (isSelected) Icons.Filled.Inventory2 else Icons.Outlined.Inventory2
            RouteName.SETTINGS -> if (isSelected) Icons.Filled.Settings else Icons.Outlined.Settings
            else -> Icons.Outlined.GridView
        },
        contentDescription = stringResource(
            when (route) {
                RouteName.MEMOS -> R.string.memos
                RouteName.EXPLORE -> R.string.explore
                RouteName.RESOURCE -> R.string.resources
                RouteName.ARCHIVED -> R.string.archived
                RouteName.SETTINGS -> R.string.settings
                else -> R.string.menu
            }
        ),
        modifier = androidx.compose.ui.Modifier.size(24.dp)
    )
}

@Composable
fun SideDrawer(
    memosNavController: NavHostController,
    drawerState: DrawerState? = null
) {
    val scope = rememberCoroutineScope()
    val memosViewModel = LocalMemos.current
    val userStateViewModel = LocalUserState.current
    val currentAccount by userStateViewModel.currentAccount.collectAsState()
    val hasExplore = currentAccount !is Account.Local
    val rootNavController = LocalRootNavController.current
    val navBackStackEntry by memosNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun isSelected(route: String): Boolean {
        return currentDestination?.hierarchy?.any { it.route == route } == true
    }

    fun isTagSelected(tag: String): Boolean {
        if (!isSelected("${RouteName.TAG}/{tag}")) return false

        val currentTag = navBackStackEntry?.arguments?.getString("tag")
        val encodedTag = URLEncoder.encode(tag, "UTF-8")
        return currentTag == tag || currentTag == encodedTag
    }

    LazyColumn {
        item {
            Text(
                R.string.moe_memos.string,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp)
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.memos.string) },
                icon = { NavigationIcon(RouteName.MEMOS, currentDestination) },
                selected = isSelected(RouteName.MEMOS),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.MEMOS) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        if (hasExplore) {
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.explore.string) },
                    icon = { NavigationIcon(RouteName.EXPLORE, currentDestination) },
                    selected = isSelected(RouteName.EXPLORE),
                    onClick = {
                        scope.launch {
                            memosNavController.navigate(RouteName.EXPLORE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState?.close()
                        }
                    },
                    colors = drawerItemColors(),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
                )
            }
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.resources.string) },
                icon = { NavigationIcon(RouteName.RESOURCE, currentDestination) },
                selected = isSelected(RouteName.RESOURCE),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.RESOURCE) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.archived.string) },
                icon = { NavigationIcon(RouteName.ARCHIVED, currentDestination) },
                selected = isSelected(RouteName.ARCHIVED),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.ARCHIVED) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.settings.string) },
                icon = { NavigationIcon(RouteName.SETTINGS, currentDestination) },
                selected = isSelected(RouteName.SETTINGS),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.SETTINGS) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }

        item {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        item {
            Text(
                R.string.tags.string,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp)
            )
        }

        memosViewModel.tags.toList().forEach { tag ->
            item {
                TagDrawerItem(
                    tag = tag,
                    selected = isTagSelected(tag),
                    memosNavController = memosNavController,
                    drawerState = drawerState
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        memosViewModel.loadTags()
    }
}

@Composable
fun SideDrawerWithMultiLevelTags(
    memosNavController: NavHostController,
    drawerState: DrawerState? = null
) {
    val scope = rememberCoroutineScope()
    val memosViewModel = LocalMemos.current
    val userStateViewModel = LocalUserState.current
    val currentAccount by userStateViewModel.currentAccount.collectAsState()
    val hasExplore = currentAccount !is Account.Local
    val rootNavController = LocalRootNavController.current
    val navBackStackEntry by memosNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    fun isSelected(route: String): Boolean {
        return currentDestination?.hierarchy?.any { it.route == route } == true
    }

    fun isTagSelected(tag: String): Boolean {
        if (!isSelected("${RouteName.TAG}/{tag}")) return false

        val currentTag = navBackStackEntry?.arguments?.getString("tag")
        val encodedTag = URLEncoder.encode(tag, "UTF-8")
        return currentTag == tag || currentTag == encodedTag
    }

    LazyColumn {
        item {
            Text(
                R.string.moe_memos.string,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp)
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.memos.string) },
                icon = { NavigationIcon(RouteName.MEMOS, currentDestination) },
                selected = isSelected(RouteName.MEMOS),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.MEMOS) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        if (hasExplore) {
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.explore.string) },
                    icon = { NavigationIcon(RouteName.EXPLORE, currentDestination) },
                    selected = isSelected(RouteName.EXPLORE),
                    onClick = {
                        scope.launch {
                            memosNavController.navigate(RouteName.EXPLORE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState?.close()
                        }
                    },
                    colors = drawerItemColors(),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
                )
            }
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.resources.string) },
                icon = { NavigationIcon(RouteName.RESOURCE, currentDestination) },
                selected = isSelected(RouteName.RESOURCE),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.RESOURCE) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.archived.string) },
                icon = { NavigationIcon(RouteName.ARCHIVED, currentDestination) },
                selected = isSelected(RouteName.ARCHIVED),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.ARCHIVED) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }
        item {
            NavigationDrawerItem(
                label = { Text(R.string.settings.string) },
                icon = { NavigationIcon(RouteName.SETTINGS, currentDestination) },
                selected = isSelected(RouteName.SETTINGS),
                onClick = {
                    scope.launch {
                        memosNavController.navigate(RouteName.SETTINGS) {
                            launchSingleTop = true
                            restoreState = true
                        }
                        drawerState?.close()
                    }
                },
                colors = drawerItemColors(),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).fillMaxWidth()
            )
        }

        item {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        item {
            Text(
                R.string.tags.string,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp)
            )
        }

        val tagsList = memosViewModel.tags.toList()
        val tagTree = TagNode.buildTagTree(tagsList)

        tagTree.forEach { tagNode ->
            item {
                MultiLevelTagDrawerItem(
                    tagNode = tagNode,
                    selected = isTagSelected(tagNode.fullPath),
                    memosNavController = memosNavController,
                    drawerState = drawerState,
                    depth = 0
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        memosViewModel.loadTags()
    }
}
