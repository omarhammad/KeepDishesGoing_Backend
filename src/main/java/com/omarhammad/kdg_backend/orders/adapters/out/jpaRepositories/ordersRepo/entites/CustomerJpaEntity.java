package com.omarhammad.kdg_backend.orders.adapters.out.jpaRepositories.ordersRepo.entites;

import com.omarhammad.kdg_backend.orders.domain.DeliveryAddress;
import com.omarhammad.kdg_backend.orders.domain.Email;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerJpaEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private DeliveryAddressJpa deliveryAddress;
    private String email;
    private String phoneNumber;

}
