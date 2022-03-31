package com.eldirohmanur.movie_app.data.model

import com.eldirohmanur.movie_app.presentation.base.getReadableDate
import com.eldirohmanur.movie_app.presentation.domain.model.AuthorDetails
import com.eldirohmanur.movie_app.presentation.domain.model.Review
import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<ReviewItem>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("errors") val errors: List<String>? = null
)

data class AuthorDetailsItem(
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("avatar_path") val avatarPath: String? = null,
    @SerializedName("rating") val rating: String? = null
) {
    fun toDomain() = AuthorDetails(
        name = name ?: username ?: "Anonymous",
        username = username.orEmpty(),
        avatarPath = avatarPath.orEmpty(),
        rating = rating.orEmpty()
    )
}

data class ReviewItem(
    @SerializedName("author") val author: String? = null,
    @SerializedName("author_details") val authorDetails: AuthorDetailsItem? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("url") val url: String? = null
) {
    fun toDomain() = Review(
        author = author.orEmpty(),
        authorDetails = authorDetails?.toDomain() ?: AuthorDetails(),
        content = content.orEmpty(),
        createdAt = createdAt?.getReadableDate().orEmpty(),
        id = id.orEmpty(),
        updatedAt = updatedAt?.getReadableDate().orEmpty(),
        url = url.orEmpty()
    )
}