package nz.co.example.rickandmortymodule.features.characters.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import nz.co.example.coremodule.common.NetworkResult
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharacter
import nz.co.example.rickandmortymodule.features.characters.data.models.DOCharactersResponse

internal class CharactersPagingSource(
    private val name: String,
    private val service: CharactersService
) : PagingSource<Int, DOCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DOCharacter> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            when (val response = service.getCharacters(page, name)) {
                is NetworkResult.Success -> {
                    val nextPageNumber = getNextPageNumber(response)
                    LoadResult.Page(
                        data = response.data.results,
                        prevKey = null,
                        nextKey = nextPageNumber
                    )
                }

                NetworkResult.NoData -> LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
                is NetworkResult.Error -> LoadResult.Error(response.exception)
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private fun getNextPageNumber(response: NetworkResult.Success<DOCharactersResponse>): Int? {
        if (response.data.info.next == null) return null
        val uri = Uri.parse(response.data.info.next)
        val nextPageQuery = uri.getQueryParameter("page")
        val nextPageNumber = nextPageQuery?.toInt()
        return nextPageNumber
    }

    override fun getRefreshKey(state: PagingState<Int, DOCharacter>): Int? {
        return null
    }

}