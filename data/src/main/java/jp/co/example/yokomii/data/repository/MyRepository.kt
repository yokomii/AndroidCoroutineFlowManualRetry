package jp.co.example.yokomii.data.repository

import jp.co.example.yokomii.data.model.ApiResponse
import jp.co.example.yokomii.data.source.MyDataSource
import kotlinx.coroutines.flow.Flow

class MyRepository(
    dataSource: MyDataSource,
) {

    val latest: Flow<ApiResponse> = dataSource.latest
}
