package cn.newbeedaly.shardingsphere.jdbc.controller;

import cn.newbeedaly.shardingsphere.jdbc.dao.po.Address;
import cn.newbeedaly.shardingsphere.jdbc.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping("/list")
    public List<Address> getList() {
        return addressService.list();
    }

    @PostMapping("/save")
    public void save(@RequestBody Address address) {
        addressService.save(address);
    }

}
