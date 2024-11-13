package com.mikeapp.sportshub.data.github.model

data class GithubFileContent(
    val name: String,
    val content: String,  // Base64 encoded content of the file
    val encoding: String
)