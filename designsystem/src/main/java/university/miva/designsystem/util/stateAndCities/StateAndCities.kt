package university.miva.designsystem.util.stateAndCities

data class State(
    val name: String,
    val cities: List<String>,
)

object StatesAndCities {
    private val nigeriaStates =
        listOf(
            State("Abia", listOf("Aba", "Umuahia", "Ohafia", "Arochukwu")),
            State("Adamawa", listOf("Yola", "Mubi", "Jimeta", "Numan", "Ganye")),
            State("Akwa Ibom", listOf("Uyo", "Eket", "Ikot Ekpene", "Oron", "Abak")),
            State("Anambra", listOf("Awka", "Onitsha", "Nnewi", "Ekwulobia", "Aguata")),
            State("Bauchi", listOf("Bauchi", "Azare", "Misau", "Jama'are", "Katagum")),
            State("Bayelsa", listOf("Yenagoa", "Ogbia", "Brass", "Sagbama", "Ekeremor")),
            State("Benue", listOf("Makurdi", "Gboko", "Otukpo", "Katsina-Ala", "Vandeikya")),
            State("Borno", listOf("Maiduguri", "Biu", "Damboa", "Gwoza", "Kukawa")),
            State("Cross River", listOf("Calabar", "Ogoja", "Ikom", "Obudu", "Ugep")),
            State("Delta", listOf("Asaba", "Warri", "Sapele", "Ughelli", "Agbor")),
            State("Ebonyi", listOf("Abakaliki", "Afikpo", "Onueke", "Ezzamgbo", "Ishielu")),
            State("Edo", listOf("Benin City", "Auchi", "Ekpoma", "Uromi", "Igarra")),
            State("Ekiti", listOf("Ado-Ekiti", "Ikere", "Oye", "Ikole", "Emure")),
            State("Enugu", listOf("Enugu", "Nsukka", "Agbani", "Awgu", "Udi")),
            State("FCT", listOf("Abuja", "Gwagwalada", "Kuje", "Bwari", "Kwali")),
            State("Gombe", listOf("Gombe", "Kumo", "Billiri", "Kaltungo", "Bajoga")),
            State("Imo", listOf("Owerri", "Orlu", "Okigwe", "Oguta", "Mbaise")),
            State("Jigawa", listOf("Dutse", "Hadejia", "Kazaure", "Gumel", "Ringim")),
            State("Kaduna", listOf("Kaduna", "Zaria", "Kafanchan", "Kagoro", "Kachia")),
            State("Kano", listOf("Kano", "Wudil", "Gaya", "Rano", "Bichi")),
            State("Katsina", listOf("Katsina", "Daura", "Funtua", "Malumfashi", "Kankia")),
            State("Kebbi", listOf("Birnin Kebbi", "Argungu", "Yauri", "Zuru", "Jega")),
            State("Kogi", listOf("Lokoja", "Okene", "Idah", "Kabba", "Ankpa")),
            State("Kwara", listOf("Ilorin", "Offa", "Jebba", "Lafiagi", "Pategi")),
            State(
                "Lagos",
                listOf(
                    "Ikeja",
                    "Lagos Island",
                    "Victoria Island",
                    "Lekki",
                    "Surulere",
                    "Yaba",
                    "Ikorodu",
                    "Epe",
                    "Badagry",
                    "Ajah",
                ),
            ),
            State("Nasarawa", listOf("Lafia", "Keffi", "Akwanga", "Nasarawa", "Doma")),
            State("Niger", listOf("Minna", "Bida", "Suleja", "Kontagora", "Lapai")),
            State("Ogun", listOf("Abeokuta", "Ijebu-Ode", "Sagamu", "Ota", "Ilaro")),
            State("Ondo", listOf("Akure", "Ondo", "Owo", "Ikare", "Okitipupa")),
            State("Osun", listOf("Osogbo", "Ile-Ife", "Ilesa", "Ede", "Ikire")),
            State("Oyo", listOf("Ibadan", "Ogbomoso", "Oyo", "Iseyin", "Saki")),
            State("Plateau", listOf("Jos", "Bukuru", "Pankshin", "Shendam", "Langtang")),
            State("Rivers", listOf("Port Harcourt", "Obio-Akpor", "Bonny", "Okrika", "Degema")),
            State("Sokoto", listOf("Sokoto", "Tambuwal", "Wurno", "Gwadabawa", "Illela")),
            State("Taraba", listOf("Jalingo", "Wukari", "Bali", "Gembu", "Serti")),
            State("Yobe", listOf("Damaturu", "Potiskum", "Gashua", "Nguru", "Geidam")),
            State("Zamfara", listOf("Gusau", "Kaura Namoda", "Talata Mafara", "Anka", "Maru")),
        )

