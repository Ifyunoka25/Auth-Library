package university.miva.designsystem.components.dialog.model

enum class ReminderInterval(
    val title: String,
    val id: Int,
) {
    FIVE_MINUTES_BEFORE("5 minutes before", 5),
    FIFTEEN_MINUTES_BEFORE("15 minutes before", 15),
    THIRTY_MINUTES_BEFORE_DEFAULT("30 minutes before (Default)", 30),
    THIRTY_MINUTES_BEFORE("30 minutes before", 30),
    ONE_HOUR_BEFORE("1 hour before", 60),
    TWO_HOURS_BEFORE("2 hour before", 120),
    ONE_DAY_BEFORE("1 day before", 1),
    TWO_DAYS_BEFORE("2 days before", 1),
    ONE_WEEK_BEFORE("1 week before", 1),
    NONE("", 0),
    ;

    companion object {
        fun fromId(id: Int): ReminderInterval = entries.firstOrNull { it.id == id } ?: NONE
    }
}
