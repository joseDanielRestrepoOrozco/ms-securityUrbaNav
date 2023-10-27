package com.msurbaNavSecurity.msurbaNavSecurity.Repositories;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PaymentMethodRepository extends MongoRepository<PaymentMethod,String>{

}
