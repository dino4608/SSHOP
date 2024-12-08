package dmon.SSHOP_springboot_backend.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import java.util.List;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    @OneToOne(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private User user;

    @OneToOne(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Seller seller;

    private String email;
    private String phone;
    private String password;

    private String refreshToken;

    @Type(ListArrayType.class)
    @Column(name = "used_tokens", columnDefinition = "text[]")
    private List<String> usedTokens;

//    @ColumnDefault("ACTIVE")
    private String status; //ACTIVE, DISABLE //todo: migrate to soft-delete
}
