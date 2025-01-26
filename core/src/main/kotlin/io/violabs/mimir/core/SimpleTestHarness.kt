package io.violabs.mimir.core

abstract class SimpleTestHarness {

  fun <T> test(testFn: TestUnit<T>.() -> Unit) {
    val testUnit = TestUnit<T>()

    testFn(testUnit)

    if (!testUnit.equalsChecked) testUnit.thenEquals()

    assert(testUnit.equalsChecked) { "Did not run test" }
  }

  fun <T> testArray(testFn: ArrayTestUnit<T>.() -> Unit) {
    val testUnit = ArrayTestUnit<T>()

    testFn(testUnit)

    if (!testUnit.equalsChecked) testUnit.thenEquals()

    assert(testUnit.equalsChecked) { "Did not run test" }
  }

  open class TestUnit<T> {
    var expected: T? = null
    var actual: T? = null
    var equalsChecked = false

    fun expect(expectedSupplier: () -> T) {
      expected = expectedSupplier()
    }

    fun whenever(supplierFn: () -> T?) {
      actual = supplierFn()
    }

    open fun thenEquals() {
      equalsChecked = true
      checkEquals(expected, actual)
    }

    fun <U> checkEquals(expected: U?, actual: U?, prefix: String = "") {
      assert(actual == expected) {
        "\n" +
        "${prefix}EXPECT: $expected\n" +
        "${prefix}ACTUAL: $actual"
      }
    }
  }

  class ArrayTestUnit<T> : TestUnit<Array<T>>() {
    override fun thenEquals() {
      val e: Array<T> = expected ?: return
      val a: Array<T> = actual ?: return

      equalsChecked = true

      e.zip(a).forEachIndexed { i, pair ->
        checkEquals(pair.first, pair.second, "[$i] ")
      }
    }
  }
}