package com.example.csgomatches.data.matches.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.csgomatches.data.matches.service.MatchResponse
import com.example.csgomatches.data.matches.service.MatchesRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class MatchesPagingSource(private val service: MatchesRemoteDataSource) :
    PagingSource<Int, MatchResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MatchResponse> {
        val page = params.key ?: 1
        return try {
            val response = service.getMatches(page)
            LoadResult.Page(
                data = response,
                prevKey = page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MatchResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}