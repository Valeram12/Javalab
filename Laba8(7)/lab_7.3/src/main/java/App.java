import dto.manufacturerDTO;
import dto.brandDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class App extends JFrame {
    private static JFrame frame;

    private static Client client;

    private static manufacturerDTO currentmanufacturer = null;
    private static brandDTO currentbrand = null;

    private static boolean editMode = false;
    private static boolean manufacturerMode = true;

    private static final JButton btnAddmanufacturer = new JButton("Add manufacturer");
    private static final JButton btnAddbrand = new JButton("Add brand");
    private static final JButton btnEdit = new JButton("Edit Data");
    private static final JButton btnBack = new JButton("Back");
    private static final JButton btnSave = new JButton("Save");
    private static final JButton btnDelete = new JButton("Delete");

    private static final Box menuPanel = Box.createVerticalBox();
    private static final Box actionPanel = Box.createVerticalBox();
    private static final Box comboPanel = Box.createVerticalBox();
    private static final Box brandPanel = Box.createVerticalBox();
    private static final Box manufacturerPanel = Box.createVerticalBox();

    private static final JComboBox combomanufacturer = new JComboBox();
    private static final JComboBox combobrand = new JComboBox();

    private static final JTextField textmanufacturerName = new JTextField(30);
    private static final JTextField textbrandName = new JTextField(30);
    private static final JTextField textbrandmanufacturerName = new JTextField(30);
    private static final JTextField textbrandYear = new JTextField(30);

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

        client.cleanMessages();

        // Menu
        menuPanel.add(btnAddmanufacturer);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddmanufacturer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                manufacturerMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                manufacturerPanel.setVisible(true);
                brandPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddbrand);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddbrand.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                manufacturerMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                manufacturerPanel.setVisible(false);
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
                manufacturerPanel.setVisible(false);
                brandPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("manufacturer:"));
        comboPanel.add(combomanufacturer);
        comboPanel.add(Box.createVerticalStrut(20));
        combomanufacturer.addActionListener(e -> {
            String name = (String) combomanufacturer.getSelectedItem();
            currentmanufacturer = client.manufacturerFindByName(name);
            manufacturerMode = true;
            manufacturerPanel.setVisible(true);
            brandPanel.setVisible(false);
            fillAuthorFields();
            pack();
        });
        comboPanel.add(new JLabel("brand:"));
        comboPanel.add(combobrand);
        comboPanel.add(Box.createVerticalStrut(20));
        combobrand.addActionListener(e -> {
            String name = (String) combobrand.getSelectedItem();
            currentbrand = client.brandFindByName(name);
            manufacturerMode = false;
            manufacturerPanel.setVisible(false);
            brandPanel.setVisible(true);
            fillBookFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        brandPanel.add(new JLabel("Name:"));
        brandPanel.add(textbrandName);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.add(new JLabel("manufacturer Name:"));
        brandPanel.add(textbrandmanufacturerName);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.add(new JLabel("Release year:"));
        brandPanel.add(textbrandYear);
        brandPanel.add(Box.createVerticalStrut(20));
        brandPanel.setVisible(false);

        manufacturerPanel.add(new JLabel("Name:"));
        manufacturerPanel.add(textmanufacturerName);
        manufacturerPanel.add(Box.createVerticalStrut(20));
        manufacturerPanel.setVisible(false);

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
                manufacturerPanel.setVisible(false);
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
        box.add(manufacturerPanel);
        box.add(brandPanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddmanufacturer.setMaximumSize(dimension);
        btnAddbrand.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddmanufacturer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddbrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        brandPanel.setPreferredSize(panelDimension);
        manufacturerPanel.setPreferredSize(panelDimension);

        combomanufacturer.setPreferredSize(dimension);
        combobrand.setPreferredSize(dimension);

        textbrandmanufacturerName.setPreferredSize(dimension);
        textbrandName.setPreferredSize(dimension);
        textbrandYear.setPreferredSize(dimension);
        textmanufacturerName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (manufacturerMode) {
                currentmanufacturer.setName(textmanufacturerName.getText());
                if (client.manufacturerUpdate(currentmanufacturer)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            } else {
                currentbrand.setName(textbrandName.getText());
                currentbrand.setReleaseYear(Integer.parseInt(textbrandYear.getText()));

                manufacturerDTO author;
                author = client.manufacturerFindByName(textbrandmanufacturerName.getText());
                if (author == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                currentbrand.setManufacturerId(author.getId());

                if (!client.brandUpdate(currentbrand)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            }
        } else {
            if (manufacturerMode) {
                manufacturerDTO manufacturer = new manufacturerDTO();
                manufacturer.setName(textmanufacturerName.getText());
                if (!client.manufacturerInsert(manufacturer)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                combomanufacturer.addItem(manufacturer.getName());
            } else {
                brandDTO brand = new brandDTO();
                brand.setName(textbrandName.getText());
                brand.setReleaseYear(Integer.parseInt(textbrandYear.getText()));

                manufacturerDTO manufacturer = null;
                manufacturer = client.manufacturerFindByName(textbrandmanufacturerName.getText());
                if (manufacturer == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                brand.setManufacturerId(manufacturer.getId());

                if (!client.brandInsert(brand)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }
                combobrand.addItem(brand.getName());
            }
        }
    }

    private static void delete(){
        if (editMode) {
            if (manufacturerMode) {
                List<brandDTO> list = client.brandFindBymanufacturerId(currentmanufacturer.getId());
                assert list != null;
                for (brandDTO m : list) {
                    combobrand.removeItem(m.getName());
                    client.brandDelete(m);
                }
                client.manufacturerDelete(currentmanufacturer);
                combomanufacturer.removeItem(currentmanufacturer.getName());
            } else {
                client.brandDelete(currentbrand);
                combobrand.removeItem(currentbrand.getName());
            }
        }
    }

    private void fillComboBoxes() {
        combomanufacturer.removeAllItems();
        combobrand.removeAllItems();
        List<manufacturerDTO> manufacturers = client.manufacturerAll();
        List<brandDTO> brands = client.brandAll();
        assert manufacturers != null;
        for (manufacturerDTO author : manufacturers) {
            combomanufacturer.addItem(author.getName());
        }
        assert brands != null;
        for (brandDTO book : brands) {
            combobrand.addItem(book.getName());
        }
    }

    private static void clearFields() {
        textmanufacturerName.setText("");
        textbrandName.setText("");
        textbrandmanufacturerName.setText("");
        textbrandYear.setText("");
        currentmanufacturer = null;
        currentbrand = null;
    }

    private static void fillAuthorFields() {
        if (currentmanufacturer == null)
            return;
        textmanufacturerName.setText(currentmanufacturer.getName());
    }

    private static void fillBookFields(){
        if (currentbrand == null)
            return;
        manufacturerDTO manufacturer = client.manufacturerFindById(currentbrand.getManufacturerId());
        textbrandName.setText(currentbrand.getName());
        assert manufacturer != null;
        textbrandmanufacturerName.setText(manufacturer.getName());
        textbrandYear.setText(String.valueOf(currentbrand.getReleaseYear()));
    }

    public static void main(String[] args){
        client = new Client();
        JFrame myWindow = new App();
        myWindow.setVisible(true);
    }
}