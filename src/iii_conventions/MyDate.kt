package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year - other.year != 0 -> year - other.year
            month - other.month != 0 -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate = this.addTimeIntervals(interval, 1)

operator fun MyDate.plus(repeated: RepeatedTimeInterval): MyDate = this.addTimeIntervals(repeated.interval, repeated.multiplier)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(multiplier: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, multiplier)

class RepeatedTimeInterval(val interval: TimeInterval, val multiplier: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val now = current
                current = current.nextDay()
                return now
            }
        }
    }

}

operator fun DateRange.contains(other: MyDate): Boolean {
    return start <= other && other <= endInclusive
}
