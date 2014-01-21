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

import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.Date;

/**
 * Generic properties for objects.
 * @author jiar
 */
public class TpsObject extends AbstractPersistable<Long> {

   @Column(name = "created_timestamp", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   public Date createdTimestamp;

   public Date getCreatedTimestamp() {
      return createdTimestamp;
   }

   public void setCreatedTimestamp(final Date createdTimestamp) {
      this.createdTimestamp = createdTimestamp;
   }

}