    private val ghanaStates =
        listOf(
            State("Greater Accra", listOf("Accra", "Tema", "Madina", "Ashaiman", "Teshie")),
            State("Ashanti", listOf("Kumasi", "Obuasi", "Ejisu", "Mampong", "Konongo")),
            State("Western", listOf("Sekondi-Takoradi", "Tarkwa", "Axim", "Prestea", "Elubo")),
            State("Central", listOf("Cape Coast", "Kasoa", "Winneba", "Mankessim", "Saltpond")),
            State("Eastern", listOf("Koforidua", "Nkawkaw", "Nsawam", "Akim Oda", "Akropong")),
            State("Volta", listOf("Ho", "Hohoe", "Keta", "Aflao", "Kpando")),
            State("Northern", listOf("Tamale", "Yendi", "Damongo", "Salaga", "Bimbilla")),
            State("Upper East", listOf("Bolgatanga", "Navrongo", "Bawku", "Zebilla", "Paga")),
            State("Upper West", listOf("Wa", "Tumu", "Lawra", "Nandom", "Jirapa")),
            State("Bono", listOf("Sunyani", "Berekum", "Dormaa Ahenkro", "Wenchi")),
        )

    private val kenyaStates =
        listOf(
            State("Nairobi", listOf("Nairobi", "Westlands", "Kibera", "Kasarani", "Embakasi")),
            State("Mombasa", listOf("Mombasa", "Nyali", "Likoni", "Kisauni", "Changamwe")),
            State("Kisumu", listOf("Kisumu", "Ahero", "Maseno", "Muhoroni", "Kombewa")),
            State("Nakuru", listOf("Nakuru", "Naivasha", "Gilgil", "Molo", "Njoro")),
            State("Kiambu", listOf("Kiambu", "Thika", "Ruiru", "Kikuyu", "Limuru")),
            State("Machakos", listOf("Machakos", "Athi River", "Kangundo", "Matuu", "Masii")),
            State("Kajiado", listOf("Kajiado", "Ngong", "Kitengela", "Ongata Rongai", "Namanga")),
            State("Uasin Gishu", listOf("Eldoret", "Burnt Forest", "Turbo", "Moiben")),
            State("Kilifi", listOf("Kilifi", "Malindi", "Watamu", "Mtwapa", "Mariakani")),
            State("Nyeri", listOf("Nyeri", "Karatina", "Othaya", "Naro Moru", "Mukurweini")),
        )

    private val southAfricaStates =
        listOf(
            State("Gauteng", listOf("Johannesburg", "Pretoria", "Soweto", "Sandton", "Centurion", "Midrand")),
            State("Western Cape", listOf("Cape Town", "Stellenbosch", "Paarl", "George", "Knysna")),
            State("KwaZulu-Natal", listOf("Durban", "Pietermaritzburg", "Newcastle", "Richards Bay", "Ballito")),
            State("Eastern Cape", listOf("Port Elizabeth", "East London", "Mthatha", "Grahamstown", "Queenstown")),
            State("Free State", listOf("Bloemfontein", "Welkom", "Kroonstad", "Bethlehem", "Sasolburg")),
            State("Mpumalanga", listOf("Nelspruit", "Witbank", "Middelburg", "Secunda", "Standerton")),
            State("Limpopo", listOf("Polokwane", "Tzaneen", "Mokopane", "Thohoyandou", "Musina")),
            State("North West", listOf("Rustenburg", "Potchefstroom", "Klerksdorp", "Mahikeng", "Brits")),
            State("Northern Cape", listOf("Kimberley", "Upington", "Springbok", "De Aar", "Kuruman")),
        )

    private val usStates =
        listOf(
            State("California", listOf("Los Angeles", "San Francisco", "San Diego", "San Jose", "Sacramento")),
            State("Texas", listOf("Houston", "Dallas", "Austin", "San Antonio", "Fort Worth")),
            State("Florida", listOf("Miami", "Orlando", "Tampa", "Jacksonville", "Fort Lauderdale")),
            State("New York", listOf("New York City", "Buffalo", "Rochester", "Albany", "Syracuse")),
            State("Illinois", listOf("Chicago", "Aurora", "Naperville", "Rockford", "Springfield")),
            State("Pennsylvania", listOf("Philadelphia", "Pittsburgh", "Allentown", "Erie", "Reading")),
            State("Ohio", listOf("Columbus", "Cleveland", "Cincinnati", "Toledo", "Akron")),
            State("Georgia", listOf("Atlanta", "Augusta", "Savannah", "Columbus", "Macon")),
            State("North Carolina", listOf("Charlotte", "Raleigh", "Durham", "Greensboro", "Winston-Salem")),
            State("Michigan", listOf("Detroit", "Grand Rapids", "Ann Arbor", "Lansing", "Flint")),
        )

