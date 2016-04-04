package nl.sijpesteijn.lasforce.domain

import java.util.*

/**
 * @author Gijs Sijpesteijn
 */
data class Segment(var color:List<Int>, var coordinates:List<List<Int>>)
data class Frame(var id:Long, var name: String, var repeat:Int, var total_segments:Int, var segments:List<Segment>)
data class Animation(var id:Long, var name: String, var last_update: Long, var repeat:Int, var total_frames:Int = 0, var frame_time: Int = 0, var frames: List<Frame> = ArrayList<Frame>())
data class AnimationInfo(var id:Long, var repeat:Int)