package org.javacode.market.mapper.response;

import org.javacode.market.mapper.Mapper;
import org.javacode.market.model.dto.response.CustomerResponseDto;
import org.javacode.market.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper implements Mapper<Customer, CustomerResponseDto> {

    @Override
    public CustomerResponseDto map(Customer customer) {
        return new CustomerResponseDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getContactNumber()
        );
    }
}
