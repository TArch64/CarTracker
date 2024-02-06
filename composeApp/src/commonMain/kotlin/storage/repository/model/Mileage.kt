package storage.repository.model

class Mileage(val value: Int) {
    companion object {
        const val INDEX_SIZE = 1000
        fun byIndex(index: Int) = Mileage(index * INDEX_SIZE)
    }

    val index: Int get() = value.floorDiv(INDEX_SIZE)
    val humanReadable: String get() = "${value / INDEX_SIZE}k"
    val next get() = byIndex(index + 1)
}
