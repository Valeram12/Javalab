import models.Manufacturers;
import models.Brands;
import models.Dealer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Application {
    private final static String path = "src/main/java/dealer.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Dealer dealer = DOMParser.parse(path);
        dealer.outputAllContents();

        System.out.println("\nAdd a new manufacturers mycompany\n");
        Manufacturers manufacturers = new Manufacturers("mycompany", dealer);
        dealer.addManufacturers(manufacturers);
        dealer.outputAllContents();

        System.out.println("\nAdd some new brands for this author\n");
        Brands brands1 = new Brands("MyBrand", 2022, dealer);
        dealer.addBrands(brands1, manufacturers.getManufacturersId());
        Brands brands2 = new Brands("MyBrand1", 2022, dealer);
        dealer.addBrands(brands2, manufacturers.getManufacturersId());
        dealer.outputAllContents();


        System.out.println("\nChange book's name\n");
        dealer.renameBrands(dealer.getBook("MyBrand"), "NewBrand");
        dealer.outputAllContents();

        System.out.println("\nChange manufacturers's name\n");
        dealer.renameManufacturers(dealer.getAuthor("mycompany"), "Newmycompany");
        dealer.outputAllContents();

        System.out.println("\nGet Newmycompany's books\n");
        List<Brands> brands = dealer.getAuthorsBooks("Newmycompany");
        for (Brands b : brands)
            b.outputBrands();

        System.out.println("\nGet all Newmycompany\n");
        Map<String, Manufacturers> dealers = dealer.getManufacturers();
        for (String id : dealers.keySet()){
            dealers.get(id).outputmanufacturers();
        }

        System.out.println("\nDelete a Newmycompany's book\n");
        dealer.removeBrands(brands1);
        brands = dealer.getAuthorsBooks("Newmycompany");
        for (Brands b : brands)
            b.outputBrands();

        System.out.println("\nDelete Newmycompany\n");
        dealer.removeManufacturers(manufacturers);
        dealer.outputAllContents();
        DOMParser.write(dealer, path);
    }
}