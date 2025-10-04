package com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.ownersRepo;

import com.omarhammad.kdg_backend.restaurants.adapters.out.jpaRepositories.ownersRepo.entites.OwnerJpaEntity;
import com.omarhammad.kdg_backend.restaurants.domain.Email;
import com.omarhammad.kdg_backend.restaurants.domain.Id;
import com.omarhammad.kdg_backend.restaurants.domain.Owner;
import com.omarhammad.kdg_backend.restaurants.domain.Restaurant;
import com.omarhammad.kdg_backend.restaurants.ports.out.LoadOwnerPort;
import com.omarhammad.kdg_backend.restaurants.ports.out.SaveOwnerPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class OwnersJpaAdapter implements SaveOwnerPort, LoadOwnerPort {

    private OwnersRepository repository;

    @Override
    public void save(Owner owner) {
        OwnerJpaEntity ownerJpaEntity = toJpaEntity(owner);
        repository.save(ownerJpaEntity);
    }

    @Override
    public Optional<Owner> findOwnerById(Id<Owner> ownerId) {

        Optional<OwnerJpaEntity> ownerJpaEntity = repository.findById(UUID.fromString(ownerId.value()));
        return ownerJpaEntity.map(this::toOwner);
    }



    private OwnerJpaEntity toJpaEntity(Owner owner) {

        return new OwnerJpaEntity(
                UUID.fromString(owner.getId().value()),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getEmail().email(),
                owner.getPhoneNumber(),
                owner.getUsername(),
                owner.getPassword()
        );
    }

    private Owner toOwner(OwnerJpaEntity ownerJpaEntity) {

        return new Owner(
                new Id<>(ownerJpaEntity.getId().toString()),
                ownerJpaEntity.getFirstName(),
                ownerJpaEntity.getLastName(),
                new Email(ownerJpaEntity.getEmail()),
                ownerJpaEntity.getPhoneNumber(),
                ownerJpaEntity.getUsername(),
                ownerJpaEntity.getPassword()
        );
    }

}
