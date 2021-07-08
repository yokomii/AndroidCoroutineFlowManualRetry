package jp.co.example.yokomii.data

import jp.co.example.yokomii.data.repository.MyRepository
import jp.co.example.yokomii.data.source.MyDataSource

object DataContainer {
    val repository = MyRepository(MyDataSource())
}
