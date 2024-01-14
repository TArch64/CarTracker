package storage.repository.model

class CarMileage(val value: Int) {
    companion object {
        const val INDEX_SIZE = 1000

        fun byIndex(index: Int) = CarMileage(index * INDEX_SIZE)
    }

    val index: Int get() = value.floorDiv(INDEX_SIZE)
    val humanReadable: String get() = "${value / INDEX_SIZE}k"
}
