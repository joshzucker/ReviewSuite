package com.review.dashboard.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Review2.
 */
@Entity
@Table(name = "review_2")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "first_name", length = 15, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "last_name", length = 15, nullable = false)
    private String lastName;

    @Column(name = "date_written")
    private ZonedDateTime dateWritten;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Size(max = 700)
    @Lob
    @Column(name = "cust_experiece", length = 700, nullable = false)
    private String custExperiece;

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

    public String getFirstName() {
        return firstName;
    }

    public Review2 firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Review2 lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getDateWritten() {
        return dateWritten;
    }

    public Review2 dateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
        return this;
    }

    public void setDateWritten(ZonedDateTime dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getToken() {
        return token;
    }

    public Review2 token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustExperiece() {
        return custExperiece;
    }

    public Review2 custExperiece(String custExperiece) {
        this.custExperiece = custExperiece;
        return this;
    }

    public void setCustExperiece(String custExperiece) {
        this.custExperiece = custExperiece;
    }

    public User getUser() {
        return user;
    }

    public Review2 user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Review2 customer(Customer customer) {
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
        Review2 review2 = (Review2) o;
        if (review2.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, review2.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Review2{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", dateWritten='" + dateWritten + "'" +
            ", token='" + token + "'" +
            ", custExperiece='" + custExperiece + "'" +
            '}';
    }
}
