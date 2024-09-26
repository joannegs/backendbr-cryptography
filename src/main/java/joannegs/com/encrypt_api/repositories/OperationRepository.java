package joannegs.com.encrypt_api.repositories;

import joannegs.com.encrypt_api.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> { }
