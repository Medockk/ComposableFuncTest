package com.example.composablefunctest.ChangeName

sealed class ChangeNameEvent {

    data class EnterUserName(val value: String) : ChangeNameEvent()
    data class SetException(val ex: String = "") : ChangeNameEvent()
    data object SaveChanges: ChangeNameEvent()
}