import dao.brandDAO;
import dao.manufacturerDAO;
import dto.manufacturerDTO;
import dto.brandDTO;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.List;

public class Server {
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;

    private static final String SEPARATOR = "#";

    public void start() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queueTo = session.createQueue("toClient");
            Destination queueFrom = session.createQueue("fromClient");

            producer = session.createProducer(queueTo);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            consumer = session.createConsumer(queueFrom);

            while (processQuery()) {

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private boolean processQuery() {
        String response = "";
        String query = "";
        try {
            Message request = consumer.receive(500);
            if (request == null) {
                return true;
            }

            if (request instanceof TextMessage) {
                TextMessage message = (TextMessage) request;
                query = message.getText();
            } else { return true; }

            String [] fields = query.split(SEPARATOR);
            if (fields.length == 0) {
                return true;
            } else {
                String action = fields[0];
                manufacturerDTO manufacturerDTO;
                brandDTO brandDTO;

                switch (action) {
                    case "manufacturerFindById":
                        Long id = Long.parseLong(fields[1]);
                        manufacturerDTO = manufacturerDAO.findById(id);
                        response = manufacturerDTO.getName();
                        TextMessage message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandFindBymanufacturerId":
                        id = Long.parseLong(fields[1]);
                        List<brandDTO> list = brandDAO.findByAuthorId(id);
                        StringBuilder str = new StringBuilder();
                        assert list != null;
                        brandsToString(str, list);
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandFindByName":
                        String name = fields[1];
                        brandDTO = brandDAO.findByName(name);
                        assert brandDTO != null;
                        response = brandDTO.getBrandId() + SEPARATOR + brandDTO.getName() + SEPARATOR + brandDTO.getReleaseYear() + SEPARATOR + brandDTO.getManufacturerId();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "manufacturerFindByName":
                        name = fields[1];
                        manufacturerDTO = manufacturerDAO.findByName(name);
                        assert manufacturerDTO != null;
                        response = manufacturerDTO.getId() + "";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        Integer year = Integer.parseInt(fields[3]);
                        Long authorId = Long.parseLong(fields[4]);
                        brandDTO = new brandDTO(id, name, year, authorId);
                        response = brandDAO.update(brandDTO) ? "true" : "false";
                        System.out.println(response);
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "manufacturerUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        manufacturerDTO = new manufacturerDTO(id, name);
                        response = manufacturerDAO.update(manufacturerDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandInsert":
                        name = fields[1];
                        year = Integer.parseInt(fields[2]);
                        authorId = Long.parseLong(fields[3]);
                        brandDTO = new brandDTO((long) 0, name, year, authorId);
                        response = brandDAO.insert(brandDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "manufacturerInsert":
                        name = fields[1];
                        manufacturerDTO = new manufacturerDTO();
                        manufacturerDTO.setName(name);
                        response = manufacturerDAO.insert(manufacturerDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandDelete":
                        id = Long.parseLong(fields[1]);
                        brandDTO = new brandDTO();
                        brandDTO.setBrandId(id);
                        response = brandDAO.delete(brandDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "manufacturerDelete":
                        id = Long.parseLong(fields[1]);
                        manufacturerDTO = new manufacturerDTO();
                        manufacturerDTO.setId(id);
                        response = manufacturerDAO.delete(manufacturerDTO) ? "true" : "false";
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "brandAll":
                        List<brandDTO> booksList = brandDAO.findAll();
                        str = new StringBuilder();
                        assert booksList != null;
                        brandsToString(str, booksList);
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                    case "manufacturerAll":
                        List<manufacturerDTO> authorsList = manufacturerDAO.findAll();
                        str = new StringBuilder();
                        assert authorsList != null;
                        for (manufacturerDTO author : authorsList) {
                            str.append(author.getId());
                            str.append(SEPARATOR);
                            str.append(author.getName());
                            str.append(SEPARATOR);
                        }
                        response = str.toString();
                        message = session.createTextMessage(response);
                        producer.send(message);
                        break;
                }
            }
            return true;
        } catch (JMSException ex) {
            return false;
        }
    }

    private void brandsToString(StringBuilder str, List<brandDTO> list) {
        for (brandDTO book : list) {
            str.append(book.getBrandId());
            str.append(SEPARATOR);
            str.append(book.getName());
            str.append(SEPARATOR);
            str.append(book.getReleaseYear());
            str.append(SEPARATOR);
            str.append(book.getManufacturerId());
            str.append(SEPARATOR);
        }
    }

    public void disconnect() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}