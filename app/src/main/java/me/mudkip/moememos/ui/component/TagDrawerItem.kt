package me.mudkip.moememos.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.mudkip.moememos.R
import me.mudkip.moememos.ui.page.common.RouteName
import java.net.URLEncoder

@Composable
fun TagDrawerItem(
    tag: String,
    selected: Boolean,
    memosNavController: NavHostController,
    drawerState: DrawerState? = null
) {
    val scope = rememberCoroutineScope()

    NavigationDrawerItem(
        label = { Text(tag) },
        icon = { Icon(Icons.Outlined.Tag, contentDescription = stringResource(R.string.tag_icon)) },
        selected = selected,
        onClick = {
            scope.launch {
                memosNavController.navigate("${RouteName.TAG}/${URLEncoder.encode(tag, "UTF-8")}") {
                    launchSingleTop = true
                    restoreState = true
                }
                drawerState?.close()
            }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
fun MultiLevelTagDrawerItem(
    tagNode: TagNode,
    selected: Boolean,
    memosNavController: NavHostController,
    drawerState: DrawerState? = null,
    depth: Int = 0
) {
    val scope = rememberCoroutineScope()
    var isExpanded by remember { mutableStateOf(tagNode.isExpanded) }
    
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "rotation"
    )
    
    val backgroundColor = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            androidx.compose.ui.graphics.Color.Transparent
        }

        Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = (depth * 16).dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor)
                .clickable {
                    if (tagNode.hasChildren) {
                        isExpanded = !isExpanded
                        tagNode.isExpanded = isExpanded
                    } else {
                        scope.launch {
                            memosNavController.navigate("${RouteName.TAG}/${URLEncoder.encode(tagNode.fullPath, "UTF-8")}") {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState?.close()
                        }
                    }
                }
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (tagNode.hasChildren) {
                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                        tagNode.isExpanded = isExpanded
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(rotation)
                ) {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                Box(modifier = Modifier.size(32.dp))
            }
            
            Icon(
                Icons.Outlined.Tag,
                contentDescription = stringResource(R.string.tag_icon),
                modifier = Modifier.size(24.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            
            Text(
                text = tagNode.name,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
        
        if (tagNode.hasChildren && isExpanded) {
            Column(
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
                )
            ) {
                tagNode.children.forEach { childNode ->
                    MultiLevelTagDrawerItem(
                        tagNode = childNode,
                        selected = selected && childNode.fullPath == tagNode.fullPath,
                        memosNavController = memosNavController,
                        drawerState = drawerState,
                        depth = depth + 1
                    )
                }
            }
        }
    }
}
