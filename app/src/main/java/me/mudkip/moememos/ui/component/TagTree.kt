package me.mudkip.moememos.ui.component

data class TagNode(
    val name: String,
    val fullPath: String,
    val children: MutableList<TagNode> = mutableListOf(),
    var isExpanded: Boolean = true
) {
    val hasChildren: Boolean get() = children.isNotEmpty()
    
    companion object {
        fun buildTagTree(tags: List<String>): List<TagNode> {
            val rootNodes = mutableListOf<TagNode>()
            
            tags.forEach { tag ->
                val parts = tag.split("/")
                var currentLevel = rootNodes
                var currentPath = ""
                
                parts.forEachIndexed { index, part ->
                    val isLastPart = index == parts.size - 1
                    val previousPath = currentPath
                    currentPath = if (currentPath.isEmpty()) part else "$currentPath/$part"
                    
                    var node = currentLevel.find { it.name == part }
                    if (node == null) {
                        node = TagNode(
                            name = part,
                            fullPath = currentPath,
                            isExpanded = true
                        )
                        currentLevel.add(node)
                    }
                    
                    if (!isLastPart) {
                        currentLevel = node.children
                    }
                }
            }
            
            sortTree(rootNodes)
            return rootNodes
        }
        
        private fun sortTree(nodes: List<TagNode>) {
            nodes.sortedBy { it.name.lowercase() }.forEach { node ->
                sortTree(node.children)
            }
        }
    }
}
