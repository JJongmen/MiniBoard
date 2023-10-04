package com.jyp.miniboard.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;

        Builder id(final Long id) {
            this.id = id;
            return this;
        }

        Builder name(final String name) {
            this.name = name;
            return this;
        }

        Builder email(final String email) {
            this.email = email;
            return this;
        }

        Builder password(final String password) {
            this.password = password;
            return this;
        }

        Member build() {
            final Member member = new Member();
            member.id = this.id;
            member.name = this.name;
            member.email = this.email;
            member.password = this.password;
            return member;
        }
    }
}
