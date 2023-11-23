package com.practice.sakilaAPI.repo;

import com.practice.sakilaAPI.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    private static ArrayList<Customer> list = null;
    private static CustomerRepo repo = null;

    public static  CustomerRepo getInstance(){
        if(repo==null) {
            repo = new CustomerRepo();
            repo.list = new ArrayList<>();
        }
        return repo;
    }

    private CustomerRepo(){}

    public void add(Customer c){
        list.add(c);
    }

    public Customer get(int index){
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public String toString(){
        return list.toString();
    }

    public List<Customer> getList(){
        return list;
    }

    public void remove(int id) {
        list.remove(id);
    }

    public void set(String firstName, String lastName, int id){
        Customer customer = new Customer(firstName, lastName, id);
        list.set(id-1, customer);
    }

    public boolean check(int id){
        return (list.contains(id-1));
    }
}
