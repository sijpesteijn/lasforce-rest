package nl.sijpesteijn.lasforce.domain


/**
 * @author Gijs Sijpesteijn
 */
data class Frame(val id:Int, var name:String, var shapes:List<Shape>? = null)