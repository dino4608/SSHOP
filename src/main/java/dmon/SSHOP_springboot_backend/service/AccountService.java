package dmon.SSHOP_springboot_backend.service;

import dmon.SSHOP_springboot_backend.dto.request.AccountCreateRequest;
import dmon.SSHOP_springboot_backend.dto.request.AccountUpdateRequest;
import dmon.SSHOP_springboot_backend.dto.response.AccountResponse;
import dmon.SSHOP_springboot_backend.entity.Account;
import dmon.SSHOP_springboot_backend.exception.AppException;
import dmon.SSHOP_springboot_backend.exception.ExceptionCode;
import dmon.SSHOP_springboot_backend.mapper.IAccountMapper;
import dmon.SSHOP_springboot_backend.repository.IAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    IAccountRepository accountRepo;
    IAccountMapper accountMapper;

    public AccountResponse createOne(AccountCreateRequest body) {
        //todo: hash password
        //desc: check that email, phone should be not existed
        if (this.accountRepo.existsByEmail(body.getEmail())) {
            throw new AppException(ExceptionCode.OBJECT_EXISTED, "email");
        }
        if (this.accountRepo.existsByPhone(body.getPhone())) {
            throw new AppException(ExceptionCode.OBJECT_EXISTED, "phone");
        }
        Account account = this.accountRepo.save(this.accountMapper.toEntity(body));
        return this.accountMapper.toResponse(account);
    }

    public List<Account> listAll() {
        List<Account> accounts;
        accounts = this.accountRepo.findAll();
        return accounts;
    }

    public AccountResponse findOne(String accountId) {
        Account account = this.accountRepo.findById(accountId)
                .orElseThrow(() -> new AppException(ExceptionCode.OBJECT_NOT_FOUND, "Account"));
        return this.accountMapper.toResponse(account);
    }

    public AccountResponse updateOne(String accountId, AccountUpdateRequest body) {
        //check
        this.findOne(accountId);
        Account account = this.accountMapper.toEntity(body);
        account.setAccountId(accountId);
        account = this.accountRepo.save(account);
        return this.accountMapper.toResponse(account);
    }
}
