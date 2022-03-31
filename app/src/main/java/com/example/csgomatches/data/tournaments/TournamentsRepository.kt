package com.example.csgomatches.data.tournaments

import com.example.csgomatches.data.tournaments.service.RostersResponse
import com.example.csgomatches.data.tournaments.service.TournamentsRemoteDataSource
import com.example.csgomatches.ui.model.Match
import com.example.csgomatches.ui.model.Player

class TournamentsRepository(private val tournamentsRemoteDataSource: TournamentsRemoteDataSource) {
    suspend fun getRostersForMatch(match: Match): Pair<List<Player>, List<Player>>? {
        if (match.teams == null) {
            return null
        }

        val rosters = tournamentsRemoteDataSource.getRosters(match.tournamentId)
            .rosters.filter { roster ->
                roster.id == match.teams.first.id || roster.id == match.teams.second.id
            }
        if (rosters.size != 2) {
            return null
        }

        return getPlayers(rosters)
    }

    private fun getPlayers(rostersDto: List<RostersResponse.Roster>): Pair<List<Player>, List<Player>> {
        val rosters: MutableList<List<Player>> = mutableListOf()
        for (rosterDto in rostersDto) {
            val roster = rosterDto.players.map { playerDto ->
                playerDto.toPlayer()
            }
            rosters.add(roster)
        }

        return Pair(rosters[0], rosters[1])
    }

    private fun RostersResponse.Roster.Player.toPlayer(): Player = Player(
        nickname = name,
        name = "$firstName $lastName",
        imageUrl = imageUrl.toString()
    )
}