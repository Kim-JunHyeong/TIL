package com.examples.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Account {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany
    private Set<Study> studies;

















//    @Temporal(TemporalType.TIMESTAMP)
//    private Date created = new Date();

//    private String yes;
//
//    @Transient
//    private String no;

//    @Embedded
//    private Address address;

//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "street", column = @Column(name = "home_street"))
//    })
//    private Address address;
//
//    public Long getId() {
//        return id;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
