package com.elephant.domain.model

data class NasaServerModel(
    val href: String,
    val items: List<Item>,
    val links: List<CollectionLink>
)

data class Item(
    val href: String
)

data class CollectionLink(
    val rel: String,
    val prompt: String,
    val href: String
)
