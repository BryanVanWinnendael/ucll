package com.square_claimer.user_data.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.square_claimer.user_data.model.team.Team;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name = "morbileUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank(message = "user.name.missing")
    @NotNull(message = "user.name.missing")
    @Getter @Setter
    private String userName;

    @NotBlank(message = "user.password.missing")
    @NotNull(message = "user.password.missing")
    @Getter
    private String password;

    @NotBlank(message = "user.email.missing")
    @NotNull(message = "user.name.missing")
    @Email(message = "user.email.invalid")
    @Getter @Setter
    private String email;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "team_id")
    @Getter @Setter
    private Team team;

    public void setPassword(String pass){
        this.password = new BCryptPasswordEncoder().encode(pass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(team, user.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, team);
    }
}
