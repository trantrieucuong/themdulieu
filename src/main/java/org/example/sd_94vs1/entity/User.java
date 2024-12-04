package org.example.sd_94vs1.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sd_94vs1.model.enums.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_code", length = 10, nullable = false)
    private String userCode = "us" + String.format("%05d", (int)(Math.random() * 100000));
    String name;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    String avatar;

    String sdt;
    String address;

    @Enumerated(EnumType.STRING)
    UserRole role;

    @Override
    public String toString() {
        return "User{" +
                "userCode='" + userCode + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sdt='" + sdt + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}
