package com.elephant.domain.model

data class NasaServerModel(
    val href: String,
    val items: List<Item>,
    val links: List<CollectionLink>,
    val metadata: Long,
)

data class Item(
    val href: String,
    val links: List<ItemLink>
)

data class CollectionLink(
    val rel: String,
    val prompt: String,
    val href: String
)

data class ItemLink(
    val href: String
)
