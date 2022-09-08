package com.elephant.domain.usecase

import com.elephant.domain.repositories.SearchRepository
import com.elephant.domain.wrappers.CallbackNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSearchQueryPictures(private val searchRepository: SearchRepository) :
    SearchQueryPicturesUseCase<List<String>, String> {

    override fun invoke(url: String): Flow<CaseResult<List<String>, String>> = flow {
        when (val result = searchRepository.getSearchQueryPictures(url)) {
            is CallbackNetwork.Success ->
                emit(CaseResult.Success(result.response))
            is CallbackNetwork.Failure -> emit(CaseResult.Failure(result.reason))
        }
    }.flowOn(Dispatchers.IO)
}