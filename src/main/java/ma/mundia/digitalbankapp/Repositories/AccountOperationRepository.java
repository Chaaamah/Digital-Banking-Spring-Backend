package ma.mundia.digitalbankapp.Repositories;

import ma.mundia.digitalbankapp.Entities.AccountOperation;
import ma.mundia.digitalbankapp.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
