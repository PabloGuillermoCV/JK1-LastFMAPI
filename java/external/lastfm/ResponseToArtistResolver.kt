package external.lastfm

import external.Artist
import external.LastFMSong

interface ResponseToArtistResolver {

    fun getArtistFromExternalData (serviceData: String, song: LastFMSong): Artist

}