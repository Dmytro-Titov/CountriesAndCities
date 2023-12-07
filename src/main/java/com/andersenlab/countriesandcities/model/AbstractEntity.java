package com.andersenlab.countriesandcities.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.util.Objects;

@MappedSuperclass
public class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Version
  private Long version;

  protected AbstractEntity(Long id, String name, Long version) {
    this.id = id;
    this.name = name;
    this.version = version;
  }

  public AbstractEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String toString() {
    Long someId = this.getId();
    return "AbstractEntity(id=" + someId + ", name=" + this.getName() + ", version=" + this.getVersion() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEntity that = (AbstractEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(
        version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, version);
  }
}
