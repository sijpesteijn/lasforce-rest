package nl.sijpesteijn.lasforce

import nl.sijpesteijn.ilda.CoordinateData
import nl.sijpesteijn.ilda.CoordinateHeader
import nl.sijpesteijn.ilda.Ilda
import nl.sijpesteijn.lasforce.domain.*
import java.util.*

/**
 * @author Gijs Sijpesteijn
 */
class IldaConverter {
    private val VIEWPORT_MIDDLE = 326

    fun fromIlda(ilda: Ilda): List<Frame> {
        var frameIndex = 0
        val frames = ilda.coordinateHeaders.filter { it.totalPoints > 0 }.map { createFrame(frameIndex++, it) }
        return frames
    }

    private fun createFrame(id: Int, coordinateHeader: CoordinateHeader): Frame {
        val frame = Frame(id, coordinateHeader.getFrameName())
        frame.shapes = getShapes(coordinateHeader.getCoordinateData())
        return frame
    }

    private fun getShapes(coordinateDatas: List<CoordinateData>): List<Shape> {
        val selected = ArrayList<ArrayList<CoordinateData>>()
        var subPaths = ArrayList<CoordinateData>()
        selected.add(subPaths)
        for (coordinateData in coordinateDatas) {
            if (!coordinateData.isBlanked()) {
                if (subPaths.size == 0 || !coordinateData.equals(subPaths.get(subPaths.size - 1))) {
                    subPaths.add(coordinateData)
                }
            } else  {
                subPaths = ArrayList<CoordinateData>()
                selected.add(subPaths)
            }
        }
        var shapeId = 0
        return selected.filter { it.size > 0 }.map { createShape(it, shapeId++) }
    }

    private fun createShape(coordinateDatas: List<CoordinateData>, id: Int): Shape {
        val colorData = coordinateDatas.get(0).getColorData()
        val blue = getPercentage(colorData.getBlue1())
        val green = getPercentage(colorData.getGreen1())
        val red = getPercentage(colorData.getRed1())
        val color = StrokeColor(blue, green, red)
        if (coordinateDatas.size == 1) {
            val point = Point(VIEWPORT_MIDDLE + coordinateDatas.get(0).x, VIEWPORT_MIDDLE + coordinateDatas.get(0).y, "Point_${id}")
            point.strokeColor = color
            point.strokeWidth = 4
            return point
        } else {
            var index = 0
            val segments = coordinateDatas.map { segment -> Segment(Point(VIEWPORT_MIDDLE + segment.x, VIEWPORT_MIDDLE + segment.y, "Point_${index++}")) }
            val path = Path(index,true,4,color,segments,false,"Path_${id}")
            return path
        }
    }

    private fun getPercentage(color: Int): Double {
        when(color) {
            0 -> return 0.0;
            else -> return ((color.toFloat()) / 255f).toDouble()
        }
    }

    fun toIlda(frames: List<Frame>): Ilda {
        return Ilda()
    }
}