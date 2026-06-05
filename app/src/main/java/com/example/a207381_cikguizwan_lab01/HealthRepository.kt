package com.example.a207381_cikguizwan_lab01

class HealthRepository(
    private val dao: HealthDao
) {

    val allData = dao.getAllData()

    suspend fun insert(
        data: HealthEntity
    ) {
        dao.insert(data)
    }
}