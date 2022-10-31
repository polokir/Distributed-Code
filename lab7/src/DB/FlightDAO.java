package DB;

import models.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    public static Flight findById(long id) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "SELECT * FROM flight WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if (resultSet.next()) {
                flight = new Flight();
                flight.setId(resultSet.getLong(1));
                flight.setDeparture(resultSet.getString(2));
                flight.setDestination(resultSet.getString(3));
                flight.setPassangers(resultSet.getInt(4));
                flight.setCompanyID(resultSet.getLong(5));
            }
            preparedStatement.close();
            return flight;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(Flight flight) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "UPDATE flight SET departure = ?, destination = ?, passengers = ?, company_id = ?, WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flight.getDeparture());
            preparedStatement.setString(2, flight.getDestination());
            preparedStatement.setInt(3, flight.getPassangers());
            preparedStatement.setLong(4, flight.getCompanyID());
            preparedStatement.setLong(5, flight.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(Flight flight) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "INSERT INTO flight (departure, destination, passengers, company_id) VALUES (?,?,?,?)";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, flight.getDeparture());
            preparedStatement.setString(2, flight.getDestination());
            preparedStatement.setInt(3, flight.getPassangers());
            preparedStatement.setLong(4, flight.getCompanyID());
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    flight.setId(resultSet.getLong(1));
                } else
                    return false;
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Flight flight) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "DELETE FROM flight WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, flight.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deleteCID(Flight flight) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "DELETE FROM flight WHERE companyID = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, flight.getCompanyID());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            DBManager.getInstance().commitAndClose(connection);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Flight> findAll() {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "SELECT * FROM flight";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Flight> list = new ArrayList<>();
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setId(resultSet.getLong(1));
                flight.setDeparture(resultSet.getString(2));
                flight.setDestination(resultSet.getString(3));
                flight.setPassangers(resultSet.getInt(4));
                flight.setCompanyID(resultSet.getLong(5));
                list.add(flight);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Flight> findByAirCompanyId(Long id) {
        try (Connection connection = DBManager.getConnection();) {
            String sql = "SELECT * FROM flight WHERE companyId = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Flight> list = new ArrayList<>();
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setId(resultSet.getLong(1));
                flight.setDeparture(resultSet.getString(2));
                flight.setDestination(resultSet.getString(3));
                flight.setPassangers(resultSet.getInt(4));
                flight.setCompanyID(resultSet.getLong(5));
                list.add(flight);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}