package com.example.csgomatches.data.matches

import android.util.Log
import androidx.paging.*
import com.example.csgomatches.R
import com.example.csgomatches.data.PAGE_SIZE
import com.example.csgomatches.data.imageUrlAsThumb
import com.example.csgomatches.data.matches.paging.MatchesPagingSource
import com.example.csgomatches.data.matches.service.MatchResponse
import com.example.csgomatches.data.matches.service.MatchesRemoteDataSource
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.MatchStatus
import com.example.csgomatches.ui.model.Team
import com.example.csgomatches.ui.model.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

private const val TAG = "MatchesRepository"

class MatchesRepository @Inject constructor(
    private val matchesRemoteDataSource: MatchesRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun getMatches(): Flow<PagingData<Match>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE)
    ) {
        MatchesPagingSource(matchesRemoteDataSource)
    }.flow
        .map { pagingData ->
            pagingData.filter { matchResponse ->
                matchResponse.opponents.size == 2
            }.map { matchResponse ->
                matchResponse.toMatch()
            }
        }.flowOn(dispatcher)

    private fun MatchResponse.toMatch(): Match = Match(
        id = id,
        status = getMatchStatus(this),
        beginAt = beginAt?.let { formatDate(it) },
        teams = getTeams(this),
        league = league.name,
        serie = if (serie.name is String) serie.name else null,
        tournamentId = tournamentId
    )

    private fun formatDate(beginAt: String?): UiText? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = beginAt?.let { LocalDateTime.parse(beginAt, formatter).atZone(ZoneOffset.UTC) } ?: return null
        val hours = date.format(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault()))

        if (date.toLocalDate() == LocalDate.now()) {
            return UiText(R.string.today, hours)
        }

        if (ChronoUnit.DAYS.between(date.toLocalDate(), LocalDate.now()) in 1..6) {
            return UiText(R.string.today, hours)
        }

        return when (date.dayOfWeek) {
            DayOfWeek.SUNDAY -> UiText(R.string.sunday, hours)
            DayOfWeek.MONDAY -> UiText(R.string.monday, hours)
            DayOfWeek.TUESDAY -> UiText(R.string.tuesday, hours)
            DayOfWeek.WEDNESDAY -> UiText(R.string.wednesday, hours)
            DayOfWeek.THURSDAY -> UiText(R.string.thursday, hours)
            DayOfWeek.FRIDAY -> UiText(R.string.friday, hours)
            DayOfWeek.SATURDAY -> UiText(R.string.saturday, hours)
            null -> null
        }
    }

    private fun getMatchStatus(match: MatchResponse): MatchStatus =
        when (match.status) {
            "running" -> MatchStatus.Running
            "finished" -> MatchStatus.Finished
            "not_started" -> MatchStatus.NotStarted
            else -> {
                Log.d(TAG, "getMatchStatus: Unknown status ${match.status} for matchId ${match.id}")
                MatchStatus.NotStarted
            }
        }

    private fun getTeams(match: MatchResponse): Pair<Team, Team> {
        val firstTeam = match.opponents[0].opponent.toTeam()
        val secondTeam = match.opponents[1].opponent.toTeam()
        return Pair(firstTeam, secondTeam)
    }

    private fun MatchResponse.Opponent.Opponent.toTeam(): Team = Team(
        id = id,
        name = name,
        imageUrl = imageUrlAsThumb(imageUrl),
        players = null
    )

}