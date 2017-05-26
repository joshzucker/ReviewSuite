package com.review.dashboard.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.review.dashboard.domain.enumeration.SocialLinks;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "link")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private SocialLinks type;

    @OneToOne
    @JoinColumn(nullable = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Link url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SocialLinks getType() {
        return type;
    }

    public Link type(SocialLinks type) {
        this.type = type;
        return this;
    }

    public void setType(SocialLinks type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public Link user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        if (link.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, link.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Link{" +
            "id=" + id +
            ", url='" + url + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
