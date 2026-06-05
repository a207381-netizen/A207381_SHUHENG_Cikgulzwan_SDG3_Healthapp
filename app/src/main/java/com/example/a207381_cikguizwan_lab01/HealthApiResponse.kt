package com.example.a207381_cikguizwan_lab01

data class HealthApiResponse(
    val cases: Long,
    val deaths: Long,
    val recovered: Long,
    val updated: Long
)
