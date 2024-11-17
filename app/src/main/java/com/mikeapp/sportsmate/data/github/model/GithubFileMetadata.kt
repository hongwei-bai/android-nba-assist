package com.mikeapp.sportsmate.data.github.model

data class GithubFileMetadata(
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val url: String,
    val html_url: String,
    val git_url: String,
    val download_url: String?,
    val type: String,
    val _links: Links,  // Use a separate class for the links
    val commit: Commit   // Define commit structure if needed
)

data class Links(
    val self: String,
    val git: String,
    val html: String
)

data class Commit(
    val sha: String,
    val author: Author
)

data class Author(
    val name: String,
    val email: String,
    val date: String
)