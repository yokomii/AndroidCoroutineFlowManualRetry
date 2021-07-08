package jp.co.example.yokomii.data.source

import jp.co.example.yokomii.data.model.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MyDataSource {

    val latest: Flow<ApiResponse> = flow {
        while (true) {
            delay(3000)
            if (Random.nextBoolean()) {
                throw Exception("")
            }
            emit(ApiResponse("success"))
        }
    }
}
