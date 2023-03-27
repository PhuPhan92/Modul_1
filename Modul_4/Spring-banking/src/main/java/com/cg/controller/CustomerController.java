package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;

//    Map Area
    @GetMapping
    public String showListPage(Model model) {

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }
    @GetMapping("/update/{customerId}")
    public String showUpdatePage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }
        model.addAttribute("customer", customerOptional.get());
        return "customer/edit";
    }
    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        Deposit deposit = new Deposit();
        deposit.setCustomer(customerOptional.get());

        model.addAttribute("deposit", deposit);

        return "customer/deposit";
    }
    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customerOptional.get());

        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(senderId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        Customer sender = customerOptional.get();

        List<Customer> recipients = customerService.findAllByIdNot(senderId);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);

//        model.addAttribute("sender", sender);
        model.addAttribute("recipients", recipients);
        model.addAttribute("transfer", transfer);

        return "customer/transfer";
    }

    //    post area
    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer, BindingResult bindingResult, Model model) {
        new Customer().validate (customer, bindingResult);
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            model.addAttribute("customer", customer);
            return "customer/create";
        }

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);

        return "customer/create";
    }
    @PostMapping("/update/{customerId}")
    public String update(@PathVariable Long customerId, @ModelAttribute Customer customer,BindingResult bindingResult, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        new Customer().validate(customer,bindingResult);
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("errors", true);
            model.addAttribute("customer", customer);
            return "customer/create";
        }

        customer.setBalance(customerOptional.get().getBalance());
        customer.setId(customerId);
        customerService.save(customer);
        return "redirect:/customers";
    }
    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @Validated @ModelAttribute Deposit deposit, BindingResult bindingResult, Model model) {

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            return "customer/deposit";
        }

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        BigDecimal transactionAmount = deposit.getTransactionAmount();

        Customer customer = customerOptional.get();
        BigDecimal currentBalance = customer.getBalance();

        BigDecimal newBalance = currentBalance.add(transactionAmount);

        customer.setBalance(newBalance);

        deposit.setCustomer(customer);

        customerService.deposit(deposit);

        deposit.setTransactionAmount(BigDecimal.ZERO);

        model.addAttribute("deposit", deposit);

        model.addAttribute("success", true);
        model.addAttribute("message", "Nạp tiền thành công");

        return "customer/deposit";
    }
    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @Validated @ModelAttribute Withdraw withdraw, BindingResult bindingResult, Model model) {

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            return "customer/withdraw";
        }

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        BigDecimal transactionAmount = withdraw.getTransactionAmount();

        Customer customer = customerOptional.get();
        BigDecimal currentBalance = customer.getBalance();
        if (currentBalance.compareTo(transactionAmount) >= 0){
            BigDecimal newBalance = currentBalance.subtract(transactionAmount);

            customer.setBalance(newBalance);

            withdraw.setCustomer(customer);

            customerService.withdraw(withdraw);

            withdraw.setTransactionAmount(BigDecimal.ZERO);

            model.addAttribute("withdraw", withdraw);
            model.addAttribute("success", true);
            model.addAttribute("message", "Rút tiền thành công");

        } else if (currentBalance.compareTo(transactionAmount) < 0) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Số dư của bạn không đủ");

        }
        return "customer/withdraw";
    }
    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, @ModelAttribute Transfer transfer, Model model) {

        Optional<Customer> senderOptional = customerService.findById(senderId);
        if (!senderOptional.isPresent()) {
            return "error/404";
        }

        Optional<Customer> recipientOptional = customerService.findById(transfer.getRecipient().getId());
        if (!recipientOptional.isPresent()) {
            return "error/404";
        }

        List<Customer> recipients = customerService.findAllByIdNot(senderId);
        model.addAttribute("recipients", recipients);

        Customer sender = senderOptional.get();
        BigDecimal senderBalance = sender.getBalance();

        Customer recipient = recipientOptional.get();
        BigDecimal recipientBalance = recipient.getBalance();

        BigDecimal transferAmount = transfer.getTransferAmount();
        long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(fees)).divide(BigDecimal.valueOf(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        BigDecimal minAmount = BigDecimal.valueOf(1000L);
        BigDecimal maxAmount = BigDecimal.valueOf(1000000L);

        if (transferAmount.compareTo(minAmount) < 0) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Số tiền chuyển khoản tối thiểu là $10.000");
        }
        else if (transferAmount.compareTo(maxAmount) > 0) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Số tiền chuyển khoản tối đa là $100.000.000");
        }
        else {
            if (senderBalance.compareTo(transactionAmount) < 0) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Số dư người gửi không đủ để thực hiện giao dịch chuyển khoản");

                return "customer/transfer";
            }

            if (sender.getId().equals(recipient.getId())) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Tài khoản người nhận không hợp lệ");

                return "customer/transfer";
            }

            BigDecimal newSenderBalance =  senderBalance.subtract(transactionAmount);
            sender.setBalance(newSenderBalance);

            BigDecimal newRecipientBalance = recipientBalance.add(transferAmount);
            recipient.setBalance(newRecipientBalance);

            transfer.setSender(sender);
            transfer.setRecipient(recipient);
            transfer.setFees(fees);
            transfer.setFeesAmount(feesAmount);
            transfer.setTransactionAmount(transactionAmount);

            customerService.transfer(transfer);

            model.addAttribute("success", true);
            model.addAttribute("message", "Giao dịch chuyển khoản thành công");
        }


        return "customer/transfer";
    }
}
