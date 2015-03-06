package br.ufpi.ejbs;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.ufpi.entidades.Cliente;

@Stateless
public class ClienteEJB {

	@PersistenceContext
	private EntityManager em;

	public Cliente procurarClientePorEmailSenha(String email, String senha) {
		TypedQuery<Cliente> query = em
				.createQuery(
						"SELECT c FROM Cliente c WHERE c.email = :email AND c.senha = :senha",
						Cliente.class);
		query.setParameter("email", email);
		query.setParameter("senha", convertStringToMd5(senha));

		List<Cliente> clientes = query.getResultList();

		if (clientes.size() == 1)
			return clientes.get(0);

		return null;
	}

	private String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		try {

			// Instanciamos o nosso HASH MD5, poderíamos usar outro como
			// SHA, por exemplo, mas optamos por MD5.
			mDigest = MessageDigest.getInstance("MD5");

			// Convert a String valor para um array de bytes em MD5
			byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

			// Convertemos os bytes para hexadecimal, assim podemos salvar
			// no banco para posterior comparação se senhas
			StringBuffer sb = new StringBuffer();
			for (byte b : valorMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,
						3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
