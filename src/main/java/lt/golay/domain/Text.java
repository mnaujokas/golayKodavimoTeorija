package lt.golay.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Text.
 */
@Entity
@Table(name = "text")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Text implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private String data;

    @Column(name = "decoded")
    private String decoded;

    @Column(name = "probability")
    private Double probability;

    @Column(name = "no_encoding")
    private String noEncoding;

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

    public Text data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDecoded() {
        return decoded;
    }

    public Text decoded(String decoded) {
        this.decoded = decoded;
        return this;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }

    public Double getProbability() {
        return probability;
    }

    public Text probability(Double probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public String getNoEncoding() {
        return noEncoding;
    }

    public Text noEncoding(String noEncoding) {
        this.noEncoding = noEncoding;
        return this;
    }

    public void setNoEncoding(String noEncoding) {
        this.noEncoding = noEncoding;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Text)) {
            return false;
        }
        return id != null && id.equals(((Text) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Text{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", decoded='" + getDecoded() + "'" +
            ", probability=" + getProbability() +
            ", noEncoding='" + getNoEncoding() + "'" +
            "}";
    }
}
