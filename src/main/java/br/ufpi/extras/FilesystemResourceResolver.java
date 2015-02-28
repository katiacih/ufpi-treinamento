package br.ufpi.extras;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.view.facelets.ResourceResolver;

@SuppressWarnings("deprecation")
public class FilesystemResourceResolver extends ResourceResolver {

	private static final String PATH_TO_FACELETS_FILES_GOES_HERE = "/home/unknow/workspace-ufpi/carrinho-compras/src/main/webapp/";

	@Override
	public URL resolveUrl(String path) {
		try {
			return new URL("file", "", PATH_TO_FACELETS_FILES_GOES_HERE + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}