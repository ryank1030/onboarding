package com.vivvo.onboarding.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "phone")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Phone {

    @Id
    @Column(name = "phone_id")
    @Type(type = "uuid-char")
    private UUID phoneId;
    @Column(name = "phone_number")
    private String phoneNumber;

    //By default this will save this in the database as a 1 or 0 which is fine. just as an FYI
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "primary")
    private boolean primary;
    @Column(name = "user_id")
    @Type(type = "uuid-char")
    private UUID userId;
    @Column(name = "verification_link")
    private String verificationLink;

}
