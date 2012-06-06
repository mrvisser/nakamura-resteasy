/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.samples.doclet;

import org.sakaiproject.nakamura.samples.doclet.api.Album;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * This is an album service.
 */
@Path("/albums")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumService {

  /**
   * Get an album by its id.
   * 
   * @param id
   * @return
   */
  @GET @Path("/id/{id}")
  public Album findById(@PathParam("id") String id) {
    return null;
  }
  
  /**
   * Create an album from the given data. A new ID will be automatically assigned
   * and returned.
   * 
   * @param album
   * @return
   */
  @POST
  public Album create(Album album) {
    return null;
  }
  
  /**
   * Update the album at the given {@code id} with the data from the given
   * {@code album}.
   * 
   * @param id
   * @param album
   */
  @POST @PUT @Path("/id/{id}")
  public void update(@PathParam("id") String id, Album album) {
    return;
  }
  
  /**
   * Delete the album with the given {@code id}.
   * @param albumId
   * @return
   */
  @DELETE @Path("/id/{id}")
  public void delete(@PathParam("id") String albumId) {
    return;
  }
  
  /**
   * Add a song to the album.
   * 
   * @param albumId
   * @param songName
   */
  @POST @PUT @Path("/id/{albumId}/songs/{songName}")
  public void addSong(@PathParam("albumId") String albumId,
      @PathParam("songName") String songName) {
    return;
  }
  
  /**
   * Delete the song for the specified album.
   * 
   * @param albumId
   * @param songName
   */
  @DELETE @Path("/id/{albumId}/songs/{songName}")
  public void deleteSong(@PathParam("albumId") String albumId,
      @PathParam("songName") String songName) {
    return;
  }
  
  @POST @PUT @Path("/id/{albumId}/artists/{artistId}")
  public void addArtist(@PathParam("albumId") String albumId,
      @PathParam("artistId") String artistId) {
    return;
  }
  
  /**
   * Delete the song for the specified album.
   * 
   * @param albumId
   * @param songName
   */
  @DELETE @Path("/id/{albumId}/artists/{artistId}")
  public void deleteArtist(@PathParam("albumId") String albumId,
      @PathParam("artistId") String artistId) {
    return;
  }
  
  /**
   * Search for an album by its name.
   * 
   * @param name
   * @return
   */
  @GET
  public Iterator<Album> searchByName(@QueryParam("name") String name) {
    return null;
  }
}
