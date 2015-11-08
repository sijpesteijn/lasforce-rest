package nl.sijpesteijn.lasforce.domain

/**
 * @author Gijs Sijpesteijn
 */
interface Shape {}

data class Point(var x: Int, var y: Int, var name: String, var strokeWidth: Int? = null, var strokeColor: StrokeColor? = null) : Shape

data class StrokeColor(var blue:Double,var green:Double,var red:Double)