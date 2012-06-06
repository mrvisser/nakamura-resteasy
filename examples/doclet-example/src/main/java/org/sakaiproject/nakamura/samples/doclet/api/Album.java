package org.sakaiproject.nakamura.samples.doclet.api;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Album {

  @XmlElement public String id;
  @XmlElement public String name;
  @XmlElement public String artistId;
  @XmlElement public Map<String, Object> credits;
  @XmlElement public List<String> songs;
  
}
