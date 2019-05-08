package external.lastfm

import external.Artist
import external.LastFMSong
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class ResponseToArtistResolverImp : ResponseToArtistResolver {

    override fun getArtistFromExternalData (serviceData: String, song: LastFMSong): Artist {

        lateinit var artist: Artist
        lateinit var description: String
        lateinit var picturePath: String

        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val doc = db.parse (InputSource (StringReader (serviceData)))

        val nodes = doc.documentElement.getElementsByTagName("content")

        val extract = nodes.item(0)

        val images = doc.documentElement.getElementsByTagName("image")
        for (i in 0 until images.length) {
            val image = images.item(i)
            val atts = image.attributes
            val size = atts.getNamedItem("size")

            if (size.textContent == "large") {
                picturePath = image.textContent
                break
            }
        }

        when (extract) {
            null -> description = "No Results"
            else -> description = extract.textContent.replace("\\n", "\n")
        }
        artist = Artist(song.artistName, description, picturePath)
        return artist
    }
}