package nl.sijpesteijn.lasforce

import nl.sijpesteijn.ilda.CoordinateData
import nl.sijpesteijn.ilda.CoordinateHeader
import nl.sijpesteijn.ilda.Ilda
import nl.sijpesteijn.lasforce.domain.Frame
import nl.sijpesteijn.lasforce.domain.Point
import nl.sijpesteijn.lasforce.domain.Shape
import nl.sijpesteijn.lasforce.domain.StrokeColor
import java.util.*

/**
 * @author Gijs Sijpesteijn
 */
class IldaConverter {
    private val VIEWPORT_MIDDLE = 326

    fun fromIlda(ilda: Ilda): List<Frame> {
        val frames = ArrayList<Frame>()
        var frameIndex = 0
        for (coordinateHeader in ilda.getCoordinateHeaders()) {
            if (coordinateHeader.getTotalPoints() !== 0)
                frames.add(createFrame(frameIndex++, coordinateHeader))
        }
        return frames
    }

    private fun createFrame(id: Int, coordinateHeader: CoordinateHeader): Frame {
        val frame = Frame(id, coordinateHeader.getFrameName())
        frame.shapes = getShapes(coordinateHeader.getCoordinateData())
        return frame
    }

    private fun getShapes(coordinateDatas: List<CoordinateData>): List<Shape> {
        var selectedIndex = 0
        var wasBlanked = false
        val selected = ArrayList<ArrayList<CoordinateData>>()
        selected.add(ArrayList<CoordinateData>())
        for (coordinateData in coordinateDatas) {
            if (!coordinateData.isBlanked()) {
                val element = selected.get(selectedIndex)
                if (element.size > 0 && !coordinateData.equals(element.get(element.size - 1))) {
                    val sub = selected.get(selectedIndex)
                    sub.add(coordinateData)
                }
                wasBlanked = false
            } else if (!wasBlanked) {
                selectedIndex++
                selected.add(ArrayList<CoordinateData>())
            }
        }
        return createShapes(selected)
    }

    private fun createShapes(selected: List<List<CoordinateData>>): List<Shape> {
        val shapes = ArrayList<Shape>()
        var shapeId = 0
        for (subSelected in selected) {
            if (subSelected.size > 0) {
                if (subSelected.size == 1) {
                    val point = createPoint(subSelected.get(0), shapeId++)
                    val colorData = subSelected.get(0).getColorData()
                    val blue = getPercentage(colorData.getBlue1())
                    val green = getPercentage(colorData.getGreen1())
                    val red = getPercentage(colorData.getRed1())
                    val color = StrokeColor(blue, green, red)
                    point.strokeColor = color
                    point.strokeWidth = 4
                    shapes.add(point)
                } else {
                    val index = 0
                    val colorData = subSelected.get(0).getColorData()
                    val blue = getPercentage(colorData.getBlue1())
                    val green = getPercentage(colorData.getGreen1())
                    val red = getPercentage(colorData.getRed1())
                    val color = StrokeColor(blue, green, red)
                    // Path path = new Path(index,true,"Path_" + shapeId++,new ArrayList<Segment>(),color,4);
                    // shapes.add(path);
                    // for(CoordinateData point: subSelected) {
                    // Segment segment = new Segment(createPoint(point, index++));
                    // path.getSegments().add(segment);
                    // }
                }
            }
        }
        return shapes
    }

    private fun createPoint(coordinateData: CoordinateData, index: Int): Point {
        return Point(VIEWPORT_MIDDLE + coordinateData.x, VIEWPORT_MIDDLE + coordinateData.y, "Point_${index}")
    }

    private fun getPercentage(color: Int): Double {
        if (color == 0) {
            return 0.0
        }
        val percentage: Float
        percentage = (color.toFloat()) / 255f
        return percentage.toDouble()
    }

    fun toIlda(frames: List<Frame>): Ilda {
        return Ilda()
    }
}