package serverTraitement;

import model.Employe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface EmployeUserInterface extends Remote {
    boolean employeSignupToBd(HashMap<String,String> employeSignData) throws RemoteException;
    Employe employeLogin(String employeUserName , String employetPassword) throws RemoteException;
    HashMap<String,Object> getReservatinoDetails(int reservId) throws RemoteException;
    HashMap<String,Object> getReservatinoDetailsOut(int reservId) throws RemoteException;
    boolean checkClientIn(HashMap<String,Object> details)throws RemoteException;
    boolean checkClientOut(HashMap<String,Object> details)throws RemoteException;
}
