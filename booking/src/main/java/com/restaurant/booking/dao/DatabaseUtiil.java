package com.restaurant.booking.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.restaurant.booking.entity.Booking;
import com.restaurant.booking.entity.DineTable;

public class DatabaseUtiil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			Map<String, Object> settings = new HashMap<>();
			settings.put("hibernate.connection.driver_class", "org.h2.Driver");
			settings.put("hibernate.connection.url", "jdbc:h2:mem:test");
			settings.put("hibernate.connection.username", "sa");
			settings.put("hibernate.connection.password", "");
			settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			settings.put("hibernate.current_session_context_class", "thread");
			settings.put("hibernate.show_sql", "true");
			settings.put("hibernate.format_sql", "true");
			settings.put("hibernate.hbm2ddl.auto", "create-drop");

			try {
				ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

				Metadata metadata = new MetadataSources(standardRegistry).addAnnotatedClass(Booking.class)
						.addAnnotatedClass(DineTable.class).getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
