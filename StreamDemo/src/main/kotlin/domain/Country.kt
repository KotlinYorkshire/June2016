package domain

data class Country(val name: String, val capital: String, val isoCode: String,
                   val area: Int, val region: String, val subregion: String,
                   val landlocked: Boolean )