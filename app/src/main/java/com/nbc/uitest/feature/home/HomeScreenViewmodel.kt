package com.nbc.uitest.feature.home

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import coil.network.HttpException
import com.nbc.uitest.di.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(
    private val showRepo: ShowRepository
) : ViewModel() {

    var currentPage by mutableStateOf<UiState>(UiState.Loading)
        private set

    fun getShows(activity: Activity, id: Int): Flow<UiState> =
        flow {
            currentPage = UiState.Loading
            currentPage = try {
                UiState.Success(showRepo.getShows(activity, id))
            } catch (e: HttpException) {
                e.printStackTrace()
                UiState.Error(e)
            }
            emit(currentPage)
        }
}