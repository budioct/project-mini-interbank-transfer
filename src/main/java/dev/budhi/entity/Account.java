package dev.budhi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bank", nullable = false)
    private String bank;
    @Column(name = "accountNumber", nullable = false)
    private String accountNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

}
