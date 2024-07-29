package com.example.z6.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sending_to_id", referencedColumnName = "id")
    private User sendingTo;

    @ManyToOne
    @JoinColumn(name = "sending_from_id", referencedColumnName = "id")
    private User sendingFrom;

    @Enumerated(EnumType.STRING)

    private TransactionStatus status;

    private Date timeTransactionMade;

    private Long amount;
}
