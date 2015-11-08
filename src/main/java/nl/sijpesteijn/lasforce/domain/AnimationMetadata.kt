package nl.sijpesteijn.lasforce.domain

/**
 * @author Gijs Sijpesteijn
 */
data class AnimationMetadata(val id:Long, var name:String, var frameRate:Int, var lastUpdate:Long? = null)