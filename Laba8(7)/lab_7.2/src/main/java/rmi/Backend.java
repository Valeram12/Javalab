package rmi;

import dto.manufacturerDTO;
import dto.BrandDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Backend extends Remote {
    public manufacturerDTO manufacturerFindById(long id) throws RemoteException;
    public manufacturerDTO manufacturerFindByName(String name) throws RemoteException;
    public boolean manufacturerUpdate(manufacturerDTO manufacturer) throws RemoteException;
    public boolean manufacturerInsert(manufacturerDTO manufacturer) throws RemoteException;
    public boolean manufacturerDelete(manufacturerDTO manufacturer) throws RemoteException;
    public List<manufacturerDTO> manufacturerFindAll() throws RemoteException;
    public BrandDTO brandFindById(long id) throws RemoteException;
    public BrandDTO brandFindByName(String name) throws RemoteException;
    public boolean brandUpdate(BrandDTO brand) throws RemoteException;
    public boolean brandInsert(BrandDTO brand) throws RemoteException;
    public boolean brandDelete(BrandDTO brand) throws RemoteException;
    public List<BrandDTO> brandFindAll() throws RemoteException;
    public List<BrandDTO> brandFindByAuthorId(Long id) throws RemoteException;
}

