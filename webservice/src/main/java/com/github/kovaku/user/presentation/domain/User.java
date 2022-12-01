package com.github.kovaku.user.presentation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_table")
@Data
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue
    private Integer id;
    @NonNull
    @Column(name="user_name")
    private String name;
    @NonNull
    @Column(name="user_email")
    private String email;
}
