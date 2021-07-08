package jp.co.example.yokomii.coroutine_flow_manual_retry

import androidx.lifecycle.*
import jp.co.example.yokomii.data.DataContainer
import kotlinx.coroutines.flow.*

class ManualRetryViewModel : ViewModel() {

    private val repository = DataContainer.repository

    private val _retryEvent: MutableLiveData<RetryEvent> = MutableLiveData(RetryEvent.Retrying)
    val retryEvent: LiveData<RetryEvent> = _retryEvent

    val uiState: LiveData<UiState> = _retryEvent.asFlow()
        .filter { it is RetryEvent.Retrying }
        .flatMapLatest { repository.latest }
        .map { UiState.Success(it.message) as UiState }
        .retryWhen { _, _ ->
            emit(UiState.Error)
            true
        }
        .onEach { _retryEvent.value = RetryEvent.Retried }
        .asLiveData()

    private val retryState = MutableStateFlow<RetryEvent>(RetryEvent.Retrying)

    val uiStateFromStateFlow: LiveData<UiState> = retryState
        .filter { it is RetryEvent.Retrying }
        .flatMapLatest { repository.latest }
        .map { UiState.Success(it.message) as UiState }
        .retryWhen { _, _ ->
            emit(UiState.Error)
            true
        }
        .onEach { retryState.value = RetryEvent.Retried }
        .asLiveData()

    val uiStateUnRetrying: LiveData<UiState> = repository.latest
        .map { UiState.Success(it.message) as UiState }
        .catch { emit(UiState.Error) }
        .asLiveData()

    val uiStateAutoRetry: LiveData<UiState> = repository.latest
        .map { UiState.Success(it.message) as UiState }
        .retryWhen { cause, attempt ->
            emit(UiState.Error)
            true
        }
        .asLiveData()

    fun retry() {
        _retryEvent.value = RetryEvent.Retrying
    }

    fun retryWithStateFlow() {
        retryState.value = RetryEvent.Retrying
    }
}

sealed class RetryEvent {
    object Retrying : RetryEvent()
    object Retried : RetryEvent()
}

sealed class UiState {
    class Success(val message: String) : UiState()
    object Error : UiState()
}
