package com.hdfc.aerospikeintegration.service;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.hdfc.aerospikeintegration.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private AerospikeClient client;
    @Value("${aerospike.namespace}")
    private String namespace;
    @Value("${aerospike.set}")
    private  String set;
    @Value("${Aerospike.ttl}")
    private int defaultTtl;

    // CREATE
    public void save(Employee emp) {
        Key key = new Key(namespace, set, emp.getId());
        WritePolicy policy = new WritePolicy();
        policy.expiration = defaultTtl;  // TTL applied
        Bin name = new Bin("name", emp.getName());
        Bin dept = new Bin("department", emp.getDepartment());
        Bin salary = new Bin("salary", emp.getSalary());

        client.put(policy, key, name, dept, salary);
    }

    // READ
    public Record getById(String id) {
        Key key = new Key(namespace, set, id);
        return client.get(null, key);
    }

    // UPDATE
    public void update(Employee emp) {
        save(emp); // same as put (overwrite)
    }

    //Update a single boolean value
    public void updateActiveFlag(String id, boolean flag) {

        Key key = new Key(namespace, set, id);

        WritePolicy policy = new WritePolicy();
        policy.recordExistsAction = RecordExistsAction.UPDATE;
        policy.expiration = -2; // preserve existing TTL

        Bin activeBin = new Bin("flag", flag);

        client.put(policy, key, activeBin);
    }
    // DELETE
    public void delete(String id) {
        Key key = new Key(namespace, set, id);
        client.delete(null, key);
    }
}