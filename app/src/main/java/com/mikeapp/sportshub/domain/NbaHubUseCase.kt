package com.mikeapp.sportshub.domain

import com.mikeapp.sportshub.data.GithubOpenApiRepository
import com.mikeapp.sportshub.data.SportsHubRepository

class NbaHubUseCase(
    private val repository: SportsHubRepository,
    private val githubRepository: GithubOpenApiRepository
) {
    suspend fun queryOnce() {
        val usersJson = repository.query()
    }
}