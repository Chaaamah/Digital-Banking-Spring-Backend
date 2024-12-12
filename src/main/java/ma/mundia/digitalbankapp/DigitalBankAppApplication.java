package ma.mundia.digitalbankapp;

import ma.mundia.digitalbankapp.Entities.*;
import ma.mundia.digitalbankapp.Enums.AccountStatus;
import ma.mundia.digitalbankapp.Enums.OperationType;
import ma.mundia.digitalbankapp.Repositories.AccountOperationRepository;
import ma.mundia.digitalbankapp.Repositories.BankAccountRepository;
import ma.mundia.digitalbankapp.Repositories.CustomerRepository;
import ma.mundia.digitalbankapp.Services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankAppApplication.class, args);
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("Hassan", "Chama", "Hamza").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});
			
			customerRepository.findAll().forEach(c -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(c);
				currentAccount.setOverDraft(9000);

				bankAccountRepository.save(currentAccount);


				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(c);
				savingAccount.setInterestRate(5.5);

				bankAccountRepository.save(savingAccount);
			});

			bankAccountRepository.findAll().forEach(acc -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
			});

		};
	}

	@Bean
	CommandLineRunner commandLineRunner(BankService bankService){
		return args -> {
			bankService.consulter();
		};
	}
}
