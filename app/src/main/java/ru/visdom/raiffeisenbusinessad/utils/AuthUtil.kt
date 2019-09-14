package ru.visdom.raiffeisenbusinessad.utils

import okhttp3.Credentials

fun getTokenFromPhoneAndPassword(phone: String, password: String) =
    Credentials.basic(phone, password)