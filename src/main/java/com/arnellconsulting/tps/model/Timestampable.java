/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arnellconsulting.tps.model;

import java.io.Serializable;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import org.springframework.data.domain.Persistable;

/**
 * Interface for entries that should have a created and last modified timestamp.
 * @author jiar
 * @param <ID>
 */
public interface Timestampable<ID extends Serializable> extends Persistable<ID> {
   
	/**
	 * Returns the creation date of the entity.
	 *
	 * @return the createdDate
	 */
	DateTime getCreated();

	/**
	 * Sets the creation date of the entity.
	 *
	 * @param creationDate the creation date to set
	 */
	void setCreated(final DateTime creationDate);

	/**
	 * Returns the date of the last modification.
	 *
	 * @return the lastModifiedDate
	 */
	DateTime getLastModified();

	/**
	 * Sets the date of the last modification.
	 *
	 * @param lastModifiedDate the date of the last modification to set
	 */
	void setLastModified(final DateTime lastModifiedDate);
}
