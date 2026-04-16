package utilidades;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import modelo.Album;
import modelo.Artista;
import modelo.Cancion;

public class ExportadorXML implements InterfazExportador {

	public void exportarArtistas(List<Artista> listaArtistas, String rutaArchivo) {
	    try {
	        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructor = fabrica.newDocumentBuilder();
	        Document doc = constructor.newDocument();

	        Element root = doc.createElement("TartangaMusic");
	        doc.appendChild(root);

	        Element artistasContenedor = doc.createElement("Artistas");
	        root.appendChild(artistasContenedor);

	        Element cancionesContenedor = doc.createElement("Canciones");
	        root.appendChild(cancionesContenedor);

	        for (Artista art : listaArtistas) {
	            Element artistaNode = doc.createElement("Artista");
	            artistaNode.setAttribute("id", String.valueOf(art.getId()));
	            artistasContenedor.appendChild(artistaNode);
	            
	            Element nombreArt = doc.createElement("Nombre");
	            nombreArt.setTextContent(art.getNombre());
	            artistaNode.appendChild(nombreArt);

	            Element tipoArt = doc.createElement("Tipo");
	            tipoArt.setTextContent(art.getTipo() != null ? art.getTipo().name() : "DESCONOCIDO");
	            artistaNode.appendChild(tipoArt);
	            
	            Element imagenNode = doc.createElement("Imagen");
	            imagenNode.setTextContent(
	            	    art.getImagen() != null ? art.getImagen() : "img/default.jpg"
	            	);
	            artistaNode.appendChild(imagenNode);

	            Element albumesContenedor = doc.createElement("Albumes");
	            artistaNode.appendChild(albumesContenedor);

	            if (art.getListaAlbumes() != null) {
	                for (Album alb : art.getListaAlbumes()) {
	                    Element albumNode = doc.createElement("Album");
	                    albumNode.setAttribute("id", String.valueOf(alb.getId()));
	                    albumesContenedor.appendChild(albumNode);

	                    Element tituloAlb = doc.createElement("Titulo");
	                    tituloAlb.setTextContent(alb.getNombre());
	                    albumNode.appendChild(tituloAlb);

	                    if (alb.getCanciones() != null) {
	                        for (Cancion can : alb.getCanciones()) {
	                            Element cancionNode = doc.createElement("Cancion");
	                            cancionNode.setAttribute("id", String.valueOf(can.getId()));
	                            cancionesContenedor.appendChild(cancionNode);

	                            Element nomCan = doc.createElement("Nombre");
	                            nomCan.setTextContent(can.getNombre());
	                            cancionNode.appendChild(nomCan);

	                            Element generoNode = doc.createElement("Genero");
	                            generoNode.setTextContent(can.getGenero() != null ? can.getGenero().name() : "DESCONOCIDO");
	                            cancionNode.appendChild(generoNode);

	                            Element albumIdRef = doc.createElement("AlbumId");
	                            albumIdRef.setTextContent(String.valueOf(alb.getId()));
	                            cancionNode.appendChild(albumIdRef);
	                        }
	                    }
	                }
	            }
	        }
	        TransformerFactory instanciaTransformador = TransformerFactory.newInstance();
	        Transformer transformador = instanciaTransformador.newTransformer();
	        transformador.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformador.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	        DOMSource fuente = new DOMSource(doc);
	        StreamResult resultado = new StreamResult(new File(rutaArchivo));
	        transformador.transform(fuente, resultado);

	        System.out.println("XML generado con éxito en: " + rutaArchivo);

	    } catch (ParserConfigurationException | TransformerException e) {
	        System.err.println("Error al generar el XML: " + e.getMessage());
	    }
	}
}