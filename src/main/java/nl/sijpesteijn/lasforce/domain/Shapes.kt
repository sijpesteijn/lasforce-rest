package nl.sijpesteijn.lasforce.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * @author Gijs Sijpesteijn
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes(
        JsonSubTypes.Type(value = Path::class, name = "path"),
        JsonSubTypes.Type(value = Segment::class, name = "segment"),
        JsonSubTypes.Type(value = Point::class, name = "point"))
interface Shape {}

data class StrokeColor(var blue:Double,var green:Double,var red:Double)

data class Point(var x: Int, var y: Int, var name: String, var strokeWidth: Int? = null, var strokeColor: StrokeColor? = null) : Shape {
    val type = "point"
}

data class Segment(var point:Point):Shape {
    val type = "segment"
}

data class Path(var id: Int, var isApplyMatrix: Boolean, var strokeWidth: Int, var strokeColor: StrokeColor, var segments: List<Segment>, var isClosed: Boolean, var name: String) : Shape {
    val type = "path"
}