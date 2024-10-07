package com.ashu.blogapp.Entity;

import com.ashu.blogapp.Security.CustomUserDetailService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder

@Table(
        name = "users"
)
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private int id;

    @Column(
            name = "user_name",
            nullable = false,
            length = 100
    )
    private String name;


    private String email;

    private String password;

    private String about;


    // relations between User & Posts (1 User can have many posts)
    @OneToMany(
            mappedBy = "user",
            // "CascadeType.ALL" indicates that child would not exist if parent won't exist and on 2nd hand if we need to add parent class so child class would be add automatically
            cascade = CascadeType.ALL,

            // "FetchType.LAZY" indicated if only we need to get only parent without child then we can, orElse we can use "FetchType.EAGER" if we need both entity to come out
            fetch = FetchType.EAGER
    )
    private List<Post> posts = new ArrayList<>();


    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "roles",
                    referencedColumnName = "id"
            )
    )
    private Set<Roles> roles = new HashSet<>();



    // userDetail methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
