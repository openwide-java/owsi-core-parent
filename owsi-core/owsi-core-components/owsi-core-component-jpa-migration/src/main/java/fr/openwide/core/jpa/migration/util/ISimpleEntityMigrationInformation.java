package fr.openwide.core.jpa.migration.util;

import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.jpa.migration.rowmapper.AbstractResultRowMapper;

public interface ISimpleEntityMigrationInformation<T extends GenericEntity<Long, T>> extends IMigrationInformation {

	Class<? extends AbstractResultRowMapper<?>> getRowMapperClass();

	Class<T> getEntityClass();

	String getSqlAllIds();

	/*
	 * Chaîne utilisée dans le IN() éventuel de la requête SQL.
	 * Dans le cas d'un import par lots, il doit obligatoirement être non null.
	 */
	String getParameterIds();

}