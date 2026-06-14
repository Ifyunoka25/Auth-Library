package university.miva.designsystem.util.countries

data class Country(
    val name: String,
    val code: String, // ISO 3166-1 alpha-2 code
    val phoneCode: String, // Phone dial code
    val flag: String, // Emoji flag
    val minPhoneLength: Int = 7, // Minimum phone number length (without country code)
    val maxPhoneLength: Int = 15, // Maximum phone number length (without country code)
)

object Countries {
    val all: List<Country> =
        listOf(
            Country("Afghanistan", "AF", "+93", "🇦🇫", 9, 9),
            Country("Albania", "AL", "+355", "🇦🇱", 9, 9),
            Country("Algeria", "DZ", "+213", "🇩🇿", 9, 9),
            Country("Andorra", "AD", "+376", "🇦🇩", 6, 9),
            Country("Angola", "AO", "+244", "🇦🇴", 9, 9),
            Country("Antigua and Barbuda", "AG", "+1-268", "🇦🇬", 7, 7),
            Country("Argentina", "AR", "+54", "🇦🇷", 10, 11),
            Country("Armenia", "AM", "+374", "🇦🇲", 8, 8),
            Country("Australia", "AU", "+61", "🇦🇺", 9, 9),
            Country("Austria", "AT", "+43", "🇦🇹", 10, 11),
            Country("Azerbaijan", "AZ", "+994", "🇦🇿", 9, 9),
            Country("Bahamas", "BS", "+1-242", "🇧🇸", 7, 7),
            Country("Bahrain", "BH", "+973", "🇧🇭", 8, 8),
            Country("Bangladesh", "BD", "+880", "🇧🇩", 10, 10),
            Country("Barbados", "BB", "+1-246", "🇧🇧", 7, 7),
            Country("Belarus", "BY", "+375", "🇧🇾", 9, 9),
            Country("Belgium", "BE", "+32", "🇧🇪", 9, 10),
            Country("Belize", "BZ", "+501", "🇧🇿", 7, 7),
            Country("Benin", "BJ", "+229", "🇧🇯", 8, 8),
            Country("Bhutan", "BT", "+975", "🇧🇹", 8, 8),
            Country("Bolivia", "BO", "+591", "🇧🇴", 8, 8),
            Country("Bosnia and Herzegovina", "BA", "+387", "🇧🇦", 8, 9),
            Country("Botswana", "BW", "+267", "🇧🇼", 7, 8),
            Country("Brazil", "BR", "+55", "🇧🇷", 10, 11),
            Country("Brunei", "BN", "+673", "🇧🇳", 7, 7),
            Country("Bulgaria", "BG", "+359", "🇧🇬", 9, 9),
            Country("Burkina Faso", "BF", "+226", "🇧🇫", 8, 8),
            Country("Burundi", "BI", "+257", "🇧🇮", 8, 8),
            Country("Cambodia", "KH", "+855", "🇰🇭", 8, 9),
            Country("Cameroon", "CM", "+237", "🇨🇲", 9, 9),
            Country("Canada", "CA", "+1", "🇨🇦", 10, 10),
            Country("Cape Verde", "CV", "+238", "🇨🇻", 7, 7),
            Country("Central African Republic", "CF", "+236", "🇨🇫", 8, 8),
            Country("Chad", "TD", "+235", "🇹🇩", 8, 8),
            Country("Chile", "CL", "+56", "🇨🇱", 9, 9),
            Country("China", "CN", "+86", "🇨🇳", 11, 11),
            Country("Colombia", "CO", "+57", "🇨🇴", 10, 10),
            Country("Comoros", "KM", "+269", "🇰🇲", 7, 7),
            Country("Congo (DRC)", "CD", "+243", "🇨🇩", 9, 9),
            Country("Congo (Republic)", "CG", "+242", "🇨🇬", 9, 9),
            Country("Costa Rica", "CR", "+506", "🇨🇷", 8, 8),
            Country("Croatia", "HR", "+385", "🇭🇷", 9, 10),
            Country("Cuba", "CU", "+53", "🇨🇺", 8, 8),
            Country("Cyprus", "CY", "+357", "🇨🇾", 8, 8),
            Country("Czech Republic", "CZ", "+420", "🇨🇿", 9, 9),
            Country("Denmark", "DK", "+45", "🇩🇰", 8, 8),
            Country("Djibouti", "DJ", "+253", "🇩🇯", 8, 8),
            Country("Dominica", "DM", "+1-767", "🇩🇲", 7, 7),
            Country("Dominican Republic", "DO", "+1-809", "🇩🇴", 10, 10),
            Country("Ecuador", "EC", "+593", "🇪🇨", 9, 9),
            Country("Egypt", "EG", "+20", "🇪🇬", 10, 11),
            Country("El Salvador", "SV", "+503", "🇸🇻", 8, 8),
            Country("Equatorial Guinea", "GQ", "+240", "🇬🇶", 9, 9),
            Country("Eritrea", "ER", "+291", "🇪🇷", 7, 7),
            Country("Estonia", "EE", "+372", "🇪🇪", 7, 8),
            Country("Eswatini", "SZ", "+268", "🇸🇿", 8, 8),
            Country("Ethiopia", "ET", "+251", "🇪🇹", 9, 9),
            Country("Fiji", "FJ", "+679", "🇫🇯", 7, 7),
            Country("Finland", "FI", "+358", "🇫🇮", 9, 10),
            Country("France", "FR", "+33", "🇫🇷", 9, 9),
            Country("Gabon", "GA", "+241", "🇬🇦", 7, 8),
            Country("Gambia", "GM", "+220", "🇬🇲", 7, 7),
            Country("Georgia", "GE", "+995", "🇬🇪", 9, 9),
            Country("Germany", "DE", "+49", "🇩🇪", 10, 11),
            Country("Ghana", "GH", "+233", "🇬🇭", 9, 10),
            Country("Greece", "GR", "+30", "🇬🇷", 10, 10),
            Country("Grenada", "GD", "+1-473", "🇬🇩", 7, 7),
            Country("Guatemala", "GT", "+502", "🇬🇹", 8, 8),
            Country("Guinea", "GN", "+224", "🇬🇳", 9, 9),
            Country("Guinea-Bissau", "GW", "+245", "🇬🇼", 7, 7),
            Country("Guyana", "GY", "+592", "🇬🇾", 7, 7),
            Country("Haiti", "HT", "+509", "🇭🇹", 8, 8),
            Country("Honduras", "HN", "+504", "🇭🇳", 8, 8),
            Country("Hungary", "HU", "+36", "🇭🇺", 9, 9),
            Country("Iceland", "IS", "+354", "🇮🇸", 7, 7),
            Country("India", "IN", "+91", "🇮🇳", 10, 10),
            Country("Indonesia", "ID", "+62", "🇮🇩", 9, 12),
            Country("Iran", "IR", "+98", "🇮🇷", 10, 10),
            Country("Iraq", "IQ", "+964", "🇮🇶", 10, 10),
            Country("Ireland", "IE", "+353", "🇮🇪", 9, 9),
            Country("Israel", "IL", "+972", "🇮🇱", 9, 10),
            Country("Italy", "IT", "+39", "🇮🇹", 9, 11),
            Country("Ivory Coast", "CI", "+225", "🇨🇮", 10, 10),
            Country("Jamaica", "JM", "+1-876", "🇯🇲", 7, 7),
            Country("Japan", "JP", "+81", "🇯🇵", 10, 11),
            Country("Jordan", "JO", "+962", "🇯🇴", 9, 9),
            Country("Kazakhstan", "KZ", "+7", "🇰🇿", 10, 10),
            Country("Kenya", "KE", "+254", "🇰🇪", 9, 10),
            Country("Kiribati", "KI", "+686", "🇰🇮", 5, 5),
            Country("Kosovo", "XK", "+383", "🇽🇰", 8, 9),
            Country("Kuwait", "KW", "+965", "🇰🇼", 8, 8),
            Country("Kyrgyzstan", "KG", "+996", "🇰🇬", 9, 9),
            Country("Laos", "LA", "+856", "🇱🇦", 9, 10),
            Country("Latvia", "LV", "+371", "🇱🇻", 8, 8),
            Country("Lebanon", "LB", "+961", "🇱🇧", 7, 8),
            Country("Lesotho", "LS", "+266", "🇱🇸", 8, 8),
            Country("Liberia", "LR", "+231", "🇱🇷", 7, 9),
            Country("Libya", "LY", "+218", "🇱🇾", 9, 10),
            Country("Liechtenstein", "LI", "+423", "🇱🇮", 7, 9),
            Country("Lithuania", "LT", "+370", "🇱🇹", 8, 8),
            Country("Luxembourg", "LU", "+352", "🇱🇺", 9, 9),
            Country("Madagascar", "MG", "+261", "🇲🇬", 9, 10),
            Country("Malawi", "MW", "+265", "🇲🇼", 8, 9),
            Country("Malaysia", "MY", "+60", "🇲🇾", 9, 10),
            Country("Maldives", "MV", "+960", "🇲🇻", 7, 7),
            Country("Mali", "ML", "+223", "🇲🇱", 8, 8),
            Country("Malta", "MT", "+356", "🇲🇹", 8, 8),
            Country("Marshall Islands", "MH", "+692", "🇲🇭", 7, 7),
            Country("Mauritania", "MR", "+222", "🇲🇷", 8, 8),
            Country("Mauritius", "MU", "+230", "🇲🇺", 7, 8),
            Country("Mexico", "MX", "+52", "🇲🇽", 10, 10),
            Country("Micronesia", "FM", "+691", "🇫🇲", 7, 7),
            Country("Moldova", "MD", "+373", "🇲🇩", 8, 8),
            Country("Monaco", "MC", "+377", "🇲🇨", 8, 9),
            Country("Mongolia", "MN", "+976", "🇲🇳", 8, 8),
            Country("Montenegro", "ME", "+382", "🇲🇪", 8, 8),
            Country("Morocco", "MA", "+212", "🇲🇦", 9, 9),
            Country("Mozambique", "MZ", "+258", "🇲🇿", 9, 9),
            Country("Myanmar", "MM", "+95", "🇲🇲", 8, 10),
            Country("Namibia", "NA", "+264", "🇳🇦", 9, 9),
            Country("Nauru", "NR", "+674", "🇳🇷", 7, 7),
            Country("Nepal", "NP", "+977", "🇳🇵", 10, 10),
            Country("Netherlands", "NL", "+31", "🇳🇱", 9, 9),
            Country("New Zealand", "NZ", "+64", "🇳🇿", 8, 10),
            Country("Nicaragua", "NI", "+505", "🇳🇮", 8, 8),
            Country("Niger", "NE", "+227", "🇳🇪", 8, 8),
            Country("Nigeria", "NG", "+234", "🇳🇬", 10, 11),
            Country("North Korea", "KP", "+850", "🇰🇵", 10, 10),
            Country("North Macedonia", "MK", "+389", "🇲🇰", 8, 8),
            Country("Norway", "NO", "+47", "🇳🇴", 8, 8),
            Country("Oman", "OM", "+968", "🇴🇲", 8, 8),
            Country("Pakistan", "PK", "+92", "🇵🇰", 10, 10),
            Country("Palau", "PW", "+680", "🇵🇼", 7, 7),
            Country("Palestine", "PS", "+970", "🇵🇸", 9, 9),
            Country("Panama", "PA", "+507", "🇵🇦", 8, 8),
            Country("Papua New Guinea", "PG", "+675", "🇵🇬", 8, 8),
            Country("Paraguay", "PY", "+595", "🇵🇾", 9, 9),
            Country("Peru", "PE", "+51", "🇵🇪", 9, 9),
            Country("Philippines", "PH", "+63", "🇵🇭", 10, 10),
            Country("Poland", "PL", "+48", "🇵🇱", 9, 9),
            Country("Portugal", "PT", "+351", "🇵🇹", 9, 9),
            Country("Qatar", "QA", "+974", "🇶🇦", 8, 8),
            Country("Romania", "RO", "+40", "🇷🇴", 9, 10),
            Country("Russia", "RU", "+7", "🇷🇺", 10, 10),
            Country("Rwanda", "RW", "+250", "🇷🇼", 9, 9),
            Country("Saint Kitts and Nevis", "KN", "+1-869", "🇰🇳", 7, 7),
            Country("Saint Lucia", "LC", "+1-758", "🇱🇨", 7, 7),
            Country("Saint Vincent and the Grenadines", "VC", "+1-784", "🇻🇨", 7, 7),
            Country("Samoa", "WS", "+685", "🇼🇸", 7, 7),
            Country("San Marino", "SM", "+378", "🇸🇲", 8, 10),
            Country("Sao Tome and Principe", "ST", "+239", "🇸🇹", 7, 7),
            Country("Saudi Arabia", "SA", "+966", "🇸🇦", 9, 9),
            Country("Senegal", "SN", "+221", "🇸🇳", 9, 9),
            Country("Serbia", "RS", "+381", "🇷🇸", 8, 9),
            Country("Seychelles", "SC", "+248", "🇸🇨", 7, 7),
            Country("Sierra Leone", "SL", "+232", "🇸🇱", 8, 8),
            Country("Singapore", "SG", "+65", "🇸🇬", 8, 8),
            Country("Slovakia", "SK", "+421", "🇸🇰", 9, 9),
            Country("Slovenia", "SI", "+386", "🇸🇮", 8, 8),
            Country("Solomon Islands", "SB", "+677", "🇸🇧", 7, 7),
            Country("Somalia", "SO", "+252", "🇸🇴", 8, 9),
            Country("South Africa", "ZA", "+27", "🇿🇦", 9, 9),
            Country("South Korea", "KR", "+82", "🇰🇷", 9, 10),
            Country("South Sudan", "SS", "+211", "🇸🇸", 9, 9),
            Country("Spain", "ES", "+34", "🇪🇸", 9, 9),
            Country("Sri Lanka", "LK", "+94", "🇱🇰", 9, 9),
            Country("Sudan", "SD", "+249", "🇸🇩", 9, 9),
            Country("Suriname", "SR", "+597", "🇸🇷", 7, 7),
            Country("Sweden", "SE", "+46", "🇸🇪", 9, 10),
            Country("Switzerland", "CH", "+41", "🇨🇭", 9, 9),
            Country("Syria", "SY", "+963", "🇸🇾", 9, 9),
            Country("Taiwan", "TW", "+886", "🇹🇼", 9, 10),
            Country("Tajikistan", "TJ", "+992", "🇹🇯", 9, 9),
            Country("Tanzania", "TZ", "+255", "🇹🇿", 9, 9),
            Country("Thailand", "TH", "+66", "🇹🇭", 9, 9),
            Country("Timor-Leste", "TL", "+670", "🇹🇱", 7, 8),
            Country("Togo", "TG", "+228", "🇹🇬", 8, 8),
            Country("Tonga", "TO", "+676", "🇹🇴", 7, 7),
            Country("Trinidad and Tobago", "TT", "+1-868", "🇹🇹", 7, 7),
            Country("Tunisia", "TN", "+216", "🇹🇳", 8, 8),
            Country("Turkey", "TR", "+90", "🇹🇷", 10, 10),
            Country("Turkmenistan", "TM", "+993", "🇹🇲", 8, 8),
            Country("Tuvalu", "TV", "+688", "🇹🇻", 5, 6),
            Country("Uganda", "UG", "+256", "🇺🇬", 9, 9),
            Country("Ukraine", "UA", "+380", "🇺🇦", 9, 9),
            Country("United Arab Emirates", "AE", "+971", "🇦🇪", 9, 9),
            Country("United Kingdom", "GB", "+44", "🇬🇧", 10, 11),
            Country("United States", "US", "+1", "🇺🇸", 10, 10),
            Country("Uruguay", "UY", "+598", "🇺🇾", 8, 9),
            Country("Uzbekistan", "UZ", "+998", "🇺🇿", 9, 9),
            Country("Vanuatu", "VU", "+678", "🇻🇺", 7, 7),
            Country("Vatican City", "VA", "+379", "🇻🇦", 6, 10),
            Country("Venezuela", "VE", "+58", "🇻🇪", 10, 10),
            Country("Vietnam", "VN", "+84", "🇻🇳", 9, 10),
            Country("Yemen", "YE", "+967", "🇾🇪", 9, 9),
            Country("Zambia", "ZM", "+260", "🇿🇲", 9, 9),
            Country("Zimbabwe", "ZW", "+263", "🇿🇼", 9, 9),
        )

