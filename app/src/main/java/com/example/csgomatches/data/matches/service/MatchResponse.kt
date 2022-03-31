package com.example.csgomatches.data.matches.service

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("begin_at")
    val beginAt: String?,
    @SerializedName("detailed_stats")
    val detailedStats: Boolean,
    @SerializedName("draw")
    val draw: Boolean,
    @SerializedName("end_at")
    val endAt: String?,
    @SerializedName("forfeit")
    val forfeit: Boolean,
    @SerializedName("game_advantage")
    val gameAdvantage: Any?,
    @SerializedName("games")
    val games: List<Game>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("league")
    val league: League,
    @SerializedName("league_id")
    val leagueId: Int,
    @SerializedName("live")
    val live: Live,
    @SerializedName("live_embed_url")
    val liveEmbedUrl: String?,
    @SerializedName("match_type")
    val matchType: String,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number_of_games")
    val numberOfGames: Int,
    @SerializedName("official_stream_url")
    val officialStreamUrl: String?,
    @SerializedName("opponents")
    val opponents: List<Opponent>,
    @SerializedName("original_scheduled_at")
    val originalScheduledAt: String,
    @SerializedName("rescheduled")
    val rescheduled: Boolean,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("scheduled_at")
    val scheduledAt: String,
    @SerializedName("serie")
    val serie: Serie,
    @SerializedName("serie_id")
    val serieId: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("streams")
    val streams: Streams,
    @SerializedName("streams_list")
    val streamsList: List<StreamItem>,
    @SerializedName("tournament")
    val tournament: Tournament,
    @SerializedName("tournament_id")
    val tournamentId: Int,
    @SerializedName("videogame")
    val videogame: Videogame,
    @SerializedName("videogame_version")
    val videogameVersion: Any?,
    @SerializedName("winner")
    val winner: Winner?,
    @SerializedName("winner_id")
    val winnerId: Int?
) {
    data class Game(
        @SerializedName("begin_at")
        val beginAt: Any?,
        @SerializedName("complete")
        val complete: Boolean,
        @SerializedName("detailed_stats")
        val detailedStats: Boolean,
        @SerializedName("end_at")
        val endAt: Any?,
        @SerializedName("finished")
        val finished: Boolean,
        @SerializedName("forfeit")
        val forfeit: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("length")
        val length: Any?,
        @SerializedName("match_id")
        val matchId: Int,
        @SerializedName("position")
        val position: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("video_url")
        val videoUrl: Any?,
        @SerializedName("winner")
        val winner: Winner,
        @SerializedName("winner_type")
        val winnerType: String
    ) {
        data class Winner(
            @SerializedName("id")
            val id: Any?,
            @SerializedName("type")
            val type: String
        )
    }

    data class League(
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("modified_at")
        val modifiedAt: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("url")
        val url: Any?
    )

    data class Live(
        @SerializedName("opens_at")
        val opensAt: Any?,
        @SerializedName("supported")
        val supported: Boolean,
        @SerializedName("url")
        val url: Any?
    )

    data class Opponent(
        @SerializedName("opponent")
        val opponent: Opponent,
        @SerializedName("type")
        val type: String
    ) {
        data class Opponent(
            @SerializedName("acronym")
            val acronym: Any?,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image_url")
            val imageUrl: String?,
            @SerializedName("location")
            val location: String,
            @SerializedName("modified_at")
            val modifiedAt: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("slug")
            val slug: String
        )
    }

    data class Result(
        @SerializedName("score")
        val score: Int,
        @SerializedName("team_id")
        val teamId: Int
    )

    data class Serie(
        @SerializedName("begin_at")
        val beginAt: String,
        @SerializedName("description")
        val description: Any?,
        @SerializedName("end_at")
        val endAt: Any?,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("league_id")
        val leagueId: Int,
        @SerializedName("modified_at")
        val modifiedAt: String,
        @SerializedName("name")
        val name: Any?,
        @SerializedName("season")
        val season: String,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("tier")
        val tier: String,
        @SerializedName("winner_id")
        val winnerId: Any?,
        @SerializedName("winner_type")
        val winnerType: Any?,
        @SerializedName("year")
        val year: Int
    )

    data class Streams(
        @SerializedName("english")
        val english: Stream,
        @SerializedName("official")
        val official: Stream,
        @SerializedName("russian")
        val russian: Stream
    ) {
        data class Stream(
            @SerializedName("embed_url")
            val embedUrl: String,
            @SerializedName("raw_url")
            val rawUrl: String
        )
    }

    data class StreamItem(
        @SerializedName("embed_url")
        val embedUrl: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("main")
        val main: Boolean,
        @SerializedName("official")
        val official: Boolean,
        @SerializedName("raw_url")
        val rawUrl: String
    )

    data class Tournament(
        @SerializedName("begin_at")
        val beginAt: String,
        @SerializedName("end_at")
        val endAt: Any?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("league_id")
        val leagueId: Int,
        @SerializedName("live_supported")
        val liveSupported: Boolean,
        @SerializedName("modified_at")
        val modifiedAt: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("prizepool")
        val prizepool: Any?,
        @SerializedName("serie_id")
        val serieId: Int,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("tier")
        val tier: String,
        @SerializedName("winner_id")
        val winnerId: Any?,
        @SerializedName("winner_type")
        val winnerType: String
    )

    data class Videogame(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
    )

    data class Winner(
        @SerializedName("acronym")
        val acronym: Any?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("modified_at")
        val modifiedAt: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("slug")
        val slug: String
    )
}
