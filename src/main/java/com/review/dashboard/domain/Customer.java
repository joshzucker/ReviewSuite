package com.review.dashboard.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_sent")
    private ZonedDateTime dateSent;

    @Column(name = "date_received")
    private ZonedDateTime dateReceived;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "is_review_1_email_clicked")
    private Boolean isReview1EmailClicked;

    @Column(name = "is_review_2_email_clicked")
    private Boolean isReview2EmailClicked;

    @OneToOne
    @JoinColumn(nullable = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDateSent() {
        return dateSent;
    }

    public Customer dateSent(ZonedDateTime dateSent) {
        this.dateSent = dateSent;
        return this;
    }

    public void setDateSent(ZonedDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public ZonedDateTime getDateReceived() {
        return dateReceived;
    }

    public Customer dateReceived(ZonedDateTime dateReceived) {
        this.dateReceived = dateReceived;
        return this;
    }

    public void setDateReceived(ZonedDateTime dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Customer reviewId(Long reviewId) {
        this.reviewId = reviewId;
        return this;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Boolean isIsReview1EmailClicked() {
        return isReview1EmailClicked;
    }

    public Customer isReview1EmailClicked(Boolean isReview1EmailClicked) {
        this.isReview1EmailClicked = isReview1EmailClicked;
        return this;
    }

    public void setIsReview1EmailClicked(Boolean isReview1EmailClicked) {
        this.isReview1EmailClicked = isReview1EmailClicked;
    }

    public Boolean isIsReview2EmailClicked() {
        return isReview2EmailClicked;
    }

    public Customer isReview2EmailClicked(Boolean isReview2EmailClicked) {
        this.isReview2EmailClicked = isReview2EmailClicked;
        return this;
    }

    public void setIsReview2EmailClicked(Boolean isReview2EmailClicked) {
        this.isReview2EmailClicked = isReview2EmailClicked;
    }

    public User getUser() {
        return user;
    }

    public Customer user(User user) {
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
        Customer customer = (Customer) o;
        if (customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", dateSent='" + dateSent + "'" +
            ", dateReceived='" + dateReceived + "'" +
            ", reviewId='" + reviewId + "'" +
            ", isReview1EmailClicked='" + isReview1EmailClicked + "'" +
            ", isReview2EmailClicked='" + isReview2EmailClicked + "'" +
            '}';
    }
}
