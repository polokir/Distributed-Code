package DB;
import models.AirCompany;
import models.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AirCompanyDAO {
    public static AirCompany findById(long id) {
        try (Connection connection = DBManager.getConnection()) {
            String sql =
                    "SELECT * FROM aircompany WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            AirCompany airCompany = null;
            if (resultSet.next()) {
                airCompany = new AirCompany();
                airCompany.setId(resultSet.getLong(1));
                airCompany.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return airCompany;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AirCompany findByName(String name) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM aircompany WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            AirCompany airCompany = null;
            if (resultSet.next()) {
                airCompany = new AirCompany();
                airCompany.setId(resultSet.getLong(1));
                airCompany.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return airCompany;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(AirCompany company) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "UPDATE aircompany SET name = ? WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setLong(2, company.getId());
            int result = preparedStatement.executeUpdate();
            DBManager.getInstance().commitAndClose(connection);
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//INSERT INTO `airport`.`aircompany` (`id`, `name`) VALUES ('4', 'RyanAir');

    public static boolean insert(AirCompany company) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO aircompany (id,name) VALUES (?,?)";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, company.getId());
            preparedStatement.setString(2,company.getName());
            int rows= preparedStatement.executeUpdate();
            System.out.println("updated " + rows + " rows");
           /* if(preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    company.setId(resultSet.getLong(2));
                } else
                    return false;
                preparedStatement.close();
                System.out.println(company.getId());
                return true;
            }*/
            DBManager.getInstance().commitAndClose(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
//DELETE FROM `airport`.`aircompany` WHERE (`id` = '4');

    public static boolean delete(AirCompany company) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "DELETE FROM aircompany WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, company.getId());
            List<Flight> flights = FlightDAO.findByAirCompanyId(company.getId());
            for(Flight item : flights)
            {
                FlightDAO.deleteCID(item);
            }
            int result = preparedStatement.executeUpdate();
            DBManager.getInstance().commitAndClose(connection);
            System.out.println(result);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<AirCompany> findAll() {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM aircompany";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AirCompany> list = new ArrayList<>();
            while (resultSet.next()) {
                AirCompany airCompany = new AirCompany();
                airCompany.setId(resultSet.getLong(1));
                airCompany.setName(resultSet.getString(2));
                list.add(airCompany);
            }
            preparedStatement.close();
            DBManager.getInstance().commitAndClose(connection);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
