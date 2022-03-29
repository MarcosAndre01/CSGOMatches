package com.example.csgomatches.data.matches

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.csgomatches.matches.data.paging.MatchesPagingSource
import com.example.csgomatches.matches.data.service.MatchResponse
import com.example.csgomatches.matches.data.service.MatchesRemoteDataSource
import com.example.csgomatches.matches.ui.model.Match
import com.example.csgomatches.matches.ui.model.MatchStatus
import com.example.csgomatches.matches.ui.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MatchesRepository"
private const val PAGE_SIZE = 50

class MatchesRepository(
    private val matchesRemoteDataSource: MatchesRemoteDataSource,
) {

    fun getMatches(): Flow<PagingData<Match>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE)
    ) {
        MatchesPagingSource(matchesRemoteDataSource)
    }.flow.map { pagingData ->
        pagingData.map { matchResponse ->
            matchResponse.toMatch()
        }
    }

    private fun MatchResponse.toMatch(): Match = Match(
        id = id,
        status = getMatchStatus(this),
        beginAt = beginAt?.let { formatDate(it) },
        teams = getTeams(this),
        league = league.name,
        serie = serie.name.toString(),
        tournamentId = tournamentId
    )

    private fun formatDate(date: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        return dateFormat.parse(date)
    }

    private fun getMatchStatus(match: MatchResponse): MatchStatus {
        return MatchStatus.NotStarted
    }

    private fun getTeams(match: MatchResponse): Pair<Team, Team>? {
        val opponents = match.opponents
        if (opponents.size != 2) {
            return null
        }

        val firstTeam = Team(
            id = opponents[0].opponent.id,
            name = opponents[0].opponent.name,
            imageUrl = opponents[0].opponent.imageUrl.toString(),
            players = null
        )
        val secondTeam = Team(
            id = opponents[1].opponent.id,
            name = opponents[1].opponent.name,
            imageUrl = opponents[1].opponent.imageUrl.toString(),
            players = null
        )

        return Pair(firstTeam, secondTeam)
    }
}