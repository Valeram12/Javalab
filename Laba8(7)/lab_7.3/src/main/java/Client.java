import dto.manufacturerDTO;
import dto.brandDTO;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private MessageConsumer consumer;
    private static final String separator = "#";

    public Client() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination queueOut = session.createQueue("fromClient");
            Destination queueIn = session.createQueue("toClient");

            producer = session.createProducer(queueOut);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            consumer = session.createConsumer(queueIn);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String handleMessage(String query, int timeout) throws JMSException {
        TextMessage message = session.createTextMessage(query);
        producer.send(message);
        Message mes = consumer.receive(timeout);
        if (mes == null) {
            return null;
        }

        if (mes instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) mes;
            return textMessage.getText();
        }

        return "";
    }

    public manufacturerDTO manufacturerFindById(Long id) {
        String query = "manufacturerFindById" + separator + id.toString();
        try {
            String response = handleMessage(query, 15000);
            return new manufacturerDTO(id, response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public brandDTO brandFindByName(String name) {
        String query = "brandFindByName" + separator + name;
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            Long id = Long.parseLong(fields[0]);
            Integer year = Integer.parseInt(fields[2]);
            Long authorId = Long.parseLong(fields[3]);
            return new brandDTO(id, name, year, authorId);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public manufacturerDTO manufacturerFindByName(String name) {
        String query = "manufacturerFindByName" + separator + name;
        try {
            String response = handleMessage(query, 15000);
            Long responseId = Long.parseLong(response);
            return new manufacturerDTO(responseId, name);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean brandUpdate(brandDTO brand) {
        String query = "brandUpdate" + separator + brand.getBrandId() +
                separator + brand.getName() + separator + brand.getReleaseYear()
                + separator + brand.getManufacturerId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean manufacturerUpdate(manufacturerDTO manufacturer) {
        String query = "manufacturerUpdate" + separator + manufacturer.getId() +
                separator + manufacturer.getName();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean brandInsert(brandDTO brand) {
        String query = "brandInsert" +
                separator + brand.getName() + separator + brand.getReleaseYear()
                + separator + brand.getManufacturerId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean manufacturerInsert(manufacturerDTO manufacturer) {
        String query = "manufacturerInsert" +
                separator + manufacturer.getName();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean manufacturerDelete(manufacturerDTO manufacturer) {
        String query = "manufacturerDelete" + separator + manufacturer.getId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean brandDelete(brandDTO brand) {
        String query = "brandDelete" + separator + brand.getBrandId();
        try {
            String response = handleMessage(query, 15000);
            return "true".equals(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<manufacturerDTO> manufacturerAll() {
        String query = "manufacturerAll";
        List<manufacturerDTO> list = new ArrayList<>();
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 2) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                list.add(new manufacturerDTO(id, name));
            }
            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<brandDTO> brandAll() {
        String query = "brandAll";
        return getbrandDTOS(query);
    }

    public List<brandDTO> brandFindBymanufacturerId(Long manufacturerId) {
        String query = "brandFindBymanufacturerId" + separator + manufacturerId.toString();
        return getbrandDTOS(query);
    }

    private List<brandDTO> getbrandDTOS(String query) {
        List<brandDTO> list = new ArrayList<>();
        try {
            String response = handleMessage(query, 15000);
            String[] fields = response.split(separator);
            for (int i = 0; i < fields.length; i += 4) {
                Long id = Long.parseLong(fields[i]);
                String name = fields[i + 1];
                Integer year = Integer.parseInt(fields[i + 2]);
                Long authorId = Long.parseLong(fields[i + 3]);
                list.add(new brandDTO(id,name, year, authorId));
            }
            return list;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanMessages() {
        try {
            Message message = consumer.receiveNoWait();
            while (message!=null) {
                message = consumer.receiveNoWait();
            }
        } catch (JMSException e) {
            e.printStackTrace();
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
}
