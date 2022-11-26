package client;

import dto.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class App extends JFrame {
    private static JFrame frame;
    private static Client client;

    private static ManufacturerDTO currentcarmanufacturer = null;
    private static BrandDTO currentBrand = null;

    private static boolean editMode = false;
    private static boolean carManufacturerMode = true;

    private static JButton btnAddCarManufacturer = new JButton("Add Author");
    private static JButton btnAddBrand = new JButton("Add Book");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box brandPanel = Box.createVerticalBox();
    private static Box carManufacturerPanel = Box.createVerticalBox();

    private static JComboBox comboCarManufacturer = new JComboBox();
    private static JComboBox comboBrand = new JComboBox();

    private static JTextField textCarManufacturerName = new JTextField(30);
    private static JTextField textBrandName = new JTextField(30);
    private static JTextField textBrandManufacturerName = new JTextField(30);
    private static JTextField textBrandYear = new JTextField(30);

    private App() {
        super("Car dealership");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                client.disconnect();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddCarManufacturer);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddCarManufacturer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                carManufacturerMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                carManufacturerPanel.setVisible(true);
                brandPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddBrand);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddBrand.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                carManufacturerMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                carManufacturerPanel.setVisible(false);
                brandPanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                carManufacturerPanel.setVisible(false);
                brandPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Car manufacturer:"));
        comboPanel.add(comboCarManufacturer);
        comboPanel.add(Box.createVerticalStrut(20));
        comboCarManufacturer.addActionListener(e -> {
            String name = (String) comboCarManufacturer.getSelectedItem();
            currentcarmanufacturer = client.authorFindByName(name);
            carManufacturerMode = true;
            carManufacturerPanel.setVisible(true);
            brandPanel.setVisible(false);
            fillAuthorFields();
            pack();
        });
        comboPanel.add(new JLabel("Brand:"));
        comboPanel.add(comboBrand);
        comboPanel.add(Box.createVerticalStrut(20));
        comboBrand.addActionListener(e -> {
            String name = (String) comboBrand.getSelectedItem();
            currentBrand = client.bookFindByName(name);
            carManufacturerMode = false;
            carManufacturerPanel.setVisible(false);
            brandPanel.setVisible(true);
            fillBookFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        brandPanel.add(new JLabel("Name:"));
        brandPanel.add(textBrandName);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.add(new JLabel("Manufactur Name:"));
        brandPanel.add(textBrandManufacturerName);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.add(new JLabel("Release year:"));
        brandPanel.add(textBrandYear);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.setVisible(false);

        carManufacturerPanel.add(new JLabel("Name:"));
        carManufacturerPanel.add(textCarManufacturerName);
        carManufacturerPanel.add(Box.createVerticalStrut(20));
        carManufacturerPanel.setVisible(false);

        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                delete();
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                carManufacturerPanel.setVisible(false);
                brandPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(carManufacturerPanel);
        box.add(brandPanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddCarManufacturer.setMaximumSize(dimension);
        btnAddBrand.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddCarManufacturer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddBrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        brandPanel.setPreferredSize(panelDimension);
        carManufacturerPanel.setPreferredSize(panelDimension);

        comboCarManufacturer.setPreferredSize(dimension);
        comboBrand.setPreferredSize(dimension);

        textBrandManufacturerName.setPreferredSize(dimension);
        textBrandName.setPreferredSize(dimension);
        textBrandYear.setPreferredSize(dimension);
        textCarManufacturerName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (carManufacturerMode) {
                currentcarmanufacturer.setName(textCarManufacturerName.getText());
                if (client.manufacturerUpdate(currentcarmanufacturer)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            } else {
                currentBrand.setName(textBrandName.getText());
                currentBrand.setReleaseYear(Integer.parseInt(textBrandYear.getText()));

                ManufacturerDTO brand = client.authorFindByName(textBrandManufacturerName.getText());
                if (brand == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                currentBrand.setManufacturerId(brand.getId());

                if (!client.brandUpdate(currentBrand)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            }
        } else {
            if (carManufacturerMode) {
                ManufacturerDTO manufacturer = new ManufacturerDTO();
                manufacturer.setName(textCarManufacturerName.getText());
                if (!client.authorInsert(manufacturer)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                comboCarManufacturer.addItem(manufacturer.getName());
            } else {
                BrandDTO brand = new BrandDTO();
                brand.setName(textBrandName.getText());
                brand.setReleaseYear(Integer.parseInt(textBrandYear.getText()));

                ManufacturerDTO author = client.authorFindByName(textBrandManufacturerName.getText());
                if (author == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                brand.setManufacturerId(author.getId());

                if (!client.bookInsert(brand)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                comboBrand.addItem(brand.getName());
            }
        }
    }

    private static void delete() {
        if (editMode) {
            if (carManufacturerMode) {
                List<BrandDTO> list = client.bookFindByAuthorId(currentcarmanufacturer.getId());
                assert list != null;
                for (BrandDTO m : list) {
                    comboBrand.removeItem(m.getName());
                    client.bookDelete(m);
                }
                client.authorDelete(currentcarmanufacturer);
                comboCarManufacturer.removeItem(currentcarmanufacturer.getName());
            } else {
                client.bookDelete(currentBrand);
                comboBrand.removeItem(currentBrand.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboCarManufacturer.removeAllItems();
        comboBrand.removeAllItems();
        List<ManufacturerDTO> manufacturers = client.authorAll();
        List<BrandDTO> brands = client.bookAll();
        assert manufacturers != null;
        for (ManufacturerDTO author : manufacturers) {
            comboCarManufacturer.addItem(author.getName());
        }
        assert brands != null;
        for (BrandDTO book : brands) {
            comboBrand.addItem(book.getName());
        }
    }

    private static void clearFields() {
        textCarManufacturerName.setText("");
        textBrandName.setText("");
        textBrandManufacturerName.setText("");
        textBrandYear.setText("");
        currentcarmanufacturer = null;
        currentBrand = null;
    }

    private static void fillAuthorFields() {
        if (currentcarmanufacturer == null)
            return;
        textCarManufacturerName.setText(currentcarmanufacturer.getName());
    }

    private static void fillBookFields() {
        if (currentBrand == null)
            return;
        ManufacturerDTO manufacurer = client.authorFindById(currentBrand.getManufacturerId());
        textBrandName.setText(currentBrand.getName());
        assert manufacurer != null;
        textBrandManufacturerName.setText(manufacurer.getName());
        textBrandYear.setText(String.valueOf(currentBrand.getReleaseYear()));
    }

    public static void main(String[] args) throws IOException {
        client = new Client("localhost", 5433);
        JFrame myWindow = new App();
        myWindow.setVisible(true);
    }
}