package com.example.csgomatches.data.matches

import android.util.Log
import com.example.csgomatches.R
import com.example.csgomatches.data.imageUrlAsThumb
import com.example.csgomatches.data.matches.local.MatchEntity
import com.example.csgomatches.data.matches.remote.MatchResponse
import com.example.csgomatches.model.Match
import com.example.csgomatches.model.MatchStatus
import com.example.csgomatches.model.Team
import com.example.csgomatches.model.UiText
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

private val TAG = "MatchMapper"

fun MatchEntity.toMatch(): Match = Match(
    id = remoteId,
    status = getMatchStatus(status),
    beginAt = beginAt?.let { formatDate(it) },
    firstTeam = Team(
        id = firstTeamId,
        name = firstTeamName,
        imageUrl = firstTeamImageUrl,
        players = null
    ),
    secondTeam = Team(
        id = secondTeamId,
        name = secondTeamName,
        imageUrl = secondTeamImageUrl,
        players = null
    ),
    league = league,
    serie = serie,
    tournamentId = tournamentId
)

fun MatchResponse.toEntity(): MatchEntity = MatchEntity(
    remoteId = id,
    status = status,
    beginAt = beginAt,
    firstTeamId = opponents[0].opponent.id,
    firstTeamName = opponents[0].opponent.name,
    firstTeamImageUrl = imageUrlAsThumb(opponents[0].opponent.imageUrl),
    secondTeamId = opponents[1].opponent.id,
    secondTeamName = opponents[1].opponent.name,
    secondTeamImageUrl = imageUrlAsThumb(opponents[1].opponent.imageUrl),
    league = league.name,
    serie = if (serie.name is String) serie.name else null,
    tournamentId = tournamentId
)

private fun formatDate(beginAt: String?): UiText? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val date = beginAt?.let { LocalDateTime.parse(beginAt, formatter).atZone(ZoneOffset.UTC) }
        ?: return null
    val hours = date.format(DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault()))

    if (date.toLocalDate() == LocalDate.now()) {
        return UiText(R.string.today, hours)
    }

    if (ChronoUnit.DAYS.between(LocalDate.now(), date.toLocalDate()) < 7) {
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

    val dayMonth =
        date.format(DateTimeFormatter.ofPattern("dd.MM").withZone(ZoneId.systemDefault()))
    return UiText(R.string.match_date, dayMonth, hours)

}

private fun getMatchStatus(status: String): MatchStatus =
    when (status) {
        "running" -> MatchStatus.Running
        "finished" -> MatchStatus.Finished
        "not_started" -> MatchStatus.NotStarted
        else -> {
            Log.d(TAG, "getMatchStatus: Unknown status ${status}")
            MatchStatus.NotStarted
        }
    }