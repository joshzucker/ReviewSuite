package com.review.dashboard.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Review1.
 */
@Entity
@Table(name = "review_1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "date_written")
    private ZonedDateTime dateWritten;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Column(name = "selected_link", nullable = false)
    private String selectedLink;

    @OneToOne
    @JoinColumn(nullable = true)
    private User user;

    @OneToOne
    @JoinColumn(nullable = true)
    private Customer customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public Review1 rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ZonedDateTime getDateWritten() {
        return dateWritten;
    }

    public Review1 dateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
        return this;
    }

    public void setDateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getToken() {
        return token;
    }

    public Review1 token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSelectedLink() {
        return selectedLink;
    }

    public Review1 selectedLink(String selectedLink) {
        this.selectedLink = selectedLink;
        return this;
    }

    public void setSelectedLink(String selectedLink) {
        this.selectedLink = selectedLink;
    }

    public User getUser() {
        return user;
    }

    public Review1 user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Review1 customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review1 review1 = (Review1) o;
        if (review1.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, review1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Review1{" +
            "id=" + id +
            ", rating='" + rating + "'" +
            ", dateWritten='" + dateWritten + "'" +
            ", token='" + token + "'" +
            ", selectedLink='" + selectedLink + "'" +
            '}';
    }
}
