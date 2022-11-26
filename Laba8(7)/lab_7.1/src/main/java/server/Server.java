package server;

import dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String separator = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        String response;
        try {
            String query = in.readLine();
            if (query == null) {
                return false;
            }

            String [] fields = query.split(separator);
            if (fields.length == 0) {
                return true;
            } else {
                String action = fields[0];
                ManufacturerDTO manufacturerDTO;
                BrandDTO brandDTO;

                switch (action) {
                    case "ManufactirerFindById":
                        Long id = Long.parseLong(fields[1]);
                        manufacturerDTO = ManufacturerDAO.findById(id);
                        response = manufacturerDTO.getName();
                        out.println(response);
                        break;
                    case "BrandFindByAuthorId":
                        id = Long.parseLong(fields[1]);
                        List<BrandDTO> list = BrandDAO.findByAuthorId(id);
                        StringBuilder str = new StringBuilder();
                        assert list != null;
                        brandsToString(str, list);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "BrandFindByName":
                        String name = fields[1];
                        brandDTO = BrandDAO.findByName(name);
                        assert brandDTO != null;
                        response = brandDTO.getBrandId() + separator + brandDTO.getName() + separator + brandDTO.getReleaseYear() + separator + brandDTO.getManufacturerId();
                        out.println(response);
                        break;
                    case "ManufactirerFindByName":
                        name = fields[1];
                        manufacturerDTO = ManufacturerDAO.findByName(name);
                        assert manufacturerDTO != null;
                        response = manufacturerDTO.getId() + "";
                        out.println(response);
                        break;
                    case "BrandUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        Integer year = Integer.parseInt(fields[3]);
                        Long authorId = Long.parseLong(fields[4]);
                        brandDTO = new BrandDTO(id, name, year, authorId);
                        if (BrandDAO.update(brandDTO))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "ManufactirerUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        manufacturerDTO = new ManufacturerDTO(id, name);
                        if (ManufacturerDAO.update(manufacturerDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BrandInsert":
                        name = fields[1];
                        year = Integer.parseInt(fields[2]);
                        authorId = Long.parseLong(fields[3]);
                        brandDTO = new BrandDTO((long) 0, name, year, authorId);
                        if (BrandDAO.insert(brandDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "ManufactirerInsert":
                        name = fields[1];
                        manufacturerDTO = new ManufacturerDTO();
                        manufacturerDTO.setName(name);
                        if (ManufacturerDAO.insert(manufacturerDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BrandDelete":
                        id = Long.parseLong(fields[1]);
                        brandDTO = new BrandDTO();
                        brandDTO.setBrandId(id);
                        if (BrandDAO.delete(brandDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "ManufactirerDelete":
                        id = Long.parseLong(fields[1]);
                        manufacturerDTO = new ManufacturerDTO();
                        manufacturerDTO.setId(id);
                        if (ManufacturerDAO.delete(manufacturerDTO)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BrandAll":
                        List<BrandDTO> booksList = BrandDAO.findAll();
                        str = new StringBuilder();
                        assert booksList != null;
                        brandsToString(str, booksList);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "ManufactirerAll":
                        List<ManufacturerDTO> authorsList = ManufacturerDAO.findAll();
                        str = new StringBuilder();
                        for (ManufacturerDTO author : authorsList) {
                            str.append(author.getId());
                            str.append(separator);
                            str.append(author.getName());
                            str.append(separator);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void brandsToString(StringBuilder str, List<BrandDTO> list) {
        for (BrandDTO brand : list) {
            str.append(brand.getBrandId());
            str.append(separator);
            str.append(brand.getName());
            str.append(separator);
            str.append(brand.getReleaseYear());
            str.append(separator);
            str.append(brand.getManufacturerId());
            str.append(separator);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(5433);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
