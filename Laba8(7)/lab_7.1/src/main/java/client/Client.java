package client;

import dto.ManufacturerDTO;
import dto.BrandDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String separator = "#";

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public ManufacturerDTO authorFindById(Long id) {
        String query = "AuthorFindById" + separator + id.toString();
        out.println(query);
        String response;
        try {
            response = in.readLine();
            return new ManufacturerDTO(id, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BrandDTO bookFindByName(String name) {
        String query = "BookFindByName" + separator + name;
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            String[] fields = response.split(separator);
            Long id = Long.parseLong(fields[0]);
            Integer year = Integer.parseInt(fields[2]);
            Long authorId = Long.parseLong(fields[3]);
            return new BrandDTO(id, name, year, authorId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ManufacturerDTO authorFindByName(String name) {
        String query = "AuthorFindByName" + separator + name;
        out.println(query);
        try {
            Long response = Long.parseLong(in.readLine());
            return new ManufacturerDTO(response, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean brandUpdate(BrandDTO book) {
        String query = "BookUpdate" + separator + book.getBrandId() +
                separator + book.getName() + separator + book.getReleaseYear()
                + separator + book.getManufacturerId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean manufacturerUpdate(ManufacturerDTO author) {
        String query = "AuthorUpdate" + separator + author.getId() +
                separator + author.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bookInsert(BrandDTO book) {
        String query = "BookInsert" +
                separator + book.getName() + separator + book.getReleaseYear()
                + separator + book.getManufacturerId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authorInsert(ManufacturerDTO author) {
        String query = "AuthorInsert" +
                separator + author.getName();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean authorDelete(ManufacturerDTO author) {
        String query = "AuthorDelete" + separator + author.getId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bookDelete(BrandDTO book) {
        String query = "BookDelete" + separator + book.getBrandId();
        out.println(query);
        try {
            String response = in.readLine();
            return "true".equals(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ManufacturerDTO> authorAll() {
        String query = "AuthorAll";
        out.println(query);
        List<ManufacturerDTO> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 2) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                list.add(new ManufacturerDTO(id, name));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BrandDTO> bookAll() {
        String query = "BookAll";
        return getBookDTOS(query);
    }

    public List<BrandDTO> bookFindByAuthorId(Long authorId) {
        String query = "BookFindByAuthorId" + separator + authorId.toString();
        return getBookDTOS(query);
    }

    private List<BrandDTO> getBookDTOS(String query) {
        out.println(query);
        List<BrandDTO> list = new ArrayList<>();
        try {
            String response = in.readLine();
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 4) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                Integer year = Integer.parseInt(fields[i + 2]);
                Long authorId = Long.parseLong(fields[i + 3]);
                list.add(new BrandDTO(id,name, year, authorId));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}