package com.example.popview.data

import com.example.popview.R

data class ContentItem(
    val imageRes: Int,
    val title: String,
    val description: String,
    val platforms: List<Plataforma>,
    val rating: Float
)

enum class Plataforma(val iconRes: Int) {
    NETFLIX(R.drawable.logo_netflix),
    DISNEY_PLUS(R.drawable.logo_disneyplus),
    AMAZON_PRIME(R.drawable.logo_amazon_prime),
    HBO_MAX(R.drawable.logo_hbo),
    JAZZTELE(R.drawable.logo_jazzteltv),
    MOVISTAR(R.drawable.logo_movistarplus)
}