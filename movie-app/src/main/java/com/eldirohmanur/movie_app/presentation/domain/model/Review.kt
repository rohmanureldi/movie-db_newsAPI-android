package com.eldirohmanur.movie_app.presentation.domain.model

data class AuthorDetails(
    val name: String = "Anonymous",
    val username: String = "",
    val avatarPath: String = "",
    val rating: String = ""
)

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)
