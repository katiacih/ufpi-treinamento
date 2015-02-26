package br.ufpi.entity.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.PersistenceUtil;
import javax.persistence.Query;

public class JpaTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hibernate-jpa-mysql");
		EntityManager em = emf.createEntityManager();

		PersistenceUtil pu = Persistence.getPersistenceUtil();
		PersistenceUnitUtil puu = emf.getPersistenceUnitUtil();

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		// Query query =
		// em.createQuery("SELECT p FROM Produto p WHERE p.precoUnitario < 7");

		Query query2 = em
				.createNativeQuery("Select * from Produto where precoUnitario < 7");
		List<Object[]> list = query2.getResultList();

		for (Object[] objects : list) {
			for (Object object : objects) {
				System.out.print(object);
				System.out.print(" ,");
			}
			System.out.println();
		}

		// List<Produto> produtos = query.getResultList();
		//
		// for (Produto produto : produtos) {
		// System.out.println(produto.getId());
		// System.out.println(produto.getDescricao());
		// System.out.println(produto.getPrecoUnitario());
		// }

		tx.commit();

		// tx.rollback();

		em.close();
		emf.close();

		// em.persist(arg0);
		// em.merge(arg0);
		// em.remove(arg0);
		// em.find(arg0, arg1);

	}

}
