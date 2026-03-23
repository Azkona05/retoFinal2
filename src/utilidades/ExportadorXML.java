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

import modelo.Artista;

public class ExportadorXML implements InterfazExportador {

	public void exportarArtistas(List<Artista> listaArtistas, String rutaArchivo) {
		try {
			// 1. EL ARQUITECTO: Configuramos la fábrica y el constructor
			DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructor = fabrica.newDocumentBuilder();

			// 2. EL LIENZO: Creamos el documento vacío
			Document doc = constructor.newDocument();

			// 3. LA RAÍZ: Creamos el nodo principal <TartangaMusic>
			Element root = doc.createElement("TartangaMusic");
			doc.appendChild(root);

			// 4. EL BUCLE: Recorremos los objetos que vinieron del DAO
			for (Artista art : listaArtistas) {
				// Nodo <Artista>
				Element artistaNode = doc.createElement("Artista");
				artistaNode.setAttribute("id", String.valueOf(art.getId())); // Atributo ID
				root.appendChild(artistaNode);

				// Nodo <Nombre>
				Element nombre = doc.createElement("Nombre");
				nombre.setTextContent(art.getNombre());
				artistaNode.appendChild(nombre);

				// Nodo <Tipo> (Solista o Grupo)
				Element tipoNode = doc.createElement("Tipo");
				if (art.getTipo() != null) {
					tipoNode.setTextContent(art.getTipo().name());
				} else {
					tipoNode.setTextContent("DESCONOCIDO");
				}

				artistaNode.appendChild(tipoNode);

				// --- Ejemplo de anidación (Si el artista tiene álbumes) ---
				/*
				 * if (art.getListaAlbumes() != null) { Element albumesNode =
				 * doc.createElement("Albumes"); for (Album alb : art.getListaAlbumes()) {
				 * Element albumNode = doc.createElement("Album");
				 * albumNode.setTextContent(alb.getNombre());
				 * albumesNode.appendChild(albumNode); } artistaNode.appendChild(albumesNode); }
				 */
			}

			// 5. EL TRANSFORMADOR: De la memoria RAM al archivo físico (.xml)
			TransformerFactory instanciaTransformador = TransformerFactory.newInstance();
			Transformer transformador = instanciaTransformador.newTransformer();

			// Configuración para que el XML sea legible (con saltos de línea y sangría)
			transformador.setOutputProperty(OutputKeys.INDENT, "yes");
			transformador.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource fuente = new DOMSource(doc);
			StreamResult resultado = new StreamResult(new File(rutaArchivo));

			transformador.transform(fuente, resultado);

			System.out.println("Archivo XML generado con éxito en: " + rutaArchivo);

		} catch (ParserConfigurationException | TransformerException e) {
			System.err.println("Error al generar el XML: " + e.getMessage());
			e.printStackTrace();
		}
	}
}