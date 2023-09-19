package serverTraitement;

import model.Employe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public interface AdminUserInterface extends Remote {

    String getAdminLoginCordinnates(String userName,String userPassword) throws RemoteException;

    List<Employe> getEmployeInfo(List<Employe> employes) throws RemoteException;

    boolean deleteEmploye(int employeId) throws RemoteException;

    double getRevenue(String fromDate,String toDate) throws RemoteException;

    boolean editEmploye( HashMap<String, Object> employeData)throws RemoteException;

}
