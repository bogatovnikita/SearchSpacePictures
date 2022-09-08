package com.elephant.data.model

import com.google.gson.annotations.SerializedName

data class NasaServerResponse(
    val collection: Collection
)

data class Collection(
    val version: String,
    val href: String,
    val items: List<Item>,
    val metadata: Metadata,
    val links: List<CollectionLink>
)

data class Item(
    val href: String,
    val data: List<Datum>,
    val links: List<ItemLink>
)

data class Datum(
    val description: String,
    val title: String,
    val photographer: Photographer? = null,
    val location: Location? = null,

    @SerializedName("nasa_id")
    val nasaID: String,

    @SerializedName("date_created")
    val dateCreated: String,

    val keywords: List<String>,

    @SerializedName("media_type")
    val mediaType: MediaType,

    val center: com.elephant.data.model.Center,

    @SerializedName("description_508")
    val description508: String? = null,

    @SerializedName("secondary_creator")
    val secondaryCreator: String? = null
)

enum class Center(val value: String) {
    Hq("HQ"),
    Jpl("JPL");

    companion object {
        public fun fromValue(value: String): com.elephant.data.model.Center = when (value) {
            "HQ" -> Hq
            "JPL" -> Jpl
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Location(val value: String) {
    MarsPAUSA("Mars, PA, USA");

    companion object {
        public fun fromValue(value: String): Location = when (value) {
            "Mars, PA, USA" -> MarsPAUSA
            else -> throw IllegalArgumentException()
        }
    }
}

enum class MediaType(val value: String) {
    Image("image");

    companion object {
        public fun fromValue(value: String): MediaType = when (value) {
            "image" -> Image
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Photographer(val value: String) {
    NASABillIngalls("NASA/Bill Ingalls");

    companion object {
        public fun fromValue(value: String): Photographer = when (value) {
            "NASA/Bill Ingalls" -> NASABillIngalls
            else -> throw IllegalArgumentException()
        }
    }
}

data class ItemLink(
    val href: String,
    val rel: Rel,
    val render: MediaType
)

enum class Rel(val value: String) {
    Preview("preview");

    companion object {
        public fun fromValue(value: String): Rel = when (value) {
            "preview" -> Preview
            else -> throw IllegalArgumentException()
        }
    }
}

data class CollectionLink(
    val rel: String,
    val prompt: String,
    val href: String
)

data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Long
)