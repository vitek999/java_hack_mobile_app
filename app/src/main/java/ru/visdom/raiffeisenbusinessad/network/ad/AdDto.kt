package ru.visdom.raiffeisenbusinessad.network.ad

import ru.visdom.raiffeisenbusinessad.domain.Ad

data class AdDto(val id: Long, val title: String, val description: String)

fun AdDto.asDomain() = Ad(id, title, description, true)

fun List<AdDto>.asDomain(): List<Ad> = map { it.asDomain() }