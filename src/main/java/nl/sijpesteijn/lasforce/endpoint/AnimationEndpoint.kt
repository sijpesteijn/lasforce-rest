package nl.sijpesteijn.lasforce.rest

import com.sun.jersey.multipart.FormDataMultiPart
import nl.sijpesteijn.lasforce.domain.Animation
import nl.sijpesteijn.lasforce.exception.LaserException
import nl.sijpesteijn.lasforce.services.AnimationService
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author: Gijs Sijpesteijn
 */
@Singleton
@Path("animations")
@Consumes(MediaType.APPLICATION_JSON)
class AnimationEndpoint @Inject constructor(animationService: AnimationService) {
    val animationService = animationService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun get(): List<Animation> {
        return animationService.getAnimationsMetadata()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun loadAnimation(@PathParam("id") id: Long): Animation {
        return animationService.getLasForceAnimation(id)
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun deleteAnimation(@PathParam("id") id: Long):Response {
        animationService.deleteAnimation(id)
        return Response.ok().build()
    }

    @GET
    @Path("/{id}/data")
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun loadLasForceAnimation(@PathParam("id") id: Long): Animation {
        return animationService.getLasForceAnimation(id)
    }

    @POST
    @Path("/upload/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(LaserException::class)
    fun uploadIlda(formParams: FormDataMultiPart): Response {
        val fieldsByName = formParams.getFields()
        // Usually each value in fieldsByName will be a list of length 1.
        // Assuming each field in the form is a file, just loop through them.
        for (fields in fieldsByName.values) {
            for (field in fields) {
                val `is` = field.getEntityAs(InputStream::class.java)
                try {
                    val data = IOUtils.toByteArray(`is`)
                    animationService.uploadAnimation(field.getContentDisposition().getFileName(), data)
                } catch (e: IOException) {
                    throw LaserException(e)
                }
            }
        }
        return Response.ok().build()
    }
}