package dmon.SSHOP_springboot_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @Id
    private Long sellerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    @MapsId
    private Account account;

    private String shopName;
    private String shopCode;
    private String shopLogo;
    private String contactEmail;
    private String contactPhone;
    private String businessType;
    private String sellerType;

//    @ColumnDefault("CLOSE")
    private String status; //OPEN, CLOSE //todo: migrate to soft-delete
}
