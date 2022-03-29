package com.example.csgomatches.data.tournaments.service


import com.google.gson.annotations.SerializedName

data class RostersResponse(
    @SerializedName("rosters")
    val rosters: List<Roster>,
    @SerializedName("type")
    val type: String
) {
    data class Roster(
        @SerializedName("acronym")
        val acronym: String,
        @SerializedName("current_videogame")
        val currentVideogame: CurrentVideogame,
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
        @SerializedName("players")
        val players: List<Player>,
        @SerializedName("slug")
        val slug: String
    ) {
        data class CurrentVideogame(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("slug")
            val slug: String
        )

        data class Player(
            @SerializedName("age")
            val age: Int?,
            @SerializedName("birth_year")
            val birthYear: Int?,
            @SerializedName("birthday")
            val birthday: String?,
            @SerializedName("first_name")
            val firstName: String,
            @SerializedName("hometown")
            val hometown: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image_url")
            val imageUrl: Any?,
            @SerializedName("last_name")
            val lastName: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("nationality")
            val nationality: String?,
            @SerializedName("role")
            val role: String,
            @SerializedName("slug")
            val slug: String
        )
    }
}