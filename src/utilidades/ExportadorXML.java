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

			Element cancionesContenedor = doc.createElement("CancionesTotales"); 
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

				if (art.getListaAlbumes() != null) {
					for (Album alb : art.getListaAlbumes()) {

						if (alb.getCanciones() != null) {
							for (Cancion can : alb.getCanciones()) {
								Element cancionNode = doc.createElement("Cancion");
								cancionNode.setAttribute("id", String.valueOf(can.getId()));
								cancionNode.setAttribute("albumId", String.valueOf(alb.getId()));
								cancionesContenedor.appendChild(cancionNode);

								Element nomCancion = doc.createElement("Nombre");
								nomCancion.setTextContent(can.getNombre());
								cancionNode.appendChild(nomCancion);

								Element generoNode = doc.createElement("Genero");
								generoNode.setTextContent(
										can.getGenero() != null ? can.getGenero().name() : "DESCONOCIDO");
								cancionNode.appendChild(generoNode);
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

			System.out.println("Archivo XML generado con éxito.");

		} catch (ParserConfigurationException | TransformerException e) {
			System.err.println("Error al generar el XML: " + e.getMessage());
		}

	}
}