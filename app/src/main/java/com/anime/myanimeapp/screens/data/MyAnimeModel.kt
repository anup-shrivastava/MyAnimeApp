package com.anime.myanimeapp.screens.data

import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(
    @SerializedName("data")
    val data: List<AnimeData> = emptyList(),

    @SerializedName("pagination")
    val pagination: Pagination? = null
)

data class AnimeData(
    @SerializedName("mal_id")
    val malId: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("title_english")
    val titleEnglish: String? = null,

    @SerializedName("synopsis")
    val synopsis: String? = null,

    @SerializedName("episodes")
    val episodes: Int = 0,

    @SerializedName("score")
    val score: Double = 0.0,

    @SerializedName("images")
    val images: ImagesData? = null,

    @SerializedName("trailer")
    val trailer: TrailerData? = null
)

data class ImagesData(
    @SerializedName("jpg")
    val jpg: ImageUrl? = null
)

data class ImageUrl(
    @SerializedName("image_url")
    val imageUrl: String = ""
)

data class TrailerData(
    @SerializedName("youtube_id")
    val youtubeId: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("embed_url")
    val embedUrl: String? = null
)

data class Pagination(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int = 1,

    @SerializedName("has_next_page")
    val hasNextPage: Boolean = false,

    @SerializedName("current_page")
    val currentPage: Int = 1
)


data class AnimeDetailResponse(
    @SerializedName("data")
    val data: AnimeDetailData? = null
)


data class AnimeDetailData(
    @SerializedName("mal_id")
    val malId: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("title_english")
    val titleEnglish: String? = null,

    @SerializedName("synopsis")
    val synopsis: String? = null,

    @SerializedName("episodes")
    val episodes: Int = 0,

    @SerializedName("score")
    val score: Double = 0.0,

    @SerializedName("images")
    val images: ImagesData? = null,

    @SerializedName("trailer")
    val trailer: TrailerData? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("duration")
    val duration: String? = null,

    @SerializedName("rank")
    val rank: Int? = null,

    @SerializedName("popularity")
    val popularity: Int? = null,

    @SerializedName("members")
    val members: Int? = null,

    @SerializedName("favorites")
    val favorites: Int? = null,

    @SerializedName("aired")
    val aired: Aired? = null,

    @SerializedName("background")
    val background: String? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("studios")
    val studios: List<Studio>? = null,

    @SerializedName("rating")
    val rating: String? = null
)

data class Aired(
    @SerializedName("string")
    val string: String? = null
)

data class Genre(
    @SerializedName("name")
    val name: String
)

data class Studio(
    @SerializedName("name")
    val name: String
)

