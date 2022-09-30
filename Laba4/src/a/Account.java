package a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Account {
    private String name;
    private String surname;
    private String futher;
    private String phoneNumber;

    public Account(String name, String surname, String father, String number) {
        this.name = name;
        this.surname = surname;
        this.futher = father;
        this.phoneNumber = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /*public ArrayList<Account> readFromFile() throws FileNotFoundException {
        Scanner input = new Scanner(new File("./src/data.txt"));
        ArrayList<Account> accounts = new ArrayList<>();
        while (input.hasNext()){
            String new_name=input.next();
            String new_surname = input.next();
            String new_father = input.next();
            String new_phone = input.next();

            Account item=new Account(new_name,new_surname,new_father,new_phone);
            accounts.add(item);
            System.out.println(item);
        }
        return accounts;
    }*/

    @Override
    public String toString() {
        return "Name: " + name + " " + surname + " " + futher + ", Phone number: " + phoneNumber  + "\n";
    }


}
