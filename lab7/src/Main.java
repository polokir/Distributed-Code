import DB.AirCompanyDAO;
import DB.DBManager;
import models.AirCompany;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            Connection connection = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(AirComanyDAO.insert(new AirCompany(6, "UPS")));
        //System.out.println(AirComanyDAO.findByName("MAU").getName());
        AirCompanyDAO.delete(new AirCompany(2,"RyanAir"));
        //AirComanyDAO.update(new AirCompany(1,"FlyEmirates"));
    }
}
