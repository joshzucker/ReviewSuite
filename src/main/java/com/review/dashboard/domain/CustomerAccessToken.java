package com.review.dashboard.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CustomerAccessToken.
 */
@Entity
@Table(name = "customer_access_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
  //14*60 * 24;
    private static final int EXPIRATION = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

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

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public CustomerAccessToken expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate() {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public String getToken() {
        return token;
    }

    public CustomerAccessToken token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public CustomerAccessToken user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    private ZonedDateTime calculateExpiryDate(final int expiryTimeInMinutes) {
        
        return ZonedDateTime.now().minusMinutes(expiryTimeInMinutes);
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public CustomerAccessToken customer(Customer customer) {
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
        CustomerAccessToken customerAccessToken = (CustomerAccessToken) o;
        if (customerAccessToken.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customerAccessToken.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerAccessToken{" +
            "id=" + id +
            ", expiryDate='" + expiryDate + "'" +
            ", token='" + token + "'" +
            '}';
    }
}
