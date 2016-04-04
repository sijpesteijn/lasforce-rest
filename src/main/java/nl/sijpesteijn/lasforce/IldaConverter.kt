package nl.sijpesteijn.lasforce

import nl.sijpesteijn.ilda.CoordinateData
import nl.sijpesteijn.ilda.CoordinateHeader
import nl.sijpesteijn.ilda.Ilda
import nl.sijpesteijn.lasforce.domain.Frame
import nl.sijpesteijn.lasforce.domain.Segment
import java.util.*

/**
 * @author Gijs Sijpesteijn
 */
class IldaConverter {

    fun fromIlda(ilda: Ilda): List<Frame> {
        var frameIndex = 0L
        val frames = ilda.coordinateHeaders.filter { it.totalPoints > 0 }.map { createFrame(frameIndex++, it) }
        return frames
    }

    private fun createFrame(id: Long, coordinateHeader: CoordinateHeader): Frame {
        val segments = getSegments(coordinateHeader.getCoordinateData())
        var frame = Frame(id, coordinateHeader.frameName, 1, segments.size, segments)
        return frame
    }

    private fun getSegments(coordinates: List<CoordinateData>): List<Segment> {
        val segments = ArrayList<Segment>()
        var segment: Segment? = null
        var points = ArrayList<List<Int>>()
        for(coordinate in coordinates) {
            if (coordinate.isBlanked) {
                if (segment != null) {
                    segments.add(segment)
                }
                segment = null
            } else {
                if (segment == null) {
                    val color = ArrayList<Int>()
                    color.add(coordinate.colorData.red1)
                    color.add(coordinate.colorData.green1)
                    color.add(coordinate.colorData.blue1)
                    points = ArrayList<List<Int>>()
                    segment = Segment(color, points)
                }
                val point = ArrayList<Int>()
                point.add(coordinate.x)
                point.add(coordinate.y)
                points.add(point);
            }
        }
        return segments
    }

    fun toIlda(paperFrames: List<Frame>): Ilda {
        return Ilda()
    }
}