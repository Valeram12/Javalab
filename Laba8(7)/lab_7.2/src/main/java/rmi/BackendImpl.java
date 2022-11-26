package rmi;

import dao.ManufacturerDAO;
import dao.BrandDAO;
import dto.manufacturerDTO;
import dto.BrandDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BackendImpl extends UnicastRemoteObject implements Backend {
    protected BackendImpl() throws RemoteException {
        super();
    }

    @Override
    public manufacturerDTO manufacturerFindById(long id) throws RemoteException {
        return ManufacturerDAO.findById(id);
    }

    @Override
    public manufacturerDTO manufacturerFindByName(String name) throws RemoteException {
        return ManufacturerDAO.findByName(name);
    }

    @Override
    public boolean manufacturerUpdate(manufacturerDTO manufacturer) throws RemoteException {
        return ManufacturerDAO.update(manufacturer);
    }

    @Override
    public boolean manufacturerInsert(manufacturerDTO manufacturer) throws RemoteException {
        return ManufacturerDAO.insert(manufacturer);
    }

    @Override
    public boolean manufacturerDelete(manufacturerDTO manufacturer) throws RemoteException {
        return ManufacturerDAO.delete(manufacturer);
    }

    @Override
    public List<manufacturerDTO> manufacturerFindAll() throws RemoteException {
        return ManufacturerDAO.findAll();
    }

    @Override
    public BrandDTO brandFindById(long id) throws RemoteException {
        return BrandDAO.findById(id);
    }

    @Override
    public BrandDTO brandFindByName(String name) throws RemoteException {
        return BrandDAO.findByName(name);
    }

    @Override
    public boolean brandUpdate(BrandDTO brand) throws RemoteException {
        return BrandDAO.update(brand);
    }

    @Override
    public boolean brandInsert(BrandDTO brand) throws RemoteException {
        return BrandDAO.insert(brand);
    }

    @Override
    public boolean brandDelete(BrandDTO brand) throws RemoteException {
        return BrandDAO.delete(brand);
    }

    @Override
    public List<BrandDTO> brandFindAll() throws RemoteException {
        return BrandDAO.findAll();
    }

    @Override
    public List<BrandDTO> brandFindByAuthorId(Long id) throws RemoteException {
        return BrandDAO.findByAuthorId(id);
    }

    public static void main(String[] args) throws RemoteException {
        BackendImpl bck = new BackendImpl();
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("videoshop", bck);
        System.out.println("Server started!");
    }
}

