package org.sakaiproject.nakamura.samples.doclet;

import org.sakaiproject.nakamura.samples.doclet.api.Artist;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/artists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtistService {

  /**
   * Get an artist by its id.
   * 
   * @param id
   * @return
   */
  @GET @Path("/id/{id}")
  public Artist findById(@PathParam("id") String id) {
    return null;
  }
  
  /**
   * Create an artist from the given data. A new ID will be automatically assigned
   * and returned.
   * 
   * @param artist
   * @return
   */
  @POST
  public Artist create(Artist artist) {
    return null;
  }
  
  /**
   * Update the artist at the given {@code id} with the data from the given
   * {@code artist}.
   * 
   * @param id
   * @param artist
   */
  @POST @PUT @Path("/id/{id}")
  public void update(@PathParam("id") String id, Artist artist) {
    return;
  }
  
  /**
   * Delete the artist with the given {@code id}.
   * @param artistId
   * @return
   */
  @DELETE @Path("/id/{id}")
  public void delete(@PathParam("id") String artistId) {
    return;
  }
  
}
