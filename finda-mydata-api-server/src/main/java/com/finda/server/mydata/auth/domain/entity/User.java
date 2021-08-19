package com.finda.server.mydata.auth.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user3")
@Entity
public class User {

    @Id
    private Long id;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "delete_time")
    private LocalDateTime deleteTime;

    @Column(name = "encrypted_pincode")
    private String encryptedPincode;

    @Column(name = "status")
    private String status;

    @Column(name = "e_fk6")
    private String encryptedCi;
}
