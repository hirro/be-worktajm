/*
 * Copyright 2014 jiar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arnellconsulting.tps.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author jiar
 */
class ProjectSerializer extends JsonSerializer<Project> {

   @Override
   public void serialize(
           Project project, 
           JsonGenerator jsonGenerator, 
           SerializerProvider sp)
           throws IOException, JsonProcessingException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeNumberField("id", project.getId());
      jsonGenerator.writeStringField("name", project.getName());
      jsonGenerator.writeStringField("description", project.getDescription());
      jsonGenerator.writeNumberField("rate", project.getRate());
      jsonGenerator.writeNumberField("customerId", project.getCustomerId());
      jsonGenerator.writeStringField("date", project.getLastModified().toString());
      jsonGenerator.writeEndObject();
   }

   
}
