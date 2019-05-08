package external.lastfm

import external.Artist
import external.ExternalService
import external.LastFMSong
import retrofit2.Response

class ExternalServiceImp (private val lastFMAPI: LastFMAPI, private val responseToArtistResolver: ResponseToArtistResolver): ExternalService {

    override fun getArtist (song: LastFMSong): Artist {
        val callResponse = getArtistFromService (song)
        return responseToArtistResolver.getArtistFromExternalData (callResponse.body () as String, song)
    }

    private fun getArtistFromService (song: LastFMSong): Response<String> {
        return lastFMAPI.getArtistInfo (song.artistName).execute ()
    }
}