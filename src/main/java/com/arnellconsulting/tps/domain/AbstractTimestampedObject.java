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
package com.arnellconsulting.tps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Generic properties for objects.
 *
 * @author hirro
 * @param <PK> the type of the auditing type's identifier
 */
@MappedSuperclass
public abstract class AbstractTimestampedObject<PK extends Serializable> 
        extends AbstractPersistable<PK> 
        implements Timestampable<PK> {

    private static final long serialVersionUID = 641461956116435381L;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date modified;

    @Override
    public DateTime getCreated() {

        return null == created ? null : new DateTime(created);
    }

    @Override
    public void setCreated(final DateTime createdDate) {

        this.created = null == createdDate ? null : createdDate.toDate();
    }

    @Override
    public DateTime getLastModified() {

        return null == modified ? null : new DateTime(modified);
    }

    @Override
    public void setLastModified(final DateTime lastModifiedDate) {

        this.modified = null == lastModifiedDate ? null : lastModifiedDate.toDate();
    }
}