    private val ukStates =
        listOf(
            State(
                "England",
                listOf("London", "Birmingham", "Manchester", "Liverpool", "Leeds", "Bristol", "Sheffield"),
            ),
            State("Scotland", listOf("Edinburgh", "Glasgow", "Aberdeen", "Dundee", "Inverness")),
            State("Wales", listOf("Cardiff", "Swansea", "Newport", "Wrexham", "Barry")),
            State("Northern Ireland", listOf("Belfast", "Derry", "Lisburn", "Newry", "Bangor")),
        )

    private val canadaStates =
        listOf(
            State("Ontario", listOf("Toronto", "Ottawa", "Mississauga", "Hamilton", "Brampton")),
            State("Quebec", listOf("Montreal", "Quebec City", "Laval", "Gatineau", "Longueuil")),
            State("British Columbia", listOf("Vancouver", "Victoria", "Surrey", "Burnaby", "Richmond")),
            State("Alberta", listOf("Calgary", "Edmonton", "Red Deer", "Lethbridge", "Medicine Hat")),
            State("Manitoba", listOf("Winnipeg", "Brandon", "Steinbach", "Thompson")),
            State("Saskatchewan", listOf("Saskatoon", "Regina", "Prince Albert", "Moose Jaw")),
        )

    private val indiaStates =
        listOf(
            State("Maharashtra", listOf("Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad")),
            State("Delhi", listOf("New Delhi", "North Delhi", "South Delhi", "East Delhi", "West Delhi")),
            State("Karnataka", listOf("Bangalore", "Mysore", "Hubli", "Mangalore", "Belgaum")),
            State("Tamil Nadu", listOf("Chennai", "Coimbatore", "Madurai", "Salem", "Tiruchirappalli")),
            State("Telangana", listOf("Hyderabad", "Warangal", "Nizamabad", "Karimnagar", "Khammam")),
            State("Gujarat", listOf("Ahmedabad", "Surat", "Vadodara", "Rajkot", "Gandhinagar")),
            State("West Bengal", listOf("Kolkata", "Howrah", "Durgapur", "Asansol", "Siliguri")),
            State("Rajasthan", listOf("Jaipur", "Jodhpur", "Udaipur", "Kota", "Ajmer")),
            State("Uttar Pradesh", listOf("Lucknow", "Kanpur", "Agra", "Varanasi", "Allahabad")),
            State("Kerala", listOf("Thiruvananthapuram", "Kochi", "Kozhikode", "Thrissur", "Kollam")),
        )

    private val uaeStates =
        listOf(
            State("Abu Dhabi", listOf("Abu Dhabi City", "Al Ain", "Madinat Zayed", "Ruwais")),
            State("Dubai", listOf("Dubai City", "Jebel Ali", "Hatta", "Dubai Marina", "Deira")),
            State("Sharjah", listOf("Sharjah City", "Khor Fakkan", "Kalba", "Dhaid")),
            State("Ajman", listOf("Ajman City", "Masfout", "Manama")),
            State("Ras Al Khaimah", listOf("Ras Al Khaimah City", "Al Jazirah Al Hamra")),
            State("Fujairah", listOf("Fujairah City", "Dibba", "Al Bidya")),
        )

    private val defaultStates =
        listOf(
            State("Region 1", listOf("City A", "City B", "City C")),
            State("Region 2", listOf("City D", "City E", "City F")),
            State("Region 3", listOf("City G", "City H", "City I")),
        )

    fun getStatesForCountry(countryCode: String): List<State> =
        when (countryCode) {
            "NG" -> nigeriaStates
            "GH" -> ghanaStates
            "KE" -> kenyaStates
            "ZA" -> southAfricaStates
            "US" -> usStates
            "GB" -> ukStates
            "CA" -> canadaStates
            "IN" -> indiaStates
            "AE" -> uaeStates
            else -> defaultStates
        }

    fun getStateNames(countryCode: String): List<String> = getStatesForCountry(countryCode).map { it.name }

    fun getCitiesForState(
        countryCode: String,
        stateName: String,
    ): List<String> =
        getStatesForCountry(countryCode)
            .find { it.name == stateName }
            ?.cities
            ?: emptyList()
}
