package org.sakaiproject.nakamura.samples.doclet.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Artist {

  @XmlElement public String id;
  @XmlElement public String firstName;
  @XmlElement public String lastName;
  
}
