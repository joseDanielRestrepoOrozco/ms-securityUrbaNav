package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.PaymentMethod;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.PaymentMethodRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/paymentmethod")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodRepository thePaymentMethodRepository;

    @Autowired
    private UserRepository theUserRepository;

    // GET TODOS
    @GetMapping("")
    public List<PaymentMethod> index() {
        return this.thePaymentMethodRepository.findAll();
    }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PaymentMethod store(@RequestBody PaymentMethod newPaymentMethod) {
        return this.thePaymentMethodRepository.save(newPaymentMethod);
    }

    // GET UNO
    @GetMapping("{id}")
    public PaymentMethod show(@PathVariable String id) {
        PaymentMethod thePaymentMethod = this.thePaymentMethodRepository
                .findById(id)
                .orElse(null);
        return thePaymentMethod;
    }

    // PUT
    @PutMapping("{id}")
    public PaymentMethod update(@PathVariable String id, @RequestBody PaymentMethod theNewPaymentMethod) {
        PaymentMethod theActualPaymentMethod = this.thePaymentMethodRepository
                .findById(id)
                .orElse(null);
        if (theActualPaymentMethod != null) {
            theActualPaymentMethod.setType(theNewPaymentMethod.getType());
            theActualPaymentMethod.setCardNumber(theNewPaymentMethod.getCardNumber());
            theActualPaymentMethod.setCardCVV(theNewPaymentMethod.getCardCVV());
            theActualPaymentMethod.setExpiryDate(theNewPaymentMethod.getExpiryDate());
            return this.thePaymentMethodRepository.save(theActualPaymentMethod);
        } else {
            return null;
        }
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {
        PaymentMethod thePaymentMethod = this.thePaymentMethodRepository
                .findById(id)
                .orElse(null);
        if (thePaymentMethod != null) {
            this.thePaymentMethodRepository.delete(thePaymentMethod);
        }
    }

    // MATCH
    @PutMapping("{paymentmethod_id}/user/{user_id}")
    public PaymentMethod matchPaymentMethodUser(@PathVariable String paymentmethod_id,
            @PathVariable String user_id) {
        PaymentMethod theActualPaymentMethod = this.thePaymentMethodRepository.findById(paymentmethod_id).orElse(null);
        User theActualUser = this.theUserRepository.findById(user_id).orElse(null);

        if (theActualPaymentMethod != null && theActualUser != null) {
            theActualPaymentMethod.setUser(theActualUser);
            return this.thePaymentMethodRepository.save(theActualPaymentMethod);
        } else {
            return null;
        }
    }

    // UNMATCH
    @PutMapping("{paymentmethod_id}/user")
    public PaymentMethod unMatchPaymentMethodUser(@PathVariable String paymentmethod_id) {
        PaymentMethod theActualPaymentMethod = this.thePaymentMethodRepository.findById(paymentmethod_id).orElse(null);
        if (theActualPaymentMethod != null) {
            theActualPaymentMethod.setUser(null);
            return this.thePaymentMethodRepository.save(theActualPaymentMethod);
        } else {
            return null;
        }
    }

}
