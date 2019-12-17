package lt.golay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Vector.
 */
@Entity
@Table(name = "vector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "\\b[0-1]{12}\\b")
    @Column(name = "data")
    private String data;

    @Column(name = "encoded")
    private String encoded;

    @Column(name = "transfered")
    private String transfered;

    @Column(name = "decoded")
    private String decoded;

    @Column(name = "probability")
    private Double probability;

    @Column(name = "errors")
    private String errors;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public Vector data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEncoded() {
        return encoded;
    }

    public Vector encoded(String encoded) {
        this.encoded = encoded;
        return this;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public String getTransfered() {
        return transfered;
    }

    public Vector transfered(String transfered) {
        this.transfered = transfered;
        return this;
    }

    public void setTransfered(String transfered) {
        this.transfered = transfered;
    }

    public String getDecoded() {
        return decoded;
    }

    public Vector decoded(String decoded) {
        this.decoded = decoded;
        return this;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }

    public Double getProbability() {
        return probability;
    }

    public Vector probability(Double probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getErrors() {
        return errors;
    }

    public Vector errors(String errors) {
        this.errors = errors;
        return this;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector)) {
            return false;
        }
        return id != null && id.equals(((Vector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vector{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", encoded='" + getEncoded() + "'" +
            ", transfered='" + getTransfered() + "'" +
            ", decoded='" + getDecoded() + "'" +
            ", probability=" + getProbability() +
            ", errors='" + getErrors() + "'" +
            "}";
    }
}
