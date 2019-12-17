package lt.golay.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @Lob
    @Column(name = "no_encoding")
    private byte[] noEncoding;

    @Column(name = "no_encoding_content_type")
    private String noEncodingContentType;

    @Lob
    @Column(name = "with_encoding")
    private byte[] withEncoding;

    @Column(name = "with_encoding_content_type")
    private String withEncodingContentType;

    @Column(name = "probability")
    private Double probability;

    @Column(name = "header")
    private byte[] header;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public Image file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Image fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public byte[] getNoEncoding() {
        return noEncoding;
    }

    public Image noEncoding(byte[] noEncoding) {
        this.noEncoding = noEncoding;
        return this;
    }

    public void setNoEncoding(byte[] noEncoding) {
        this.noEncoding = noEncoding;
    }

    public String getNoEncodingContentType() {
        return noEncodingContentType;
    }

    public Image noEncodingContentType(String noEncodingContentType) {
        this.noEncodingContentType = noEncodingContentType;
        return this;
    }

    public void setNoEncodingContentType(String noEncodingContentType) {
        this.noEncodingContentType = noEncodingContentType;
    }

    public byte[] getWithEncoding() {
        return withEncoding;
    }

    public Image withEncoding(byte[] withEncoding) {
        this.withEncoding = withEncoding;
        return this;
    }

    public void setWithEncoding(byte[] withEncoding) {
        this.withEncoding = withEncoding;
    }

    public String getWithEncodingContentType() {
        return withEncodingContentType;
    }

    public Image withEncodingContentType(String withEncodingContentType) {
        this.withEncodingContentType = withEncodingContentType;
        return this;
    }

    public void setWithEncodingContentType(String withEncodingContentType) {
        this.withEncodingContentType = withEncodingContentType;
    }

    public Double getProbability() {
        return probability;
    }

    public Image probability(Double probability) {
        this.probability = probability;
        return this;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", noEncoding='" + getNoEncoding() + "'" +
            ", noEncodingContentType='" + getNoEncodingContentType() + "'" +
            ", withEncoding='" + getWithEncoding() + "'" +
            ", withEncodingContentType='" + getWithEncodingContentType() + "'" +
            ", probability=" + getProbability() +
            "}";
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(final byte[] header) {
        this.header = header;
    }
}
