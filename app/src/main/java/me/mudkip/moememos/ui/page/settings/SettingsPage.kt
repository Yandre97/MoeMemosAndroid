package me.mudkip.moememos.ui.page.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mudkip.moememos.R
import me.mudkip.moememos.data.model.Account
import me.mudkip.moememos.data.model.MemoEditGesture
import me.mudkip.moememos.data.model.Settings
import me.mudkip.moememos.data.model.displayTitle
import me.mudkip.moememos.ext.settingsDataStore
import me.mudkip.moememos.ext.string
import me.mudkip.moememos.ui.component.MemosIcon
import me.mudkip.moememos.ui.page.common.RouteName
import me.mudkip.moememos.viewmodel.LocalUserState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    drawerState: DrawerState? = null,
    navController: NavHostController
) {
    val userStateViewModel = LocalUserState.current
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val scope = rememberCoroutineScope()
    val accounts by userStateViewModel.accounts.collectAsState()
    val currentAccount by userStateViewModel.currentAccount.collectAsState()
    val settings by context.settingsDataStore.data.collectAsState(initial = Settings())
    var showEditGestureDialog by remember { mutableStateOf(false) }
    val currentEditGesture = settings.usersList
        .firstOrNull { it.accountKey == settings.currentUser }
        ?.settings
        ?.editGesture
        ?: MemoEditGesture.NONE

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = R.string.settings.string,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (drawerState != null) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = R.string.menu.string)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Text(
                    text = R.string.accounts.string,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            accounts.forEach { account ->
                when (account) {
                    is Account.MemosV0 -> item {
                        SettingItem(
                            icon = MemosIcon,
                            text = account.info.displayTitle(),
                            subtitle = account.info.host,
                            trailingIcon = {
                                if (currentAccount?.accountKey() == account.accountKey()) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = R.string.selected.string,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                        }) {
                            navController.navigate("${RouteName.ACCOUNT}?accountKey=${account.accountKey()}")
                        }
                    }
                    is Account.MemosV1 -> item {
                        SettingItem(
                            icon = MemosIcon,
                            text = account.info.displayTitle(),
                            subtitle = account.info.host,
                            trailingIcon = {
                                if (currentAccount?.accountKey() == account.accountKey()) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = R.string.selected.string,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                        }) {
                            navController.navigate("${RouteName.ACCOUNT}?accountKey=${account.accountKey()}")
                        }
                    }
                    is Account.Local -> item {
                        SettingItem(
                            icon = Icons.Outlined.Home,
                            text = R.string.local_account.string,
                            trailingIcon = {
                                if (currentAccount?.accountKey() == account.accountKey()) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = R.string.selected.string,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        ) {
                            navController.navigate("${RouteName.ACCOUNT}?accountKey=${account.accountKey()}")
                        }
                    }
                }
            }

            item {
                SettingItem(icon = Icons.Outlined.PersonAdd, text = R.string.add_account.string) {
                    navController.navigate(RouteName.ADD_ACCOUNT)
                }
            }

            item {
                Text(
                    text = R.string.preferences.string,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SettingItem(
                    icon = Icons.Outlined.Edit,
                    text = R.string.edit_gesture.string,
                    trailingIcon = {
                        Text(
                            text = currentEditGesture.titleResource.string,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                ) {
                    showEditGestureDialog = true
                }
            }

            item {
                Text(
                    text = R.string.about.string,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SettingItem(icon = Icons.Outlined.Web, text = R.string.website.string) {
                    uriHandler.openUri("https://memos.moe")
                }
            }

            item {
                SettingItem(icon = Icons.Outlined.Lock, text = R.string.privacy_policy.string) {
                    uriHandler.openUri("https://memos.moe/privacy")
                }
            }

            item {
                SettingItem(icon = Icons.Outlined.Source, text = R.string.acknowledgements.string) {
                    uriHandler.openUri("https://memos.moe/android-acknowledgements")
                }
            }

            item {
                SettingItem(icon = Icons.Outlined.BugReport, text = R.string.report_an_issue.string) {
                    uriHandler.openUri("https://github.com/mudkipme/MoeMemosAndroid/issues")
                }
            }
        }
    }

    if (showEditGestureDialog) {
        AlertDialog(
            onDismissRequest = { showEditGestureDialog = false },
            title = { Text(R.string.edit_gesture.string) },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(MemoEditGesture.entries.size) { index ->
                        val gesture = MemoEditGesture.entries[index]
                        val isSelected = gesture == currentEditGesture
                        Surface(
                            onClick = {
                                showEditGestureDialog = false
                                scope.launch(Dispatchers.IO) {
                                    context.settingsDataStore.updateData { existingSettings ->
                                        val userIndex = existingSettings.usersList.indexOfFirst { user ->
                                            user.accountKey == existingSettings.currentUser
                                        }
                                        if (userIndex == -1) {
                                            return@updateData existingSettings
                                        }
                                        val users = existingSettings.usersList.toMutableList()
                                        val user = users[userIndex]
                                        users[userIndex] = user.copy(
                                            settings = user.settings.copy(editGesture = gesture)
                                        )
                                        existingSettings.copy(usersList = users)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                MaterialTheme.colorScheme.surfaceContainerLow
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = gesture.titleResource.string,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (isSelected) {
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onSurface
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showEditGestureDialog = false }) {
                    Text(R.string.close.string)
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    }
}

private val MemoEditGesture.titleResource: Int
    get() = when (this) {
        MemoEditGesture.NONE -> R.string.edit_gesture_none
        MemoEditGesture.SINGLE -> R.string.edit_gesture_single
        MemoEditGesture.DOUBLE -> R.string.edit_gesture_double
        MemoEditGesture.LONG -> R.string.edit_gesture_long
    }
