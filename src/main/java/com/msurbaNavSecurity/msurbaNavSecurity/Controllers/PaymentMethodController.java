package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.PaymentMethod;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.PaymentMethodRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/private/paymentmethod")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodRepository thePaymentMethodRepository;

    @Autowired
    private UserRepository theUserRepository;

    /**
     * Listado de metodos de pago
     * @return listado de metodos de pago
     */
    @GetMapping("")
    public List<PaymentMethod> index() {
        System.out.println("Aqui estoy");
        return this.thePaymentMethodRepository.findAll();

    }

    /**
     * Crear un metodo de pago
     * @param newPaymentMethod Objeto de PaymentMethod
     * @return lo que devuelve el metodo save
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PaymentMethod store(@RequestBody PaymentMethod newPaymentMethod) {
        return this.thePaymentMethodRepository.save(newPaymentMethod);
    }

    /**
     * Mostrar un solo metodo de pago
     * @param id identificador del metodo de pago
     * @return un objeto de tipo PaymentMethod
     */
    @GetMapping("{id}")
    public PaymentMethod show(@PathVariable String id) {
        PaymentMethod thePaymentMethod = this.thePaymentMethodRepository
                .findById(id)
                .orElse(null);
        return thePaymentMethod;
    }

    /**
     * Actualizar un metodo de pago
     * @param id identificador de un metodo de pago
     * @param theNewPaymentMethod el objeto actualizado
     * @return null || el metodo de pago
     */
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


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("list")
    public List<PaymentMethod> storeList(@RequestBody List<PaymentMethod> ListPayment) {
        List<PaymentMethod> savedPayments = new ArrayList<>();
        for (PaymentMethod payment : ListPayment) {
            PaymentMethod savedPayment = this.thePaymentMethodRepository.save(payment);
            savedPayments.add(savedPayment);
        };
        return  savedPayments;
    }

    /**
     * Eliminar un metodo de pago
     * @param id identificador del metodo de pago
     */
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




    @GetMapping("user/{id}")
    public List<PaymentMethod> showPaymentUser(@PathVariable String id) {
        List<PaymentMethod> listPayment = this.thePaymentMethodRepository.findAll();
        List<PaymentMethod> listPaymentxUse = new ArrayList<>();
        for (PaymentMethod paymentMethod : listPayment) {

            if (paymentMethod.getUser() != null) {
                if (paymentMethod.getUser().get_id().equals(id)) {
                    paymentMethod.setUser(null);
                    listPaymentxUse.add(paymentMethod);
                }
            }
        }
        return listPaymentxUse;
    }

    /**
     * Une el metodo de pago con el usuario
     * @param paymentmethod_id identificador del metodo de pago
     * @param user_id identificador del usuario
     * @return null || metodo de pago con usuario
     */
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

    /**
     * Separa un metodo de pago de un usuario
     * @param paymentmethod_id
     * @return null || metodo de pago sin usuario
     */
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