    // Get country by code
    fun getByCode(code: String): Country? = all.find { it.code == code }

    // Get country by phone code
    fun getByPhoneCode(phoneCode: String): Country? = all.find { it.phoneCode == phoneCode }

    // Get country by name
    fun getByName(name: String): Country? = all.find { it.name.equals(name, ignoreCase = true) }

    // Search countries by name
    fun search(query: String): List<Country> =
        all.filter {
            it.name.contains(query, ignoreCase = true)
            it.code.contains(query, ignoreCase = true)
        }

    // Get Nigeria as default (for your app)
    val nigeria: Country = all.find { it.code == "NG" }!!

    val popularCountries: List<Country> =
        listOf(
            getByCode("NG")!!, // Nigeria
            getByCode("GB")!!, // United Kingdom
            getByCode("US")!!, // United States
            getByCode("SA")!!, // Saudi Arabia
            getByCode("CA")!!, // Canada
            getByCode("DE")!!, // Germany
            getByCode("GH")!!, // Ghana
            getByCode("IT")!!, // Italy
            getByCode("AE")!!, // UAE
        )

    // Phone validation functions
    fun isValidPhoneNumber(
        phoneNumber: String,
        country: Country,
    ): Boolean {
        val digitsOnly = phoneNumber.filter { it.isDigit() }
        return digitsOnly.length in country.minPhoneLength..country.maxPhoneLength
    }

    fun getPhoneValidationError(
        phoneNumber: String,
        country: Country,
    ): PhoneValidationError? {
        val digitsOnly = phoneNumber.filter { it.isDigit() }
        return when {
            digitsOnly.isEmpty() -> null // Empty is handled separately as required field
            digitsOnly.length < country.minPhoneLength -> PhoneValidationError.TooShort(country.minPhoneLength)
            digitsOnly.length > country.maxPhoneLength -> PhoneValidationError.TooLong(country.maxPhoneLength)
            else -> null
        }
    }
}

sealed class PhoneValidationError {
    data class TooShort(
        val minLength: Int,
    ) : PhoneValidationError()

    data class TooLong(
        val maxLength: Int,
    ) : PhoneValidationError()
}
