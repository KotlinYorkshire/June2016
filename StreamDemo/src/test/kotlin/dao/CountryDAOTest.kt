package dao

import domain.Country
import org.junit.Assert.*
import org.junit.Test

fun List<Country>.containsAll(countryCodes: List<String>): Boolean {
    return this.map { it.isoCode }.containsAll(countryCodes)
}

fun List<Country>.containsNone(countryCodes: List<String>): Boolean {
    val x = this.filter{ countryCodes.contains(it.isoCode) }
    return x.isEmpty()
}

/**
 * Unit Tests for the Country DAO
 */
class CountryDAOTest {

    private val countryDao = CountryDAO()

    @Test
    fun testFindByIsoCode() {
        val country = countryDao.findCountryByIsoCode("GBR")
        assertNotNull(country)
        if (country != null) {
            assertEquals("GBR", country.isoCode)
            assertEquals("United Kingdom", country.name)
            assertFalse(country.landlocked)
        }
    }

    @Test
    fun testFindByIncorrectCode() {
        val country = countryDao.findCountryByIsoCode("XXX")
        assertNull(country)
    }

    @Test
    fun testFetchCapitalCities() {
        val cities = countryDao.fetchCapitalCities();
        assertNotNull(cities)
        assertTrue(cities.size > 0)
        assertTrue(cities.contains("London"))
        assertTrue(cities.contains("Paris"))
        assertTrue(cities.contains("Canberra"))
        assertFalse(cities.contains("Edinburgh"))
    }

    @Test
    fun testSplitByLandlockedStatus() {
        val (landlocked, coastal) = countryDao.splitByLandLockedStatus()
        val expectedLandlocked = listOf<String>("CHE","UGA")
        val expectedCoastal = listOf<String>("FRA","USA","AUS","GBR")
        assertTrue(landlocked.containsAll(expectedLandlocked))
        assertTrue(coastal.containsNone(expectedLandlocked))
        assertTrue(landlocked.containsNone(expectedCoastal))
        assertTrue(coastal.containsAll(expectedCoastal))
    }

    @Test
    fun testFindCountriesByRegion(){
        val americanCountries = countryDao.findCountriesByRegion("Americas")
        assertTrue(americanCountries.containsAll(listOf("ARG", "BRA", "PER", "CHL", "COL","CAN", "USA", "MEX")))
        assertTrue(americanCountries.containsNone(listOf("GBR", "FRA", "AUS")))
    }

    @Test
    fun testFindCountriesByRegionAndSubRegion(){
        val southAmericanCountries = countryDao.findCountriesByRegionAndSubregion("Americas", "South America")
        assertEquals(15, southAmericanCountries.size)
        assertTrue(southAmericanCountries.containsAll(listOf("ARG", "BRA", "PER", "CHL", "COL")))
        assertTrue(southAmericanCountries.containsNone(listOf("GBR", "FRA", "AUS", "CAN", "USA", "MEX")))
    }

    @Test
    fun testFetchCountryNamesGroupedByRegion() {
        val countriesByRegion = countryDao.fetchCountryNamesGroupedByRegion()
        assertEquals(6, countriesByRegion.size)
        assertTrue(countriesByRegion.keys.containsAll(listOf("Europe", "Americas", "Asia", "Africa", "Oceania")))
        assertTrue(countriesByRegion.get("Europe")!!.containsAll(listOf("United Kingdom", "France", "Germany")))
        assertTrue(countriesByRegion.get("Oceania")!!.containsAll(listOf("Australia", "New Zealand")))
    }

    @Test
    fun testFindLargestEuropeanCountriesByRegion(){
        // Find largest 7 European Countries
        val countries = countryDao.findLargestCountriesByRegion("Europe", 7)
        assertEquals(7, countries.size)
        assertTrue(countries.containsAll(listOf("RUS" , "UKR", "FRA", "ESP", "SWE", "DEU", "FIN")))
    }

    @Test
    fun testFindLargestAmericanCountriesByRegion(){
        // Find largest 5 American Countries
        val countries = countryDao.findLargestCountriesByRegion("Americas")
        assertEquals(5, countries.size)
        assertTrue(countries.containsAll(listOf("CAN" , "USA", "BRA", "ARG", "GRL")))
    }


    @Test
    fun testFindAverageSize(){
        val averageSize = countryDao.findAverageSizeOfCountries()
        assertNotNull(averageSize)
        assertEquals(605177.75, averageSize, 1.0)
    }


}