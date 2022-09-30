package a;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class myThread implements Runnable{

    ReadWriteLock readWriteLock;
    Account account;

    String type;

    ArrayList<Account> accounts = new ArrayList<>();


    public myThread(ReadWriteLock readWriteLock,Account account,String type,ArrayList<Account> a) throws FileNotFoundException {
        this.account=account;
        this.readWriteLock=readWriteLock;
        this.type=type;
        this.accounts=a;
        readFromFile();
    }
    public void readFromFile() throws FileNotFoundException {
        Scanner input = new Scanner(new File("./src/a/data.txt"));
        while (input.hasNext()){
            String new_name=input.next();
            String new_surname = input.next();
            String new_father = input.next();
            String new_phone = input.next();

            Account item=new Account(new_name,new_surname,new_father,new_phone);
            accounts.add(item);
            System.out.println(item);
        }

    }

    private void searchPhoneByName(String Name) throws FileNotFoundException, InterruptedException {
        readWriteLock.lockRead();
        String result = null;
        boolean isFounded = false;
        for (int i=0;i<4;i++){
        for( var item : accounts){
            if(Name.equals(item.getName())){
                result=item.getPhoneNumber();
                isFounded=true;

            }
        }
        }
        if(!isFounded){
            System.out.println("No data");
        }

        readWriteLock.unlockRead();
        System.out.println(Name + " has number " + result);


    }

    private void deleteAdd() throws InterruptedException, FileNotFoundException {

        readWriteLock.lockWrite();
        Account to_add = new Account("Anatoliy","Sharapov","Vladimirovich","0984834343");
        Account to_delete = new Account("Положенець","Кирило","Олксандрович","890123456");
       for (int i=0;i<4;i++){
            accounts.remove(to_delete);
            accounts.add(to_add);
            //readFromFile();
        }
        readWriteLock.unlockWrite();
       for(var item : accounts){
           System.out.println(item);
       }

    }
    private void searchNameByPhone(String phone) throws FileNotFoundException, InterruptedException {
        readWriteLock.lockRead();
        String result = "";
        boolean isFounded = false;
        for (int i=0;i<4;i++) {
            for (var item : accounts) {
                if (phone.equals(item.getPhoneNumber())) {
                    result = item.getSurname();
                    isFounded = true;
                    System.out.println(phone + " has number " + result);
                }
            }
        }
        if(!isFounded){
            System.out.println("No data");
        }
       
        readWriteLock.unlockRead();
        //System.out.println(phone + " has number " + result);

    }

    @Override
    public void run() {
        /*switch (this.type){
            case "phone":try {
                searchNameByPhone("123456789");
                 } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
                 } catch (InterruptedException e) {
                throw new RuntimeException(e);
                }
                break;
            case "name": try {
                searchPhoneByName("Положенець");
                } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
                } catch (InterruptedException e) {
                throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Nicht verstein");
        }*/
        try {
            deleteAdd();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
