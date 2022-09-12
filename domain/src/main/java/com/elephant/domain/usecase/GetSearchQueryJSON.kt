package com.elephant.domain.usecase

import com.elephant.domain.model.NasaServerModel
import com.elephant.domain.repositories.SearchRepository
import com.elephant.domain.wrappers.CallbackNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSearchQueryJSON(private val searchRepository: SearchRepository) :
    SearchQueryJSONUseCase<NasaServerModel, String> {

    override fun invoke(date: String, page: Int): Flow<CaseResult<NasaServerModel, String>> = flow {
        when (val result = searchRepository.getSearchQueryJSON(date, page)) {
            is CallbackNetwork.Success ->
                emit(CaseResult.Success(result.response))
            is CallbackNetwork.Failure -> emit(CaseResult.Failure(result.reason))
        }
    }.flowOn(Dispatchers.IO)
}